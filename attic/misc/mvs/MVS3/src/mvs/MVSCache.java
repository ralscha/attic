package mvs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import com.intersys.objects.CacheDatabase;
import com.intersys.objects.CacheException;
import com.intersys.objects.CacheQuery;
import com.intersys.objects.Database;

public class MVSCache {

  int refno;
  Random r = new Random();

  String endmarkstring = "#####";
  Integer endmark = new Integer(endmarkstring.hashCode());

  private CacheQuery selectWordFromWordListQuery;
  private CacheQuery selectWord12Query;
  private CacheQuery selectWord3Query;
  
  PreparedStatement sWLstmt, sW12stmt, sW3stmt;
  
  public MVSCache(String args[]) throws CacheException {

    Database db = CacheDatabase.getDatabase("jdbc:Cache://localhost:1972/MVS", "_SYSTEM", "SYS");
    selectWordFromWordListQuery = new CacheQuery(db, "SELECT Word FROM mvs.WordList where Hash = ?");
    selectWord12Query = new CacheQuery(db, "SELECT Word3Ref,Total FROM mvs.Word12 WHERE Word1 = ? AND Word2 = ?");
    selectWord3Query = new CacheQuery(db, "SELECT Word3, Hits FROM mvs.Word3 WHERE Ref = ?");
    
    List files;
    List hash, words;
    String input, help;
    int ix;
    ResultSet rs;


    if (args.length >= 1) {
      if (args[0].equals("G")) {
        
        long start = System.currentTimeMillis();
        int noOfWords = 0;
        
        int t, sum, w1, w2, next, total, rnd;

        StringBuffer line = new StringBuffer();

        try {
          
          for (int i = 0; i < 50; i++) {
          total = 800;//BeginList.SumTotal(db).intValue();
          
          
          sum = 0;
          w1 = 0;
          w2 = 0;
          rnd = Math.abs((r.nextInt() % total)) + 1;
          
          
          Iterator it = db.openByQuery(BeginList.getCacheClassName(), "");
          while (it.hasNext() && (sum < rnd)) {
            BeginList beginList = (BeginList)it.next();
            w1 = beginList.getword1().intValue();
            w2 = beginList.getword2().intValue();
            t = beginList.gettotal().intValue();
            sum += t;
           
         }
  
         line.append(getWord(w1)).append(" ").append(getWord(w2)).append(" ");
         next = getNext(new Integer(w1), new Integer(w2));
  
         while (next != endmark.hashCode()) {
           line.append(getWord(next)).append(" ");
           noOfWords++;
           if (line.length() > 60) {
             System.out.println(line);
             line.setLength(0);
           }
           w1 = w2;
           w2 = next;
           next = getNext(new Integer(w1), new Integer(w2));
         }
         System.out.println(line);
          }


        } catch (Exception ex) {
          ex.printStackTrace();
        }
        
        long elapsed = (System.currentTimeMillis() - start) / 1000;
        long wordsPerSecond = noOfWords / elapsed;
                
        System.out.println(wordsPerSecond + " Words/Second");

      }

      else {
        
        long start = System.currentTimeMillis();
        
        files = new ArrayList();

        if (args[0].equals("all")) {
          File f = new File(args[1]);
          String lst[] = f.list();

          for (int i = 0; i < lst.length; i++) {
            if (lst[i].endsWith(".txt"))
              files.add(args[1] + "\\" + lst[i]);
          }

        } else {
          for (int i = 0; i < args.length; i++)
            files.add(args[i]);
        }

 
        try {
 
 
          hash = new Vector();
          words = new Vector();

          Iterator it = db.openByQuery(RefNo.getCacheClassName(), "");
          RefNo dbref = null;
          if (it.hasNext()) {
            dbref = (RefNo)it.next();
            refno = dbref.getrefno().intValue();            
          } else {
            refno = 1;
          }

          for (int i = 0; i < files.size(); i++) {
            hash.clear();
            words.clear();

            
            BufferedReader br = new BufferedReader(new FileReader((String)files.get(i)));
                        

            System.out.println("Reading ... : " + (String)files.get(i));


            
            while ((input = br.readLine()) != null) {
              StringTokenizer st = new StringTokenizer(input);
              while (st.hasMoreTokens()) {
                help = st.nextToken();
                Integer hashCode = new Integer(help.hashCode()); 
                hash.add(hashCode);
                words.add(help);
                
                
                rs = selectWordFromWordListQuery.execute(hashCode);
                if (!rs.next()) {
                  WordList newWordList = new WordList(db);
                  newWordList.sethash(hashCode);
                  newWordList.setword(help);
                  newWordList._save();
                }
                rs.close();
                
       
              }
            }
            
            br.close();

            ix = 0;
            String ws;

            while (ix + 2 < hash.size()) {
              addWords(db, (Integer)hash.get(ix), (Integer)hash.get(ix + 1), (Integer)hash.get(ix + 2));

              if (ix == 0) {
                ws = (String)words.get(ix);
                if (Character.isUpperCase(ws.charAt(0)))
                  addWordsToBeginList(db, (Integer)hash.get(ix), (Integer)hash.get(ix + 1));
              } else {
                ws = (String)words.get(ix - 1);
                if ((Character.isUpperCase(ws.charAt(0))) && (ws.endsWith(".")))
                  addWordsToBeginList(db,(Integer)hash.get(ix), (Integer)hash.get(ix + 1));
              }

              ix++;
            }

            addWords(db, (Integer)hash.get(ix), (Integer)hash.get(ix + 1), endmark);

            
            if (dbref != null) {
              dbref.setrefno(new Integer(refno));
              dbref._save();
            } else {
              dbref = new RefNo(db);
              dbref.setrefno(new Integer(refno));
              dbref._save();
            }
          } /* for */
       
        } catch (Exception ex) {
          ex.printStackTrace();
        }

        
        System.out.println(((System.currentTimeMillis() - start) / 1000) + " s");
      }
    }
  }



