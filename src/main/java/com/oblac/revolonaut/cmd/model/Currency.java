package com.oblac.revolonaut.cmd.model;

public enum Currency {
	USD(1), EUR(2), GBP(3);

	private final int id;

	Currency(final int id) {
		this.id = id;
	}

	public static Currency of(final int id) {
		for (final Currency currency : values()) {
			if (currency.id == id) {
				return currency;
			}
		}
		throw new IllegalArgumentException("Invalid currency ID: " + id);
	}

	public int id() {
		return id;
	}
}