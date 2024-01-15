package ch.ess.moreover;

import java.io.*;
import java.net.*;

import ch.ess.moreover.mapping.*;

public class Test {

  public static void main(String[] args) {
    try {
      
      File f = new File("D:\\eclipse\\workspace1.4\\moreover\\page.xml");
      System.out.println(f.toURL().toString());
      URL url = new URL("file:/D:/eclipse/workspace1.4/moreover/page.xml");
      
      
      BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
      Moreovernews news = Moreovernews.unmarshal(br);
      br.close();
      
      Article[] articles = news.getArticle();
      if (articles != null) {
        for (int i = 0; i < articles.length; i++) {
          System.out.println(articles[i].getHeadline_text());
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
