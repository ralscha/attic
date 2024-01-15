package ch.ess.cal.web.contact;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.cal.model.Contact;
import ch.ess.cal.model.ContactAttribute;
import ch.ess.cal.service.search.SearchEngine;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

public class ContactEditAction extends AbstractEditAction<Contact> {

  private SearchEngine searchEngine;
  private Config appConfig;
  private UserDao userDao;
  
  public void setSearchEngine(SearchEngine searchEngine) {
    this.searchEngine = searchEngine;
  }
    
  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }
  
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    String googleKey = appConfig.getStringProperty(AppConfig.GOOGLE_API_KEY);
    ContactForm contactForm = (ContactForm)form;
    contactForm.setShowMap(false);
    contactForm.setShowHomeMap(false);
    contactForm.setGoogleApiKey(googleKey);
    contactForm.setTabset("generalTab");
  }

  @Override
  protected void beforeDelete(FormActionContext ctx, String id) {
    searchEngine.deleteContactDocument(id);
  }

  @Override
  protected void afterSave(FormActionContext ctx, Contact contact) {
    searchEngine.addContact(contact);
  }

  @Override
  // vom Form in die DB
  public void formToModel(final ActionContext ctx, Contact contact) {
    
    ContactForm contactForm = (ContactForm)ctx.form();
    
    if (contact.getContactAttributes() != null) {
      
      Set<String> keys = new HashSet<String>(contact.getContactAttributes().keySet()); 
      for (String key : keys) {
        contact.getContactAttributes().remove(key);
      }
    } else {
      contact.setContactAttributes(new HashMap<String, ContactAttribute>());     
    }
    Set<String> keys = contactForm.getValueMap().keySet();
    for (String key : keys) {
      String value = (String) contactForm.getValueMap().get(key);
      if (StringUtils.isNotBlank(value)) {
        ContactAttribute ca = new ContactAttribute();
        ca.setContact(contact);
        ca.setField(key);
        ca.setValue(value);
        contact.getContactAttributes().put(key,ca);
      }
    }
    
    if (contactForm.isPrivateContact()) {
      User theUser = Util.getUser(ctx.session(), userDao);
      contact.getUsers().add(theUser);
    } else {
      contact.getUsers().clear();
    }
    
    
    updateCategory(contact);
    
    //Compute Lat/Lng
    String googleKey = appConfig.getStringProperty(AppConfig.GOOGLE_API_KEY);
        
    if (StringUtils.isNotBlank(googleKey)) {

      try {
        
        String location = getLocationString(contactForm);
        String output = geoCode(googleKey, location);
        
        StringTokenizer st = new StringTokenizer(output, ",");
        String status = st.nextToken();
        
        if (!"200".equals(status) && StringUtils.isNotBlank(location)) {
          location += "+Switzerland";
          
          output = geoCode(googleKey, location);
          
          st = new StringTokenizer(output, ",");
          status = st.nextToken();
        }
        
        if ("200".equals(status)) {
          st.nextToken();
          String lat = st.nextToken();
          String lng = st.nextToken();
          
          ContactAttribute ca = new ContactAttribute();
          ca.setContact(contact);
          ca.setField("lat");
          ca.setValue(lat);
          contact.getContactAttributes().put("lat",ca);
          
          ca = new ContactAttribute();
          ca.setContact(contact);
          ca.setField("lng");
          ca.setValue(lng);
          contact.getContactAttributes().put("lng",ca);    
                              
          contactForm.getValueMap().put("lat", lat);
          contactForm.getValueMap().put("lng", lng);
          contactForm.setShowMap(true);
          
        } else {
          contactForm.setShowMap(false);
          contact.getContactAttributes().remove("lat");
          contact.getContactAttributes().remove("lng");
        }
        
        
        //Home Map
        location = getHomeLocationString(contactForm);
        output = geoCode(googleKey, location);
        
        st = new StringTokenizer(output, ",");
        status = st.nextToken();
        
        if (!"200".equals(status) && StringUtils.isNotBlank(location)) {
          location += "+Switzerland";
          
          output = geoCode(googleKey, location);
          
          st = new StringTokenizer(output, ",");
          status = st.nextToken();
        }
        
        if ("200".equals(status)) {
          st.nextToken();
          String lat = st.nextToken();
          String lng = st.nextToken();
          
          ContactAttribute ca = new ContactAttribute();
          ca.setContact(contact);
          ca.setField("homeLat");
          ca.setValue(lat);
          contact.getContactAttributes().put("homeLat",ca);
          
          ca = new ContactAttribute();
          ca.setContact(contact);
          ca.setField("homeLng");
          ca.setValue(lng);
          contact.getContactAttributes().put("homeLng",ca);    
                              
          contactForm.getValueMap().put("homeLat", lat);
          contactForm.getValueMap().put("homeLng", lng);
          contactForm.setShowHomeMap(true);
          
        } else {
          contactForm.setShowHomeMap(false);
          contact.getContactAttributes().remove("homeLat");
          contact.getContactAttributes().remove("homeLng");
        }        
        

      } catch (MalformedURLException e) {     
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
  
    }    
    
  }

  private String getLocationString(ContactForm contactForm) {
    String street = (String) contactForm.getValueMap().get("addressStreet");
    String poCode = (String) contactForm.getValueMap().get("addressPostalCode");
    String city =  (String) contactForm.getValueMap().get("addressCity");
    String country =  (String) contactForm.getValueMap().get("addressCountry");
    
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

  private String getHomeLocationString(ContactForm contactForm) {
    String street = (String) contactForm.getValueMap().get("homeStreet");
    String poCode = (String) contactForm.getValueMap().get("homePostalCode");
    String city =  (String) contactForm.getValueMap().get("homeCity");
    String country =  (String) contactForm.getValueMap().get("homeCountry");
    
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
  
  private String geoCode(String googleKey, String location) throws IOException {
    String urlString = MessageFormat.format(Constants.BASE_GOOGLE_URL, location, googleKey);
    
    URL url = new URL(urlString);
    URLConnection conn = url.openConnection();
    
    InputStream is = conn.getInputStream();
    String output = IOUtils.toString(is);
    is.close(); 
    
    return output;
  }
  
  @Override
  // von der DB ins Form
  public void modelToForm(final ActionContext ctx, final Contact contact) {

    ContactForm contactForm = (ContactForm)ctx.form();
    
    contactForm.getValueMap().clear();
    Set<String> keys = contact.getContactAttributes().keySet();
    for (String key : keys) {
      ContactAttribute value = contact.getContactAttributes().get(key);
      contactForm.getValueMap().put(key,value.getValue());      
    }

    if (!contact.getUsers().isEmpty()) {
      contactForm.setPrivateContact(true);
    } else {
      contactForm.setPrivateContact(false);
    }
    
    String googleKey = appConfig.getStringProperty(AppConfig.GOOGLE_API_KEY);
    String lat = (String)contactForm.getValueMap().get("lat");
    String homeLat = (String)contactForm.getValueMap().get("homeLat");
    contactForm.setShowMap(StringUtils.isNotBlank(googleKey) && StringUtils.isNotBlank(lat));
    contactForm.setShowHomeMap(StringUtils.isNotBlank(googleKey) && StringUtils.isNotBlank(homeLat));
    contactForm.setGoogleApiKey(googleKey);
    
  }
  
  private void updateCategory(Contact contact) {
    
    ContactAttribute ca = contact.getContactAttributes().get("lastName");
    if (ca == null) {
      ca = contact.getContactAttributes().get("firstName");
    }
    if (ca == null) {
      ca = contact.getContactAttributes().get("companyName");
    }
              
    if (ca != null) {
      String val = ca.getValue();
      if (StringUtils.isNotBlank(val)) {
        contact.setCategory(val.substring(0, 1).toLowerCase());
      }
    } else {
      contact.setCategory(" ");
    }    
  }  
}