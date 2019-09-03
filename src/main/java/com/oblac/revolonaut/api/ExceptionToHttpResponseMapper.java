package com.oblac.revolonaut.api;

import com.oblac.revolonaut.exception.AppException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;

import java.util.function.Supplier;

/**
 * Simple trait that converts exceptions to correct HTTPS error code.
 * Non-caught exceptions will be returned as 500.
 */
public interface ExceptionToHttpResponseMapper {

	/**
	 * Runs the given code and returns 200 if execution was successful.
	 * Otherwise, the exception is caught, mapped to status code and returned as an 4xx or 5xx error.
	 *
	 * TODO fine-tune exceptions
	 */
	public default <R> HttpResponse runAndMapExToStatus(final Supplier<R> supplier) {
		try {
			return HttpResponse.ok(supplier.get());
		} catch (final AppException ex) {
			return HttpResponse.status(HttpStatus.CONFLICT, ex.getMessage());
		} catch (final NumberFormatException ex) {
			return HttpResponse.status(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
		}
	}

}
