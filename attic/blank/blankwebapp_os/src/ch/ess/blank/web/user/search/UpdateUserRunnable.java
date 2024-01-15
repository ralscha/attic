package ch.ess.blank.web.user.search;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import ch.ess.blank.db.User;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.search.SearchEngine;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.5 $ $Date: 2004/05/22 12:24:42 $
 */
public class UpdateUserRunnable implements Runnable {

  private static final Log LOG = LogFactory.getLog(UpdateUserRunnable.class);

  private Long id;

  public UpdateUserRunnable(Long id) {
    this.id = id;
  }

  public void run() {

    Transaction tx = null;

    try {

      Session sess = sess = HibernateSession.currentSession();
      tx = sess.beginTransaction();

      User user = (User) sess.get(User.class, id);

      if (user != null) {

        //first delete
        Term t = new Term(Constants.SEARCH_ID, id.toString());
        SearchEngine.deleteTerm(t);

        //second insert
        UserDocument doc = new UserDocument(user);

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