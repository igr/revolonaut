package com.oblac.revolonaut.api.validator;

import com.oblac.revolonaut.cmd.validator.BalanceValidator;
import com.oblac.revolonaut.exception.AppException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BalanceValidatorTest {

	@Test
	void testPositiveBalance() {
		new BalanceValidator(){}.requirePositiveBalance(new BigDecimal(1), "testiban");
	}

	@Test
	void testPositiveBalance_zero() {
		new BalanceValidator(){}.requirePositiveBalance(new BigDecimal(0), "testiban");
	}

	@Test
	void testPositiveBalance_negative() {
		assertThrows(AppException.class, () -> {
			new BalanceValidator(){}.requirePositiveBalance(new BigDecimal("-1"), "testiban");
		});
	}


}
