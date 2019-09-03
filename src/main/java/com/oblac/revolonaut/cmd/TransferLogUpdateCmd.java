package com.oblac.revolonaut.cmd;

import com.oblac.revolonaut.cmd.model.TransferStatus;
import com.oblac.revolonaut.db.ConnectionSupplier;
import com.oblac.revolonaut.db.DbQuery;
import com.oblac.revolonaut.exception.AppException;

/**
 * Updates the status of given transfer log.
 */
public class TransferLogUpdateCmd {

	public static final String SQL =
		"UPDATE " +
			"transfer_log " +
			"SET status_id = ?, note = ? " +
			"WHERE " +
			"id = ?";

	private final ConnectionSupplier connectionSupplier;

	public TransferLogUpdateCmd(final ConnectionSupplier connectionSupplier) {
		this.connectionSupplier = connectionSupplier;
	}

	public void updateStatus(final Long logId, final TransferStatus newStatus, final String note) {
		DbQuery.of(connectionSupplier)
			.update(SQL, ps -> {
				ps.setLong(1, newStatus.id());
				ps.setString(2, note);
				ps.setLong(3, logId);
			})
			.ensureUpdate(AppException::updateTransferLogStatusFailed);
	}

}
