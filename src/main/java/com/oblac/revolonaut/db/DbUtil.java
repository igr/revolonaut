package com.oblac.revolonaut.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Few DB-related utilities.
 */
public interface DbUtil {

	public static void rollback(final Connection connection) {
		try {
			connection.rollback();
		} catch (final SQLException ex) {
			throw new SqlRuntimeException(ex);
		}
	}

	public static void commit(final Connection connection) {
		try {
			connection.commit();
		} catch (final SQLException ex) {
			rollback(connection);
			throw new SqlRuntimeException(ex);
		}
	}

	public static void close(final Connection connection) {
		try {
			connection.close();
		} catch (final SQLException ex) {
			throw new SqlRuntimeException(ex);
		}
	}
}
