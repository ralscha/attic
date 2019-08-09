package wampclient;

import java.util.concurrent.TimeUnit;

import rx.schedulers.Schedulers;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampError;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class JawampaPublisher {
	public static void main(String[] args) throws Exception {

		final WampClient client;
		try {
			WampClientBuilder builder = new WampClientBuilder();

			builder.withConnectorProvider(new NettyWampClientConnectorProvider())
					.withUri("ws://0.0.0.0:8080/ws").withRealm("realm")
					.withInfiniteReconnects().withReconnectInterval(5, TimeUnit.SECONDS);
			client = builder.build();

			client.statusChanged()

					.subscribe((WampClient.State newState) -> {
						if (newState instanceof WampClient.ConnectedState) {
							Schedulers.computation().createWorker()
									.schedulePeriodically(() -> {

										Location location = new Location();
										location.setX((int) (Math.random() * 1000));
										location.setY((int) (Math.random() * 1000));
										client.publish("location", location);
									}, 1000, 1000, TimeUnit.MILLISECONDS);
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
			return;
		}
	}

}
