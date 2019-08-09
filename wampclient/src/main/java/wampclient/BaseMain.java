package wampclient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseMain {
	public static void main(String[] args) {
		BaseWampClient client = new BaseWampClient("ws://0.0.0.0:8080/ws", "realm");
		try {
			client.connect();
		}
		catch (Exception e) {
			// this could be a timeout exception or something other
			e.printStackTrace();
		}

		RpcRequest request = new RpcRequest();
		request.args = new String[] { "BREAK", "NOT_READY" };

		try {
			client.sendRequest(request).whenComplete((response, ex) -> {
				if (ex == null) {
					System.out.println("response: " + response);
				}
				else {
					// this could be a timeout exception or something else
					ex.printStackTrace();
				}
			});
		}
		catch (Exception e) {
			// this could be a timeout exception or something other
			e.printStackTrace();
		}

		try {
			ObjectMapper om = new ObjectMapper();
			client.subscribe("testtopic", pubSubData -> {
				PubSubMessage msg = om.convertValue(pubSubData.arguments().get(0),
						PubSubMessage.class);
				System.out.println(msg);
			}, throwable -> {
				throwable.printStackTrace();
			});
		}
		catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}

		try {
			TimeUnit.MINUTES.sleep(10);
		}
		catch (InterruptedException e) {
			// only test, ignore
		}

	}
}
