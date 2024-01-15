package ch.ess.addressbook.search;

import java.util.*;

import org.apache.lucene.index.*;
import org.quartz.*;

import ch.ess.addressbook.resource.*;


public class IndexAll implements Job {


  public void execute(JobExecutionContext context) throws JobExecutionException {
                                   
                  
    net.sf.hibernate.Session sess = null;

    try {

      sess = HibernateManager.open();      
            
      //create new index
      IndexWriter writer = SearchEngine.getIndexWriter(true);
      writer.close();
      

      List resultList = sess.find("select contact.id from Contact as contact");    
      
      Iterator it = resultList.iterator();
      while (it.hasNext()) {
        Long id = (Long)it.next();        
        SearchEngine.updateIndex(new UpdateAddressRunnable(id));  
      }

      SearchEngine.updateIndex(new OptimizeRunnable());
      
      
      HibernateManager.commit(sess);

      
    } catch (Exception dbse) {
      HibernateManager.exceptionHandling(sess);
      throw new JobExecutionException(dbse, false);

    } finally {
      HibernateManager.finallyHandling(sess);
    }   

  }

}
