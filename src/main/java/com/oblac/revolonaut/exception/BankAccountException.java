package com.oblac.revolonaut.exception;

public class BankAccountException extends AppException {
	public BankAccountException(final String iban, final String message) {
		super("Bank Account [" + iban + "] " + message);
	}
}
