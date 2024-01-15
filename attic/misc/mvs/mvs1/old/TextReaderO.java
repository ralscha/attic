package mvs;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import common.util.*;
import common.io.*;
import mvs.dbo.*;
import net.sourceforge.osage.api.*;
import net.sourceforge.osage.Database;
import net.sourceforge.osage.util.*;
import net.sourceforge.osage.*;


public class TextReaderO {
  
  private final static String ENDMARK = "#####";

  private PersistenceManager persistenceManager = null;
  private Database db = null;

  public TextReaderO() {
    try {
      persistenceManager = PersistenceManagerFactory.create();
      Configuration conf = ConfigurationBuilder.build("d:/javaprojects/private/mvs/osage/ox.xml");
      persistenceManager.setConfiguration(conf);
      db = persistenceManager.getDatabase();
      db.setLogger(new PrintWriter(new FileWriter("log")));
    } catch (Exception e) {
      System.err.println(e);
      System.exit(1);
    }
  }

  public void shutDown() {
    try {
      if (db != null)
        db.close();
    } catch (Exception e) { }

    if (persistenceManager != null)
      persistenceManager.destroy();          
  }

  public void readDir(File dir) {
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++) {
      readFile(files[i]);
    }
  }
  		
  public void readFile(File file) {  
    System.out.println("reading ... : " + file);
 		try {
      BufferedReader br = new BufferedReader(new TagFilterReader(new FileReader(file)));

      String line;
      List words = new ArrayList();

      db.begin();

			while ((line = br.readLine()) != null) {
			  StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
				  String token = st.nextToken();						
					token = MVSUtil.convertText(token);
						
					words.add(token);						
          addWord2Db(token);
				}
			}

      db.commit();

      int ix = 0;

			if (words.size() >= 2)  {				
        

				while (ix + 2 < words.size()) {
          String word1 = (String)words.get(ix);
          String word2 = (String)words.get(ix+1);
          String word3 = (String)words.get(ix+2);

					addWords(word1.hashCode(), word2.hashCode(), word3.hashCode());
          
					if (ix == 0) {					  
						if (Character.isUpperCase(word1.charAt(0))) {
						  addWordsToBeginList(word1.hashCode(), word2.hashCode());
						}
					} else {            
						String word0 = (String)words.get(ix-1);
						if ((Character.isUpperCase(word1.charAt(0))) && (word0.endsWith("."))) {
							addWordsToBeginList(word1.hashCode(), word2.hashCode());
						}
					}
					ix++;
				}
        
				String word1 = (String)words.get(ix);
				String word2 = (String)words.get(ix+1);
				addWords(word1.hashCode(), word2.hashCode(), ENDMARK.hashCode());

			}


    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e);
      shutDown();
      System.exit(1);
    } 
	}

	public void addWordsToBeginList(int w1, int w2) throws Exception {
    db.begin();

    RetrieveCriteria criteria = new RetrieveCriteria(Beginlist.FOR_NAME);
    criteria.addSelectEqualTo(Beginlist.WORD1, w1);
    criteria.addSelectEqualTo(Beginlist.WORD2, w2);
    
    Beginlist bl = (Beginlist)db.processCriteriaForOne(criteria);
    
    if (bl == null) {
      //nicht gefunden

      Beginlist newEntry = new Beginlist();
      newEntry.setWord1(w1);
      newEntry.setWord2(w2);
      newEntry.setTotal(1);
      db.save(newEntry);
    } else { 
      //gefunden
      bl.setTotal(bl.getTotal()+1);
      db.save(bl);      
    }		
    db.commit();
	}

	public void test() throws Exception {
	  db.begin();

	  RetrieveCriteria criteria = new RetrieveCriteria(Word12.FOR_NAME);
	  criteria.addSelectEqualTo(Word12.WORD1, 113319000);
	  criteria.addSelectEqualTo(Word12.WORD2, 113319001);
	
	  Word12 word12 = (Word12)db.processCriteriaForOne(criteria);
      
    List t = new ArrayList(word12.getWords3());
    System.out.println(t.size());
    Word3 w3 = (Word3)t.get(0);
    System.out.println(w3.getWord3());
    
	  if (word12 == null) {
	    //do something here
	  } else {
	    word12.setTotal(word12.getTotal()+1);
	    db.save(word12); 
	  }

    db.commit();
    
	}


	public void addWords(int hash1, int hash2, int hash3) throws Exception {

    db.begin();

    RetrieveCriteria criteria = new RetrieveCriteria(Word12.FOR_NAME);
    criteria.addSelectEqualTo(Word12.WORD1, hash1);
    criteria.addSelectEqualTo(Word12.WORD2, hash2);
    
    Word12 word12 = (Word12)db.processCriteriaForOne(criteria);
    System.out.println("---> "  + word12);
    if (word12 == null) {

      //nicht gefunden      
      Word12 w12 = new Word12();
      w12.setWord1(hash1);
      w12.setWord2(hash2);
      w12.setTotal(1);

      Word3 w3 = new Word3();
      w3.setWord3(hash3);
      w3.setHits(1);

      w12.addWords3(w3);      
      db.save(w12);

    } else {
      word12.setTotal(word12.getTotal()+1);
      db.save(word12);
      /*
      //gefunden
      word12.setTotal(word12.getTotal()+1);
      List words3 = new ArrayList(word12.getWords3());
      System.out.println(words3.size());

      if (words3 == null) {
        //nicht gefunden
        Word3 w3 = new Word3();
        w3.setWord3(hash3);
        w3.setHits(1);
        word12.addWords3(w3);
        db.save(word12);  
      } else {
        boolean found = false;
        Iterator it = words3.iterator(); 
        while (it.hasNext()) {
          Word3 t = (Word3)it.next();
          if (t.getWord3() == hash3) {
            t.setHits(t.getHits()+1);
            found = true;
            System.out.println(t.getWord1());
            System.out.println(t.getWord2());
            System.out.println(t.getWord3());
            //db.save(t);  
            break;
          } 
        }
        if (!found) {
        
          //nicht gefunden
          Word3 w3 = new Word3();
          w3.setWord3(hash3);
          w3.setHits(1);
          word12.addWords3(w3);
          db.save(word12);  
        }

      } */         
      
    }
    db.commit();

	}


  private void addWord2Db(String word) throws Exception {
    
    RetrieveCriteria criteria = new RetrieveCriteria(Wordlist.FOR_NAME);
    criteria.addSelectEqualTo(Wordlist.HASH, word.hashCode());
    
    Wordlist wl = (Wordlist)db.processCriteriaForOne(criteria);
    
    if (wl == null) {
      //nicht gefunden
      Wordlist newEntry = new Wordlist();
      newEntry.setWord(word);
      newEntry.setHash(word.hashCode());
      db.save(newEntry);
    }

  }

	public static void main(String args[]) {
   File file = new File("t.txt");
   TextReaderO tr = new TextReaderO();
   //tr.readFile(file);
   try { tr.test(); 
   } catch (Exception e) { 
    tr.shutDown();
    System.err.println(e);
   }
   tr.shutDown();
   /*
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
  */		
	}
 
}