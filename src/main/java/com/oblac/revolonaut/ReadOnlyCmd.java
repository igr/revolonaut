package com.oblac.revolonaut;

/**
 * Read-only variant of {@link Cmd} which execution
 * could be optimized additionally.
 */
public class ReadOnlyCmd<T> extends Cmd<T> {

	ReadOnlyCmd(final Class<T> t) {
		super(t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadOnly() {
		return true;
	}
}