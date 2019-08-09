package wampclient;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import rx.Observable;
import rx.Subscription;
import ws.wamp.jawampa.PubSubData;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.internal.ArgArrayBuilder;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public final class TestCaller {

	private final ObjectMapper om;
	private final String url;
	private final String realm;
	private final CompletableFuture<WampClient> clientFuture;
	private final Map<String, Subscription> eventSubscription = new ConcurrentHashMap<>();

	private static final Logger LOGGER = Logger.getLogger(JawampaCaller3.class.getName());
	private static final int TIMEOUT = 5;

	TestCaller(String url, String realm) {
		this.url = url;
		this.realm = realm;
		this.om = new ObjectMapper();
		this.clientFuture = new CompletableFuture<>();
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
		WampClientBuilder builder = new WampClientBuilder();

		builder.withConnectorProvider(new NettyWampClientConnectorProvider())
				.withUri(this.url).withRealm(this.realm).withInfiniteReconnects()
				.withNrReconnects(10).withReconnectInterval(1, TimeUnit.SECONDS);
		WampClient cl = builder.build();

		cl.statusChanged().subscribe(newState -> {
			if (newState instanceof WampClient.ConnectedState) {
				this.clientFuture.complete(cl);

				for (String topic : this.eventSubscription.keySet()) {
					try {
						this.eventSubscription.remove(topic).unsubscribe();
						Observable<PubSubData> obSub = this.clientFuture
								.get(30, TimeUnit.SECONDS).makeSubscription(topic);
						this.eventSubscription.put(topic, subscribe(obSub));
					}
					catch (InterruptedException | ExecutionException
							| TimeoutException e) {
						LOGGER.log(Level.SEVERE, "re-subscribe", e);
					}
				}
			}
		}, throwable -> this.clientFuture.completeExceptionally(throwable));
		cl.open();
	}

	public CompletableFuture<String> sendAsynchronous(String rpc,
			final RpcRequest request) throws Exception {

		CompletableFuture<String> completableFuture = new CompletableFuture<>();

		ArrayNode an = ArgArrayBuilder.buildArgumentsArray(this.om, request.args);
		ObjectNode on = this.om.createObjectNode();
		on.setAll(request.kwargs);

		this.clientFuture.get(30, TimeUnit.SECONDS).call(rpc, an, on)
				.subscribe(result -> {
					String response = this.om.convertValue(result.arguments().get(0),
							String.class);
					completableFuture.complete(response);
				}, error -> completableFuture.completeExceptionally(error));

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

		Subscription sub = this.eventSubscription.remove(topic);
		if (sub != null) {
			sub.unsubscribe();
		}

		Observable<PubSubData> obSub = this.clientFuture.get(30, TimeUnit.SECONDS)
				.makeSubscription(topic);
		sub = subscribe(obSub);

		this.eventSubscription.put(topic, sub);
	}

	private Subscription subscribe(Observable<PubSubData> obSub) {
		return obSub.subscribe(s -> {
			try {
				PubSubMessage msg = this.om.convertValue(s.arguments().get(0),
						PubSubMessage.class);
				System.out.println(msg);
			}
			catch (Throwable e) {
				LOGGER.log(Level.SEVERE, "handle pubsub message", e);
			}
		}, e -> {
			LOGGER.log(Level.SEVERE, "subscribe", e);
		});
	}

	public void unsubscribe(String topic) {
		Subscription sub = this.eventSubscription.remove(topic);
		if (sub != null) {
			sub.unsubscribe();
		}
	}

	public void close()
			throws InterruptedException, ExecutionException, TimeoutException {
		this.clientFuture.get(30, TimeUnit.SECONDS).close().toBlocking().last();
	}

}
