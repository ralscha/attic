
package mvs;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import common.util.*;
import common.io.*;
import com.objectmatter.bsf.*;
import mvs.db.*;

public class GenerateText {
  
  private final static long ENDMARK = "#####".hashCode();
  private Random r = new Random();

  private Database db = null;
  
  public GenerateText() {
    try {
      db = new Database("mvs.schema");
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


  public void go() {
    long sum, total, rnd, next, t, w1, w2;

    StringBuffer line = new StringBuffer();

    total = 0;
    try {
		  DBSReader rdr = db.getValues("beginListSum", null, 1);
		  rdr.nextRow();
		  total = rdr.getInt(1);
	  } catch (Exception e) {
	    System.err.println(e);
      System.exit(1);
	  }

    sum = 0;
    w1 = 0;
    w2 = 0;
    rnd = Math.abs((r.nextLong() % total)) + 1;

    Beginlist[] bl = (Beginlist[])db.list(Beginlist.class);
    if (bl != null) {
      int i = 0;
      while ((i < bl.length) && (sum < rnd)) {
        w1 = bl[i].getWord1();
        w2 = bl[i].getWord2();
        t  = bl[i].getTotal();
        sum += t;
        i++;
      }
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

  public String getWord(long hash) {
    Wordlist wl = (Wordlist)db.lookup(Wordlist.class, hash);
    if (wl != null) {
      return wl.getWord();
    } else {
      System.out.println(hash);
      return "";
    
    }
  }


  public long getNext(long w1, long w2) {

    OQuery query = new OQuery();
    query.add(w1, "word1");
    query.add(w2, "word2");

    Word12[] word12 = (Word12[])db.get(Word12.class, query);
    if (word12 != null) {
      long total = word12[0].getTotal();
      long sum = 0;
      long rnd = Math.abs((r.nextLong() % total)) + 1;
      long w3 = ENDMARK;
      Word3[] words3 = word12[0].getWords3();
      if (words3 != null) {
        int i = 0;
        while ((i < words3.length) && (sum < rnd)) {
          w3 = words3[i].getWord3();
          long t = words3[i].getHits();
          sum += t;
          i++;
        }
        return w3;
      } 
    
    }
      
    return ENDMARK;
  }    



	public static void main(String args[]) {
    new GenerateText().go();
	}
 
}