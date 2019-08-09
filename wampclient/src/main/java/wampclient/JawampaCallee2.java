package wampclient;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampError;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class JawampaCallee2 {
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
							client.registerProcedure("MakeReady").subscribe(request -> {
								RpcRequest req = om.convertValue(
										request.arguments().get(0), RpcRequest.class);
								System.out.println(req);

								request.reply("success");
							});
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
