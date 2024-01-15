package sr.mvs;

import java.util.*;

import sr.mvs.db.*;
import cirrus.hibernate.*;

public class TestMvs {

  public static void main(String[] args) {
    try {
      Datastore ds = Hibernate.createDatastore();
      ds.storeClass(Beginlist.class);
      ds.storeClass(Word12.class);
      ds.storeClass(Word3.class);
      ds.storeClass(Wordlist.class);


      // First read in those Properties from my file
      Properties dbProps = new Properties();
      dbProps.load(TestMvs.class.getResourceAsStream("/hibernate.properties"));

      // Then build a session to the database
      SessionFactory sf = ds.buildSessionFactory(dbProps);
      Session session = sf.openSession();

      
      
      Wordlist wl = new Wordlist();
      String test = "TEST";
      
      wl.setHash(test.hashCode());
      wl.setWord(test);
      
      session.save(wl);




      session.flush();
      session.connection().commit();

      session.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
