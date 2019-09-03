package com.oblac.revolonaut.cmd.validator;

import com.oblac.revolonaut.cmd.model.Currency;
import com.oblac.revolonaut.exception.AppException;

public interface CurrencyValidator {

	public default void requireEqualCurrency(final Currency currency1, final Currency currency2) {
		if (currency1 != currency2) {
			throw AppException.unmatchedCurrency(currency1);
		}
	}
}
