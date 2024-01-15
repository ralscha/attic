package ch.ess.downloader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


public class Test {

  public static void main(String[] args) {
    
    Configuration conf = new AnnotationConfiguration().configure();
    SessionFactory sf = conf.buildSessionFactory();
    

    Session session = sf.openSession();
    
    Transaction tx = session.beginTransaction();
    
    
    tx.commit();
    session.close();

  }

}
