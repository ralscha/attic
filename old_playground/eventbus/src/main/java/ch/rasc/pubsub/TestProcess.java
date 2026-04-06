package ch.rasc.pubsub;

import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.ByteStreams;

public class TestProcess {

	public static void main(String[] args) throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "dir c:");
		pb.redirectErrorStream(true);

		Process shell = pb.start();
		int shellExitStatus = shell.waitFor();
		System.out.println("Exit status: " + shellExitStatus);
		try (InputStream is = shell.getInputStream()) {
			String output = new String(ByteStreams.toByteArray(is));
			System.out.println(output);
		}

	}

}
