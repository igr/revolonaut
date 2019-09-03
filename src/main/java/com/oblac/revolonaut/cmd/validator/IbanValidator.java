package com.oblac.revolonaut.cmd.validator;

import com.oblac.revolonaut.exception.AppException;

import java.util.OptionalInt;

/**
 * Simple IBAN validator.
 * TODO add real IBAN validation rules.
 */
public interface IbanValidator {

	public default void requiredValidIban(final String iban) {
		if (iban == null) {
			throw AppException.invalidIban(iban);
		}
		if (iban.length() < 2) {
			throw AppException.invalidIban(iban);
		}
		final OptionalInt invalidChar =
			iban.chars()
			.filter(it -> !Character.isLetterOrDigit(it))
			.findAny();

		if (invalidChar.isPresent()) {
			throw AppException.invalidIban(iban);
		}
	}
}
