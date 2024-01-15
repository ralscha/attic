
import org.xml.sax.Attributes;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class MoreOverNewsHandler {

  private List articleList;
  private Article newArticle;

  public List getArticleList() {
    return articleList;
  }

  public void startmoreovernews(Attributes list) {
    articleList = new ArrayList();
  }

	public void startarticle(Attributes list) {
    newArticle = new Article();
    newArticle.setID(list.getValue("id"));
	}	

	public void endarticle() {
    articleList.add(newArticle);
	}

	public void textOfurl(String str) {
    try {
      newArticle.setURL(str);
    } catch (MalformedURLException mue) {
      newArticle.setURL((URL)null);
    }
	}

	public void textOfheadline_text(String str) {
    String tmp = newArticle.getHeadline();
    if (tmp != null) {
      tmp += str;
      newArticle.setHeadline(tmp);
    } else {
      newArticle.setHeadline(str);
    }
	}

	public void textOfsource(String str) {
    newArticle.setSource(str);
	}

	public void textOfmedia_type(String str) {
    newArticle.setMediaType(str);
	}

	public void textOfcluster(String str) {
    newArticle.setCluster(str);
	}

	public void textOftagline(String str) {
    newArticle.setTagline(str);
	}

	public void textOfdocument_url(String str) {
    try {
      newArticle.setDocumentURL(str);  
    } catch (MalformedURLException mue) {
      newArticle.setDocumentURL((URL)null);
    }
	}

	public void textOfharvest_time(String str) {
    try {
      newArticle.setHarvestTime(str);
    } catch (ParseException pe) {
      newArticle.setHarvestTime((Date)null);
    }
	}

  public void textOfaccess_registration(String str) {
    newArticle.setAccessRegistration(str);
  }

  public void textOfaccess_status(String str) {
    newArticle.setAccessStatus(str);
  }


}

