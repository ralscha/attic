package ch.ess.addressbook.web.contact.search;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import ch.ess.addressbook.db.Contact;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.search.SearchEngine;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class UpdateContactRunnable implements Runnable {

  private static final Log LOG = LogFactory.getLog(UpdateContactRunnable.class);

  private Long id;

  public UpdateContactRunnable(Long id) {
    this.id = id;
  }

  public void run() {

    Transaction tx = null;

    try {

      Session sess = sess = HibernateSession.currentSession();
      tx = sess.beginTransaction();

      Contact contact = (Contact)sess.load(Contact.class, id);

      if (contact != null) {

        //first delete
        Term t = new Term(Constants.SEARCH_ID, id.toString());
        SearchEngine.deleteTerm(t);

        //second insert
        ContactDocument doc = new ContactDocument(contact);

        IndexWriter writer = null;

        writer = SearchEngine.getIndexWriter(false);

        writer.addDocument(doc.getDocument());
        writer.close();
      } else {
        LOG.error("UpdateUserRunnable: user == null");
      }

      tx.commit();

    } catch (Exception dbse) {
      HibernateSession.rollback(tx);
      LOG.error("UpdateUserRunnable", dbse);

    } finally {
      HibernateSession.closeSession();
    }

  }

}
