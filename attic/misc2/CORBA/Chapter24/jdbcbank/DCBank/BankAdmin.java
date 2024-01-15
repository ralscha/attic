// BankAdmin.java  JDBC Bank Class

package DCBank;
import java.net.URL;
import java.sql.*;

public class BankAdmin
{
  Connection con;
  Statement stmt;
  PreparedStatement pstmt;


  public void createDB(int tps) throws Exception
  {
    connect("LocalServer", "Master", "", "");
    //dropBank();
    //createBank();
    closeConnection();
    connect("LocalServer", "Bank", "", "");
    createBankTables();
    //createBankProcedures();
    loadBankData(tps);
    createBankIndexes();
  }


  public void connect(String datasource, String db,
                      String id, String pw)
                      throws Exception
  {
    try
    {
      // Load the jdbc-odbc bridge driver
      Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
      // String url   = "jdbc:odbc:" + datasource + ";database=" + db;
      String url = "jdbc:odbc:"+db;
      // Load Connect Software's FastForward JDBC driver 
      // Class.forName ("connect.microsoft.MicrosoftDriver");
      // String url   = "jdbc:ff-microsoft://" + datasource + ":1433/" + db;

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


  private void dropBank() throws Exception
  {
    try
    {
      System.out.println("Dropping database bank");
      stmt = con.createStatement();
      stmt.execute("DROP DATABASE bank");
      stmt.close();
    } catch(Exception e)
    { System.err.println("System Exception in dropBank");
      System.err.println(e);
    }
  }


  private void createBank() throws Exception
  {
    try
    {
      System.out.println("Creating database bank");
      stmt = con.createStatement();
      stmt.execute("CREATE DATABASE bank ON bank = 30");
      stmt.close();
    } catch(Exception e)
    { System.err.println("System Exception in createDBbank");
      System.err.println(e);
      throw e;
    }
  }


  private void createBankTables() throws Exception
  {
    try
    {
      System.out.println("Creating bank tables");
      stmt = con.createStatement();
      stmt.execute("CREATE TABLE ACCOUNT(" +
                   "ACCT_NUM INT NOT NULL, " +
                   "NAME CHAR(20) NOT NULL, " +
                   "BRANCH_ID SMALLINT NOT NULL, " +
                   "BALANCE INT NOT NULL, " +
                   "ADDRESS CHAR(30) NOT NULL, " +
                   "TEMP1 CHAR(40) NOT NULL)");

      stmt.execute("CREATE TABLE BRANCH(" +
                   "BRANCH_ID SMALLINT NOT NULL, " +
                   "BRANCH_NAME CHAR(20) NOT NULL, " +
                   "BALANCE INT NOT NULL, " +
                   "AREA_CODE CHAR(4) NOT NULL, " +
                   "ADDRESS CHAR(30) NOT NULL, " +
                   "TEMP1 CHAR(40) NOT NULL)");

      stmt.execute("CREATE TABLE TELLER(" +
                   "TELLER_ID   SMALLINT NOT NULL, " +
                   "TELLER_NAME CHAR(20) NOT NULL, " +
                   "BRANCH_ID SMALLINT NOT NULL, " +
                   "BALANCE INTEGER  NOT NULL, " +
                   "TELLER_CODE CHAR(2)  NOT NULL, " +
                   "ADDRESS CHAR(30) NOT NULL, " +
                   "TEMP1 CHAR(40) NOT NULL)");

      stmt.execute("CREATE TABLE HISTORY( " +
                   "ACCT_NUM INTEGER NOT NULL, " +
                   "TELLER_ID SMALLINT NOT NULL, " +
                   "BRANCH_ID SMALLINT NOT NULL, " +
                   "BALANCE INTEGER NOT NULL, " +
                   "DELTA INTEGER NOT NULL, " +
                   "PID INTEGER NOT NULL, " +
                   "TRANSID INTEGER NOT NULL, " +
                   "ACCTNAME CHAR(20) NOT NULL, " +
                   "TEMP1 CHAR(6) NOT NULL)");

      con.commit();
      stmt.close();
    } catch(Exception e)
    { System.err.println("System Exception in createBankTables");
      System.err.println(e);
      throw e;
    }
  }


  private void createBankProcedures() throws Exception
  {
    try
    {
      System.out.println("Creating bank Stored Procedures");
      stmt = con.createStatement();
      stmt.execute("CREATE PROCEDURE UpdateAcctBalance (@acct_num INT, @delta INT) AS \n" +
                 "DECLARE c1 CURSOR FOR " +
                 "   SELECT BALANCE FROM ACCOUNT WHERE ACCT_NUM = @acct_num FOR UPDATE\n" +
                 "OPEN c1 \n" +
                 "FETCH NEXT FROM c1 \n" +
                 "UPDATE ACCOUNT SET BALANCE = BALANCE + @delta " +
                              "WHERE CURRENT OF c1 \n" +
                 "CLOSE c1 \n" +
                 "DEALLOCATE c1 \n"
                  );
      con.commit();
      stmt.close();
    } catch(Exception e)
    { System.err.println("System Exception in createBankTables");
      System.err.println(e);
      throw e;
    }
  }


  private void loadBankData(int tps) throws Exception
  {
    String  name    = "<--20 BYTE STRING-->";
    String  address = "<------ 30 BYTE STRING ------>";
    String  temp1    = "<----------- 40 BYTE STRING ----------->";
    int     balance = 1000;
    int     acctNum = 0;
    short   branchID = 0;
    short   tellerID = 0;
    String  tellerCode = "ab";
    String  areaCode = "abcd";
    try
    {
      System.out.println("Inserting bank data");
      con.setAutoCommit(false);

      // Calculate Start time
      System.out.println("Starting data insertion into ACCOUNT table..");
      long startTime = System.currentTimeMillis();

      pstmt = con.prepareStatement(
      "INSERT INTO ACCOUNT (ACCT_NUM, NAME, BRANCH_ID, " +
      "BALANCE, ADDRESS, TEMP1) VALUES ( ?, ?, ?, ?, ?, ? )");

      // Insert accounts in ACCOUNT table, commit every 10,000 rows
      for (int i=0; i < tps; i++)
      {
        for (int j=0; j < 10; j++)
        {
          pstmt.setString(2, name);
          pstmt.setInt(4, balance);
          pstmt.setString(5, address);
          pstmt.setString(6, temp1);
          for (int k=1; k <= 10000; k++)
          {
            acctNum  = i*100000 + j*10000 + k;
            branchID = (short)(Math.random() * 10 + 1);

            pstmt.setInt(1, acctNum);
            pstmt.setShort(3, branchID);
            pstmt.executeUpdate();
          }

          con.commit();
          System.out.println(acctNum + " accounts created.");
        }
      }

      pstmt.close();
      long stopTime = System.currentTimeMillis();
      System.out.println("Account table load complete.");
      System.out.println("Load time = " +
                         ((stopTime - startTime)/(1000f)) +
                         " seconds");

      // Insert 100 tellers into TELLER table
      pstmt = con.prepareStatement(
                  "INSERT INTO TELLER (TELLER_ID, TELLER_NAME, " +
                  "BRANCH_ID, BALANCE, TELLER_CODE, ADDRESS, TEMP1) " +
                  "VALUES ( ?, ?, ?, ?, ?, ?, ?)");
      pstmt.setString(2, name);
      pstmt.setInt(4, balance);
      pstmt.setString(5, tellerCode);
      pstmt.setString(6, address);
      pstmt.setString(7, temp1);

      for (tellerID = 1; tellerID <= 100; tellerID++)
      { branchID = (short)(Math.random() * 10 + 1);
        pstmt.setShort(1, tellerID);
        pstmt.setShort(3, branchID);
        pstmt.executeUpdate();
      }

      con.commit();
      pstmt.close();
      System.out.println("Teller Insert succeeded.");

      // Insert 10 Branches into BRANCH table
      pstmt = con.prepareStatement(
                  "INSERT INTO BRANCH (BRANCH_ID, BRANCH_NAME, " +
                  "BALANCE, AREA_CODE, ADDRESS, TEMP1) " +
                  "VALUES ( ?, ?, ?, ?, ?, ?)");

      pstmt.setString(2, name);
      pstmt.setInt(3, balance);
      pstmt.setString(4, areaCode);
      pstmt.setString(5, address);
      pstmt.setString(6, temp1);

      for (branchID = 1; branchID <= 10; branchID++)
      {
        pstmt.setShort(1, branchID);
        pstmt.executeUpdate();
      }

      con.commit();
      pstmt.close();
      System.out.println("Branch Insert succeeded.");
      System.out.println("Bank data insertion complete");
    } catch(Exception e)
    { System.err.println("System Exception in loadBankData");
      System.err.println(e);
      throw e;
    }
  }


  private void createBankIndexes() throws Exception
  {
    try
    {
      System.out.println("Creating bank indexes");

      stmt = con.createStatement();
      stmt.execute("CREATE INDEX ACCTINDX ON ACCOUNT(ACCT_NUM)");
      stmt.execute("CREATE INDEX BRANINDX ON BRANCH(BRANCH_ID)");
      stmt.execute("CREATE INDEX TELLINDX ON TELLER(TELLER_ID)");

      con.commit();
      stmt.close();
    } catch(Exception e)
    { System.err.println("System Exception in createBankIndexes");
      System.err.println(e);
      throw e;
    }
  }

}





