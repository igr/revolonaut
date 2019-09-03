package com.oblac.revolonaut.cmd.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccount {
	private final Long id;
	private final String iban;
	private final String owner;
	private final BigDecimal balance;
	private final Currency currency;
}
