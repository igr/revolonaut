package com.oblac.revolonaut.cmd;

import com.oblac.revolonaut.cmd.model.BankAccount;
import com.oblac.revolonaut.db.ConnectionSupplier;
import com.oblac.revolonaut.db.DbQuery;

import java.util.List;

/**
 * Returns a list of all bank accounts.
 * TODO add pagination
 */
public class BankAccountListCmd implements BankAccountMapper {

	private final static String SQL =
		"SELECT " +
			"id, iban, owner, balance, currency_id " +
			"FROM bank_account " +
			"ORDER BY id";

	private final ConnectionSupplier connectionSupplier;

	public BankAccountListCmd(final ConnectionSupplier connectionSupplier) {
		this.connectionSupplier = connectionSupplier;
	}

	public List<BankAccount> listAll() {
		return
			DbQuery.of(connectionSupplier)
				.exec(SQL)
				.list(mapToBankAccount);
	}

}
