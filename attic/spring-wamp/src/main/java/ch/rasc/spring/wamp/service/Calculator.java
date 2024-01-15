package ch.rasc.spring.wamp.service;

import org.springframework.stereotype.Service;

import ch.rasc.spring.wamp.WampMethod;

@Service(value = "calc")
public class Calculator {

	@WampMethod
	public int add(int a, int b) {
		return a + b;
	}

	@WampMethod
	public int sub(int a, int b) {
		return a - b;
	}

	@WampMethod
	public int mul(int a, int b) {
		return a * b;
	}

}
