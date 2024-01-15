
import java.net.*;
import java.text.*;
import java.util.*;

public class Article {

	private String id;
	private URL url;
	private String headline;
	private String source;
	private String media_type;
	private String cluster;
	private String tagline;
	private URL document_url;
	private Date harvest_time;
	private String access_registration;
	private String access_status;

  private final static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mmaa");
  private final static SimpleDateFormat outputDateFormat = 
                                          new SimpleDateFormat("dd.MM.yyyy HH:mm");

  public Article() {
    id = null;
    url = null;
    headline = null;
    source = null;
    media_type = null;
    cluster = null;
    tagline = null;
    document_url = null;
    harvest_time = null;
    access_registration = null;
    access_status = null;
  }

  public void setID(String id) {
    this.id = id;
  }

  public String getID() {
    return id;
  }

  public void setURL(String urlstr) throws MalformedURLException {
    this.url = new URL(urlstr);
  }

  public void setURL(URL url) {
    this.url = url;
  }

  public URL getURL() {
    return url;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }
  
  public String getHeadline() {
    return headline;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getSource() {
    return source;
  }

  public void setMediaType(String media_type) {
    this.media_type = media_type;
  }

  public String getMediaType() {
    return media_type;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }
  
  public String getCluster() {
    return cluster;
  }

  public void setTagline(String tagline) {
    this.tagline = tagline;
  }

  public String getTagline() {
    return tagline;
  }

  public void setDocumentURL(String urlstr) throws MalformedURLException {
    this.document_url = new URL(urlstr);
  }

  public void setDocumentURL(URL url) {
    this.document_url = url;
  }

  public URL getDocumentURL() {
    return document_url;
  }

  public void setHarvestTime(String harvesttimestr) throws ParseException {
    this.harvest_time = dateFormat.parse(harvesttimestr);
  }

  public void setHarvestTime(Date harvesttime) {
    this.harvest_time = harvesttime;
  }

  public Date getHarvestTime() {
    return harvest_time;
  }

  public String getFormattedHarvestTime() {
    if (harvest_time != null)
      return outputDateFormat.format(harvest_time);
    else
      return "";
  }

  public void setAccessRegistration(String accessregistration) {
    this.access_registration = accessregistration;
  }

  public String getAccessRegistration() {
    return access_registration;
  }


  public void setAccessStatus(String accessstatus) {
    this.access_status = accessstatus;
  }

  public String getAccessStatus() {
    return access_status;
  }

}