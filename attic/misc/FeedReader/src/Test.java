import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import com.sun.syndication.feed.synd.SyndContentI;
import com.sun.syndication.feed.synd.SyndEntryI;
import com.sun.syndication.feed.synd.SyndFeedI;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;


/**
 * @author sr
 */
public class Test {

  public static void main(String[] args) {
    
    try {
      URL feedUrl = new URL("http://herzkirsche.com/weblog/rss.xml");
      
      SyndFeedInput input = new SyndFeedInput();
      SyndFeedI feed = input.build(feedUrl.openStream());

      
      List list = feed.getEntries();
      for (Iterator it = list.iterator(); it.hasNext();) {
        SyndEntryI entry = (SyndEntryI)it.next();
        
        List contentsList = entry.getContents();
        for (Iterator it2 = contentsList.iterator(); it2.hasNext();) {
          SyndContentI content = (SyndContentI)it2.next();
          System.out.println(content.getValue());          
        }
        
        System.out.println();
        System.out.println();
        System.out.println();
      }
      
      //System.out.println(feed);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (FeedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    
  }
}
