package wampclient;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import rx.Subscription;
import rx.functions.Action1;
import ws.wamp.jawampa.ApplicationError;
import ws.wamp.jawampa.Request;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampRouter;
import ws.wamp.jawampa.WampRouterBuilder;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;
import ws.wamp.jawampa.transport.netty.SimpleWampWebsocketListener;

public class WebResponder2 {

	private final ObjectMapper om;
	private final String url;
	private final String realm;
	private final CompletableFuture<WampClient> clientFuture;

	private static final Logger LOGGER = Logger.getLogger(WebResponder.class.getName());
	public static final int TIMEOUT = 5;

	public WebResponder2(String url, String realm) {
		this.url = url;
		this.realm = realm;
		this.om = new ObjectMapper();
		this.clientFuture = new CompletableFuture<>();
	}

	// private static final ScheduledExecutorService scheduler = Executors
	// .newScheduledThreadPool(1, r -> {
	// Thread t = Executors.defaultThreadFactory().newThread(r);
	// t.setName("failAfterThread");
	// t.setDaemon(true);
	// return t;
	// });
	//
	// private static <T> CompletableFuture<T> failAfter(Duration duration) {
	// final CompletableFuture<T> future = new CompletableFuture<>();
	// scheduler.schedule(() -> {
	// final TimeoutException ex = new TimeoutException("Timeout after " + duration);
	// return future.completeExceptionally(ex);
	// }, duration.toMillis(), TimeUnit.MILLISECONDS);
	// return future;
	// }
	//
	// private static <T> CompletableFuture<T> within(CompletableFuture<T> future,
	// Duration duration) {
	// final CompletableFuture<T> timeout = failAfter(duration);
	// return future.applyToEither(timeout, Function.identity());
	// }
	//
	// public CompletableFuture<String> sendAsynchronous(String rpc,
	// final Map<String, Object> request) throws Exception {
	//
	// CompletableFuture<String> completableFuture = new CompletableFuture<>();
	// this.clientFuture.get(TIMEOUT, TimeUnit.SECONDS).call(rpc, request)
	// .subscribe(result -> {
	// String response = this.om.convertValue(result.arguments().get(0),
	// String.class);
	// completableFuture.complete(response);
	// }, error -> completableFuture.completeExceptionally(error));
	//
	// return within(completableFuture, Duration.ofSeconds(TIMEOUT));
	// }

	public void startRouter() {
		// Start Router
		WampRouterBuilder routerBuilder = new WampRouterBuilder();
		WampRouter router;
		try {
			routerBuilder.addRealm(this.realm);
			router = routerBuilder.build();
		}
		catch (ApplicationError e1) {
			e1.printStackTrace();
			return;
		}

		URI serverUri = URI.create(this.url);
		SimpleWampWebsocketListener server;

		try {
			server = new SimpleWampWebsocketListener(router, serverUri, null);
			server.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void startClient() throws Exception {
		WampClientBuilder builder = new WampClientBuilder();

		builder.withConnectorProvider(new NettyWampClientConnectorProvider())
				.withUri(this.url).withRealm(this.realm).withInfiniteReconnects()
				.withNrReconnects(10).withReconnectInterval(1, TimeUnit.SECONDS);
		WampClient cl = builder.build();

		cl.statusChanged().subscribe(newState -> {
			if (newState instanceof WampClient.ConnectedState) {
				LOGGER.info(">>>>>Client connected with the FMB Router<<<<<");
				this.clientFuture.complete(cl);

			}
		}, throwable -> this.clientFuture.completeExceptionally(throwable));
		cl.open();
	}

	public void publishEvent(String topic, String event) {
		try {
			this.clientFuture.get().publish(topic, event);
		}
		catch (InterruptedException | ExecutionException e) {
			LOGGER.severe(">>>>>Publish Event<<<<< " + e.getMessage());
		}
	}

	private final Map<String, Subscription> subscriptions = new HashMap<>();

	public void registerRPC(String rpc, Action1<? super Request> onNext) {
		try {
			this.subscriptions.put(rpc,
					this.clientFuture.get().registerProcedure(rpc).subscribe(onNext));
		}
		catch (InterruptedException | ExecutionException e) {
			LOGGER.severe(">>>>>Register RPC<<<<< " + e.getMessage());
		}
	}

	public void unregisterRPC(String rpc) {
		Subscription subscription = this.subscriptions.remove(rpc);
		if (subscription != null) {
			subscription.unsubscribe();
		}
	}

	public static void main(String[] args) throws Exception {

		WebResponder2 app = new WebResponder2("ws://0.0.0.0:8888/ws", "realm");

		// start Router
		app.startRouter();

		// Start Client
		app.startClient();

		// Publish Event1
		app.publishEvent("event1", String.valueOf(Instant.now().getEpochSecond()));

		// Register Callee
		app.registerRPC("method1", request -> request.reply(""));

	}
}
