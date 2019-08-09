package ch.rasc.wampdemo.wampdemo;

import java.time.Instant;

import org.springframework.stereotype.Service;

import ch.rasc.wamp2spring.annotation.WampProcedure;

@Service
public class RpcHandler {

	@WampProcedure
	public String method1() {
		return "method1:" + Instant.now();
	}

	@WampProcedure
	public String method2() {
		return "method2:" + Instant.now();
	}

	@WampProcedure
	public String method3() {
		return "method3:" + Instant.now();
	}

	@WampProcedure
	public String method4() {
		return "method4:" + Instant.now();
	}

	@WampProcedure
	public String method5() {
		return "method5:" + Instant.now();
	}

	@WampProcedure
	public String method6() {
		return "method6:" + Instant.now();
	}
}