  public void addWordsToBeginList(Database db, Integer w1, Integer w2) throws CacheException {
    
    Iterator it = db.openByQuery(BeginList.getCacheClassName(), "Word1 = ? AND Word2 = ?", new Object[] {w1, w2});

    if (it.hasNext()) {
      BeginList bl = (BeginList)it.next();
      int total = bl.gettotal().intValue();
      total++;
      bl.settotal(new Integer(total));
      bl._save();
    } else {
      BeginList newBl = new BeginList(db);
      newBl.settotal(new Integer(1));
      newBl.setword1(w1);
      newBl.setword2(w2);
      newBl._save();
    }    
    //BeginList.incTotal(db, w1, w2);
    
  }

  public void addWords(Database db, Integer w1, Integer w2, Integer w3) throws CacheException {
//    if (Word12.incTotal(db, w1, w2, w3, new Integer(refno)).booleanValue()) {
//      refno++;
//    }
//    
    int r1, t, h;
    
    Iterator it = db.openByQuery(Word12.getCacheClassName(), "Word1 = ? AND Word2 = ?", new Object[] {w1, w2});
    if (it.hasNext()) {
      Word12 word12 = (Word12)it.next();
      r1 = word12.getword3ref().intValue();
      t = word12.gettotal().intValue();
      t++;
      
    
      //Word3 suchen
      it = db.openByQuery(Word3.getCacheClassName(), "Ref = ? AND Word3= ?", new Object[] {new Integer(r1), w3});
      if (it.hasNext()) {
        Word3 word3 = (Word3)it.next();
        h = word3.gethits().intValue();
        h++;
        word3.sethits(new Integer(h));
        word3._save();        
      } else {
        Word3 newWord3 = new Word3(db);
        newWord3.sethits(new Integer(1));
        newWord3.setref(new Integer(r1));
        newWord3.setword3(w3);
        newWord3._save();
      }
      //Word3.incHits(db, new Integer(r1), w3);
      
      
      word12.settotal(new Integer(t));
      word12._save();
    } else {
      Word12 newWord12 = new Word12(db);
      newWord12.setword1(w1);
      newWord12.setword2(w2);
      newWord12.setword3ref(new Integer(refno));
      newWord12.settotal(new Integer(1));
      newWord12._save();
      
      Word3 newWord3 = new Word3(db);
      newWord3.setref(new Integer(refno));
      newWord3.sethits(new Integer(1));
      newWord3.setword3(w3);
      newWord3._save();

      refno++;    
    }
    

  }

  public int getNext(Integer w1, Integer w2) throws CacheException, SQLException {
    
  
    int total, sum, w3, t, rnd;
    Integer ref;
    
    
    ResultSet rs = selectWord12Query.execute(new Object[] {w1, w2});
    if (rs.next()) {
      ref = new Integer(rs.getInt(1));
      total = rs.getInt(2);
      
      sum = 0;
      w3 = endmark.intValue();
      rnd = Math.abs((r.nextInt() % total)) + 1;
      
      rs.close();
      rs = selectWord3Query.execute(ref);
      
      while (rs.next() && (sum < rnd)) {
        w3 = rs.getInt(1);
        t = rs.getInt(2);
        sum += t;
      }
      rs.close();
      return w3;
    }
    
    return (endmark.intValue());
    
 

  }
  
public String getWord(int hash) throws SQLException, CacheException {


ResultSet rs = selectWordFromWordListQuery.execute(new Integer(hash));

String word = endmarkstring;
if (rs.next()) {
word = rs.getString(1);
rs.close();
return (word);
} 
rs.close();
return (word);    


}


  public int getNext(int w1, int w2) throws SQLException {
    ResultSet rs12, rs3;
    int ref, total, sum, w3, t, rnd;
    sW12stmt.setInt(1, w1);
    sW12stmt.setInt(2, w2);
    rs12 = sW12stmt.executeQuery();
    if (rs12.next()) {
      ref = rs12.getInt(3);
      total = rs12.getInt(4);

      sum = 0;
      w3 = endmark.intValue();
      rnd = Math.abs((r.nextInt() % total)) + 1;

      sW3stmt.setInt(1, ref);
      rs3 = sW3stmt.executeQuery();
      while ((rs3.next()) && (sum < rnd)) {
        w3 = rs3.getInt(2);
        t = rs3.getInt(3);
        sum += t;
      }

      rs3.close();
      rs12.close();
      return (w3);
    } 
    System.out.println("Hier");
    rs12.close();
    return (endmark.intValue());
  }

  public String getWordSQL(int hash) throws SQLException {
    ResultSet rs;
    String word;
    sWLstmt.setInt(1, hash);
    rs = sWLstmt.executeQuery();
    if (rs.next()) {
      word = rs.getString(2);
      rs.close();
      return (word);
    } 
    rs.close();
    return (endmarkstring);    

  }
  
  public static void main(String args[]) {
    try {
      Class.forName("com.intersys.jdbc.CacheDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    
    try {
      new MVSCache(args);
    } catch (CacheException e) {
      e.printStackTrace();
    }
    
    
  }

}
