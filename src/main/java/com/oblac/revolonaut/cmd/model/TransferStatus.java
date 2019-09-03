package com.oblac.revolonaut.cmd.model;

public enum TransferStatus {
	STARTED(1), FAILED(0), SUCCEED(200);

	private final int id;

	TransferStatus(final int id) {
		this.id = id;
	}

	public static TransferStatus of(final int id) {
		for (final TransferStatus transferStatus : values()) {
			if (transferStatus.id == id) {
				return transferStatus;
			}
		}
		throw new IllegalArgumentException("Invalid transfer status ID: " + id);
	}

	public int id() {
		return id;
	}

}
