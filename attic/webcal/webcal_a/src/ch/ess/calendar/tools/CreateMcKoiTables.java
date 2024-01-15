
package ch.ess.calendar.tools;

import java.sql.*;
 
public class CreateMcKoiTables {
  private Statement statement;
  private Connection con;
  
  public static void main(String[] args) {
    try {
      if (args.length == 5) {
        CreateMcKoiTables ct = new CreateMcKoiTables();
        ct.openDb(args[1], args[2], args[3], args[4]);
        if ("DROP".equalsIgnoreCase(args[0]))
          ct.dropTables();
        else if ("CREATE".equalsIgnoreCase(args[0]))
          ct.createTables();
        else if ("INSERT".equalsIgnoreCase(args[0]))
          ct.insertData();
        ct.shutDown();
      }
    } catch (Exception sqle) {
      System.err.println(sqle);
    }
  }

  void shutDown() {
    try {
      if (statement != null) {
        statement.close();
      }
      if (con != null) {
          con.commit();
        con.close();
      }
    } catch (SQLException sqle) {
      System.out.println(sqle);
    }
  }
  
  
  public void openDb(String driver, String dburl, String user, String pw) throws SQLException {   
    try {
      Class.forName(driver).newInstance();
    } catch (Exception e) {
      System.err.println(e);
    } 

    con = DriverManager.getConnection(dburl, user, pw);
    statement = con.createStatement();
    

  } 


  private void createTables() {
    
    try {
      System.out.println(statement.executeUpdate("CREATE TABLE Users("+
          "userid        VARCHAR(20) NOT NULL,"+
          "firstname     VARCHAR(100),"+
          "name          VARCHAR(100),"+
          "passwort      VARCHAR(50) NOT NULL,"+
          "administrator CHAR(1),"+
          "email VARCHAR(100), "+
          "CONSTRAINT user_pk PRIMARY KEY (userid)) "));

      System.out.println(statement.executeUpdate("CREATE TABLE Categories("+
          "categoryid       INT NOT NULL,"+
          "description      VARCHAR(50) NOT NULL,"+
          "color            VARCHAR(50) NOT NULL, " +
          "CONSTRAINT categories_pk PRIMARY KEY (categoryid))"));

      System.out.println(statement.executeUpdate("CREATE TABLE Appointments("+
          "appointmentid      INT NOT NULL,"+
          "userid             VARCHAR(20) NOT NULL,"+
          "categoryid         INT NOT NULL,"+
          "startdate          TIMESTAMP NOT NULL,"+
          "enddate            TIMESTAMP NOT NULL,"+
          "body               VARCHAR(200),"+
          "location           VARCHAR(50),"+
          "subject            VARCHAR(50),"+
          "alldayevent        CHAR(1),"+
          "importance         INT,"+
          "private            CHAR(1),"+
          "CONSTRAINT appointments_pk PRIMARY KEY (appointmentid))"));

      System.out.println(statement.executeUpdate("CREATE TABLE Reminders("+
          "reminderid         INT DEFAULT UNIQUEKEY('Reminders'),"+
          "appointmentid      INT NOT NULL,"+
          "minutesbefore      INT NOT NULL,"+
          "email              VARCHAR(100) NOT NULL,"+
          "CONSTRAINT reminders_pk PRIMARY KEY (reminderid),"+
          "CONSTRAINT rem_app_fk FOREIGN KEY (appointmentid) REFERENCES Appointments(appointmentid))"));

      System.out.println(statement.executeUpdate("CREATE TABLE Repeaters("+
          "repeaterid         INT DEFAULT UNIQUEKEY('Repeaters'),"+
          "appointmentid      INT NOT NULL,"+
          "until              TIMESTAMP,"+
          "every              INT,"+
          "everyperiod        INT,"+
          "repeaton           INT,"+
          "repeatonweekday    INT,"+
          "repeatonperiod     INT,"+
          "CONSTRAINT repeaters_pk PRIMARY KEY (repeaterid),"+
          "CONSTRAINT reap_app_fk FOREIGN KEY (appointmentid) REFERENCES Appointments(appointmentid))"));

        
      System.out.println("Create Table successful");
      
    } catch (SQLException e) {
      System.err.println(e);
    }
  }

  public void insertData() {
    try {
      System.out.println(statement.executeUpdate("INSERT INTO Categories("+
          "categoryid,description,color) VALUES ("+
          "1, 'customer', 'FF9900')"));
      System.out.println(statement.executeUpdate("INSERT INTO Categories("+
          "categoryid,description,color) VALUES ("+
          "2, 'private', '0000FF')"));
      System.out.println(statement.executeUpdate("INSERT INTO Categories("+
          "categoryid,description,color) VALUES ("+
          "3, 'holiday', 'FFFF00')"));
      System.out.println(statement.executeUpdate("INSERT INTO Categories("+
          "categoryid,description,color) VALUES ("+
          "4, 'military', '66CC00')"));
      System.out.println(statement.executeUpdate("INSERT INTO Categories("+
          "categoryid,description,color) VALUES ("+
          "5, 'meeting', 'FF0000')"));
      System.out.println(statement.executeUpdate("INSERT INTO Categories("+
          "categoryid,description,color) VALUES ("+
          "6, 'education', 'FF00FF')"));                                        
    
      System.out.println(statement.executeUpdate("INSERT INTO Users("+
          "userid,firstname,name,passwort,administrator) VALUES ("+
          "'fh', 'Fritz', 'Hugentobler','hugentobler','Y')"));                                        
      
      System.out.println(statement.executeUpdate("INSERT INTO Users("+
          "userid,firstname,name,passwort,administrator) VALUES ("+
          "'fx', 'Felix', 'Muster','muster','N')"));                                        
      
    } catch (SQLException e) {
      System.err.println(e);
    }
  }

  public void dropTables() {
    try {
    
      statement.executeUpdate("DROP TABLE Users");
      statement.executeUpdate("DROP TABLE Categories");
      statement.executeUpdate("DROP TABLE Reminders");
      statement.executeUpdate("DROP TABLE Repeaters");
      statement.executeUpdate("DROP TABLE Appointments");

      System.out.println("Drop Table successful");
    } catch (SQLException e) {
      System.err.println(e);
    }
  }
  
  
    


}