package ch.ess.map;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.StringTokenizer;
import org.apache.commons.io.IOUtils;



public class TestHttp {
  
  private static final String BASE_URL = "http://maps.google.com/maps/geo?q={0}&output={1}&key={2}";
  private static final String XML = "xml", CSV = "csv";
  private static final String API_KEY = "ABQIAAAAKoSCK01hBi1SddxdGlp-ihTwM0brOpm-All5BF6PoaKBxRWWERQ3w6HFpRGaa6jpLaiyIfL0cVmmvw";
  
  public static void main(String[] args) {
    try {
      String location = "Ostermundigensfdsf+Switzerland";
      
      String urlString = MessageFormat.format(BASE_URL, location, CSV, API_KEY);
      
      URL url = new URL(urlString);
      URLConnection conn = url.openConnection();
      
      InputStream is = conn.getInputStream();
      String output = IOUtils.toString(is);
      is.close();
      
      StringTokenizer st = new StringTokenizer(output, ",");
      String status = st.nextToken();
      String accuracy = st.nextToken();
      String lat = st.nextToken();
      String lng = st.nextToken();
      
      System.out.println(status);
      System.out.println(lat);
      System.out.println(lng);
      
      System.out.println(output);
//      
//      XMLInputFactory xmlif = XMLInputFactory.newInstance();
//      XMLEventReader xmler = xmlif.createXMLEventReader(new StringReader(output));
//      XMLEvent event;
//      boolean end = false;
//      while(xmler.hasNext() && !end) {
//        event = xmler.nextEvent();
//        if (event.isStartElement()) {
//          if (event.asStartElement().getName().getLocalPart().equals("Status")) {
//            event = xmler.nextTag();
//            event = xmler.nextEvent();
//            System.out.println(event.asCharacters().getData());
//          } else if (event.asStartElement().getName().getLocalPart().equals("coordinates")) {
//            event = xmler.nextEvent();
//            System.out.println(event.asCharacters().getData());
//            end = true;
//          }
//        }
//      }      
      
    } catch (MalformedURLException e) {      
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } /*catch (XMLStreamException e) {      
      e.printStackTrace();
    }*/
    
  }
}
