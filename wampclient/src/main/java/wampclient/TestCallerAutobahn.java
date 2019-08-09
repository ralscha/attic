package wampclient;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.crossbar.autobahn.wamp.Client;
import io.crossbar.autobahn.wamp.Session;

public final class TestCallerAutobahn {

	private final ObjectMapper om;
	private final String url;
	private final String realm;
	private Client client;
	private final CompletableFuture<Session> sessionFuture;

	private static final Logger LOGGER = Logger.getLogger(JawampaCaller3.class.getName());
	private static final int TIMEOUT = 5;

	TestCallerAutobahn(String url, String realm) {
		this.url = url;
		this.realm = realm;
		this.om = new ObjectMapper();
		this.sessionFuture = new CompletableFuture<>();
	}

	private static final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1, r -> {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setName("failAfterThread");
				t.setDaemon(true);
				return t;
			});

	private static <T> CompletableFuture<T> failAfter(Duration duration) {
		final CompletableFuture<T> future = new CompletableFuture<>();
		scheduler.schedule(() -> {
			final TimeoutException ex = new TimeoutException("Timeout after " + duration);
			return future.completeExceptionally(ex);
		}, duration.toMillis(), TimeUnit.MILLISECONDS);
		return future;
	}

	private static <T> CompletableFuture<T> within(CompletableFuture<T> future,
			Duration duration) {
		final CompletableFuture<T> timeout = failAfter(duration);
		return future.applyToEither(timeout, Function.identity());
	}

	public void connect() throws Exception {

		Session session = new Session();
		session.addOnJoinListener((sess, det) -> {
			this.sessionFuture.complete(sess);
		});

		this.client = new Client(session, this.url, this.realm);
		this.client.connect();
	}

	public CompletableFuture<String> sendAsynchronous(String rpc,
			final RpcRequest request) throws Exception {

		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		this.sessionFuture.get(30, TimeUnit.SECONDS).call(rpc, request)
				.whenComplete((result, error) -> {
					if (error == null) {
						String response = this.om.convertValue(result.results.get(0),
								String.class);
						completableFuture.complete(response);
					}
					else {
						completableFuture.completeExceptionally(error);
					}
				});

		return within(completableFuture, Duration.ofSeconds(30));
	}

	//////////////////////////// CALL RPC
	public void callSyncRPC(String rpc, final RpcRequest request) throws Exception {
		LOGGER.info("Caller: Calling RPC: " + rpc);
		try {
			String response = this.sendAsynchronous(rpc, request).get(TIMEOUT,
					TimeUnit.SECONDS);
			LOGGER.info("RPC result: " + response);
		}
		catch (Exception e) {
			LOGGER.info("RPC error: " + e);
		}
	}

	public void callASyncRPC(String rpc, final RpcRequest request) throws Exception {
		LOGGER.info("Caller: Calling RPC: " + rpc);

		// Non-Blocking call
		CompletableFuture<String> newLocation = this.sendAsynchronous(rpc, request);
		newLocation.thenAccept(System.out::println);
		newLocation
				.thenAccept(callResult -> LOGGER.info("Got async result: " + callResult));
		newLocation.whenComplete((callResult, throwable) -> {
			if (throwable == null) {
				LOGGER.info("Got async result: " + callResult + " " + callResult);
			}
			else {
				LOGGER.info("Async ERROR - call failed: " + throwable.getMessage());
			}
		});
	}

	/////////////////////////// SUBSCRIBE

	// This should return a JSON object

	public void callSub(String topic) throws Exception {
		LOGGER.info("Subscribe to: " + topic);

		this.sessionFuture.get(30, TimeUnit.SECONDS).subscribe(topic,
				(payload, details) -> {
					try {
						PubSubMessage msg = this.om.convertValue(payload.get(0),
								PubSubMessage.class);
						System.out.println(msg);
					}
					catch (Throwable e) {
						LOGGER.log(Level.SEVERE, "handle pubsub message", e);
					}
				});
	}

}
