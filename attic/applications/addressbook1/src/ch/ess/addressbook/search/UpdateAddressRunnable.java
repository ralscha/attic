package ch.ess.addressbook.search;

import org.apache.commons.logging.*;
import org.apache.lucene.index.*;

import ch.ess.addressbook.db.*;
import ch.ess.addressbook.resource.*;

public class UpdateAddressRunnable implements Runnable {

  private static final Log logger = LogFactory.getLog(UpdateAddressRunnable.class);

  private Long id;

  public UpdateAddressRunnable(Long id) {
    this.id = id;
  }

  public void run() {

    net.sf.hibernate.Session sess = null;

    try {

      sess = sess = HibernateManager.open();  
      
      Contact contact = (Contact)sess.load(Contact.class, id);
      
      if (contact != null) {

        //first delete
        Term t = new Term("id", id.toString());
        SearchEngine.deleteTerm(t);
  
        //second insert
        ContactSearchDocument doc = new ContactSearchDocument(contact);
        
        IndexWriter writer = null;
  
        writer = SearchEngine.getIndexWriter(false);
  
        writer.addDocument(doc.getDocument());
        writer.close();
      } else {
        logger.error("UpdateAddressRunnable: contact == null");
      }
      
      
      HibernateManager.commit(sess);


    } catch (Exception dbse) {
      HibernateManager.exceptionHandling(sess);
      logger.error("UpdateAddressRunnable", dbse);

    } finally {
      HibernateManager.finallyHandling(sess);
    }   
   
  }

}
