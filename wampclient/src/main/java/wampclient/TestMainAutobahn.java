package wampclient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class TestMainAutobahn {

	private static final Logger LOGGER = Logger
			.getLogger(TestMainAutobahn.class.getName());
	private static TestCallerAutobahn caller = new TestCallerAutobahn(
			"ws://0.0.0.0:8080/ws", "realm");

	public static void main(String[] args) throws Exception {

		// open the connection with the Router with a timeout
		try {
			caller.connect();
		}
		catch (TimeoutException e) {
			LOGGER.info("timeout : " + e);
		}

		// do some business logic.....
		TimeUnit.SECONDS.sleep(1);

		// call a
		// RPC/////////////////////////////////////////////////////////////////////////////////////

		// Request message
		// {
		// "state": "NOT_READY",
		// "reason": "BREAK"
		// }

		String rpc = "MakeReady";
		RpcRequest request = new RpcRequest();
		request.args = new String[] { "BREAK", "NOT_READY" };

		try {
			caller.callSyncRPC(rpc, request);
		}
		catch (Exception e) {
			LOGGER.info("callRPC error: " + e);
		}

		try {
			caller.callASyncRPC(rpc, request);
		}
		catch (Exception e) {
			LOGGER.info("callRPC error: " + e);
		}

		// Subscribe/////////////////////////////////////////////////////////////////////////////////////
		String topic = "myTopic";
		try {
			caller.callSub(topic);
		}
		catch (Exception e) {
			LOGGER.info("callRPC error: " + e);
		}

		TimeUnit.MINUTES.sleep(10);

	}
}
