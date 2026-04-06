package ch.rasc.ratpack;

import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.registry.NotInRegistryException;
import ratpack.render.NoSuchRendererException;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

public class Main {

	public static void main(String... args)
			throws NoSuchRendererException, NotInRegistryException, Exception {
		ServerConfig serverConfig = ServerConfig.builder().port(8080).build();
		Example e = new Example();
		Action<Chain> handlers = chain -> {
			chain.get(ctx -> ctx.render("Hello " + ctx.get(String.class)))
					.get("example", ctx -> e.handle(ctx)).get(":name", ctx -> ctx
							.render("Hello " + ctx.getPathTokens().get("name") + "!"));

		};

		RatpackServer.start(server -> server.serverConfig(serverConfig)
				.registryOf(registry -> registry.add("World!")).handlers(handlers));
	}

}
