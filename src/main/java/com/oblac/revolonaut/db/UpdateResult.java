package com.oblac.revolonaut.db;

import java.util.function.Supplier;

/**
 * Result of an SQL update.
 */
public class UpdateResult {

	private final int updatedRows;
	private final Long id;

	UpdateResult(final int updatedRows) {
		this.updatedRows = updatedRows;
		this.id = null;
	}

	UpdateResult(final int updatedRows, final Long id) {
		this.updatedRows = updatedRows;
		this.id = id;
	}

	public boolean isUpdated() {
		return updatedRows > 0;
	}

	public Long getGeneratedId() {
		return id;
	}

	public UpdateResult ensureUpdate(final Supplier<RuntimeException> exceptionSupplier) {
		if (!isUpdated()) {
			throw exceptionSupplier.get();
		}
		return this;
	}

}
