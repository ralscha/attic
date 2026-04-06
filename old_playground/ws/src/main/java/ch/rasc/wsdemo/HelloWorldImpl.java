package ch.rasc.wsdemo;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.stereotype.Service;

@WebService
@Service
public class HelloWorldImpl {

	public String sayHi(String text) {
		System.out.println("sayHi called");
		return "A message from the server: Hello " + text;
	}

	@WebMethod(exclude = true)
	public String notVisible(String dummy) {
		return dummy;
	}
}