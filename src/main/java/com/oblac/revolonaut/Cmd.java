package com.oblac.revolonaut;

import com.oblac.revolonaut.db.ConnectionSupplier;
import com.oblac.revolonaut.db.DbUtil;

import java.sql.Connection;
import java.util.function.Function;

import static com.oblac.revolonaut.db.DbUtil.commit;
import static com.oblac.revolonaut.db.DbUtil.rollback;

/**
 * The main fabric of command execution.
 * Creates commands, injects dependencies and executes the code
 * in the transaction.
 */
public class Cmd<T> {

	/**
	 * Creates new command.
	 */
	public static <T> Cmd<T> of(final Class<T> t) {
		return new Cmd<>(t);
	}

	public static <T> Cmd<T> ofReadOnly(final Class<T> t) {
		return new ReadOnlyCmd<>(t);
	}

	private final Class<T> t;

	Cmd(final Class<T> t) {
		this.t = t;
	}

	/**
	 * Returns {@code true} if command is a read-only query command.
	 */
	public boolean isReadOnly() {
		return false;
	}

	/**
	 * Creates a command and wraps method execution in the
	 * transactional context.
	 */
	public <R> CmdRunnable<R> wrap(final Function<T, R> function) {
		return new CmdRunnable<>(() -> wrapInTx(function));
	}

	private <R> R wrapInTx(final Function<T, R> function) {
		final Connection connection = App.ctx().connectionSupplier().get();

		final T instance = App.ctx()
			.dependencyInjector()
			.createInstance(t, ConnectionSupplier.of(connection));

		try {
			final R result = function.apply(instance);

			commit(connection);

			return result;
		}
		catch (final Exception ex) {
			rollback(connection);

			throw ex;
		}
		finally {
			DbUtil.close(connection);
		}
	}

}
