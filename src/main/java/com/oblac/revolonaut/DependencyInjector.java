package com.oblac.revolonaut;

import com.oblac.revolonaut.db.ConnectionSupplier;
import com.oblac.revolonaut.exception.AppException;
import lombok.Builder;
import lombok.Data;

import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * Simple injector of side-effects dependencies to commands.
 * Scans ctor parameters and match dependencies by type.
 * There are two type of dependencies: global and local (created on the usage point).
 */
@Singleton
public class DependencyInjector {

	@Data
	@Builder
	private static class CtorAndParameters<T> {
		private Constructor<T> ctor;
		private Object[] parameters;

		public boolean isAllParamsSet() {
			for (final Object parameter : parameters) {
				if (parameter == null) {
					return false;
				}
			}
			return true;
		}

		public T createInstance() {
			try {
				return ctor.newInstance(parameters);
			} catch (final Exception ex) {
				throw AppException.internal(ex);
			}
		}
	}

	/**
	 * Creates new instance by injecting all the dependencies as ctor arguments.
	 */
	@SuppressWarnings("unchecked")
	public <T> T createInstance(final Class<T> type, final Object... localDependencies) {
		return (T) Arrays.stream(type.getConstructors())
			.map((Constructor<?> ctor) -> prepareParameters(ctor, localDependencies))
			.filter(CtorAndParameters::isAllParamsSet)
			.findFirst()
			.orElseThrow(() -> AppException.internal("Injection of dependencies failed."))
			.createInstance();
	}

	@SuppressWarnings("unchecked")
	private CtorAndParameters<?> prepareParameters(final Constructor<?> ctor, final Object... localDependencies) {
		return CtorAndParameters.builder()
				.ctor((Constructor<Object>) ctor)
				.parameters(Arrays.stream(ctor.getParameterTypes())
					.map(it -> instanceOf(it, localDependencies))
					.toArray())
			.build();
	}

	@SuppressWarnings("unchecked")
	private <T> T instanceOf(final Class<T> type, final Object... localDependencies) {
		for (final Object localDependency : localDependencies) {
			if (type.isAssignableFrom(localDependency.getClass())) {
				return (T) localDependency;
			}
		}
		if (type.isAssignableFrom(ConnectionSupplier.class)) {
			return (T) App.ctx().connectionSupplier();
		}
		return null;
	}
}
