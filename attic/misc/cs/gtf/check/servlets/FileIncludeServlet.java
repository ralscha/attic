package gtf.check.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class FileIncludeServlet extends HttpServlet {

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest req,
                  	HttpServletResponse res) throws ServletException, IOException {

		String head, body, tail;
		String name, value;

		head = body = tail = null;

		Enumeration values = req.getParameterNames();
		while (values.hasMoreElements()) {
			name = (String) values.nextElement();
			value = req.getParameterValues(name)[0];
			if (name.equals("head")) {
				head = value;
			} else if (name.equals("body")) {
				body = value;
			} else if (name.equals("tail")) {
				tail = value;
			}
		}

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		try {
			BufferedReader br;
			if (head != null) {
				br = new BufferedReader(new FileReader(head));
				writeFile(out, br);
				br.close();
			}
			if (body != null) {
				br = new BufferedReader(new FileReader(body));
				writeFile(out, br);
				br.close();
			}
			if (tail != null) {
				br = new BufferedReader(new FileReader(tail));
				writeFile(out, br);
				br.close();
			}
		} catch (Exception e) {
			out.print("<HTML><HEAD><TITLE>Panic</TITLE></HEAD>");
			out.print("<BODY>");
			out.print(e);
			out.println("</BODY></HTML>");
		}
		out.close();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	private void writeFile(PrintWriter out, BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			out.println(line);
		}
	}
}