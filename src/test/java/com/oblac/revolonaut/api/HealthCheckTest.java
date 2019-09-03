package com.oblac.revolonaut.api;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class HealthCheckTest {

	@Inject
	@Client("/")
	RxHttpClient client;

	@Test
	void testHealhtCheck() {
		final String body = client.toBlocking().retrieve(
			HttpRequest.GET("/healthcheck"));

		assertEquals("OK", body);
	}

}
