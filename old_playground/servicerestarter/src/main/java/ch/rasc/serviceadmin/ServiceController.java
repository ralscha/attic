package ch.rasc.serviceadmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

	// @RequestMapping("/query")
	// String home() throws IOException {
	// return callSc("query");
	// }

	@RequestMapping("/stop")
	String stop(HttpServletResponse response) throws IOException {

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		return callSc("stop");
	}

	@RequestMapping("/start")
	String start(HttpServletResponse response) throws IOException {

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		return callSc("start");
	}

	@RequestMapping("/restart")
	String restart(HttpServletResponse response)
			throws IOException, InterruptedException {

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		String stop = callSc("stop");
		TimeUnit.SECONDS.sleep(15);
		String start = callSc("start");
		return stop + "\n" + start;
	}

	private static String callSc(String command) throws IOException {
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "net", command,
				"AService");
		Process process = pb.start();
		return fromStream(process.getInputStream());
	}

	public static String fromStream(InputStream in) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				out.append(line);
				out.append("\n");
			}
			return out.toString();
		}
		catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}