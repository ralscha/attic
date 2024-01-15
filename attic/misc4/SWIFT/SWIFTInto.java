import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import com.sun.java.swing.*;
import java.awt.event.*;

public class SWIFTInto
{
    private Timer timer;
    private String dataPath, backupPath;
    private final static String drv = "sun.jdbc.odbc.JdbcOdbcDriver";
 	private final static String url = "jdbc:odbc:SWIFTInput";
    private DbAccess dba;
    
    public SWIFTInto(String args[])
    {
        
        timer = new Timer(1000, new ActionListener() {
                                      public void actionPerformed(ActionEvent event) {
                                          startReading();
                                      }});        
                                      
        
        if (args.length == 2) {
            dataPath = args[0];
            backupPath = args[1];
        }    
        else {
            System.out.println("java SWIFTInto <dataPath> <backupPath>");
            return;
        }

        
        
        try {
    		dba = new DbAccess(drv, url, "", "");
    		timer.start();
        } catch (SQLException ex) {
            while (ex != null) {
              System.out.println ("SQLState: " + ex.getSQLState ());
              System.out.println ("Message:  " + ex.getMessage ());
              System.out.println ("Vendor:   " + ex.getErrorCode ());
              ex = ex.getNextException ();
              System.out.println ("");
            }
            System.exit(1);
    	} catch (ClassNotFoundException cne) {
            System.err.println("Class sun.jdbc.odbc.JdbcOdbcDriver not found "+cne);
        }
    }
        
