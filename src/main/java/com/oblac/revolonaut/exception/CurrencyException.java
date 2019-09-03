package com.oblac.revolonaut.exception;

import com.oblac.revolonaut.cmd.model.Currency;

public class CurrencyException extends AppException {
	public CurrencyException(final Currency currency, final String message) {
		super("Currency [" + currency + "] " + message);
	}
}