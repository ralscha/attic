package wampclient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.schedulers.Schedulers;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampError;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class JawampaPublisher2 {
	public static void main(String[] args) throws Exception {

		final WampClient client;
		try {
			WampClientBuilder builder = new WampClientBuilder();

			builder.withConnectorProvider(new NettyWampClientConnectorProvider())
					.withUri("ws://0.0.0.0:8080/ws").withRealm("realm")
					.withInfiniteReconnects().withReconnectInterval(1, TimeUnit.SECONDS);
			client = builder.build();
			AtomicBoolean running = new AtomicBoolean();
			client.statusChanged()

					.subscribe((WampClient.State newState) -> {
						if (newState instanceof WampClient.ConnectedState) {
							if (!running.get()) {
								running.set(true);
								Schedulers.computation().createWorker()
										.schedulePeriodically(() -> {

											PubSubMessage msg = new PubSubMessage();
											msg.dateTimeStamp = "now";

											client.publish("myTopic", msg);
										}, 1000, 1000, TimeUnit.MILLISECONDS);
							}
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
