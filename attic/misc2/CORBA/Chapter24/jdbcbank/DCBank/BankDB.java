// BankDB.java  JDBC BankDB Class

package DCBank;
import java.net.URL;
import java.sql.*;

public class BankDB
{
  Connection con;
  Driver driver = null;
  ResultSet rs;
  CallableStatement pstmt;
  PreparedStatement pstmt1;
  PreparedStatement pstmt2;
  PreparedStatement pstmt3;
  boolean prepared = false;

  public void connect(String datasource, String db,
                      String id, String pw)
                      throws Exception
  {
    try
    {
      // Load the jdbc-odbc bridge driver
      Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
      String url   = "jdbc:odbc:" + db;

      // Load Connect Software's FastForward JDBC driver 
      //Class.forName ("connect.microsoft.MicrosoftDriver");
      //String url   = "jdbc:ff-microsoft://" + datasource + ":1433/" + db;

      System.out.println("Connecting to " + url);
      con = DriverManager.getConnection(url, id, pw);

    } catch(Exception e)
    { System.err.println("System Exception in connect");
      System.err.println(e);
      throw e;
    }
  }


  public void closeConnection() throws Exception
  {
    try                   
    {
      System.out.println("Closing connection");
      con.close();
    } catch (Exception e)
    { System.err.println("System Exception in closeConnection");
      System.err.println(e);
      throw e;
    }
  }


  public void debitCreditTransaction(double random1,
                                     double random2,
                                     boolean staticTxn)
                                     throws Exception
  {
    int     balance = 1000;
    int     delta   = 100;
    int     acctNum = 0;
    short   branchID = 0;
    short   tellerID = 0;
    String  tellerCode = "ab";
    String  areaCode = "abcd";

    try
    {
      // randomly select account, teller, branch
      acctNum  = (int)((random1 * random2 * 100000) + 1);
      tellerID = (short)((random1 * 100) + 1);
      branchID = (short)((random2 *  10) + 1);

      if ((staticTxn && !prepared) || !staticTxn)
      {
      //  pstmt  = con.prepareCall("{?= call UpdateAcctBalance(?,?)}");
        pstmt1 = con.prepareStatement(
               "UPDATE TELLER SET BALANCE = BALANCE + ? WHERE TELLER_ID = ?");
        pstmt2 = con.prepareStatement(
               "UPDATE BRANCH SET BALANCE = BALANCE + ? WHERE BRANCH_ID = ?");
        pstmt3 = con.prepareStatement(
              "INSERT INTO HISTORY(ACCT_NUM, TELLER_ID, BRANCH_ID, BALANCE, " +
              "DELTA, PID, TRANSID, ACCTNAME, TEMP1) " +
              "VALUES(?, ?, ?, ?, ?, 1, 3, ?, 'hist  ')");
        prepared = true;
      }
      /*pstmt.registerOutParameter(1,Types.INTEGER);  
      pstmt.setInt(2, acctNum);
      pstmt.setInt(3, delta);
      pstmt.execute();
      ResultSet rs1 = pstmt.getResultSet();
      rs1.next();
      balance = rs1.getInt(1);
      rs1.close();
      */
      balance = 0;

      // Update the TELLER table's balance
      pstmt1.setInt(1, delta);
      pstmt1.setShort(2, tellerID);
      pstmt1.executeUpdate();

      // Update the Branch balance
      pstmt2.setInt(1, delta);
      pstmt2.setShort(2, branchID);
      pstmt2.executeUpdate();

      //Update the history
      pstmt3.setInt(1, acctNum);
      pstmt3.setShort(2, tellerID);
      pstmt3.setShort(3, branchID);
      pstmt3.setInt(4, balance);
      pstmt3.setInt(5, delta);
      pstmt3.setString(6, "Account Name, len=20");
      pstmt3.executeUpdate();

      if ((staticTxn && !prepared) || !staticTxn)
      {
        //pstmt.close();
        pstmt1.close();
        pstmt2.close();
        pstmt3.close();
      }

      //Commit the Transaction
      con.commit();
    }
    catch( Exception e )
    { System.err.println("System Exception in debitCreditTransaction");
      System.err.println(e);
      con.rollback();
      throw e;
    }
  }
}


