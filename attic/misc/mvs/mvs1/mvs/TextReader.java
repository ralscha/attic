package mvs;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import common.util.*;
import common.io.*;
import com.objectmatter.bsf.*;
import mvs.db.*;

public class TextReader {
  
  private final static String ENDMARK = "#####";

  private com.objectmatter.bsf.Database db = null;
  
  public TextReader() {
    try {
      db = new com.objectmatter.bsf.Database("mvs.schema");
      db.open();
    } catch (BODBException e) {
      MVSUtil.dbMsg(e);
      System.exit(1);
    } catch (Exception e) {
      System.err.println(e);
      System.exit(1);
    }
  }

  public void shutDown() {
    if (db != null)
      db.close(); //Close database connection    
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

			while ((line = br.readLine()) != null) {
			  StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
				  String token = st.nextToken();						
					token = MVSUtil.convertText(token);
						
					words.add(token);						
          addWord2Db(token);
				}
			}
      
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
						if ((Character.isUpperCase(word0.charAt(0))) && (word0.endsWith("."))) {
							addWordsToBeginList(word1.hashCode(), word2.hashCode());
						}
					}
					ix++;
				}
        
				String word1 = (String)words.get(ix);
				String word2 = (String)words.get(ix+1);
				addWords(word1.hashCode(), word2.hashCode(), ENDMARK.hashCode());
			}


    } catch (BODBException e) {
      MVSUtil.dbMsg(e);
      if (db != null)
        db.close(); //Close database connection
      System.exit(1);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e);
      if (db != null)
        db.close(); //Close database connection
      System.exit(1);
    } 
	}

	public void addWordsToBeginList(int w1, int w2) throws BODBException, BOUpdateConflictException {
    OQuery query = new OQuery(Beginlist.class);
    query.add(w1, "word1");
    query.add(w2, "word2");
    Beginlist[] bl = (Beginlist[])query.execute(db);

    if ((bl == null) || (bl.length < 1)) {
      //nicht gefunden
      Beginlist newEntry = (Beginlist)db.create(Beginlist.class);  
      newEntry.setWord1(w1);
      newEntry.setWord2(w2);
      newEntry.setTotal(1);
      db.insert(newEntry);
    } else { 
      //gefunden
      bl[0].setTotal(bl[0].getTotal()+1);
      db.update(bl[0]);
    }		
	}

	public void addWords(int hash1, int hash2, int hash3) throws BODBException, BOUpdateConflictException {


    OQuery query = new OQuery(Word12.class);
    query.add(hash1, "word1");
    query.add(hash2, "word2");
    Word12[] word12 = (Word12[])query.execute(db);

    if ((word12 == null) || (word12.length < 1)) {
      //nicht gefunden      
      Word12 w12 = (Word12)db.create(Word12.class);
      w12.setWord1(hash1);
      w12.setWord2(hash2);
      w12.setTotal(1);

      Word3 w3 = (Word3)db.create(Word3.class);
      w3.setWord3(hash3);
      w3.setHits(1);

      w12.addWords3(w3);      
      db.update(w12);

    } else {
      
      //gefunden
      word12[0].setTotal(word12[0].getTotal()+1);
      
      Word3[] words3 = word12[0].getWords3();

      if ((words3 == null) || (words3.length < 1)) {
        //nicht gefunden
        Word3 w3 = (Word3)db.create(Word3.class);
        w3.setWord3(hash3);
        w3.setHits(1);
        word12[0].addWords3(w3);
        db.update(word12[0]);  
      } else {
        boolean found = false;
        for (int i = 0; i < words3.length; i++) {
          if (words3[i].getWord3() == hash3) {
            words3[i].setHits(words3[i].getHits()+1);
            found = true;
            db.update(words3[i]);  
            break;
          } 
        }
        if (!found) {

        //nicht gefunden
        Word3 w3 = (Word3)db.create(Word3.class);
        w3.setWord3(hash3);
        w3.setHits(1);
        word12[0].addWords3(w3);        
        db.update(word12[0]);  
        }

      }          
      
    }
    

	}


  private void addWord2Db(String word) throws BODBException, BOUpdateConflictException {
    Wordlist wl = (Wordlist)db.lookup(Wordlist.class, (long)word.hashCode());
    if (wl == null) {
      Wordlist newEntry = (Wordlist)db.create(Wordlist.class);      
      newEntry.setWord(word);
      newEntry.setHash(word.hashCode());
      db.insert(newEntry);
    } 
  }

	public static void main(String args[]) {
   //Debug.setDebugging(Debugger.DATABASE + Debugger.DEEP, false, false);

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
	}
 
}