package wampclient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class JawampaCaller3 {

	private final ObjectMapper om;
	private WampClient client;
	private final TypeReference<List<Location>> typeRef = new TypeReference<List<Location>>() {
	};
	private final ScheduledThreadPoolExecutor delayer;

	JawampaCaller3() {
		this.om = new ObjectMapper();
		this.delayer = new ScheduledThreadPoolExecutor(2);
	}

	public CompletableFuture<Void> connect() throws Exception {
		CompletableFuture<Void> completableFuture = new CompletableFuture<>();

		WampClientBuilder builder = new WampClientBuilder();

		builder.withConnectorProvider(new NettyWampClientConnectorProvider())
				.withUri("ws://0.0.0.0:8080/ws").withRealm("realm")
				.withInfiniteReconnects().withReconnectInterval(5, TimeUnit.SECONDS);
		WampClient cl = builder.build();

		cl.statusChanged().subscribe(newState -> {
			if (newState instanceof WampClient.ConnectedState) {
				this.client = cl;
				completableFuture.complete(null);
			}
		}, throwable -> completableFuture.completeExceptionally(throwable));
		cl.open();

		return completableFuture;
	}

	public CompletableFuture<Location> sendAsynchronous(final Location location)
			throws Exception {

		CompletableFuture<Location> completableFuture = new CompletableFuture<>();
		this.client.call("multiplyWithTwo", location).subscribe(result -> {
			List<Location> locations = this.om.convertValue(result.arguments(),
					this.typeRef);
			completableFuture.complete(locations.get(0));
		}, error -> completableFuture.completeExceptionally(error));

		return completableFuture;
	}

	// http://iteratrlearning.com/java9/2016/09/13/java9-timeouts-completablefutures.html
	public <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit) {
		CompletableFuture<T> result = new CompletableFuture<>();
		this.delayer.schedule(() -> result.completeExceptionally(new TimeoutException()),
				timeout, unit);
		return result;
	}

	public static void main(String[] args) throws Exception {
		JawampaCaller3 caller = new JawampaCaller3();

		caller.connect().get(30, TimeUnit.SECONDS);

		Location location = new Location();
		location.setX(10);
		location.setY(22);

		CompletableFuture<Location> newLocation = caller.sendAsynchronous(location);
		newLocation.whenComplete((loc, ex) -> {
			if (ex == null) {
				System.out.println("successful: " + loc);
			}
			else {
				System.out.println("error: " + ex);
			}
		});

		CompletableFuture<Location> timeoutCompletable = caller.timeoutAfter(30,
				TimeUnit.SECONDS);
		timeoutCompletable.whenComplete((loc, ex) -> {
			System.out.println("Timeout");
		});

		newLocation = caller.sendAsynchronous(location);
		newLocation.whenComplete((loc, ex) -> {
			if (ex == null) {
				System.out.println("successful: " + loc);
			}
			else {
				System.out.println("error: " + ex);
			}
		});

		newLocation.acceptEither(timeoutCompletable, loc -> {
		});

		TimeUnit.MINUTES.sleep(1);
	}

}
