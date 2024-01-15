package ch.ess.addressbook.tools;

import java.io.*;
import java.util.*;

import net.sf.hibernate.*;
import ch.ess.addressbook.db.*;
import ch.ess.addressbook.mapping.contacts.*;
import ch.ess.addressbook.mapping.contacts.Contact;
import ch.ess.addressbook.mapping.contacts.types.*;
import ch.ess.addressbook.resource.*;



public class Xml2BO {

  public static void main(String[] args) {
    try {
      
      List classList = new ArrayList();
      classList.add(ch.ess.addressbook.db.Contact.class);
      HibernateManager.init(classList);      
      SessionFactory sf = HibernateManager.createConfiguration().buildSessionFactory();
      Session session = sf.openSession();
 

      BufferedReader br = new BufferedReader(new FileReader("D:\\eclipse\\workspace\\addressbook\\build\\contacts.xml"));
      Contacts contacts = Contacts.unmarshal(br);
      br.close();

      Contact[] contact = contacts.getContact();
      if (contact != null) {
        for (int i = 0; i < contact.length; i++) {
          Attribut[] attributes = contact[i].getAttribut();

          ch.ess.addressbook.db.Contact dbContact = new ch.ess.addressbook.db.Contact();
          
          if (attributes != null) {
            Map attrMap = new HashMap();  
            for (int j = 0; j < attributes.length; j++) {
              Attribut attr = attributes[j]; 
                         
              attrMap.put(attr.getName().toString(), attr.getContent());

              if (attr.getName().getType() == AttributNames.LASTNAME_TYPE) {
                if ((attr.getContent() != null) && (dbContact.getCategory() == null)) {
                  dbContact.setCategory(attr.getContent().substring(0, 1).toLowerCase());
                }
              }
              
               
            }
            
            dbContact.setAttributes(attrMap);
            
          }
          
          if (dbContact.getCategory() == null) {
            
            String val = (String)dbContact.getAttributes().get(AttributeEnum.FIRST_NAME.getName());
            if (val != null) {
              dbContact.setCategory(val.substring(0, 1).toLowerCase());
            }
          }
          
          if (dbContact.getCategory() == null) {
            String val = (String)dbContact.getAttributes().get(AttributeEnum.COMPANY_NAME.getName());
            if (val != null) {
              dbContact.setCategory(val.substring(0, 1).toLowerCase());
            }                        
          }
          
          if (dbContact.getCategory() == null) {
            dbContact.setCategory(" ");
          }
          
          session.save(dbContact);
         
          

        }
      }
      session.flush();
      session.connection().commit();
      session.close();
      sf.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
