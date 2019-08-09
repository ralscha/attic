package wampclient;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;

import rx.Subscription;
import rx.functions.Action1;
import ws.wamp.jawampa.PubSubData;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class BaseWampClient {

	private final ObjectMapper om;
	private final CompletableFuture<WampClient> clientFuture;
	private Subscription subscription;
	private final String url;
	private final String realm;

	private static final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1, r -> {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setName("failAfterThread");
				t.setDaemon(true);
				return t;
			});

	public BaseWampClient(String url, String realm) {
		this.url = url;
		this.realm = realm;
		this.om = new ObjectMapper();
		this.clientFuture = new CompletableFuture<>();
	}

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
				.withReconnectInterval(1, TimeUnit.SECONDS);
		WampClient cl = builder.build();

		cl.statusChanged().subscribe(newState -> {
			if (newState instanceof WampClient.ConnectedState) {
				this.clientFuture.complete(cl);
			}
		}, throwable -> this.clientFuture.completeExceptionally(throwable));
		cl.open();
	}

	public CompletableFuture<String> sendRequest(final RpcRequest request)
			throws Exception {
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		this.clientFuture.get(30, TimeUnit.SECONDS).call("rpc", request)
				.subscribe(result -> {
					String response = this.om.convertValue(result.arguments().get(0),
							String.class);
					completableFuture.complete(response);
				}, error -> completableFuture.completeExceptionally(error));

		return within(completableFuture, Duration.ofSeconds(30));
	}

	public void subscribe(String topic, Action1<PubSubData> onSuccess,
			Action1<Throwable> onError)
			throws InterruptedException, ExecutionException, TimeoutException {
		if (this.subscription != null) {
			this.subscription.unsubscribe();
		}
		this.subscription = this.clientFuture.get(30, TimeUnit.SECONDS)
				.makeSubscription(topic).subscribe(onSuccess, onError);
	}

}
