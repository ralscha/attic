package ch.ess.addressbook.tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import ch.ess.addressbook.db.AttributeEnum;
import ch.ess.addressbook.mapping.contacts.Attribut;
import ch.ess.addressbook.mapping.contacts.Contact;
import ch.ess.addressbook.mapping.contacts.Contacts;
import ch.ess.addressbook.mapping.contacts.types.AttributNames;
import ch.ess.common.db.HibernateFactoryManager;
import ch.ess.common.db.HibernateSession;



public class Xml2BO {

  public static void main(String[] args) {
    Transaction tx = null;
    try {
      
      HibernateFactoryManager.initXML("/ch/ess/addressbook/tool/hibernate.cfg.xml");


      Session session = HibernateSession.currentSession();
      tx = session.beginTransaction();
 

      BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\contacts.xml"));
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
      tx.commit();
      HibernateSession.closeSession();
      
    } catch (Exception e) {
      HibernateSession.rollback(tx);
      e.printStackTrace();
    }

  }
}
