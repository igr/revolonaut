package com.oblac.revolonaut;

import java.util.function.Supplier;

/**
 * Runnable implementation of a {@link Cmd command},
 * delegated and ready to be executed.
 */
public class CmdRunnable<R> {

	private final Supplier<R> resultSupplier;

	CmdRunnable(final Supplier<R> resultSupplier) {
		this.resultSupplier = resultSupplier;
	}

	public R run() {
		return this.resultSupplier.get();
	}
}
