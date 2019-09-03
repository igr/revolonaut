package com.oblac.revolonaut.api;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

/**
 * Just a dummy healthcheck.
 */
@Controller
public class HealthCheckApi {

	@Get("/healthcheck")
	@Produces(MediaType.TEXT_JSON)
	public String healthCheck() {
		return "OK";
	}

}
