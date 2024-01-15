package ch.ess.mvs.db4o;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

public class TextReader {

  private final static Integer ENDMARK = "#####".hashCode();
  private ObjectContainer db;
  
  
  public TextReader(ObjectContainer db) {
    this.db = db;
  }

  public void readDir(File dir) throws IOException {
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        readFile(files[i]);
      }
    }
  }

  public void readFile(File file) throws IOException{
    System.out.println("reading ... : " + file);

    BufferedReader br = new BufferedReader(new FileReader(file));

    String line;
    List<String> words = new ArrayList<String>();

    while ((line = br.readLine()) != null) {
      StringTokenizer st = new StringTokenizer(line);
      while (st.hasMoreTokens()) {
        String token = st.nextToken();

        words.add(token);
        addWord2Db(token);
      }
    }
    br.close();


    int ix = 0;

    if (words.size() >= 2) {

      while (ix + 2 < words.size()) {
        String word1 = words.get(ix);
        String word2 = words.get(ix + 1);
        String word3 = words.get(ix + 2);

        int word1Int = word1.hashCode();
        int word2Int = word2.hashCode();
        int word3Int = word3.hashCode();

        addWords(word1Int, word2Int, word3Int);

        if (ix == 0) {
          if (Character.isUpperCase(word1.charAt(0))) {
            addWordsToBeginList(word1Int, word2Int);
          }
        } else {
          String word0 = words.get(ix - 1);
          if ((Character.isUpperCase(word0.charAt(0))) && (word0.endsWith("."))) {
            addWordsToBeginList(word1Int, word2Int);
          }
        }
        ix++;


      }

      String word1 = words.get(ix);
      String word2 = words.get(ix + 1);
      addWords(word1.hashCode(), word2.hashCode(), ENDMARK);
    }

  }

  public void addWordsToBeginList(final int w1, final int w2) {

    
    List<Beginlist> beginlist = db.query(new Predicate<Beginlist>() {
      @Override
      public boolean match(Beginlist bl) {
        return bl.getWord1() == w1 && bl.getWord2() == w2;
      }
    });
    
    
    if (beginlist.isEmpty()) {
      //nicht gefunden
      Beginlist newEntry = new Beginlist();
      newEntry.setWord1(w1);
      newEntry.setWord2(w2);
      newEntry.setTotal(1);
      db.set(newEntry);
    } else {
      //gefunden
      Beginlist bl = beginlist.get(0);
      bl.setTotal(bl.getTotal() + 1);
      db.set(bl);
    }
  }

  public void addWords(final int hash1, final int hash2, final int hash3) {

    List<Word12> word12List = db.query(new Predicate<Word12>() {
      @Override
      public boolean match(Word12 w12) {
        return w12.getWord1() == hash1 && w12.getWord2() == hash2;
      }
    });
    

    if (word12List.isEmpty()) {

      //nicht gefunden      
      Word12 w12 = new Word12();
      w12.setWord1(hash1);
      w12.setWord2(hash2);
      w12.setTotal(1);
      db.set(w12);

      Word3 w3 = new Word3();
      w3.setWord1(hash1);
      w3.setWord2(hash2);
      w3.setWord3(hash3);
      w3.setHits(1);

      db.set(w3);

    } else {

      //gefunden
      Word12 word12 = word12List.get(0);
      word12.setTotal(word12.getTotal() + 1);
      db.set(word12);

      
      List<Word3> word3List = db.query(new Predicate<Word3>() {
        @Override
        public boolean match(Word3 w3) {
          return w3.getWord1() == hash1 && w3.getWord2() == hash2 && w3.getWord3() == hash3;
        }
      });
      

      if (word3List.isEmpty()) {
        //nicht gefunden
        Word3 w3 = new Word3();
        w3.setWord1(hash1);
        w3.setWord2(hash2);
        w3.setWord3(hash3);
        w3.setHits(1);

        db.set(w3);
      } else {

        Word3 w3 = word3List.get(0);
        w3.setHits(w3.getHits() + 1);
        db.set(w3);

      }

    }

  }

  private void addWord2Db(String word) {
    
    final int hashCode = word.hashCode();
    List<Wordlist> wordlist = db.query(new Predicate<Wordlist>() {
      @Override
      public boolean match(Wordlist wl) {
        return wl.getHash() == hashCode;
      }
    });
    
    if (wordlist.isEmpty()) {
      Wordlist wl = new Wordlist();
      wl.setHash(hashCode);
      wl.setWord(word);
      db.set(wl);
    }
    
  }

  public static void main(String args[]) {
    
    ObjectContainer db = Db4o.openFile("D:/eclipse/workspace/mvs/mvs.yap");
    Db4o.configure().objectClass(Beginlist.class).objectField("word1").indexed(true);
    Db4o.configure().objectClass(Beginlist.class).objectField("word2").indexed(true);
    Db4o.configure().objectClass(Word12.class).objectField("word1").indexed(true);
    Db4o.configure().objectClass(Word12.class).objectField("word2").indexed(true);
    Db4o.configure().objectClass(Word3.class).objectField("word1").indexed(true);
    Db4o.configure().objectClass(Word3.class).objectField("word2").indexed(true);
    Db4o.configure().objectClass(Word3.class).objectField("word3").indexed(true);
    Db4o.configure().objectClass(Wordlist.class).objectField("hash").indexed(true);
    
    
//    ((YapStream)db).getNativeQueryHandler().addListener(new
//        Db4oQueryExecutionListener() {
//        public void notifyQueryExecuted(NQOptimizationInfo info) {
//        System.err.println(info);
//        }
//        });
    
    
    
    long start = System.currentTimeMillis();

    try {
      if (args.length == 1) {
        File file = new File(args[0]);
        if (file.exists()) {
          TextReader tr = new TextReader(db);
          if (file.isDirectory()) {
            tr.readDir(file);
          } else {
            tr.readFile(file);
          }

        } else {
          System.err.println(file + " does not exists");
        }
      } else {
        System.out.println("java mvs.TextReader <file|dir>");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      db.close();
    }

    System.out.println((System.currentTimeMillis() - start) + " ms");
  }

}