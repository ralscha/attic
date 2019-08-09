package wampclient;

import java.net.URI;

import ws.wamp.jawampa.ApplicationError;
import ws.wamp.jawampa.WampRouter;
import ws.wamp.jawampa.WampRouterBuilder;
import ws.wamp.jawampa.transport.netty.SimpleWampWebsocketListener;

public class Router {

	public static void main(String[] args) {
		WampRouterBuilder routerBuilder = new WampRouterBuilder();
		WampRouter router;
		try {
			routerBuilder.addRealm("realm");
			router = routerBuilder.build();
		}
		catch (ApplicationError e1) {
			e1.printStackTrace();
			return;
		}

		URI serverUri = URI.create("ws://0.0.0.0:8080/ws");
		SimpleWampWebsocketListener server;

		try {
			server = new SimpleWampWebsocketListener(router, serverUri, null);
			server.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
