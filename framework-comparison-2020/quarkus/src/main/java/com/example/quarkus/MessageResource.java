package com.example.quarkus;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class MessageResource {

	@GET
	@Path("/helloJSON/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public HelloMessage helloJSON(@PathParam("name") String name) {
		return new HelloMessage("Hello " + name, System.currentTimeMillis());
	}
}