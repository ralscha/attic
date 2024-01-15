
import java.io.*;
import java.util.*;

public class Test {
  private static void main(String[] args) {
    try {
    BufferedReader br = new BufferedReader(new FileReader("page.xml"));
    Moreovernews news = MoreovernewsImpl.unmarshal(br, false);  
    System.out.println(news.getArticleList().size());
    List ar = news.getArticleList();
    Iterator it = ar.iterator();
    while(it.hasNext()) {
      System.out.println(((Article)it.next()).getHeadline_text());
    }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } 
  }
}