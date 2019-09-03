package com.oblac.revolonaut.cmd.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransferLog {
	private final Long id;
	private final Long fromAccountId;
	private final Long toAccountId;
	private final BigDecimal amount;
	private final Currency currency;
	private final LocalDate localDate;
	private final TransferStatus transferStatus;
	private final String note;
}
