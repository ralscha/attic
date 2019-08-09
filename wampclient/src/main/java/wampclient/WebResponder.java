package wampclient;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import rx.schedulers.Schedulers;
import wampclient.dto.CallVariable;
import wampclient.dto.Dialog;
import wampclient.dto.DialogCallVariables;
import wampclient.dto.DialogMediaProperties;
import wampclient.dto.DialogResponse;
import wampclient.dto.User;
import wampclient.dto.UserReason;
import wampclient.dto.UserResponse;
import ws.wamp.jawampa.ApplicationError;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampError;
import ws.wamp.jawampa.WampRouter;
import ws.wamp.jawampa.WampRouterBuilder;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;
import ws.wamp.jawampa.transport.netty.SimpleWampWebsocketListener;

public class WebResponder {
	private static final Logger LOGGER = Logger.getLogger(WebResponder.class.getName());

	public static void main(String[] args) throws Exception {

		// Start Router
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
			LOGGER.severe("start router:" + e);
		}

		// Start Client

		final WampClient client;
		try {
			WampClientBuilder builder = new WampClientBuilder();

			builder.withConnectorProvider(new NettyWampClientConnectorProvider())
					.withUri("ws://0.0.0.0:8080/ws").withRealm("realm")
					.withInfiniteReconnects().withReconnectInterval(3, TimeUnit.SECONDS);
			client = builder.build();

			client.statusChanged().subscribe((WampClient.State newState) -> {
				if (newState instanceof WampClient.ConnectedState) {

					Schedulers.computation().createWorker().schedulePeriodically(() -> {
						Location location = new Location();
						location.setX((int) (Math.random() * 1000));
						location.setY((int) (Math.random() * 1000));
						client.publish("location", location);
					}, 1000, 1000, TimeUnit.MILLISECONDS);

					Schedulers.computation().createWorker().schedulePeriodically(() -> {
						if (Math.random() < 0.5) {
							// client.publish("event1", "event1:" + Instant.now().getEpochSecond());
							client.publish("event1", createDialogData());
						}
						else {
							client.publish("event2",
									"event2:" + Instant.now().getEpochSecond());
						}
					}, 10, 10, TimeUnit.SECONDS);

					client.registerProcedure("getMyData").subscribe(request -> {
						request.reply("");
						CallEvent callEvent = new CallEvent();
						DetailEvent detailEvent = new DetailEvent();
						detailEvent.rc = 0;
						detailEvent.status = "success";
						detailEvent.fieldList = new ArrayList<>();
						detailEvent.fieldList.add("zip:1234");
						detailEvent.fieldList.add("state:IL");

						callEvent.size = 391;
						callEvent.name = "Me";
						callEvent.processed = true;
						callEvent.de = detailEvent;

						client.publish("myData", callEvent);
					});

					client.registerProcedure("getUserData").subscribe(request -> {
						System.out.println(request.arguments());
						request.reply(createUserData());
					});

					client.registerProcedure("getDialogData").subscribe(request -> {
						System.out.println(request.arguments());
						request.reply(createDialogData());
					});

					client.registerProcedure("method1").subscribe(request -> {
						request.reply("method1:" + Instant.now().getEpochSecond());
					});
					client.registerProcedure("method2").subscribe(request -> {
						request.reply("method2:" + Instant.now().getEpochSecond());
					});
					client.registerProcedure("method3").subscribe(request -> {
						request.reply("method3:" + Instant.now().getEpochSecond());
					});
					client.registerProcedure("method4").subscribe(request -> {
						request.reply("method4:" + Instant.now().getEpochSecond());
					});
					client.registerProcedure("method5").subscribe(request -> {
						request.reply("method5:" + Instant.now().getEpochSecond());
					});
					client.registerProcedure("method6").subscribe(request -> {
						request.reply("method6:" + Instant.now().getEpochSecond());
					});

				}
			});

			client.open();

			TimeUnit.MINUTES.sleep(10);

		}
		catch (WampError e) {
			LOGGER.severe("callee:" + e);
		}
	}

	private static UserResponse createUserData() {
		UserResponse userResponse = new UserResponse();

		User user = new User();
		user.setUserData1("42001");
		user.setUserData2("42001_FN");
		user.setUserData3("42001_LN");
		user.setUserData4("42001");

		UserReason reason = new UserReason();
		reason.setUserData5("NOT_READY");
		reason.setUserData6("3");
		user.setReason(reason);

		user.setUserData7("2017-10-13T20:46:11.010Z");
		user.setUserData8("5000");
		user.setUserData9("Team_Vest");
		user.setUserData10("/localhost/api/User/35122570");

		userResponse.setUser(user);
		return userResponse;
	}

	private static DialogResponse createDialogData() {
		DialogResponse response = new DialogResponse();
		response.setDialogData6("ACTIVE");
		response.setDialogData7("to441312464547");
		response.setDialogData8("PUT");
		response.setDialogData9("2017-10-13T20:46:11.010Z");
		response.setDialogData10("/localhost/api/Dialog/35122570");

		Dialog dialog = new Dialog();
		dialog.setDialogData1(12345);
		dialog.setDialogData2("from441312464547");
		dialog.setDialogData3("35122570");

		DialogMediaProperties mproperties = new DialogMediaProperties();
		mproperties.setDialogData4("63594");
		mproperties.setDialogData5("INSIDE");
		DialogCallVariables cv = new DialogCallVariables();
		List<CallVariable> cvs = new ArrayList<>();

		CallVariable c = new CallVariable();
		c.setName("CallData1");
		c.setValue("SLUK_OPS_TEL_WEL_AN");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CallData2");
		c.setValue(":0990004931779:P:");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CallData3");
		c.setValue(":Mrs");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CallData4");
		c.setValue("Illingworth");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CallData5");
		c.setValue("D1006777000:P:");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CallData6");
		c.setValue("idordmd");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CallData7");
		c.setValue(null);
		cvs.add(c);

		c = new CallVariable();
		c.setName("CallData8");
		c.setValue(null);
		cvs.add(c);

		c = new CallVariable();
		c.setName("CallData9");
		c.setValue("1521061810");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CallData10");
		c.setValue("REMEDIATION - GROUP PENS");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CustomData1");
		c.setValue("0");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CustomData2");
		c.setValue("en-us");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CustomData3");
		c.setValue("media");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CustomData4");
		c.setValue("sys");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CustomData5");
		c.setValue("Dry");
		cvs.add(c);

		c = new CallVariable();
		c.setName("CustomData6");
		c.setValue("Test");
		cvs.add(c);

		cv.setCallVariables(cvs);
		mproperties.setCallVariables(cv);
		dialog.setMediaProperties(mproperties);

		response.setDialog(dialog);
		return response;
	}

}
