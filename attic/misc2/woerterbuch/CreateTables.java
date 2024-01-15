
import java.sql.*;
import java.io.*;
import java.util.*;
import java.math.*;
 
public class CreateTables {
	private Statement statement;
	private Connection con;
	
  public static void main(String[] args) {
		try {
			if (args.length == 3) {
				CreateTables ct = new CreateTables();
				ct.openDb(args[1], args[2]);
				if ("DROP".equalsIgnoreCase(args[0]))
					ct.dropTables();
				else if ("CREATE".equalsIgnoreCase(args[0]))
					ct.createTables();
				else if ("CREATEVARCHAR".equalsIgnoreCase(args[0]))
					ct.createTablesVarchar();
				else if ("INSERT".equalsIgnoreCase(args[0]))
					ct.insertDataBinary();
				else if ("INSERTVARCHAR".equalsIgnoreCase(args[0]))
					ct.insertDataBinary();
				else if ("CREATEFILE".equalsIgnoreCase(args[0]))
					ct.createHexFile();
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
	
	
	public void openDb(String driver, String dburl) throws SQLException {		
	  try {
		  Class.forName(driver).newInstance();
	  } catch (Exception e) {
		  System.err.println(e);
	  } 

		String user = "sa";
		String pw = "";
		con = DriverManager.getConnection(dburl, user, pw);
		statement = con.createStatement();
		

	}  

	
	private void createTables() {
		
		try {
			System.out.println(statement.executeUpdate("CREATE TABLE Woerterbuch("+
					"wort			CHAR(32) NOT NULL Primary Key)"));
			System.out.println("Create Table successful");
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	private void createTablesVarchar() {
		
		try {
			System.out.println(statement.executeUpdate("CREATE TABLE Woerterbuch("+
					"wort			VARCHAR(20) NOT NULL Primary Key)"));
			System.out.println("Create Table successful");
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}


	public void insertData() {
    try {
      String insertSQL = "INSERT INTO Woerterbuch(wort) VALUES(?)";
      PreparedStatement ps = con.prepareStatement(insertSQL);
    
      BufferedReader br = new BufferedReader(new FileReader("outputhex.txt"));
      String line = null;

      while ((line = br.readLine()) != null) {
        ps.setString(1, line);
        ps.executeUpdate();
      }

      br.close();
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	public void createHexFile() {
    try {

     String[] FILES = {"wordlist\\english7.txt", 
                       "wordlist\\german7.txt", 
                       "wordlist\\french7.txt", 
                       "wordlist\\misc7.txt",
                       "wordlist\\american7.txt",
                       "wordlist\\computer7.txt",
                       "wordlist\\italian7.txt",
                       "wordlist\\literature7.txt",
                       "wordlist\\movie7.txt",
                       "wordlist\\music7.txt",
                       "wordlist\\names7.txt",
                       "wordlist\\religion7.txt",
                       "wordlist\\science7.txt"};

      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("outputhex.txt")));
      Set ws = new HashSet();

      for (int i = 0; i < FILES.length; i++) {
        BufferedReader br = new BufferedReader(new FileReader(FILES[i]));
        String line = null;
			System.out.println(FILES[i]);
        while ((line = br.readLine()) != null) {
          line = line.trim();
          if (line.length() <= 20) {
            //line = line.toLowerCase();
            if (!ws.contains(line)) {
              ws.add(line);
              byte[] digest = ch.ess.pbroker.security.PasswordValidator.getDigest(line);
              pw.println(ch.ess.pbroker.util.Util.toHex(digest));
            }

          }
        }

        br.close();        
      }
      pw.close();
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	public void insertDataBinary() {
    try {

     String[] FILES = {"wordlist\\english7.txt", 
                       "wordlist\\german7.txt", 
                       "wordlist\\french7.txt", 
                       "wordlist\\misc7.txt",
                       "wordlist\\american7.txt",
                       "wordlist\\computer7.txt",
                       "wordlist\\italian7.txt",
                       "wordlist\\literature7.txt",
                       "wordlist\\movie7.txt",
                       "wordlist\\music7.txt",
                       "wordlist\\names7.txt",
                       "wordlist\\religion7.txt",
                       "wordlist\\science7.txt"};

      String insertSQL = "INSERT INTO Woerterbuch(wort) VALUES(?)";
      PreparedStatement ps = con.prepareStatement(insertSQL);
      
      for (int i = 0; i < FILES.length; i++) {
        BufferedReader br = new BufferedReader(new FileReader(FILES[i]));
        String line = null;
			System.out.println(FILES[i]);
        while ((line = br.readLine()) != null) {
          line = line.trim();
          if (line.length() <= 20) {
            //line = line.toLowerCase();
            byte[] digest = ch.ess.pbroker.security.PasswordValidator.getDigest(line);
            ByteArrayInputStream bai = new ByteArrayInputStream(digest);
            ps.setBinaryStream(1, bai, digest.length);
            try {
              ps.executeUpdate();
            } catch (SQLException sqle) {
              System.err.println(sqle);
            }
            bai.close();

          }
        }

        br.close();
      }
		} catch (Exception e) {
			System.err.println(e);
		}

	}


	public void dropTables() {
		try {
		
			statement.executeUpdate("DROP TABLE Woerterbuch");
						
			System.out.println("Drop Table successful");
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	
		


}