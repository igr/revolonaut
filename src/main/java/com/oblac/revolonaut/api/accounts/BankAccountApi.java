package com.oblac.revolonaut.api.accounts;

import com.oblac.revolonaut.Cmd;
import com.oblac.revolonaut.cmd.BankAccountFindCmd;
import com.oblac.revolonaut.cmd.BankAccountListCmd;
import com.oblac.revolonaut.cmd.model.BankAccount;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import java.util.List;
import java.util.Optional;

@Controller("/accounts")
public class BankAccountApi {

	@Get
	@Produces(MediaType.TEXT_JSON)
	public List<BankAccount> list() {
		return Cmd
			.ofReadOnly(BankAccountListCmd.class)
			.wrap(BankAccountListCmd::listAll)
			.run();
	}

	@Get("/{iban}")
	@Produces(MediaType.TEXT_JSON)
	public Optional<BankAccount> get(final String iban) {
		return Cmd
			.of(BankAccountFindCmd.class)
			.wrap(it -> it.find(iban))
			.run();
	}
}
