package wampclient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class JawampaCaller2 {

	private final ObjectMapper om;

	JawampaCaller2() {
		this.om = new ObjectMapper();
	}

	public CompletableFuture<Location> sendAsynchronous(final Location location)
			throws Exception {

		CompletableFuture<Location> completableFuture = new CompletableFuture<>();

		WampClientBuilder builder = new WampClientBuilder();

		builder.withConnectorProvider(new NettyWampClientConnectorProvider())
				.withUri("ws://0.0.0.0:8080/ws").withRealm("realm")
				.withInfiniteReconnects().withReconnectInterval(5, TimeUnit.SECONDS);
		WampClient client = builder.build();

		client.statusChanged().subscribe((WampClient.State newState) -> {
			if (newState instanceof WampClient.ConnectedState) {
				client.call("multiplyWithTwo", location).subscribe(result -> {
					List<Location> locations = this.om.convertValue(result.arguments(),
							new TypeReference<List<Location>>() {
							});
					completableFuture.complete(locations.get(0));
				});
			}
		}, throwable -> completableFuture.completeExceptionally(throwable));

		client.open();

		return completableFuture;
	}

	public Location sendSynchronous(final Location location) throws Exception {
		try {
			return sendAsynchronous(location).get(15, TimeUnit.SECONDS);
		}
		catch (TimeoutException te) {
			System.out.println("timeout");
			return null;
		}
		catch (IllegalStateException e) {
			System.out.println("error");
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		JawampaCaller2 caller = new JawampaCaller2();

		Location location = new Location();
		location.setX(10);
		location.setY(22);

		// Non-Blocking call
		CompletableFuture<Location> newLocation = caller.sendAsynchronous(location);
		newLocation.thenAccept(System.out::println);

		// Synchronous, blocking call
		Location newLoc = caller.sendSynchronous(location);
		System.out.println(newLoc);

		TimeUnit.MINUTES.sleep(1);
	}

}
