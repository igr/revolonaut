package com.oblac.revolonaut;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
	info = @Info(
		title = "Micronaut Revolut",
		version = "1.0",
		description = "SuprAPI",
		license = @License(name = "BSD", url = "https://opensource.org/licenses/BSD-2-Clause"),
		contact = @Contact(url = "https://igo.rs", name = "Igor Spasic", email = "igor.spasic@gmail.com")
	)
)
public class Application {
	public static void main(final String[] args) {
        Micronaut.run(Application.class);
    }
}