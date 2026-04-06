package ch.rasc.irc;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.schwering.irc.lib.IRCConnection;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.IRCRuntimeConfig;
import org.schwering.irc.lib.IRCServerConfig;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.impl.DefaultIRCConnection;
import org.schwering.irc.lib.impl.DefaultIRCRuntimeConfig;
import org.schwering.irc.lib.impl.DefaultIRCServerConfig;
import org.schwering.irc.lib.util.IRCModeParser;

public class Test {
	public static void main(String[] args)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {
		IRCServerConfig config = new DefaultIRCServerConfig("irc.wikimedia.org",
				new int[] { 6667, 6669 }, "rcmon", "rcmon", "rcmon", "rcmon", "UTF-8");
		IRCRuntimeConfig runtimeConfig = new DefaultIRCRuntimeConfig(2000, true, true,
				null, null, null, null);
		IRCConnection conn = new DefaultIRCConnection(config, runtimeConfig);

		conn.addIRCEventListener(new IRCEventListener() {
			@Override
			public void onRegistered() {
				System.out.println("Connected");
			}

			@Override
			public void onDisconnected() {
				System.out.println("Disconnected");
			}

			@Override
			public void onError(String msg) {
				System.out.println("Error: " + msg);
			}

			@Override
			public void onError(int num, String msg) {
				System.out.println("Error #" + num + ": " + msg);
			}

			@Override
			public void onInvite(String chan, IRCUser u, String nickPass) {
				System.out.println(chan + "> " + u.getNick() + " invites " + nickPass);
			}

			@Override
			public void onJoin(String chan, IRCUser u) {
				System.out.println(chan + "> " + u.getNick() + " joins");
			}

			@Override
			public void onKick(String chan, IRCUser u, String nickPass, String msg) {
				System.out.println(chan + "> " + u.getNick() + " kicks " + nickPass);
			}

			@Override
			public void onMode(IRCUser u, String nickPass, String mode) {
				System.out.println(
						"Mode: " + u.getNick() + " sets modes " + mode + " " + nickPass);
			}

			@Override
			public void onMode(String chan, IRCUser u, IRCModeParser mp) {
				System.out.println(
						chan + "> " + u.getNick() + " sets mode: " + mp.getLine());
			}

			@Override
			public void onNick(IRCUser u, String nickNew) {
				System.out
						.println("Nick: " + u.getNick() + " is now known as " + nickNew);
			}

			@Override
			public void onNotice(String target, IRCUser u, String msg) {
				System.out.println(target + "> " + u.getNick() + " (notice): " + msg);
			}

			@Override
			public void onPart(String chan, IRCUser u, String msg) {
				System.out.println(chan + "> " + u.getNick() + " parts");
			}

			@Override
			public void onPrivmsg(String chan, IRCUser u, String msg) {
				System.out.println(chan + "> " + u.getNick() + ": " + msg);
			}

			@Override
			public void onQuit(IRCUser u, String msg) {
				System.out.println("Quit: " + u.getNick());
			}

			@Override
			public void onReply(int num, String value, String msg) {
				System.out.println("Reply #" + num + ": " + value + " " + msg);
			}

			@Override
			public void onTopic(String chan, IRCUser u, String topic) {
				System.out.println(
						chan + "> " + u.getNick() + " changes topic into: " + topic);
			}

			@Override
			public void onPing(String p) {
				System.out.println("onPing: " + p);
			}

			@Override
			public void unknown(String a, String b, String c, String d) {
				System.out.println("UNKNOWN: " + a + " b " + c + " " + d);
			}
		});

		conn.connect();
		// conn.doList();
		conn.doJoin("#en.wikipedia");
		// conn.doNames();
		try {
			TimeUnit.MINUTES.sleep(5);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		conn.doQuit();

	}
}
