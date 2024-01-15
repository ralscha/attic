/*
import java.io.*;
import java.util.*;
import COM.odi.util.*;
import java.text.*;

public class File2Db {
    
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");    

    public File2Db() {
        
        Calendar old = new GregorianCalendar();
        old.add(Calendar.DATE, -10);
        
        DbManager.initialize(false);
        DbManager.startUpdateTransaction();
        startReading(old);
        DbManager.setLastUpdate((new GregorianCalendar()).getTime().getTime());        
        DbManager.commitTransaction();        

        DbManager.startUpdateTransaction();
        deleteMessagesOlderThan(old);
        DbManager.commitTransaction();        
        
        deleteFiles();
        
        DbManager.shutdown(true);        
    }
    
    public File2Db(String tosn) {
        DbManager.initialize(false);
        DbManager.startReadTransaction();
        showSWIFT(tosn);
        DbManager.commitTransaction();
        DbManager.shutdown(false);        
    }
    
    private void deleteFiles() {
        File f1 = new File("tlc21.dat");
        File f2 = new File("tlc24.dat");
        if (f1.exists()) f1.delete();
        if (f2.exists()) f2.delete();
    }
        
    private void showSWIFT(String tosn) {
        SWIFTHeader sh = DbManager.getSWIFT(tosn);
        MessageLine msg;
        if (sh != null) {            
            System.out.println(sh);            
            List l = sh.getMessage();
            Iterator it = l.iterator();
            while (it.hasNext()) {
                msg = (MessageLine)it.next();
                System.out.println(msg);
            }
        }
        else
            System.out.println("No SWIFT: "+tosn);
    }

    private void deleteMessagesOlderThan(Calendar old) {
        Set set = DbManager.getSIDatesMapKeys();
        ParsePosition pos = new ParsePosition(0);
        Calendar cal = new GregorianCalendar();

        Iterator it = set.iterator();
        while(it.hasNext()) {
            String dateStr = (String)it.next();
            pos = new ParsePosition(0);
            Date d = dateFormat.parse(dateStr, pos);
            cal.setTime(d);
            if (cal.before(old))  {
                System.out.println("Deleting ... " + dateStr);
                DbManager.deleteMessages(dateStr);
            }
        }

    }
        
    private void startReading(Calendar old) {
        int year, month, day, h, m;
        GregorianCalendar date;
        BufferedReader dis;
        String line;
      	
        SWIFTHeader shObj;
        MessageLine mlObj;
        Set tempSet = new OSHashSet();

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
                    shObj.setSendDate(date);

                    shObj.setAddressSender(line.substring(14,26).trim());
                    shObj.setSessionNumber(line.substring(26,30));
                    shObj.setSequenceNumber(line.substring(30,36));
                    shObj.setAddressReceiver(line.substring(45,56).trim()); 
                    shObj.setProcCenter(line.substring(36,40)); 
                    shObj.setMessageType(line.substring(40,43));
                    shObj.setDuplicate(line.substring(44,45)); 
                    shObj.setPriority(line.substring(43,44)); 

                    date=null;
                    year  = Integer.parseInt(line.substring(56,60));
                    month = Integer.parseInt(line.substring(60,62))-1;
                    day   = Integer.parseInt(line.substring(62,64));
                    h     = Integer.parseInt(line.substring(64,66));
                    m     = Integer.parseInt(line.substring(66,68));
                    date = new GregorianCalendar(year, month, day, h, m, 0);
                    shObj.setReceiveDate(date);
                   
                    if (!shObj.getReceiveDate().before(old)) {
                        if (DbManager.getSWIFT(shObj.getTOSN()) == null) {
                            DbManager.addSWIFT(shObj);
                            tempSet.add(shObj.getTOSN());
                        }
                    }
	            }
	        }
            dis.close();

            DbManager.checkPoint();

            dis = new BufferedReader(new FileReader("tlc24.dat"));
            String tosn, mtosn;

            line = dis.readLine();
            mtosn = line.substring(0,6);
            OSVectorList vect = null;
    		int counter = 0;

            while (line != null) {
                tosn = line.substring(0,6);
                vect = new OSVectorList();
                while ((tosn.equals(mtosn)) && (line != null)) {
	                if (line.length() > 2) {
                        mlObj = new MessageLine();
                        mlObj.setLineno(Integer.parseInt(line.substring(6,10)));
                        mlObj.setFieldTag(line.substring(10,13).trim());
                        mlObj.setMsgLine(line.substring(13).trim());
                        vect.add(mlObj);                    
                    }
                    line = dis.readLine();
                    if (line != null) 
                        tosn = line.substring(0,6);
                }
                
                if (tempSet.contains(mtosn)) {
                    SWIFTHeader sh = DbManager.getSWIFT(mtosn);
      	            if (sh != null) {
        				counter++;
                        sh.setMessage(vect);
				    }
			        else System.out.println("SHIT " + mtosn);
    			}

	    		if (counter > 500) {
	           		DbManager.checkPoint();
			    	counter = 0;
    			}
                        					                                
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
*/