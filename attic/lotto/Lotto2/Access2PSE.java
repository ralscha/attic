
import lotto.*;
import lotto.util.*;

import java.sql.*;
import java.util.*;
import COM.odi.util.*;
import java.io.*;
import java.text.*;

public class Access2PSE {
    
    /* DB */
    private final static String url = "jdbc:odbc:Lotto";
    private final static String jdbcDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
    private final static String user = "";
    private final static String password = "";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");  

    private final static String selectZiehungenStr =
                        "SELECT Datum,Nr,Jahr,Zahl1,Zahl2,Zahl3,"+
                        "Zahl4,Zahl5,Zahl6,Zusatzzahl,Joker,Summe,Verbund,Gerade "+
                        "FROM Ziehungen ORDER BY Jahr,Nr";
 
    private final static String selectGQStr =
                        "SELECT Nr, Jahr, Jackpot, R6Anzahl, R6Quote, R5PAnzahl, R5PQuote,"+
                        " R5Anzahl, R5Quote, R4Anzahl, R4Quote, R3Anzahl, R3Quote "+
                        "FROM Gewinnquoten";

    private final static String selectJokerGQStr =
                        "SELECT Nr, Jahr, Jackpot, E6Anzahl, E6Quote, " +
                        "E5Anzahl, E5Quote, E4Anzahl, E4Quote, E3Anzahl, E3Quote, " +
                        "E2Anzahl, E2Quote FROM JokerGewinnquoten";

    
    Ausspielungen auss;
    
	public Access2PSE(String args[]) {
	    super();
	    
        DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
        auss = DbManager.getAusspielungen();                
        
        if (args.length == 1) {
            DbManager.startReadTransaction();
            if (args[0].equalsIgnoreCase("db"))
                saveDataToDb();
            else
                saveDataToFile();
            DbManager.commitTransaction();

        } else {
            DbManager.startUpdateTransaction();
            loadDatafromDB();
            DbManager.commitTransaction();            
        }
        DbManager.shutdown();

	}
		
    public static void main(String args[]) {
        new Access2PSE(args);
    }
    
    
    private void saveDataToFile() {
        
        PrintWriter bwq, bwz, bwj;
        Ziehung help;
        LottoGewinnquote lgq;
        JokerGewinnquote jgq;
        
        try
        {
        	  bwj = new PrintWriter(new BufferedWriter(new FileWriter("JokerGewinnquoten.txt")));
            bwq = new PrintWriter (new BufferedWriter(new FileWriter("Gewinnquoten.txt")));
            bwz = new PrintWriter (new BufferedWriter(new FileWriter("Ziehung.txt")));
            
            Iterator it = auss.iterator();
            
            while (it.hasNext()) {                
                help = (Ziehung)it.next();
                lgq = help.getLottoGewinnquote();
                jgq = help.getJokerGewinnquote();
                
                if (lgq != null) {
                    bwq.print(help.getNr()+";"+help.getJahr()+";");
                    bwq.print(lgq);
                    bwq.println();
                } else {
                		bwq.println("NULL");
                }
                if (jgq != null) {
                		bwj.print(help.getNr()+";"+help.getJahr()+";");
					   bwj.print(jgq);
					   bwj.println();	
                } else {
                	bwj.println("NULL");
                }
                
                bwz.print(help);
                bwz.println();
            }
            
            bwq.close();
            bwz.close();
            bwj.close();

        } catch (IOException ex) {
            System.out.println(ex);            
        }

    }

    private String makeInsertZieString(Ziehung zie) {
        int zahlen[];
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO Ziehungen(Datum,Nr,Jahr,Zahl1,Zahl2,Zahl3,Zahl4,Zahl5,Zahl6,Zusatzzahl,Joker,Summe,Verbund,Gerade) ");
        sb.append("VALUES(");
        sb.append("'").append(dateFormat.format(zie.getDate().getTime())).append("'").append(",");
        sb.append(zie.getNr()).append(",");
        sb.append(zie.getJahr()).append(",");
        
        zahlen = zie.getZahlen();
        for (int j = 0; j < 6; j++)
           sb.append(zahlen[j]).append(",");
        sb.append(zie.getZZ()).append(",");

        if (zie.getJoker() != null)
            sb.append("'").append(zie.getJoker()).append("'").append(",");
        else
            sb.append("''").append(",");
        sb.append(zie.getSumme()).append(",");
        sb.append(zie.getVerbund()).append(",");
        sb.append(zie.getGerade());
        sb.append(")");        
        return sb.toString();
    }
    
