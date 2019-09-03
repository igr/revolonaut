package com.oblac.revolonaut.db;

import java.sql.SQLException;

public class SqlRuntimeException extends RuntimeException {
	public SqlRuntimeException(final SQLException ex) {
		super(ex);
	}
}
