package com.oblac.revolonaut.api.validator;

import com.oblac.revolonaut.cmd.validator.IbanValidator;
import com.oblac.revolonaut.exception.AppException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class IbanValidatorTest {

	@Test
	void testIban_null() {
		assertThrows(AppException.class, () -> {
			new IbanValidator(){}.requiredValidIban(null);
		});

	}

	@Test
	void testIban_short() {
		assertThrows(AppException.class, () -> {
			new IbanValidator(){}.requiredValidIban("x");
		});
	}

	@Test
	void testIban_invalid() {
		assertThrows(AppException.class, () -> {
			new IbanValidator(){}.requiredValidIban("RE*484");
		});
	}

	@Test
	void testIban() {
		new IbanValidator(){}.requiredValidIban("RE0101");
	}

}
