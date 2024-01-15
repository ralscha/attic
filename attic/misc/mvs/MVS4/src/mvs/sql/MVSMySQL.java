package mvs.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class MVSMySQL {

  PreparedStatement urefNo, iWLstmt, sWLstmt, sW12stmt, uW12stmt, iW12stmt, sW3stmt, uW3stmt, iW3stmt, sBLstmt,
      uBLstmt, iBLstmt;
  int refno;
  Random r = new Random();

  String endmarkstring = "#####";
  Integer endmark = new Integer(endmarkstring.hashCode());

  public MVSMySQL(String args[]) {

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e1) {
      e1.printStackTrace();
    }
    
    List files;
    List hash, words;
    String input, help;
    int ix;
    ResultSet rs;

    String url = "jdbc:mysql://localhost/mvs";

    if (args.length >= 1) {
      if (args[0].equals("G")) {
        long start = System.currentTimeMillis();
        int noOfWords = 0;
        int t, sum, w1, w2, next, total, rnd;

        StringBuffer line = new StringBuffer();

        try {
          
          Connection con = DriverManager.getConnection(url, "root", "gruentor30");
          checkForWarning(con.getWarnings());

          String selectWord3 = "SELECT ref, word3, hits FROM Word3 WHERE ref = ?";
          String selectWord12 = "SELECT word1, word2, word3ref, total FROM Word12 " + "WHERE word1 = ? AND word2 = ?";
          String selectWordList = "SELECT hash, word FROM WordList WHERE hash = ?";

          Statement stmt = con.createStatement();
          sW3stmt = con.prepareStatement(selectWord3);
          sW12stmt = con.prepareStatement(selectWord12);
          sWLstmt = con.prepareStatement(selectWordList);

          for (int i = 0; i < 50; i++) {
            /* Monte-Carlo fuer BeginList */
            rs = stmt.executeQuery("SELECT SUM(total) FROM BeginList");
            rs.next();
            total = rs.getInt(1);
            rs.close();
  
            sum = 0;
            w1 = 0;
            w2 = 0;
            rnd = Math.abs((r.nextInt() % total)) + 1;
  
            rs = stmt.executeQuery("SELECT word1, word2, total from BeginList");
            while ((rs.next()) && (sum < rnd)) {
              w1 = rs.getInt(1);
              w2 = rs.getInt(2);
              t = rs.getInt(3);
              sum += t;
            }
            rs.close();
  
            line.append(getWord(w1)).append(" ").append(getWord(w2)).append(" ");
            next = getNext(w1, w2);
  
            while (next != endmark.hashCode()) {
              line.append(getWord(next)).append(" ");
              noOfWords++;
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
          
          sW12stmt.close();
          sW3stmt.close();
          sWLstmt.close();
          con.close();
        } catch (SQLException ex) {
          System.out.println("\n*** SQLException caught ***\n");
          while (ex != null) {
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("Message:  " + ex.getMessage());
            System.out.println("Vendor:   " + ex.getErrorCode());
            ex = ex.getNextException();
            System.out.println("");
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
              files.add(args[1] + "/" + lst[i]);
          }

        } else {
          for (int i = 0; i < args.length; i++)
            files.add(args[i]);
        }

        String insertWordList = "INSERT INTO WordList(hash, word) VALUES(?, ?)";

        String selectWord12 = "SELECT word1, word2, word3ref, total FROM Word12 " + "WHERE word1 = ? AND word2 = ?";
        String updateWord12 = "UPDATE Word12 SET total = ? " + "WHERE word1 = ? AND word2 = ?";
        String insertWord12 = "INSERT INTO Word12(word1, word2, word3ref, total) " + "VALUES(?, ?, ?, ?)";
        String selectWord3 = "SELECT ref, word3, hits FROM Word3 " + "WHERE ref = ? AND word3 = ?";
        String updateWord3 = "UPDATE Word3 SET hits = ? " + "WHERE ref = ? AND word3 = ?";
        String insertWord3 = "INSERT INTO Word3(ref, word3, hits) " + "VALUES(?, ?, ?)";

        String selectBeginList = "SELECT word1, word2, total FROM BeginList " + "WHERE word1 = ? AND word2 = ?";
        String updateBeginList = "UPDATE BeginList SET total = ? " + "WHERE word1 = ? AND word2 = ?";
        String insertBeginList = "INSERT INTO BeginList(word1, word2, total) " + "VALUES(?, ?, ?)";

        String updateRefNo = "UPDATE RefNo SET refno = ?";

        try {
          Connection con = DriverManager.getConnection(url, "root", "gruentor30");
          checkForWarning(con.getWarnings());

          Statement stmt = con.createStatement();
          urefNo = con.prepareStatement(updateRefNo);
          iWLstmt = con.prepareStatement(insertWordList);
          sW12stmt = con.prepareStatement(selectWord12);
          uW12stmt = con.prepareStatement(updateWord12);
          iW12stmt = con.prepareStatement(insertWord12);
          sW3stmt = con.prepareStatement(selectWord3);
          uW3stmt = con.prepareStatement(updateWord3);
          iW3stmt = con.prepareStatement(insertWord3);
          sBLstmt = con.prepareStatement(selectBeginList);
          uBLstmt = con.prepareStatement(updateBeginList);
          iBLstmt = con.prepareStatement(insertBeginList);

          hash = new Vector();
          words = new Vector();

          rs = stmt.executeQuery("SELECT refno FROM RefNo");
          if (rs.next()) {
            refno = rs.getInt(1);
          } else {
            refno = 1;
          }
          rs.close();

          for (int i = 0; i < files.size(); i++) {
            hash.clear();
            words.clear();

            
            BufferedReader br = new BufferedReader(new FileReader((String)files.get(i)));
                        

            System.out.println("Reading ... : " + (String)files.get(i));


            
            while ((input = br.readLine()) != null) {
              StringTokenizer st = new StringTokenizer(input);
              while (st.hasMoreTokens()) {
                help = st.nextToken();
                hash.add(new Integer(help.hashCode()));
                words.add(help);
                iWLstmt.setInt(1, help.hashCode());
                iWLstmt.setString(2, help);
                try {
                  iWLstmt.executeUpdate();
                } catch (SQLException ex) {
                  if (!ex.getSQLState().equals("23000")) {
                    while (ex != null) {
                      System.out.println("SQLState: " + ex.getSQLState());
                      System.out.println("Message:  " + ex.getMessage());
                      System.out.println("Vendor:   " + ex.getErrorCode());
                      ex = ex.getNextException();
                      System.out.println("");
                    }
                    System.exit(1);
                  }
                }
              }
            }
            
            br.close();

            ix = 0;
            String ws;

            while (ix + 2 < hash.size()) {
              addWords((Integer)hash.get(ix), (Integer)hash.get(ix + 1), (Integer)hash.get(ix + 2));

              if (ix == 0) {
                ws = (String)words.get(ix);
                if (Character.isUpperCase(ws.charAt(0)))
                  addWordsToBeginList((Integer)hash.get(ix), (Integer)hash.get(ix + 1));
              } else {
                ws = (String)words.get(ix - 1);
                if ((Character.isUpperCase(ws.charAt(0))) && (ws.endsWith(".")))
                  addWordsToBeginList((Integer)hash.get(ix), (Integer)hash.get(ix + 1));
              }

              ix++;
            }

            addWords((Integer)hash.get(ix), (Integer)hash.get(ix + 1), endmark);

            urefNo.setInt(1, refno);
            urefNo.executeUpdate();
          } /* for */

          stmt.close();
          urefNo.close();
          iWLstmt.close();
          sW12stmt.close();
          uW12stmt.close();
          iW12stmt.close();
          sW3stmt.close();
          uW3stmt.close();
          iW3stmt.close();
          sBLstmt.close();
          uBLstmt.close();
          iBLstmt.close();
          con.close();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          System.err.println("InTest: " + e);
        } catch (SQLException ex) {
          System.out.println("\n*** SQLException caught ***\n");
          while (ex != null) {
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("Message:  " + ex.getMessage());
            System.out.println("Vendor:   " + ex.getErrorCode());
            ex.printStackTrace();
            ex = ex.getNextException();
            System.out.println("");
            
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }

        System.out.println(((System.currentTimeMillis() - start) / 1000) + " s");
      }
    }
  }

  public boolean checkForWarning(SQLWarning warn) {
    boolean rc = false;

    if (warn != null) {
      System.out.println("\n *** Warning ***\n");
      rc = true;
      while (warn != null) {
        System.out.println("SQLState: " + warn.getSQLState());
        System.out.println("Message:  " + warn.getMessage());
        System.out.println("Vendor:   " + warn.getErrorCode());
        System.out.println("");
        warn = warn.getNextWarning();
      }
      warn.printStackTrace();
    }
    return rc;
  }

  public void addWordsToBeginList(Integer w1, Integer w2) throws SQLException {
    int t;
    sBLstmt.setInt(1, w1.intValue());
    sBLstmt.setInt(2, w2.intValue());
    ResultSet rs = sBLstmt.executeQuery();
    if (rs.next()) {
      //Gefunden
      t = rs.getInt(3);
      t++;

      uBLstmt.setInt(1, t);
      uBLstmt.setInt(2, w1.intValue());
      uBLstmt.setInt(3, w2.intValue());
      uBLstmt.executeUpdate();
    } else {
      //Nicht gefunden
      iBLstmt.setInt(1, w1.intValue());
      iBLstmt.setInt(2, w2.intValue());
      iBLstmt.setInt(3, 1);
      iBLstmt.executeUpdate();
    }
    rs.close();
  }

  public void addWords(Integer w1, Integer w2, Integer w3) throws SQLException {
    int r1, t, h;
    ResultSet rs12, rs3;

    sW12stmt.setInt(1, w1.intValue());
    sW12stmt.setInt(2, w2.intValue());
    rs12 = sW12stmt.executeQuery();
    if (rs12.next()) {
      //Gefunden
      r1 = rs12.getInt(3);
      t = rs12.getInt(4);
      t++;

      //Word3 suchen
      sW3stmt.setInt(1, r1);
      sW3stmt.setInt(2, w3.intValue());
      rs3 = sW3stmt.executeQuery();

      if (rs3.next()) {
        //Gefunden
        h = rs3.getInt(3);
        h++;

        uW3stmt.setInt(1, h);
        uW3stmt.setInt(2, r1);
        uW3stmt.setInt(3, w3.intValue());

        uW3stmt.executeUpdate();
      } else {
        //Nicht gefunden
        iW3stmt.setInt(1, r1);
        iW3stmt.setInt(2, w3.intValue());
        iW3stmt.setInt(3, 1);

        iW3stmt.executeUpdate();
      }

      //Update Word12
      uW12stmt.setInt(1, t);
      uW12stmt.setInt(2, w1.intValue());
      uW12stmt.setInt(3, w2.intValue());

      uW12stmt.executeUpdate();

      rs3.close();
    } else {
      //Nicht gefunden
      iW12stmt.setInt(1, w1.intValue());
      iW12stmt.setInt(2, w2.intValue());
      iW12stmt.setInt(3, refno);
      iW12stmt.setInt(4, 1);
      iW12stmt.executeUpdate();

      iW3stmt.setInt(1, refno);
      iW3stmt.setInt(2, w3.intValue());
      iW3stmt.setInt(3, 1);
      iW3stmt.executeUpdate();
      refno++;
    }

    rs12.close();

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

  public String getWord(int hash) throws SQLException {
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
    new MVSMySQL(args);        
  }

}
