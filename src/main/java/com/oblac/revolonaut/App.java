package com.oblac.revolonaut;

import com.oblac.revolonaut.db.ConnectionSupplier;
import com.oblac.revolonaut.db.ds.H2DataSource;

/**
 * This is the main App context. Contains instances of all
 * app-wide components, that are mostly used as side-effects.
 */
public class App {

	private static final App app = new App();

	public static App ctx() {
		return app;
	}

	// ---------------------------------------------------------------- dependencies

	private final DependencyInjector dependencyInjector;
	private final ConnectionSupplier connectionSupplier;

	public App() {
		this.dependencyInjector = new DependencyInjector();
		this.connectionSupplier = new H2DataSource();
	}

	public DependencyInjector dependencyInjector() {
		return dependencyInjector;
	}

	public ConnectionSupplier connectionSupplier() {
		return connectionSupplier;
	}
}
