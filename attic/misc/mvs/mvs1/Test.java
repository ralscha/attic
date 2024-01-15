

import com.objectmatter.bsf.* ;

import java.util.* ;
import java.io.* ;
import java.math.BigDecimal ;
import java.text.* ;
import mvs.db.*;
import mvs.*;
import common.io.*;


public class Test {

  public static void main(String[] args) {
    //Debug.setDebugging(Debugger.PERSISTENCE + Debugger.DATABASE+Debugger.DEEP, false, false);

    com.objectmatter.bsf.Database db = null;
    try {


      db = new com.objectmatter.bsf.Database("mvs.schema");
      db.open();
  
/*
      User newUser = (User)db.create(User.class);
      newUser.setName("Ralph2");
      newUser.setId(1);
      db.insert(newUser);

      for (int i = 0; i < 10; i++) {
        Address newAddress = (Address)db.create(Address.class);
        newAddress.setId(i+10);
        newAddress.setStreet("street"+i);
        newUser.addAddress(newAddress);
        //db.insert(newAddress);
      }
        
      db.update(newUser);

      db.close();
*/
  
      OQuery query = new OQuery();
      query.add(113318999, "word1");
      query.add(113319000, "word2");

      Word12[] word12 = (Word12[])db.get(Word12.class, query);
      
      Word3[] words3 = word12[0].getWords3();
      System.out.println("w12len  : " + word12.length);
      System.out.println("w3len   : " + words3.length);
      for (int i = 0; i < words3.length; i++) {
        System.out.println(words3[i].getWord3());
      }
    
      /*
      OQuery query = new OQuery(Wordlist.class);
      query.add("ralph".hashCode(), "hash");
      System.out.println(query.execute(db));
      */

      /*
      BufferedReader br = new BufferedReader(new TagFilterReader(new FileReader("txt/m_welt.txt")));

      String line;
      List words = new ArrayList();

			while ((line = br.readLine()) != null) {
			  StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
				  String token = st.nextToken();						
					token = MVSUtil.convertText(token);
						
					words.add(token);						
				}
			}

			int ix = 0;
	
			if (words.size() >= 2)  {				
	      
	      Transaction txn = new Transaction(db);

			  while (ix + 2 < words.size()) {
			    String word1 = (String)words.get(ix);
			    String word2 = (String)words.get(ix+1);
			    String word3 = (String)words.get(ix+2);
          System.out.println(word1);
          System.out.println(word2);
          System.out.println(word3);
          System.out.println("----");

          Word3 w3 = (Word3)db.create(Word3.class);
          w3.setWord3(word3.hashCode());
          w3.setHits(1);
      
          Word12 w12 = (Word12)db.create(Word12.class);
          w12.setWord1(word1.hashCode());
          w12.setWord2(word2.hashCode());
          w12.setTotal(1);
          w12.addWords3(w3);

          db.insert(w12);
          
          ix++;
			  }
        txn.commit();
			}
       
      br.close();
      
      Wordlist wl = new Wordlist();
      wl.setWord("Ralph");      

      wl = (Wordlist)db.insert(wl);
      System.out.println(wl.getHash());
      
      */
    } catch (BODBException e) {
      dbMsg(e);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e);
    } finally { 
      if (db != null)
        db.close(); //Close database connection
    } 
  }


  public static void dbMsg(BODBException dbEx) {
    System.out.println("Database exception(s) thrown:");
    BODBException exHolder = dbEx ;
    while (exHolder != null) {
      System.err.println(exHolder.getCode() + "  " + exHolder.getMessage());
      exHolder = dbEx.getNext();
      dbEx = exHolder ;
    }
  }

} //End class


