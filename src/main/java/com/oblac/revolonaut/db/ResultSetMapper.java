package com.oblac.revolonaut.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResultSetMapper {
	private final ResultSet resultSet;

	ResultSetMapper(final ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	@FunctionalInterface
	public interface ResultSetFunction<T> {
		public T parseResultSet(ResultSet resultSet) throws SQLException;
	}

	/**
	 * Maps a result set to single record.
	 */
	public <T> Optional<T> find(final ResultSetFunction<T> resultSetFunction) {
		try {
			if (resultSet.next()) {
				return Optional.of(resultSetFunction.parseResultSet(resultSet));
			}
			return Optional.empty();
		}
		catch (final SQLException ex) {
			throw new SqlRuntimeException(ex);
		}
	}

	/**
	 * Maps all the results to list of records.
	 */
	public <T> List<T> list(final ResultSetFunction<T> resultSetFunction) {
		final List<T> results = new ArrayList<>();
		try {
			while (resultSet.next()) {
				results.add(resultSetFunction.parseResultSet(resultSet));
			}
		}
		catch (final SQLException ex) {
			throw new SqlRuntimeException(ex);
		}

		return results;
	}
}
