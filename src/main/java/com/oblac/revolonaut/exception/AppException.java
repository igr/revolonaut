package com.oblac.revolonaut.exception;

import com.oblac.revolonaut.cmd.model.Currency;

import java.math.BigDecimal;

/**
 * Base business exception. Also a factory class.
 * TODO if the number of exceptions grows, move them to specific implementations.
 */
public class AppException extends RuntimeException {
	public AppException(final Throwable cause) {
		super(cause);
	}

	public AppException(final String message) {
		super(message);
	}

	public AppException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// ---------------------------------------------------------------- ctors

	public static AppException internal(final String message) {
		return new AppException(message);
	}

	public static AppException internal(final Exception ex) {
		return new AppException(ex);
	}

	// ---------------------------------------------------------------- bank account

	public static AppException bankAccountNotFound(final String iban) {
		return new BankAccountException(iban, "Not found.");
	}

	public static AppException notSufficientFunds(final String iban) {
		return new BankAccountException(iban, "No sufficient funds");
	}

	public static AppException updateBankAccountBalanceFailed(final String iban) {
		return new BankAccountException(iban, "Failed to update account balance.");
	}

	public static AppException invalidIban(final String iban) {
		return new BankAccountException(iban, "Invalid IBAN");
	}

	// ---------------------------------------------------------------- currency

	public static AppException unmatchedCurrency(final Currency currency) {
		return new CurrencyException(currency, "Unmatched currencies");
	}

	// ---------------------------------------------------------------- amount

	public static AppException invalidAmount(final String amount) {
		return new AmountException(amount, "Not valid!");
	}
	public static AppException negativeAmount(final BigDecimal amount) {
		return new AmountException(amount, "Negative!");
	}

	// ---------------------------------------------------------------- transfer log

	public static AppException createTransferLogFailed() {
		return new AppException("Failed to create transfer log");
	}

	public static AppException updateTransferLogStatusFailed() {
		return new AppException("Failed to update status log");
	}


}
