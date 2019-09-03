package com.oblac.revolonaut.db;

import java.sql.Connection;
import java.util.function.Supplier;

/**
 * Simple wrapper that supplies connections.
 * Needed for dependency injection matching.
 */
public interface ConnectionSupplier extends Supplier<Connection> {
	static ConnectionSupplier of(final Connection connection) {
		return () -> connection;
	}
}
