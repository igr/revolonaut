package com.oblac.revolonaut.api.validator;

import com.oblac.revolonaut.cmd.validator.AmountValidator;
import com.oblac.revolonaut.exception.AppException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AmountValidatorTest {

	@Test
	void testPositiveAmount() {
		new AmountValidator(){}.requirePositiveAmount(new BigDecimal(1));
	}

	@Test
	void testPositiveAmount_zero() {
		new AmountValidator(){}.requirePositiveAmount(BigDecimal.ZERO);
	}

	@Test
	void testPositiveAmount_negative() {
		assertThrows(AppException.class, () -> {
			new AmountValidator(){}.requirePositiveAmount(new BigDecimal("-1"));
		});
	}
}
