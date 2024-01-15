package ch.sr.bookmark;
import java.io.*;
import java.util.*;

public class HTMLBookmarkPage {

	private PrintWriter writer;

	public HTMLBookmarkPage(String fileName) throws IOException {
		this.writer = new PrintWriter(new UmlautFilterWriter(new FileWriter(fileName)));
	}

	public void close() {
		writer.close();
	}

	public void printHeader() {
		String title = AppProperties.getStringProperty("html.title");
		if (title == null)
			title = "Bookmarks";

		writer.println("<html>");
		writer.println("<head>");
		writer.print("<title>");
		writer.print(title);
		writer.println("</title>");
		
		writer.println("<meta http-equiv=\"KEYWORDS\" content=\"Links, Bookmarks\">");
		writer.print("<meta http-equiv=\"DESCRIPTION\" content=\"");
		writer.print(title);
		writer.println("\">");
		writer.println("<link rel=stylesheet type=\"text/css\" href=\"bm.css\">");
		writer.println("</head>");
		writer.println("<body>");
		writer.print("<h2>");
		writer.print(title);
		writer.println("</h2>");
	}

	public void printContents(List l) {
		writer.println("<hr>");
		Iterator it = l.iterator();
		while (it.hasNext()) {
			String title = (String) it.next();
			writer.print("<a href=\"#");
			writer.print(title);
			writer.print("\">");
			writer.print(title);
			writer.println("</a>");

			if (it.hasNext())
				writer.println(" &nbsp;&nbsp;/&nbsp;&nbsp;");
			else
				writer.println();
		}
		writer.println("<hr>");
	}

	public void printTitle1(String title) {
		writer.print("<br>");
		writer.print("<br>");
		writer.print("<a name=\"");
		writer.print(title);
		writer.print("\"></a><h3>");
		writer.print(title);
		writer.println("</h3>");
	}

	public void printTitle2(String title) {
		writer.print("<br>");
		writer.print(" <b>");
		writer.print(title);
		writer.print("</b><br>");
	}

	public void printStartList() {
		writer.println("<ul>");
	}

	public void printEndList() {
		writer.println("</ul>");
	}

	public void printBookmark(String title, String url) {
		writer.print(" <li><a href=\"");
		writer.print(url);
		writer.print("\">");
		writer.print(title);
		writer.println("</a>");
	}

	public void printParagraph() {
		writer.println("<p>");
	}

	public void printFooter() {
		writer.println("</body>");
		writer.println("</html>");
	}
}