import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ch.ess.base.Constants;
import ch.ess.cal.dao.ContactDao;
import ch.ess.cal.model.Contact;
import ch.ess.cal.model.ContactAttribute;

public class MigrateLatLng {

  public static void main(String[] args) throws IOException {

    String googleApiKey = "ABQIAAAAKoSCK01hBi1SddxdGlp-ihTwM0brOpm-All5BF6PoaKBxRWWERQ3w6HFpRGaa6jpLaiyIfL0cVmmvw";
    
    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");

      ApplicationContext ap = new ClassPathXmlApplicationContext(new String[]{"/spring-datasource-local.xml",
          "/spring.xml", "/spring-mail.xml", "/spring-hibernate.xml", "/spring-init.xml", "/spring-last.xml"});

      SessionFactory sf = (SessionFactory)ap.getBean("sessionFactory");

      Session session = SessionFactoryUtils.getSession(sf, true);
      session.setFlushMode(FlushMode.MANUAL);

      TransactionSynchronizationManager.bindResource(sf, new SessionHolder(session));

      ContactDao contactDao = (ContactDao)ap.getBean("contactDao");
      
      List<Contact> contacts =  contactDao.findAll();
      for (Contact contact : contacts) {
        
        ContactAttribute ca = contact.getContactAttributes().get("lat");
        if (ca == null) {
          
          String location = getLocationString(contact);
          System.out.println("INPUT1: " + location);
          String output = geoCode(googleApiKey, location);
          
          StringTokenizer st = new StringTokenizer(output, ",");
          String status = st.nextToken();
          
          if (!"200".equals(status) && StringUtils.isNotBlank(location)) {
            location += "+Switzerland";
            System.out.println("INPUT2: " + location);
            
            output = geoCode(googleApiKey, location);
            
            st = new StringTokenizer(output, ",");
            status = st.nextToken();
          }
          
          System.out.println("OUPUT: " + output);
          
          if ("200".equals(status)) {
            
            st.nextToken();
            String lat = st.nextToken();
            String lng = st.nextToken();
            
            ca = new ContactAttribute();
            ca.setContact(contact);
            ca.setField("lat");
            ca.setValue(lat);
            contact.getContactAttributes().put("lat",ca);
            
            ca = new ContactAttribute();
            ca.setContact(contact);
            ca.setField("lng");
            ca.setValue(lng);
            contact.getContactAttributes().put("lng",ca);  
            
            contactDao.save(contact);
          }
        }
        
        
        
        ca = contact.getContactAttributes().get("homeLat");
        if (ca == null) {

          
          String location = getHomeLocationString(contact);
          System.out.println("INPUT1: " + location);
          String output = geoCode(googleApiKey, location);
          
          StringTokenizer st = new StringTokenizer(output, ",");
          String status = st.nextToken();
          
          if (!"200".equals(status) && StringUtils.isNotBlank(location)) {
            location += "+Switzerland";
            System.out.println("INPUT2: " + location);
            
            output = geoCode(googleApiKey, location);
            
            st = new StringTokenizer(output, ",");
            status = st.nextToken();
          }
          
          System.out.println("OUPUT: " + output);
          
          if ("200".equals(status)) {
            
            st.nextToken();
            String lat = st.nextToken();
            String lng = st.nextToken();
            
            ca = new ContactAttribute();
            ca.setContact(contact);
            ca.setField("homeLat");
            ca.setValue(lat);
            contact.getContactAttributes().put("homeLat",ca);
            
            ca = new ContactAttribute();
            ca.setContact(contact);
            ca.setField("homeLng");
            ca.setValue(lng);
            contact.getContactAttributes().put("homeLng",ca);  
            
            contactDao.save(contact);
          }
                  
        }
        
      }

      TransactionSynchronizationManager.unbindResource(sf);
      SessionFactoryUtils.releaseSession(session, sf);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } 

  }
  
  
  private static String getLocationString(Contact contact) {
    ContactAttribute ca = contact.getContactAttributes().get("addressStreet"); 
    String street = (ca != null ? ca.getValue() : null);
    
    ca = contact.getContactAttributes().get("addressPostalCode"); 
    String poCode = (ca != null ? ca.getValue() : null);
    
    ca = contact.getContactAttributes().get("addressCity"); 
    String city =  (ca != null ? ca.getValue() : null);
    
    ca = contact.getContactAttributes().get("addressCountry"); 
    String country = (ca != null ? ca.getValue() : null);
    
    String location = "";
    if (StringUtils.isNotBlank(street)) {
      location = street.replace(" ", "+").replace("\n", "+");
    }
    if (StringUtils.isNotBlank(poCode)) {
      if (location.length() > 0) {
        location += "+";
      }
      location += poCode.replace(" ", "+");
    }
    if (StringUtils.isNotBlank(city)) {
      if (location.length() > 0) {
        location += "+";
      }          
      location += city.replace(" ", "+");
    }
    if (StringUtils.isNotBlank(country)) {
      if (location.length() > 0) {
        location += "+";
      }          
      location += country.replace(" ", "+");
    }
    return location;
  }
  
  private static String getHomeLocationString(Contact contact) {
    ContactAttribute ca = contact.getContactAttributes().get("homeStreet"); 
    String street = (ca != null ? ca.getValue() : null);
    
    ca = contact.getContactAttributes().get("homePostalCode"); 
    String poCode = (ca != null ? ca.getValue() : null);
    
    ca = contact.getContactAttributes().get("homeCity"); 
    String city =  (ca != null ? ca.getValue() : null);
    
    ca = contact.getContactAttributes().get("homeCountry"); 
    String country = (ca != null ? ca.getValue() : null);
    
    String location = "";
    if (StringUtils.isNotBlank(street)) {
      location = street.replace(" ", "+").replace("\n", "+");
    }
    if (StringUtils.isNotBlank(poCode)) {
      if (location.length() > 0) {
        location += "+";
      }
      location += poCode.replace(" ", "+");
    }
    if (StringUtils.isNotBlank(city)) {
      if (location.length() > 0) {
        location += "+";
      }          
      location += city.replace(" ", "+");
    }
    if (StringUtils.isNotBlank(country)) {
      if (location.length() > 0) {
        location += "+";
      }          
      location += country.replace(" ", "+");
    }
    return location;
  }
  
  private static String geoCode(String googleKey, String location) throws IOException {
    String urlString = MessageFormat.format(Constants.BASE_GOOGLE_URL, location, googleKey);
    
    URL url = new URL(urlString);
    URLConnection conn = url.openConnection();
    
    InputStream is = conn.getInputStream();
    String output = IOUtils.toString(is);
    is.close(); 
    
    return output;
  }
}
