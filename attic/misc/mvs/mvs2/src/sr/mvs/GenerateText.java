package sr.mvs;

import java.io.*;
import java.sql.*;
import java.util.*;

import sr.mvs.db.*;
import cirrus.hibernate.*;
import cirrus.hibernate.type.*;

public class GenerateText {


    private final static Type[] TYPETWO = new Type[]{Hibernate.INTEGER, Hibernate.INTEGER};
    //private final static Type[] TYPETHREE = new Type[]{Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER};
  
    private final static int ENDMARK = "#####".hashCode();
    private Random r = new Random();

    private SessionFactory sf;
    private Session session;
  
    public GenerateText() throws IOException, HibernateException, SQLException  {
      Datastore ds = Hibernate.createDatastore();
      ds.storeClass(Beginlist.class);
      ds.storeClass(Word12.class);
      ds.storeClass(Word3.class);
      ds.storeClass(Wordlist.class);

      // First read in those Properties from my file
      Properties dbProps = new Properties();
      dbProps.load(TestMvs.class.getResourceAsStream("/hibernate.properties"));

      // Then build a session to the database
      sf = ds.buildSessionFactory(dbProps);
      session = sf.openSession();
      session.setFlushMode(FlushMode.NEVER);

    }

    public void shutDown() throws HibernateException, SQLException {
      session.flush();
      session.connection().commit();

      session.close();
    }


    public void go() throws HibernateException, SQLException {
      long sum, rnd, t;
      int w1, w2, next, total;

      StringBuffer line = new StringBuffer();

      total = 0;
      
      //SELECT SUM(total) FROM Beginlist        
      Iterator results = session.iterate("select sum(bl.total) from bl in class Beginlist");
      if (results.hasNext()) {            
        total = ((Integer)results.next()).intValue();        
      }
               
        
        
      sum = 0;
      w1 = 0;
      w2 = 0;
      rnd = Math.abs((r.nextLong() % total)) + 1;



      Iterator iter = session.iterate("from bl in class Beginlist");      
      int i = 0;
      while ((iter.hasNext()) && (sum < rnd)) {
        Beginlist bl = (Beginlist) iter.next();
        w1 = bl.getWord1();
        w2 = bl.getWord2();
        t  = bl.getTotal();
        sum += t;
        i++;
      }

      line.append(getWord(w1)).append(" ").append(getWord(w2)).append(" ");    
      next = getNext(w1, w2);
      while (next != ENDMARK) {
        line.append(getWord(next)).append(" ");      
        if (line.length() > 60) {
          System.out.println(line);
          line.setLength(0);
        }
        w1 = w2;
        w2 = next;
        next = getNext(w1, w2);
      }

      System.out.println(line);
  
    }

    public String getWord(int hash) throws SQLException {
      
      Wordlist wl;
    
      try {
        wl = (Wordlist)session.load(Wordlist.class, new Integer(hash));
      } catch (HibernateException e) {
        //e.printStackTrace();
        wl = null;
      } 

                  
      if (wl != null) {
        return wl.getWord();
      } else {
        System.out.println(hash);
        return "";   
      }
    }


    public int getNext(int w1, int w2) throws HibernateException, SQLException {
      Integer w1Int = new Integer(w1);
      Integer w2Int = new Integer(w2);
      
      List word12List = session.find("from word12 in class Word12 where word12.word1 = ? and word12.word2 = ?",
                   new Object[]{w1Int, w2Int},
                   TYPETWO);
    
      if (!word12List.isEmpty()) {
        Word12 word12 = (Word12)word12List.get(0);
        
        int total = word12.getTotal();
        int sum = 0;
        long rnd = Math.abs((r.nextLong() % total)) + 1;
        int w3 = ENDMARK;
        
        
        List word3List = session.find("from word3 in class Word3 where word3.word1 = ? and word3.word2 = ?",
                  new Object[]{w1Int, w2Int},
                  TYPETWO);      
        
        
        if (!word3List.isEmpty()) {
          int i = 0;
          while ((i < word3List.size()) && (sum < rnd)) {
            Word3 words3 = (Word3)word3List.get(i);
            w3 = words3.getWord3();
            int t = words3.getHits();
            sum += t;
            i++;
          }
          return w3;
        } 
    
      }
      
      return ENDMARK;
    }    



    public static void main(String args[]) {
      try {
        new GenerateText().go();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    }
 
  }

