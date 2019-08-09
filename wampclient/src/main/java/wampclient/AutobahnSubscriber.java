package wampclient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import io.crossbar.autobahn.wamp.Client;
import io.crossbar.autobahn.wamp.Session;
import io.crossbar.autobahn.wamp.types.EventDetails;
import io.crossbar.autobahn.wamp.types.ExitInfo;
import io.crossbar.autobahn.wamp.types.SessionDetails;
import io.crossbar.autobahn.wamp.types.Subscription;

public class AutobahnSubscriber {

	public void demonstrateSubscribe(Session session,
			@SuppressWarnings("unused") SessionDetails details) {
		CompletableFuture<Subscription> subFuture = session.subscribe("location",
				this::onEvent);
		subFuture.whenComplete((subscription, throwable) -> {
			if (throwable == null) {
				System.out.println("Subscribed to topic '" + subscription.topic + "'");
			}
			else {
				throwable.printStackTrace();
			}
		});
	}

	@SuppressWarnings("unused")
	private void onEvent(List<Object> args, Map<String, Object> kwargs,
			EventDetails details) {
		System.out.println(kwargs.get("iss_position"));
	}

	public void main() {
		Session session = new Session();
		session.addOnJoinListener(this::demonstrateSubscribe);

		Client client = new Client(session,
				"wss://demo.rasc.ch/wamp2spring-demo-iss/wamp", null);
		CompletableFuture<ExitInfo> exitInfoCompletableFuture = client.connect();

		try {
			TimeUnit.MINUTES.sleep(10);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AutobahnSubscriber().main();

	}

}
