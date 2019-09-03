package com.oblac.revolonaut.api;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
class SwaggerEndpointTest {

	@Inject
	@Client("/")
	RxHttpClient client;

	@Test
	void testYml() {
		final String body = client.toBlocking().retrieve(
			HttpRequest.GET("/swagger/micronaut-revolut-1.0.yml"));

		assertNotNull(body);
	}

}
