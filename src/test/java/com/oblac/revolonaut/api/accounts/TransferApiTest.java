package com.oblac.revolonaut.api.accounts;

import com.oblac.revolonaut.api.accounts.fixture.TransferApiInputs;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
class TransferApiTest {

	@Inject
	@Client("/")
	RxHttpClient client;

	@Test
	void transferTest_ok() {
		client.toBlocking().retrieve(
			HttpRequest.POST("/transfer", TransferApiInputs.tr0101_0202_12_34));

		final Map from = client.toBlocking().retrieve(
			HttpRequest.GET("/accounts/RE0101"), Map.class);

		assertEquals(88.15d, (Double) from.get("balance"), 0.001);

		final Map to = client.toBlocking().retrieve(
			HttpRequest.GET("/accounts/RE0202"), Map.class);

		assertEquals(111.51d, (Double) to.get("balance"), 0.001);
	}

	@Test
	void transferTest_notSufficientFunds() {
		try {
			client.toBlocking().retrieve(
				HttpRequest.POST("/transfer", TransferApiInputs.tr0101_0202_1000));
			fail();
		}
		catch (final HttpClientResponseException ex) {
			assertEquals(409, ex.getStatus().getCode());
		}
	}

	@Test
	void transferTest_differentCurrency() {
		try {
			client.toBlocking().retrieve(
				HttpRequest.POST("/transfer", TransferApiInputs.tr0101_0303_10));
			fail();
		}
		catch (final HttpClientResponseException ex) {
			assertEquals(409, ex.getStatus().getCode());
		}
	}

	@Test
	void transferTest_negativeValue() {
		try {
			client.toBlocking().retrieve(
				HttpRequest.POST("/transfer", TransferApiInputs.tr0101_0202_NEGATIVE));
			fail();
		}
		catch (final HttpClientResponseException ex) {
			assertEquals(409, ex.getStatus().getCode());
		}
	}

	@Test
	void transferTest_nanValue() {
		try {
			client.toBlocking().retrieve(
				HttpRequest.POST("/transfer", TransferApiInputs.tr0101_0202_NAN));
			fail();
		}
		catch (final HttpClientResponseException ex) {
			assertEquals(422, ex.getStatus().getCode());
		}
	}

}
