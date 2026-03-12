package com.example.quarkus;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@QuarkusTest
public class MessageResourceTest {

	@Test
	void testMessageJSON() {
		Response response = given().when().get("/helloJSON/John").then().statusCode(200)
				.contentType(ContentType.JSON).extract().response();

		Assertions.assertEquals("Hello John", response.jsonPath().get("msg"));
		Assertions.assertNotNull(response.jsonPath().getLong("ts"));
	}

}