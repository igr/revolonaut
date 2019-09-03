package com.oblac.revolonaut.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Rather simple executor of SQL queries.
 */
public class DbQuery {
	private final Connection connection;

	/**
	 * Creates new {@code DbQuery} instance.
	 */
	public static DbQuery of(final ConnectionSupplier connectionSupplier) {
		return new DbQuery(connectionSupplier.get());
	}

	public DbQuery(final Connection connection) {
		this.connection = connection;
	}

	@FunctionalInterface
	public interface PreparedStatementConsumer {
		public void accept(PreparedStatement preparedStatement) throws SQLException;
	}

	/**
	 * Executes SQL without any parameters.
	 */
	public ResultSetMapper exec(final String sql) {
		return exec(sql, ps ->{});
	}

	/**
	 * Executes SQL and set the parameters.
	 */
	public ResultSetMapper exec(final String sql, final PreparedStatementConsumer ps) {
		Objects.requireNonNull(sql);

		try {
			final PreparedStatement preparedStatement = connection.prepareStatement(sql);

			ps.accept(preparedStatement);

			final ResultSet resultSet = preparedStatement.executeQuery();

			return new ResultSetMapper(resultSet);
		}
		catch (final SQLException ex) {
			throw new SqlRuntimeException(ex);
		}
	}

	/**
	 * Executes the update.
	 */
	public UpdateResult update(final String sql, final PreparedStatementConsumer ps) {
		Objects.requireNonNull(sql);
		try {
			final PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.accept(preparedStatement);

			final int updateCount = preparedStatement.executeUpdate();

			final ResultSet keysResultSet = preparedStatement.getGeneratedKeys();

			if (keysResultSet.next()) {
				final Long newKey = keysResultSet.getLong(1);

				return new UpdateResult(updateCount, newKey);
			}
			else {
				return new UpdateResult(updateCount);
			}
		}
		catch (final SQLException ex) {
			throw new SqlRuntimeException(ex);
		}
	}

}
