package ch.ess.task.web.task.search;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.search.OptimizeRunnable;
import ch.ess.common.search.SearchEngine;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class IndexAll implements Job {

  private static final Log LOG = LogFactory.getLog(UpdateTaskRunnable.class);

  public void execute(JobExecutionContext context) {

    Transaction tx = null;

    try {
      Session sess = HibernateSession.currentSession();
      tx = sess.beginTransaction();

      //create new index
      IndexWriter writer = SearchEngine.getIndexWriter(true);
      writer.close();

      List resultList = sess.find("select task.id from Task as task");

      Iterator it = resultList.iterator();
      while (it.hasNext()) {
        Long id = (Long)it.next();
        SearchEngine.updateIndex(new UpdateTaskRunnable(id));
      }

      SearchEngine.updateIndex(new OptimizeRunnable());
      tx.commit();

    } catch (Exception dbse) {
      HibernateSession.rollback(tx);
      LOG.error("IndexAll", dbse);

    } finally {
      HibernateSession.closeSession();
    }

  }

}
