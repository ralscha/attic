package wampclient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import rx.Subscription;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampError;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class JawampaSubscriber {
	public static void main(String[] args) throws Exception {

		final WampClient client;
		try {
			// Create a builder and configure the client
			WampClientBuilder builder = new WampClientBuilder();

			builder.withConnectorProvider(new NettyWampClientConnectorProvider())
					// .withUri("wss://demo.rasc.ch/wamp2spring-demo-iss/wamp")
					.withUri("ws://0.0.0.0:8080/ws").withRealm("realm")
					.withInfiniteReconnects().withReconnectInterval(5, TimeUnit.SECONDS);

			ObjectMapper om = new ObjectMapper();
			client = builder.build();
			client.statusChanged()

					.subscribe((WampClient.State newState) -> {
						if (newState instanceof WampClient.ConnectedState) {
							Subscription eventSubscription = client
									.makeSubscription("location").subscribe((s) -> {
										try {
											List<Location> locations = om.convertValue(
													s.arguments(),
													new TypeReference<List<Location>>() {
													});
											for (Location location : locations) {
												System.out.println(location);
											}
										}
										catch (Throwable e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}, (e) -> {
										System.out.println(e);
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
			e.printStackTrace();
		}
	}

}
