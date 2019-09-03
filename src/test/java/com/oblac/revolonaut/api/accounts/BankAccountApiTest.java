package com.oblac.revolonaut.api.accounts;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
class BankAccountApiTest {

	@Inject
	@Client("/")
	RxHttpClient client;

	@SuppressWarnings("unchecked")
	@Test
	void testBankAccount_list() {
		final List<Map> result = client.toBlocking().retrieve(
			HttpRequest.GET("/accounts"), List.class);

		assertEquals(3, result.size());

		assertEquals("RE0101", result.get(0).get("iban"));
		assertEquals("RE0202", result.get(1).get("iban"));
	}

	@Test
	void testBankAccount_iban01() {
		final Map result = client.toBlocking().retrieve(
			HttpRequest.GET("/accounts/RE0101"), Map.class);

		assertEquals("RE0101", result.get("iban"));
	}

	@Test
	void testBankAccount_iban02() {
		final Map result = client.toBlocking().retrieve(
			HttpRequest.GET("/accounts/RE0202"), Map.class);

		assertEquals("RE0202", result.get("iban"));
	}

	@Test
	void testBankAccount_notFound() {
		try {
			client.toBlocking().exchange(HttpRequest.GET("/accounts/RE0000"));
			fail();
		}
		catch (final HttpClientResponseException ex) {
			assertEquals(404, ex.getResponse().status().getCode());
		}
	}

}
