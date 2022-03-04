package com.example.validation;

import java.io.IOException;
import java.nio.file.Files;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidationApplicationTests {

	@Autowired
	private TestRestTemplate template;

	@ParameterizedTest
	@CsvSource({
			"missing-address.json,400,\"address\":\"must not be null\"",
			"valid-person.json,200,You sent a valid person",
			"zip-too-short.json,400,\"address.zip\":\"size must be between 5 and 6\""
	})
	void checkValidation(String file, String responseCode, String msg) throws IOException {
		String body = Files.readString(new ClassPathResource(file).getFile().toPath());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = template.exchange("/", HttpMethod.POST, entity, String.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(Integer.parseInt(responseCode));
		assertThat(response.getBody()).contains(msg);
	}

}
