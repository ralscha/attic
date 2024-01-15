
import java.io.*;
import java.math.*;
import java.sql.*;

public class PaymentCheck {

	private PreparedStatement selectPS;
	private Connection con;
	
	private final static String selectSQL = "select * from Bookings where accountno = ? AND amount = ? and type = ?";

	
	static {
		try {
			Class.forName("oracle.lite.poljdbc.POLJDBCDriver");
		} catch (Exception e) {
			System.err.println(e);
		} 
	}

	void shutDown() {
		try {
			if (selectPS != null) {
				selectPS.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
	}
	
	
	public void openDb() throws SQLException {		
		String url = "jdbc:Polite:STAT";
		String user = "SYSTEM";
		String pw = "SYSTEM";
		con = DriverManager.getConnection(url, user, pw);
		selectPS = con.prepareStatement(selectSQL);
	} 
	
	
	public boolean exists(String accountno, String valdate, String t, BigDecimal amount) throws SQLException {

		if (accountno.trim().length() == 0) return true;
		
		selectPS.setString(1, accountno);
		selectPS.setBigDecimal(2, amount);
		selectPS.setString(3, t);
		
		ResultSet rs = selectPS.executeQuery();
		while(rs.next()) {
			String acctno = rs.getString(1);
			String bookdate = rs.getString(2);
			String text = rs.getString(3);
			String val = rs.getString(4);
			String type = rs.getString(5);
			BigDecimal am = rs.getBigDecimal(6,3);
			
			
			//if (val.equals(valdate))
				return true;
				
		}
		
		return false;
	
	}	
	
	private void checkFiles(String directory) throws IOException, SQLException {
		String line;

		File dir = new File(directory);
		File[] files = dir.listFiles();
	
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pay.dat")));
		
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
			BufferedReader br = new BufferedReader(new FileReader(files[i]));
			while((line = br.readLine()) != null) {
				if (line.length() > 500) {
					String type = line.substring(37,38);							
					BigDecimal amount = new BigDecimal(line.substring(60,78).trim());						
					String acct  = line.substring(79,103);
					String acct2 = line.substring(83,85);
					String valDate = line.substring(110,114)+line.substring(107,109)+line.substring(104,106);
					String orderDate = line.substring(121,125)+line.substring(118,120)+line.substring(115,117);
					
					int orderDay = Integer.parseInt(orderDate.substring(6,8));
					int orderMonth = Integer.parseInt(orderDate.substring(4,6));
					if (!acct2.equals("98")) {
						if (orderMonth == 11) {
							if ((orderDay > 2) && (orderDay < 22)) {
								if (!exists(acct, valDate, type, amount))
									pw.println(line);
							}
						}
					}
							
					
				}
			} 
			br.close();
		}
		pw.close();
	}
	
	
	public static void main(String[] args) {
		try {
				PaymentCheck isd = new PaymentCheck();
				isd.openDb();
				isd.checkFiles("d:/javaprojects/business/payment_recon/pay/");
			
				isd.shutDown();
		} catch (Exception sqle) {
			System.err.println(sqle);
		}
	}

	
}