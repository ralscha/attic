package ch.ess.task.web.task.search;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.search.SearchEngine;
import ch.ess.task.db.Task;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class UpdateTaskRunnable implements Runnable {

  private static final Log LOG = LogFactory.getLog(UpdateTaskRunnable.class);

  private Long id;

  public UpdateTaskRunnable(Long id) {
    this.id = id;
  }

  public void run() {

    Transaction tx = null;

    try {

      Session sess = sess = HibernateSession.currentSession();
      tx = sess.beginTransaction();

      Task task = (Task)sess.get(Task.class, id);

      if (task != null) {

        //first delete
        Term t = new Term(Constants.SEARCH_ID, id.toString());
        SearchEngine.deleteTerm(t);

        //second insert
        TaskDocument doc = new TaskDocument(task);

        IndexWriter writer = null;

        writer = SearchEngine.getIndexWriter(false);

        writer.addDocument(doc.getDocument());
        writer.close();
      } else {
        LOG.error("UpdateTaskRunnable: user == null");
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
