package com.oblac.revolonaut.cmd;

import com.oblac.revolonaut.cmd.model.BankAccount;
import com.oblac.revolonaut.db.ConnectionSupplier;

import java.util.Optional;

/**
 * Finds {@link BankAccount} and locks it for update.
 *
 * We use {@code FOR UPDATE} on selecting, database will lock the row
 * (H2's default engine does that).
 */
public class BankAccountLockCmd {

	private final BankAccountFindCmd bankAccountFindCmd;

	public BankAccountLockCmd(final ConnectionSupplier connectionSupplier) {
		bankAccountFindCmd = new BankAccountFindCmd(connectionSupplier) {
			@Override
			protected String sql() {
				return super.sql() + " FOR UPDATE";
			}
		};
	}

	public Optional<BankAccount> findAndLock(final String ibanLookup) {
		return bankAccountFindCmd.find(ibanLookup);
	}

}
