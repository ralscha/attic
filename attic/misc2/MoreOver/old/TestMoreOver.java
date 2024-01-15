
import java.io.*;
import java.util.*;
import org.xml.sax.*;

public class TestMoreOver {

	public static void main(String[] args) {
	  try {
	    Lax lax = new Lax();

	    MoreOverNewsHandler moHandler = new MoreOverNewsHandler();
	    lax.addHandler(moHandler);

      BufferedReader br = new BufferedReader(new FileReader("page.xml"));
      InputSource is = new InputSource(br);
      //InputSource is = new InputSource("http://p.moreover.com/cgi-local/page?index_java+xml");
	    lax.parseDocument(true, lax, is);
      //br.close();

      List articleList = moHandler.getArticleList();

      if (!articleList.isEmpty()) {
        for (int i = 0; i < articleList.size(); i++) {
          Article article = (Article)articleList.get(i);
          System.out.println(article.getHeadline());
        }
      } else {
        System.out.println("Keine Artikel gefunden");
      }

	  } catch (Exception ioe) {
	    System.err.println(ioe);
	  }    
	}
}