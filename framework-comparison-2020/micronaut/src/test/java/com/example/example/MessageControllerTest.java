package com.example.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

@MicronautTest
public class MessageControllerTest {

	@Inject
	EmbeddedServer server;

	@BeforeEach
	void setup() {
		RestAssured.port = this.server.getPort();
	}

	@Test
	void testMessageJSON() {
		Response response = given().when().get("/helloJSON/John").then().statusCode(200)
				.contentType(ContentType.JSON).extract().response();

		Assertions.assertEquals("Hello John", response.jsonPath().get("msg"));
		Assertions.assertNotNull(response.jsonPath().getLong("ts"));
	}

}
