package com.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
			title = "Accounts Microservice",
			description = "Accounts Microservice built with sprinboot rest web services and Data JPA",
			contact = @Contact(
					name = "Dhanush Narayanan R",
					email = "abc@email.com",
					url = "https://www.linkedIn.com/in/dhanushnarayanan7"
					),
			version = "V1",
			license = @License(
					name = "Apache 2.0",
					url = "https://www.google.co.in"
					)
				),
		externalDocs = @ExternalDocumentation(
				description = "Accounts microservice rest api documentation",
				url = "https://www.google.co.in"
				)
		)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
