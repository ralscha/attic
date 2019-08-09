package wampclient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import io.crossbar.autobahn.wamp.Client;
import io.crossbar.autobahn.wamp.Session;
import io.crossbar.autobahn.wamp.types.CallResult;
import io.crossbar.autobahn.wamp.types.ExitInfo;
import io.crossbar.autobahn.wamp.types.SessionDetails;

public class AutobahnCaller {

	public void demonstrateCall(Session session, SessionDetails details) {
		// Call a remote procedure.
		CompletableFuture<CallResult> callFuture = session.call("initialload");
		callFuture.thenAccept(callResult -> System.out.println(callResult.results));
	}

	public void main() {
		Session session = new Session();
		session.addOnJoinListener(this::demonstrateCall);

		Client client = new Client(session,
				"wss://demo.rasc.ch/wamp2spring-demo-earthquake/wamp", null);
		CompletableFuture<ExitInfo> exitInfoCompletableFuture = client.connect();

		try {
			TimeUnit.MINUTES.sleep(10);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AutobahnCaller().main();

	}

}
