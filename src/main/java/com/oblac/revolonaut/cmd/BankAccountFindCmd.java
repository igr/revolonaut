package com.oblac.revolonaut.cmd;

import com.oblac.revolonaut.cmd.model.BankAccount;
import com.oblac.revolonaut.cmd.validator.IbanValidator;
import com.oblac.revolonaut.db.ConnectionSupplier;
import com.oblac.revolonaut.db.DbQuery;

import java.util.Optional;

/**
 * Finds single {@link BankAccount} by its IBAN code.
 */
public class BankAccountFindCmd implements BankAccountMapper, IbanValidator {

	private final static String SQL =
		"SELECT " +
			"id, iban, owner, balance, currency_id " +
			"FROM bank_account " +
			"WHERE " +
			"iban = ?";

	private final ConnectionSupplier connectionSupplier;

	public BankAccountFindCmd(final ConnectionSupplier connectionSupplier) {
		this.connectionSupplier = connectionSupplier;
	}

	/**
	 * Returns sql, allowing subclasses to change it.
	 */
	protected String sql() {
		return SQL;
	}

	/**
	 * Finds the {@code BankAccount} for given IBAN.
	 */
	public Optional<BankAccount> find(final String ibanLookup) {
		requiredValidIban(ibanLookup);

		return
			DbQuery.of(connectionSupplier)
			.exec(sql(), ps -> ps.setString(1, ibanLookup))
			.find(mapToBankAccount);
	}

}
