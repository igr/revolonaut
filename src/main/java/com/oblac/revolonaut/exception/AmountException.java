package com.oblac.revolonaut.exception;

import java.math.BigDecimal;

public class AmountException extends AppException {

	public AmountException(final BigDecimal amount, final String message) {
		super("Amount [" + amount + "] " + message);
	}

	public AmountException(final String amount, final String message) {
		super("Amount [" + amount + "] " + message);
	}
}
