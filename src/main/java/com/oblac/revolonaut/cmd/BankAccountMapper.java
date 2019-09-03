package com.oblac.revolonaut.cmd;

import com.oblac.revolonaut.cmd.model.BankAccount;
import com.oblac.revolonaut.cmd.model.Currency;
import com.oblac.revolonaut.db.ResultSetMapper;

/**
 * Trait with resultset mapper to bank account.
 * todo: don't hardcode column numbers
 */
public interface BankAccountMapper {

	public static ResultSetMapper.ResultSetFunction<BankAccount> mapToBankAccount =
		rs -> {
			final var id = rs.getLong(1);
			final var iban = rs.getString(2);
			final var owner = rs.getString(3);
			final var balance = rs.getBigDecimal(4);
			final var currency = Currency.of(rs.getInt(5));

			return new BankAccount(id, iban, owner, balance, currency);
		};
}
