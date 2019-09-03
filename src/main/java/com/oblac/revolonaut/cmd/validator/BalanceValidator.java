package com.oblac.revolonaut.cmd.validator;

import com.oblac.revolonaut.exception.AppException;

import java.math.BigDecimal;

/**
 * Traint for validating balance.
 */
public interface BalanceValidator {

	public default void requirePositiveBalance(final BigDecimal balance, final String iban) {
		if (balance.compareTo(BigDecimal.ZERO) < 0) {
			throw AppException.notSufficientFunds(iban);
		}
	}
}
