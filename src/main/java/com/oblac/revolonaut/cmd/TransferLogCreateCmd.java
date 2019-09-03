package com.oblac.revolonaut.cmd;

import com.oblac.revolonaut.cmd.model.BankAccount;
import com.oblac.revolonaut.cmd.model.Currency;
import com.oblac.revolonaut.db.ConnectionSupplier;
import com.oblac.revolonaut.db.DbQuery;
import com.oblac.revolonaut.exception.AppException;

import java.math.BigDecimal;

/**
 * Create new transfer log.
 */
public class TransferLogCreateCmd {

	public static final String SQL =
		"INSERT INTO " +
			"transfer_log(from_account_id, to_account_id, amount, currency_id, date, status_id, note) " +
			"VALUES " +
			"(?, ?, ?, ?, now(), 1, null)";

	private final ConnectionSupplier connectionSupplier;

	public TransferLogCreateCmd(final ConnectionSupplier connectionSupplier) {
		this.connectionSupplier = connectionSupplier;
	}

	public Long startTransfer(final BankAccount from, final BankAccount to, final BigDecimal amount, final Currency currency) {
		return DbQuery.of(connectionSupplier)
			.update(SQL, ps -> {
				ps.setLong(1, from.getId());
				ps.setLong(2, to.getId());
				ps.setBigDecimal(3, amount);
				ps.setInt(4, currency.id());
			})
			.ensureUpdate(AppException::createTransferLogFailed)
			.getGeneratedId();
	}
}