    private String makeInsertJokerGQString(Ziehung zie, JokerGewinnquote jgq) {
       StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO JokerGewinnquoten(Nr, Jahr, Jackpot, E6Anzahl, E6Quote, E5Anzahl, E5Quote, E4Anzahl, E4Quote, E3Anzahl, E3Quote, E2Anzahl, E2Quote) VALUES(");
        sb.append(zie.getNr()).append(",");
        sb.append(zie.getJahr()).append(",");
        
        sb.append(jgq.getQuote(JokerGewinnquote.JACKPOT)).append(",");
        sb.append(jgq.getAnzahl(JokerGewinnquote.R6)).append(",");
        sb.append(jgq.getQuote(JokerGewinnquote.R6)).append(",");
        sb.append(jgq.getAnzahl(JokerGewinnquote.R5)).append(",");
        sb.append(jgq.getQuote(JokerGewinnquote.R5)).append(",");
        sb.append(jgq.getAnzahl(JokerGewinnquote.R4)).append(",");
        sb.append(jgq.getQuote(JokerGewinnquote.R4)).append(",");
        sb.append(jgq.getAnzahl(JokerGewinnquote.R3)).append(",");
        sb.append(jgq.getQuote(JokerGewinnquote.R3)).append(",");
        sb.append(jgq.getAnzahl(JokerGewinnquote.R2)).append(",");
        sb.append(jgq.getQuote(JokerGewinnquote.R2));

        sb.append(")");        
        return sb.toString();                
    }
    
    private String makeInsertGQString(Ziehung zie, LottoGewinnquote lgq) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO Gewinnquoten(Nr, Jahr, Jackpot, R6Anzahl, R6Quote, R5PAnzahl, R5PQuote, R5Anzahl, R5Quote, R4Anzahl, R4Quote, R3Anzahl, R3Quote) VALUES(");
        sb.append(zie.getNr()).append(",");
        sb.append(zie.getJahr()).append(",");
        
        sb.append(lgq.getQuote(LottoGewinnquote.JACKPOT)).append(",");
        sb.append(lgq.getAnzahl(LottoGewinnquote.R6)).append(",");
        sb.append(lgq.getQuote(LottoGewinnquote.R6)).append(",");
        sb.append(lgq.getAnzahl(LottoGewinnquote.R5P)).append(",");
        sb.append(lgq.getQuote(LottoGewinnquote.R5P)).append(",");
        sb.append(lgq.getAnzahl(LottoGewinnquote.R5)).append(",");
        sb.append(lgq.getQuote(LottoGewinnquote.R5)).append(",");
        sb.append(lgq.getAnzahl(LottoGewinnquote.R4)).append(",");
        sb.append(lgq.getQuote(LottoGewinnquote.R4)).append(",");
        sb.append(lgq.getAnzahl(LottoGewinnquote.R3)).append(",");
        sb.append(lgq.getQuote(LottoGewinnquote.R3));

        sb.append(")");        
        return sb.toString();
        
        
    }
    
    private void saveDataToDb() {
        Connection con;
        Statement stmt;
        
        Ziehung zie;
        LottoGewinnquote lgq;
        JokerGewinnquote jgq;
        Calendar help = null;
        
        try {
            Class.forName(jdbcDriver);
            
            con = DriverManager.getConnection(url, user, password);                           
            stmt = con.createStatement();                
            
            Iterator it = auss.iterator();            
            while (it.hasNext()) {   
                zie = (Ziehung)it.next();
                stmt.executeUpdate(makeInsertZieString(zie));

                lgq = zie.getLottoGewinnquote();
                if (lgq != null) 
                    stmt.executeUpdate(makeInsertGQString(zie, lgq));
                    
                jgq = zie.getJokerGewinnquote();
                if (jgq != null)
                    stmt.executeUpdate(makeInsertJokerGQString(zie, jgq));
            }
            
            stmt.close();
            con.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);            
        } catch(ClassNotFoundException e) {
            System.out.println(e);
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
            
    }

    
    private void loadDatafromDB() {
        int zahlen[] = new int[6];
        Statement stmt;
        ResultSet rs, rs2, rs3;
        Connection con;
        java.sql.Date sqlDate;
        Calendar date;
        int nr, jahr, zz;
        String joker;
        PreparedStatement selectGQ;

        double jackpot, r6q, r5pq, r5q, r4q, r3q;
        int r6a, r5pa, r5a, r4a, r3a;
        
        double e6q, e5q, e4q, e3q, e2q;
        int    e6a, e5a, e4a, e3a, e2a;

        int gqnr, gqjahr;

        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(url, user, password);  
            stmt = con.createStatement();
            rs = stmt.executeQuery(selectZiehungenStr);
            
            while(rs.next()) {
                sqlDate = rs.getDate(1);
                date = new MyCalendar(sqlDate.getTime());
        
                nr = rs.getInt(2);
                jahr = rs.getInt(3);

                for (int j = 0; j < 6; j++)
                    zahlen[j] = rs.getInt(4+j);

                zz     = rs.getInt(10);
                joker  = rs.getString(11);                        	
            	
            	auss.addZiehung(date, nr, jahr, zahlen, zz, joker);
            }
            rs.close();

            rs2 = stmt.executeQuery(selectGQStr);
            Hashtable ht = new Hashtable();
            
            while(rs2.next()) {
        	   gqnr   = rs2.getInt(1);
               gqjahr = rs2.getInt(2);
               
               jackpot = rs2.getDouble(3);
               r6a     = rs2.getInt(4);
               r6q     = rs2.getDouble(5);
               r5pa    = rs2.getInt(6);
               r5pq    = rs2.getDouble(7);
               r5a     = rs2.getInt(8);
               r5q     = rs2.getDouble(9);
               r4a     = rs2.getInt(10);
               r4q     = rs2.getDouble(11);
               r3a     = rs2.getInt(12);
               r3q     = rs2.getDouble(13);
               
               LottoGewinnquote lgq = new LottoGewinnquote(jackpot, r6a, r6q,
                                          r5pa, r5pq, r5a, r5q,
                                          r4a, r4q, r3a, r3q); 
               ht.put(new Integer(gqjahr*1000+gqnr), lgq);                   
            }            
            rs2.close();

            rs3 = stmt.executeQuery(selectJokerGQStr);
            while(rs3.next()) {
                gqnr = rs3.getInt(1);
                gqjahr = rs3.getInt(2);
                
                jackpot = rs3.getDouble(3);
                e6a     = rs3.getInt(4);
                e6q     = rs3.getDouble(5);
                e5a     = rs3.getInt(6);
                e5q     = rs3.getDouble(7);
                e4a     = rs3.getInt(8);
                e4q     = rs3.getDouble(9);
                e3a     = rs3.getInt(10);
                e3q     = rs3.getDouble(11);
                e2a     = rs3.getInt(12);
                e2q     = rs3.getDouble(13);   

                JokerGewinnquote jgq = new JokerGewinnquote(jackpot, e6a, e6q,
                                          e5a, e5q, e4a, e4q, e3a, e3q, e2a, e2q); 

                ht.put(new Integer(gqjahr*10000+gqnr), jgq);                   
            }
            rs3.close();
            
            Iterator it = auss.iterator();
            Ziehung help;
            
            while (it.hasNext()) {
                help = (Ziehung)it.next();
                LottoGewinnquote lgq = (LottoGewinnquote)(ht.get(new Integer(help.getJahr()*1000+help.getNr())));
                if (lgq != null)
                	help.setLottoGewinnquote(lgq);
                else {
                    help.setLottoGewinnquote(null);
                    System.out.println(help.getNr() + " / " + help.getJahr());
                }
                JokerGewinnquote jgq = (JokerGewinnquote)(ht.get(new Integer(help.getJahr()*10000+help.getNr())));                
                help.setJokerGewinnquote(jgq);
            }
 
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);            
        } catch(ClassNotFoundException e) {
            System.out.println(e);
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
    }
}