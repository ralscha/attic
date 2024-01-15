import java.io.*;
import java.util.*;
import COM.odi.util.*;


public class File2Db
{    
    long fromDate, toDate;
    
    public File2Db() {
        DbManager.initialize(false);
        DbManager.startUpdateTransaction();
        fromDate = DbManager.getFromDate();
        toDate   = DbManager.getToDate();
        startReading();
        DbManager.setFromDate(fromDate);
        DbManager.setToDate(toDate);
        DbManager.commitTransaction();
        DbManager.shutdown();        
    }
    
    public File2Db(String tosn) {
        DbManager.initialize(false);
        DbManager.startReadTransaction();
        showSWIFT(tosn);
        DbManager.commitTransaction();
        DbManager.shutdown();        
    }
        
    private void showSWIFT(String tosn) {
        SWIFTHeader sh = (SWIFTHeader)(DbManager.getSWIFTInputCollection().get(tosn));
        SWIFTLines sl;
        if (sh != null) {
            
            System.out.println(sh);
            
            List l = sh.getMessage();
            Iterator it = l.iterator();
            while (it.hasNext()) {
                sl = (SWIFTLines)it.next();
                System.out.println(sl);
            }
        }
        else
            System.out.println("No SWIFT: "+tosn);
    }
        
    private void startReading() {

        int year, month, day, h, m;
        GregorianCalendar date;
        BufferedReader dis;
        String line;
      	
        SWIFTHeader shObj;
        SWIFTLines  slObj;
        
        try {                
            dis = new BufferedReader(new FileReader("tlc21.dat"));
            
            while ((line = dis.readLine()) != null) {
                if (line.length() > 2) {
                   shObj = new SWIFTHeader();
                   shObj.setTOSN(line.substring(0,6));

                   year  = Integer.parseInt(line.substring(6,10));
                   month = Integer.parseInt(line.substring(10,12))-1;
                   day   = Integer.parseInt(line.substring(12,14));
                   date = new GregorianCalendar(year, month, day);
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
                   year  = Integer.parseInt(line.substring(56,60));
                   month = Integer.parseInt(line.substring(60,62))-1;
                   day   = Integer.parseInt(line.substring(62,64));
                   h     = Integer.parseInt(line.substring(64,66));
                   m     = Integer.parseInt(line.substring(66,68));
                   date = new GregorianCalendar(year, month, day, h, m, 0);
                   shObj.setReceive_Date(date);
                   
                   long millis = date.getTime().getTime();
                   if (fromDate == 0 && toDate == 0) 
                      fromDate = toDate = millis;
                   if (millis < fromDate)    fromDate = millis;
                   else if (millis > toDate) toDate = millis;
                   
                   DbManager.addSWIFT(shObj);
                }
            }
            dis.close();


            dis = new BufferedReader(new FileReader("tlc24.dat"));
            String tosn, mtosn;

            line = dis.readLine();
            mtosn = line.substring(0,6);
            OSVectorList vect = null;

            while (line != null) {
                tosn = line.substring(0,6);
                vect = new OSVectorList();
                while ((tosn.equals(mtosn)) && (line != null)) {
                    if (line.length() > 2) {
                        slObj = new SWIFTLines();
                        slObj.setLineno(Integer.parseInt(line.substring(6,10)));
                        slObj.setField_Tag(line.substring(10,13));
                        slObj.setMsg_Line(line.substring(13));
                        vect.add(slObj);                    
                    }
                    line = dis.readLine();
                    if (line != null) 
                        tosn = line.substring(0,6);
                }
                // Neue TOSN
               SWIFTHeader sh = (SWIFTHeader)(DbManager.getSWIFTInputCollection().get(mtosn));
               if (sh != null)
                   sh.setMessage(vect);
                else
                    System.out.println("No Header: "+mtosn);
                    
               if (line != null)
                   mtosn = new String(tosn);
            }
            dis.close();
            
            
        }
        catch (FileNotFoundException e) {
            System.err.println(e);
        }
        catch (IOException e) {
             System.err.println(e);
        }
        catch (NumberFormatException nfe) {
             System.err.println(nfe);
        }

    }

    public static void main(String args[]) {
        if (args.length == 0)
            new File2Db();  
        else if (args.length == 1)
            new File2Db(args[0]);
    }

}
