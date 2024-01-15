package ch.sr.bookmark;
public class Bookmark {
   
   private String title;
   private String url;
   
   public Bookmark(String title, String url) {
   	this.title = title;
   	this.url = url;
   }
   
   public String getTitle() {
   	return title;
   }
   
   public String getURL() {
   	return url;
   }
   
   public void setTitle(String title) {
   	this.title = title;
   }
   
   public void setURL(String url) {
   	this.url = url;
   }
   
}