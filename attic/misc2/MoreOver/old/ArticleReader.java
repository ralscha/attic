import org.xml.sax.*;
import java.util.List;

public class ArticleReader {
	
  private Lax lax;
  private MoreOverNewsHandler moHandler;

  public ArticleReader() {
    lax = new Lax();
    moHandler = new MoreOverNewsHandler();
    lax.addHandler(moHandler);
  }

  public List getArticleList(String uri) {
    InputSource is = new InputSource(uri);
	  lax.parseDocument(false, lax, is);
    return moHandler.getArticleList();
  }

}