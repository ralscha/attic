package wampclient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampError;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class JawampaCaller {
	public static void main(String[] args) throws Exception {

		final WampClient client;
		try {
			// Create a builder and configure the client
			WampClientBuilder builder = new WampClientBuilder();

			builder.withConnectorProvider(new NettyWampClientConnectorProvider())
					// .withUri("wss://demo.rasc.ch/wamp2spring-demo-earthquake/wamp")
					.withUri("ws://0.0.0.0:8080/ws").withRealm("realm")
					.withInfiniteReconnects().withReconnectInterval(5, TimeUnit.SECONDS);
			// Create a client through the builder. This will not immediatly start
			// a connection attempt
			client = builder.build();
			ObjectMapper om = new ObjectMapper();
			client.statusChanged()

					.subscribe((WampClient.State newState) -> {
						if (newState instanceof WampClient.ConnectedState) {

							Location location = new Location();
							location.setX(10);
							location.setY(22);

							client.call("multiplyWithTwo", location).subscribe(result -> {
								List<Location> locations = om.convertValue(
										result.arguments(),
										new TypeReference<List<Location>>() {
										});
								System.out.println(locations.get(0));
							});

						}
						else if (newState instanceof WampClient.DisconnectedState) {
							System.out.println(
									"Client got disconnected from the remoute router");
						}
						else if (newState instanceof WampClient.ConnectingState) {
							System.out.println("Connecting");
						}
					});

			client.open();

			TimeUnit.MINUTES.sleep(10);

		}
		catch (WampError e) {
			// Catch exceptions that will be thrown in case of invalid configuration
			System.out.println(e);
			return;
		}
	}

}
