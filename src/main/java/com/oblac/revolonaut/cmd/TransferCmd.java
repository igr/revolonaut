package com.oblac.revolonaut.cmd;

import com.oblac.revolonaut.cmd.model.BankAccount;
import com.oblac.revolonaut.cmd.model.TransferStatus;
import com.oblac.revolonaut.cmd.validator.AmountValidator;
import com.oblac.revolonaut.cmd.validator.BalanceValidator;
import com.oblac.revolonaut.cmd.validator.CurrencyValidator;
import com.oblac.revolonaut.db.ConnectionSupplier;
import com.oblac.revolonaut.db.DbQuery;
import com.oblac.revolonaut.exception.AppException;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

import static com.oblac.revolonaut.exception.AppException.updateBankAccountBalanceFailed;

/**
 * Transfer.
 */
public class TransferCmd implements
	CurrencyValidator, AmountValidator, BalanceValidator {

	@Data
	@Builder
	public static class Input {
		private final String ibanFrom;
		private final String ibanTo;
		private final String amount;
	}

	/**
	 * Update SQL. Very important is the check for existing balance,
	 * as this ensure that the balance has not been changed during the
	 * transaction (depends on isolation level of the lock and tx).
	 */
	private final static String SQL =
		"UPDATE bank_account SET balance=? where id=? and balance=?";

	private final ConnectionSupplier connectionSupplier;
	private final BankAccountLockCmd bankAccountLockCmd;
	private final TransferLogCreateCmd transferLogCreateCmd;
	private final TransferLogUpdateCmd transferLogUpdateCmd;

	public TransferCmd(final ConnectionSupplier connectionSupplier) {
		this.connectionSupplier = connectionSupplier;
		this.bankAccountLockCmd = new BankAccountLockCmd(connectionSupplier);
		this.transferLogCreateCmd = new TransferLogCreateCmd(connectionSupplier);
		this.transferLogUpdateCmd = new TransferLogUpdateCmd(connectionSupplier);
	}

	/**
	 * Transfer amount from one bank account to another.
	 */
	public Long transfer(final Input input) {
		Objects.requireNonNull(input);

		final var ibanFrom = input.getIbanFrom();
		final var ibanTo = input.getIbanTo();
		final var amount = input.getAmount();

		// find and lock both accounts

		final var fromBankAccount = bankAccountLockCmd
			.findAndLock(ibanFrom)
			.orElseThrow(() -> AppException.bankAccountNotFound(ibanFrom));

		final var toBankAccount = bankAccountLockCmd
			.findAndLock(ibanTo)
			.orElseThrow(() -> AppException.bankAccountNotFound(ibanTo));

		final var currency = fromBankAccount.getCurrency();

		requireEqualCurrency(currency, toBankAccount.getCurrency());

		final var amountDecimal = new BigDecimal(amount);

		requirePositiveAmount(amountDecimal);

		// start with the transfer

		final var logId = transferLogCreateCmd.startTransfer(
			fromBankAccount, toBankAccount, amountDecimal,currency);

		final var balanceFrom = fromBankAccount.getBalance();
		final var newBalanceFrom = balanceFrom.subtract(amountDecimal);

		requirePositiveBalance(newBalanceFrom, ibanFrom);

		final var newBalanceTo = toBankAccount.getBalance().add(amountDecimal);

		// update both transfers

		updateBalance(ibanFrom, fromBankAccount, newBalanceFrom);

		updateBalance(ibanTo, toBankAccount, newBalanceTo);

		// the end

		transferLogUpdateCmd.updateStatus(logId, TransferStatus.SUCCEED, "Yeay!");

		return logId;
	}

	/**
	 * Updates the balance. Two things are here in place: the bank account row is locked
	 * and the update checks the existing balance.
	 */
	private void updateBalance(final String iban, final BankAccount bankAccount, final BigDecimal newBalance) {
		DbQuery.of(connectionSupplier)
			.update(SQL, ps -> {
				ps.setBigDecimal(1, newBalance);
				ps.setLong(2, bankAccount.getId());
				ps.setBigDecimal(3, bankAccount.getBalance());
			})
			.ensureUpdate(() -> updateBankAccountBalanceFailed(iban));
	}
}
