package ch.rasc.sse.simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StreamingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/event-stream");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		@SuppressWarnings("resource")
		PrintWriter out = response.getWriter();

		for (int i = 0; i < 100; i++) {

			StringBuilder sb = new StringBuilder();

			sb.append("data:").append(i).append("\n");
			sb.append("\n");

			out.write(sb.toString());
			out.flush();

			try {
				TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3, 11));
			}
			catch (InterruptedException e) {
				break;
			}
		}

	}
}
