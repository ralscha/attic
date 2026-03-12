package com.example.example;


import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

@Controller
public class MessageController {

	@Get(value = "/helloJSON/{name}", produces = MediaType.APPLICATION_JSON)
	public HelloMessage helloJSON(@PathVariable("name") String name) {
		return new HelloMessage("Hello " + name, System.currentTimeMillis());
	}
}