    synchronized private void startReading()
    {

        Vector headFiles, msgFiles;

        headFiles = new Vector();
        msgFiles  = new Vector();

        java.sql.Date date;
        java.sql.Time time;
        int year,month,day;

        ResultSet rs;

        BufferedReader dis;
        PrintWriter    out;
        String line;

      	SWIFTHeader shObj;
       	SWIFTLines  slObj;
		SWIFTLinesDriver linesDriver = new SWIFTLinesDriver(dba);
        SWIFTHeaderDriver headerDriver = new SWIFTHeaderDriver(dba); 

        File f;
        f = new File(dataPath);            
        String lst[] = f.list();

        for (int i = 0; i < lst.length; i++) {
            if (lst[i].endsWith(".dat"))
                if (lst[i].startsWith("SI_Head"))
                    headFiles.addElement(lst[i]);
                else if (lst[i].startsWith("SI_Msg"))
                    msgFiles.addElement(lst[i]);
        }


        try {
       	    
       	    /*
            rs = headerDriver.query(null, "TOSN asc");
            int mtosn = 0;
            int tosnno = 0;

            shObj = headerDriver.next(rs);
            if (shObj != null) {
                mtosn = Integer.parseInt(shObj.getTOSN());        
                do {
                    tosnno = Integer.parseInt(shObj.getTOSN());
                    while(tosnno > mtosn + 1) {
                        mtosn++;
                        System.out.println("Es fehlt folgender SWIFT: "+mtosn);
                    }
                    mtosn++;
 	            } while ((shObj = headerDriver.next(rs)) != null);
     	    }
            */
            /*
            Vector tosnArray = new Vector();
            Calendar today = Calendar.getInstance();
            today.add(Calendar.DATE, -30);

            date = new java.sql.Date(today.get(Calendar.YEAR)-1900, today.get(Calendar.MONTH),
                                     today.get(Calendar.DATE));
            rs = headerDriver.query("Receive_Date < #"+date+"#",null);
            
            while ((shObj = headerDriver.next(rs)) != null)
	            tosnArray.addElement(shObj.getTOSN());

            Enumeration e = tosnArray.elements();
            while(e.hasMoreElements())
                linesDriver.deleteTOSN((String)e.nextElement());
            headerDriver.delete("Receive_Date < #"+date+"#");
            */

            for (int i = 0; i < headFiles.size(); i++) {
                System.out.println("reading ... "+dataPath+"\\"+(String)headFiles.elementAt(i));
                
                dis = new BufferedReader(new FileReader(dataPath+"\\"+(String)headFiles.elementAt(i)));
                out = new PrintWriter(new FileWriter(backupPath+"\\"+(String)headFiles.elementAt(i)));
                shObj = new SWIFTHeader();
            
                while ((line = dis.readLine()) != null) {
                   if (line.length() > 2) {
                       out.println(line); /* BACKUP */
                       
                       shObj.setTOSN(line.substring(0,6));

                       year  = Integer.parseInt(line.substring(6,10))-1900;
                       month = Integer.parseInt(line.substring(10,12))-1;
                       day   = Integer.parseInt(line.substring(12,14));
                       date = new java.sql.Date(year, month, day);
                       shObj.setSend_Date(date);

                       shObj.setAddress_Sender(line.substring(14,26));
                       shObj.setSession_Number(line.substring(26,30));
                       shObj.setSequence_Number(line.substring(30,36));
                       shObj.setAddress_Receiver(line.substring(45,56)); 
                       shObj.setProc_Center(line.substring(36,40)); 
                       shObj.setMessage_Type(line.substring(40,43));
                       shObj.setDuplicate(line.substring(44,45)); 
                       shObj.setPriority(line.substring(43,44)); 

                       date=null;
                       year  = Integer.parseInt(line.substring(56,60))-1900;
                       month = Integer.parseInt(line.substring(60,62))-1;
                       day   = Integer.parseInt(line.substring(62,64));
                       date = new java.sql.Date(year, month, day);
                       shObj.setReceive_Date(date);

                       time=null;
                       time = new java.sql.Time(Integer.parseInt(line.substring(64,66)),
                                               Integer.parseInt(line.substring(66,68)),0);

                       shObj.setReceive_Time(time);
                       
                       try {
                           headerDriver.insert(shObj);
                           headerDriver.commit();
                       } catch (SQLException ex) {
                          if (!ex.getSQLState().equals("S1000"))
                            throw(ex);
                       }
                   }
               }
               dis.close();
               out.close();
            }


            for (int i = 0; i < msgFiles.size(); i++) {
                System.out.println("reading ... "+dataPath+"\\"+(String)msgFiles.elementAt(i));

                dis = new BufferedReader(new FileReader(dataPath+"\\"+(String)msgFiles.elementAt(i)));
                out = new PrintWriter(new FileWriter(backupPath+"\\"+(String)msgFiles.elementAt(i)));

                String tosn;
                int to;

                slObj = new SWIFTLines();
                while ((line = dis.readLine()) != null) {
                    out.println(line);
                    if (line.length() > 2) {
                        tosn = line.substring(0,6);                    
                        slObj.setTOSN(tosn);                       
                        slObj.setLineno(Integer.parseInt(line.substring(6,10)));
                        slObj.setField_Tag(line.substring(10,13));
                        slObj.setMsg_Line(line.substring(13));
                       
                        try {
                            linesDriver.insert(slObj);
                            linesDriver.commit();
                        } catch (SQLException ex) {
                            if (!ex.getSQLState().equals("S1000"))
                                throw(ex);
                        }
                    }
                }
                dis.close();
                out.close();
            }
            
            
            /* Files kopieren und löschen */
            for (int i = 0; i < headFiles.size(); i++) {
                f = new File(dataPath+"\\"+(String)headFiles.elementAt(i));
                f.delete();
            }
            for (int i = 0; i < msgFiles.size(); i++) {
                f = new File(dataPath+"\\"+(String)msgFiles.elementAt(i));
                f.delete();
            }
                       
            
        }
        catch (SQLException ex)
        {
            while (ex != null)
            {
              System.out.println ("SQLState: " + ex.getSQLState ());
              System.out.println ("Message:  " + ex.getMessage ());
              System.out.println ("Vendor:   " + ex.getErrorCode ());
              ex = ex.getNextException ();
              System.out.println ("");
            }
            System.exit(1);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("InTest: " + e);
        }
        catch (IOException e)
        {
             System.err.println("InTest: " + e);
        }
        catch (NumberFormatException nfe)
        {
             System.err.println("date error: "+nfe);
        }

    }

    public static void main(String args[])
    {
        new SWIFTInto(args);
    }

}
