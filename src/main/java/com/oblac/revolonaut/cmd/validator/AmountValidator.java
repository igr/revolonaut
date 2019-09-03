package com.oblac.revolonaut.cmd.validator;

import com.oblac.revolonaut.exception.AppException;

import java.math.BigDecimal;

/**
 * Trait for validating amounts.
 */
public interface AmountValidator {

	public default void requirePositiveAmount(final BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw AppException.negativeAmount(amount);
		}
	}
}
