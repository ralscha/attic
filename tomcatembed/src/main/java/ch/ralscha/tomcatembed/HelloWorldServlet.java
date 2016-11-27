package ch.ralscha.tomcatembed;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		@SuppressWarnings("resource")
		OutputStream out = response.getOutputStream();

		String html = "<html><title>Hello World</title><body><h1>Hello World</h1></body></html>";
		out.write(html.getBytes());

		out.close();
	}

}
