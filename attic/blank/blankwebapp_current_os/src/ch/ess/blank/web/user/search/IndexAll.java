package ch.ess.blank.web.user.search;

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
 * @author Ralph Schaer
 * @version $Revision: 1.5 $ $Date: 2004/05/22 12:24:42 $
 */
public class IndexAll implements Job {

  private static final Log LOG = LogFactory.getLog(UpdateUserRunnable.class);

  public void execute(JobExecutionContext context) {

    Transaction tx = null;

    try {
      Session sess = HibernateSession.currentSession();
      tx = sess.beginTransaction();

      //create new index
      IndexWriter writer = SearchEngine.getIndexWriter(true);
      writer.close();

      List resultList = sess.find("select user.id from User as user");

      Iterator it = resultList.iterator();
      while (it.hasNext()) {
        Long id = (Long) it.next();
        SearchEngine.updateIndex(new UpdateUserRunnable(id));
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