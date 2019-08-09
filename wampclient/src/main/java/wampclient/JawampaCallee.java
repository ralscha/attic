package wampclient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampError;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class JawampaCallee {
	public static void main(String[] args) throws Exception {

		final WampClient client;
		try {
			WampClientBuilder builder = new WampClientBuilder();

			builder.withConnectorProvider(new NettyWampClientConnectorProvider())
					.withUri("ws://0.0.0.0:8080/ws").withRealm("realm")
					.withInfiniteReconnects().withReconnectInterval(5, TimeUnit.SECONDS);
			client = builder.build();

			ObjectMapper om = new ObjectMapper();

			client.statusChanged()

					.subscribe((WampClient.State newState) -> {
						if (newState instanceof WampClient.ConnectedState) {
							client.registerProcedure("multiplyWithTwo")
									.subscribe(request -> {
										List<Location> locations = om.convertValue(
												request.arguments(),
												new TypeReference<List<Location>>() {
												});
										Location location = locations.get(0);
										location.setX(location.getX() * 2);
										location.setY(location.getY() * 2);

										request.reply(location);
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
