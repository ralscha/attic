package sr.mvs;

import java.io.*;
import java.sql.*;
import java.util.*;

import cirrus.hibernate.*;
import cirrus.hibernate.type.*;

import sr.mvs.db.*;

public class TextReader {

  private final static Integer ENDMARK = new Integer("#####".hashCode());
  private Session session;
  
  private final static Type[] TYPETWO = new Type[]{Hibernate.INTEGER, Hibernate.INTEGER};
  private final static Type[] TYPETHREE = new Type[]{Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER};

  private SessionFactory sf;

  public TextReader() throws IOException, HibernateException, SQLException {
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
  }

  public void shutDown() throws HibernateException, SQLException {
    session.flush();
    session.connection().commit();

    session.close();
  }

  public void readDir(File dir) throws HibernateException, IOException, SQLException {
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++) {
      readFile(files[i]);
      shutDown();
      session = sf.openSession();    
    }
  }

  public void readFile(File file) throws IOException, HibernateException, SQLException {
    System.out.println("reading ... : " + file);

    BufferedReader br = new BufferedReader(new TagFilterReader(new FileReader(file)));    
    
    String line;
    List words = new ArrayList();

    while ((line = br.readLine()) != null) {
      StringTokenizer st = new StringTokenizer(line);
      while (st.hasMoreTokens()) {
        String token = st.nextToken();
        token = Util.convertText(token);

        words.add(token);
        addWord2Db(token);
      }
    }
    shutDown();
    br.close();
    
    session = sf.openSession();


    int ix = 0;

    if (words.size() >= 2) {

      while (ix + 2 < words.size()) {
        String word1 = (String) words.get(ix);
        String word2 = (String) words.get(ix + 1);
        String word3 = (String) words.get(ix + 2);
        
        Integer word1Int = new Integer(word1.hashCode());
        Integer word2Int = new Integer(word2.hashCode());
        Integer word3Int = new Integer(word3.hashCode());

        addWords(word1Int, word2Int, word3Int);

        if (ix == 0) {
          if (Character.isUpperCase(word1.charAt(0))) {
            addWordsToBeginList(word1Int, word2Int);
          }
        } else {
          String word0 = (String) words.get(ix - 1);
          if ((Character.isUpperCase(word0.charAt(0))) && (word0.endsWith("."))) {
            addWordsToBeginList(word1Int, word2Int);
          }
        }
        ix++;
        
        if (ix >= 50) {
          shutDown();
          session = sf.openSession();        
        }
        
      }

      String word1 = (String) words.get(ix);
      String word2 = (String) words.get(ix + 1);
      Integer word1Int = new Integer(word1.hashCode());
      Integer word2Int = new Integer(word2.hashCode());      
      addWords(word1Int, word2Int, ENDMARK);
    }

  }

  public void addWordsToBeginList(Integer w1, Integer w2) throws HibernateException, SQLException {
    
    List beginList = session.find("from beginList in class Beginlist where beginList.word1 = ? and beginList.word2 = ?",
              new Object[]{w1, w2},
              TYPETWO);
    
    if (beginList.isEmpty()) {
      //nicht gefunden
      Beginlist newEntry = new Beginlist();
      newEntry.setWord1(w1.intValue());
      newEntry.setWord2(w2.intValue());
      newEntry.setTotal(1);
      session.save(newEntry);
    } else {
      //gefunden
      Beginlist bl = (Beginlist)beginList.get(0);
      bl.setTotal(bl.getTotal() + 1);
      session.update(bl);
    }
  }

  public void addWords(Integer hash1, Integer hash2, Integer hash3) throws HibernateException, SQLException {

    List word12List = session.find("from word12 in class Word12 where word12.word1 = ? and word12.word2 = ?",
              new Object[]{hash1, hash2},
              TYPETWO);
    
    if (word12List.isEmpty()) {

      //nicht gefunden      
      Word12 w12 = new Word12();
      w12.setWord1(hash1.intValue());
      w12.setWord2(hash2.intValue());
      w12.setTotal(1);
      session.save(w12);
      
      
      Word3 w3 = new Word3();
      w3.setWord1(hash1.intValue());
      w3.setWord2(hash2.intValue());
      w3.setWord3(hash3.intValue());
      w3.setHits(1);

      session.save(w3);

    } else {
      
      
      //gefunden
      Word12 word12 = (Word12)word12List.get(0);      
      word12.setTotal(word12.getTotal() + 1);
      
      List word3List = session.find("from word3 in class Word3 where word3.word1 = ? and word3.word2 = ? and word3.word3 = ?",
                new Object[]{hash1, hash2, hash3},
                TYPETHREE);      

      if (word3List.isEmpty()) {
        //nicht gefunden
        Word3 w3 = new Word3();
        w3.setWord1(hash1.intValue());
        w3.setWord2(hash2.intValue());
        w3.setWord3(hash3.intValue());
        w3.setHits(1);

        session.save(w3);
      } else {
        
        Word3 w3 = (Word3)word3List.get(0);
        w3.setHits(w3.getHits() + 1);
        session.update(w3);
        
      }

    }

  }

  private void addWord2Db(String word) throws HibernateException, SQLException {
    Wordlist wl;
    
    try {
      wl = (Wordlist)session.load(Wordlist.class, new Integer(word.hashCode()));
    } catch (HibernateException e) {
      //e.printStackTrace();
      wl = null;
    } 
    
   
    if (wl == null) {      
      wl = new Wordlist();      
      wl.setHash(word.hashCode());
      wl.setWord(word);
      
      session.save(wl);      
    }
  }

  public static void main(String args[]) {
   
    long start = System.currentTimeMillis();
    
    try {
      if (args.length == 1) {
        File file = new File(args[0]);
        if (file.exists()) {
          TextReader tr = new TextReader();
          if (file.isDirectory()) {
            tr.readDir(file);
          } else {
            tr.readFile(file);
          }
          tr.shutDown();
        } else {
          System.err.println(file + " does not exists");
        }
      } else {
        System.out.println("java mvs.TextReader <file|dir>");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    System.out.println((System.currentTimeMillis() - start) + " ms");
  }

}