package com.example.springboot;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageControllerTest {

	@LocalServerPort
	int port;

	@BeforeEach
	void setup() {
		RestAssured.port = this.port;
	}

	@Test
	void testMessageJSON() {
		Response response = given().when().get("/helloJSON/John").then().statusCode(200)
				.contentType(ContentType.JSON).extract().response();

		Assertions.assertEquals("Hello John", response.jsonPath().get("msg"));
		Assertions.assertNotNull(response.jsonPath().getLong("ts"));
	}

}
