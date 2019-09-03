package com.oblac.revolonaut.db.ds;

import com.oblac.revolonaut.db.ConnectionSupplier;
import com.oblac.revolonaut.db.SqlRuntimeException;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * H2 dota source.
 *
 * TODO move setting to either external configuration file or ENV value.
 */
public class H2DataSource implements ConnectionSupplier {

	private static final Logger log = LoggerFactory.getLogger(H2DataSource.class);

	private final HikariDataSource ds;

	public H2DataSource() {
		ds = new HikariDataSource();
		ds.setJdbcUrl("jdbc:h2:mem:revolonaut;" +
			"INIT=" +
				"RUNSCRIPT FROM 'classpath:db/schema.sql'\\;" +
				"RUNSCRIPT FROM 'classpath:db/init.sql';" +
			"TRACE_LEVEL_FILE=4");

		ds.setUsername("sa");
		ds.setPassword("sa");

		// use explicit transactions management
		ds.setAutoCommit(false);

		log.info("The database is up and ready.");
	}

	/**
	 * Returns connection.
	 */
	@Override
	public Connection get() {
		try {
			return ds.getConnection();
		} catch (final SQLException e) {
			throw new SqlRuntimeException(e);
		}

	}
}
