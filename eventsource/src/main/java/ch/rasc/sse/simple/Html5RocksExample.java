package ch.rasc.sse.simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Html5RocksExample extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Last-Event-ID: " + request.getHeader("Last-Event-ID"));
		response.setContentType("text/event-stream");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(200);

		@SuppressWarnings("resource")
		PrintWriter out = response.getWriter();
		for (int i = 0; i < 10; i++) {
			out.write(createMessage());
			out.flush();
			try {
				TimeUnit.SECONDS.sleep(5);
			}
			catch (InterruptedException e) {
				break;
			}
		}
	}

	private static String createMessage() {
		StringBuilder sb = new StringBuilder();
		long id = System.currentTimeMillis();
		sb.append("id:").append(id).append("\n");
		sb.append("data: {\n");
		sb.append("data: \"msg\":").append(id).append(", \n");
		sb.append("data: \"id\":").append(id).append("\n");
		sb.append("data: }\n");
		sb.append("\n");
		return sb.toString();
	}

}
