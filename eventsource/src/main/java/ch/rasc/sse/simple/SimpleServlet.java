package ch.rasc.sse.simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("USERID: " + request.getParameter("userid"));
		System.out.println(new Date());
		System.out.println("Last-Event-ID: " + request.getHeader("Last-Event-ID"));
		response.setContentType("text/event-stream");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(200);

		@SuppressWarnings("resource")
		PrintWriter out = response.getWriter();

		StringBuilder sb = new StringBuilder();
		// sb.append("data:event1\n");
		// sb.append("\n");
		// sb.append("data:event2\n");
		// sb.append("\n");
		// sb.append("data:event3\n");
		// sb.append("\n");

		// sb.append("data:{\n");
		// sb.append("data: \"fullName\":\"Philip Cervantes\",\n");
		// sb.append("data: \"firstName\":\"Philip\",\n");
		// sb.append("data: \"lastName\":\"Cervantes\",\n");
		// sb.append("data: \"id\":\"1\",\n");
		// sb.append("data: \"street\":\"P.O. Box 521, 7327 Vel Street\",\n");
		// sb.append("data: \"city\":\"Manhattan Beach\",\n");
		// sb.append("data: \"state\":\"MB\",\n");
		// sb.append("data: \"zip\":\"X2Z 3N5\",\n");
		// sb.append("data: \"country\":\"Gambia\"\n");
		// sb.append("data:}\n");
		// sb.append("\n");

		// sb.append("event:add\n");
		// sb.append("data:100\n");
		// sb.append("\n");
		// sb.append("data:56\n");
		// sb.append("event:remove\n");
		// sb.append("\n");
		// sb.append("event:add\n");
		// sb.append("data:101\n");
		// sb.append("\n");
		// sb.append("data:common event\n");
		// sb.append("\n");

		// sb.append("event:add\n");
		// sb.append("data:100\n");
		// sb.append("retry:0\n");
		// sb.append("\n");

		sb.append("id:2012-08-19T10:11:20\n");
		sb.append("data:APPL\n");
		sb.append("data:648\n");
		sb.append("\n");

		out.write(sb.toString());
		out.flush();

	}
}
