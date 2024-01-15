import javax.servlet.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;
import COM.odi.*;
import COM.odi.util.*;

public class FileDataReaderRunner implements Runnable {

    private Thread myThread;
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");    
    
    private Transaction tr;

    private Map datesMap;
    private Map swiftInputMap;
    private List bookingList;
    private List specialList;
    
    private Set stateSet;
    private List missingSWIFTList;

    private SWIFTServer server;

    private int waitTimeInSeconds;
    private int maxLoopToCheckPoint;

    private String dataFile1, dataFile2, stateFile, okFile, bookingFile;

    public FileDataReaderRunner(SWIFTServer ss) {
        
        dataFile1 = AppProperties.getProperty("headerFile");
        dataFile2 = AppProperties.getProperty("msgFile");
        stateFile = AppProperties.getProperty("stateFile");
        okFile    = AppProperties.getProperty("okFile");
        bookingFile = AppProperties.getProperty("bookingFile");

        try {
            waitTimeInSeconds   = Integer.parseInt(AppProperties.getProperty("waitTimeInSeconds"));
            maxLoopToCheckPoint = Integer.parseInt(AppProperties.getProperty("maxLoopToCheckPoint"));
        } catch (NumberFormatException nfe) {
            waitTimeInSeconds = 900;
            maxLoopToCheckPoint = 500;
        }
        
        this.server = ss;
        myThread = new Thread(this);
        myThread.start();   
    }

/*
    public synchronized void join() throws InterruptedException {
    	if (myThread != null) 
    	    myThread.join();
    }
*/

    public void run() {
        File newF1, newF2, newStateF, okf, newBookingF;
                   
        while(Thread.currentThread() == myThread) {
            
            okf = new File(okFile);
            if (okf.exists()) {
            
                okf.delete();
            
                File f1 = new File(dataFile1);
                File f2 = new File(dataFile2);   
                File statef = new File(stateFile);
                File bookingF = new File(bookingFile);
                
                if ( (f1.exists() && f2.exists()) || (bookingF.exists())) {
                    DiskLog.log(DiskLog.INFO, getClass().getName(), "new data");
               
                    server.destroyServlets();     
                    server.stopServer();                

                    Session session = null;
                    try {
                        session = DbManager.createSession();
                        session.join();        
                                                                                                       
                        if (f1.exists() && f2.exists()) {
                            newF1 = new File(dataFile1+System.currentTimeMillis());
                            newF2 = new File(dataFile2+System.currentTimeMillis());
                    
                            f1.renameTo(newF1);
                            f2.renameTo(newF2);
                    
                            newStateF = null;    
                            if (statef.exists()) {
                                newStateF = new File(stateFile+System.currentTimeMillis());
                                statef.renameTo(newStateF);                        
                            }
                
                            try {
                                if (!DbManager.isInitialized())
                                    DbManager.initialize(false);
                
                                Database db = Database.open(DbManager.DB_NAME, ObjectStore.UPDATE);        
        
                                Calendar old = new GregorianCalendar();
                                old.add(Calendar.DATE, -10);
                        
                                tr = Transaction.begin(ObjectStore.UPDATE);   
                        
                                swiftInputMap = (OSTreeMapString)db.getRoot(DbManager.ROOT_STR);
                                datesMap = (OSHashMap)db.getRoot(DbManager.DATE_COLL_ROOT_STR);
                                
                                deleteMessagesOlderThan(old);
                                tr.commit(ObjectStore.RETAIN_HOLLOW);

                                tr = Transaction.begin(ObjectStore.UPDATE);
                                missingSWIFTList = (OSVectorList)db.getRoot(DbManager.MISSING_SWIFT_ROOT);                        
                                checkSWIFTs();
                                tr.commit(ObjectStore.RETAIN_HOLLOW);
                                        
                                tr = Transaction.begin(ObjectStore.UPDATE);   
                                startReading(newF1, newF2, old);
                                tr.commit(ObjectStore.RETAIN_HOLLOW);

                                if (newStateF != null) {
                                    if (newStateF.exists()) {
                                        tr = Transaction.begin(ObjectStore.UPDATE);   
                                    
                                        stateSet = (OSHashSet)db.getRoot(DbManager.STATE_ROOT);
                                        stateSet.clear();
                                        
                                        readStates(newStateF);                            
                                        tr.commit(ObjectStore.RETAIN_HOLLOW);
                                    }                    
                                }
                               
                                tr = Transaction.begin(ObjectStore.UPDATE);   
                                Long lastUpdate = new Long((new GregorianCalendar()).getTime().getTime());
                                db.setRoot(DbManager.LAST_UPDATE_ROOT_STR, lastUpdate);                
                                tr.commit();
                                db.close();
                                db.GC();
                                
                            } finally {
                                newF1.delete();                        
                                newF2.delete();
                                if (newStateF != null) newStateF.delete();
                            }   
                        }     
                            
                        if (bookingF.exists()) {
                            newBookingF = new File(bookingFile+System.currentTimeMillis());    
                            bookingF.renameTo(newBookingF);
                    
                            try {
                                if (!DbManager.isBookingDBInitialized())
                                    DbManager.initialize_bookingdb(false);
                
                                Database db = Database.open(DbManager.BOOKING_DB_NAME, ObjectStore.UPDATE);        
                                tr = Transaction.begin(ObjectStore.UPDATE);   
                                bookingList = (OSVectorList)db.getRoot(DbManager.BOOKING_ROOT);
                                bookingList.clear();
                                specialList = (OSVectorList)db.getRoot(DbManager.SPECIAL_BOOKING_ROOT);
                                specialList.clear();
                                
                                readBookings(newBookingF);
                                
                                Long lastUpdate = new Long((new GregorianCalendar()).getTime().getTime());
                                db.setRoot(DbManager.LAST_UPDATE_ROOT_STR, lastUpdate);                
                                
                                
                                tr.commit();
                                db.close();
                                db.GC();
                            } finally {
                                newBookingF.delete();
                            }
                        }
                    } finally {
                        session.terminate();
                        server.startServer();
                    }

                }              
                                    
            }             
                        
            try {
               myThread.sleep(waitTimeInSeconds*1000);
            } catch(InterruptedException ex) { 
                return; 
            }
        }
    }
    

    private void checkSWIFTs() {
        
        DecimalFormat format = new DecimalFormat("000000");        
        
        missingSWIFTList.clear();
        
        Set keys = swiftInputMap.keySet();
        
        Object arr[] = new Object[keys.size()];
        arr = keys.toArray(); 
        Sorting.quickSort(arr, 0, arr.length-1, new LessString());
        
        int mtosn  = 0;
        int tosn   = 0;

        if (arr.length > 0)
            mtosn = Integer.parseInt((String)arr[0]);

        for(int i = 0; i < arr.length; i++) {
            tosn = Integer.parseInt((String)arr[i]);
            while(tosn > mtosn) {
                missingSWIFTList.add(format.format(mtosn));
                mtosn++;                
            }
            mtosn++;
        }
    }
    

    public void deleteMessages(String date) {
        OSHashSet tempSet = new OSHashSet();
        
        Set set = (Set)datesMap.get(date);
        if (set != null) {
            Iterator it = set.iterator();
            while(it.hasNext()) {
                tempSet.add(swiftInputMap.remove(((SWIFTHeader)it.next()).getTOSN()));    
            }                        
            
            set = (OSHashSet)datesMap.remove(date);                        
            it = tempSet.iterator();
            while(it.hasNext()) {
                SWIFTHeader sh = (SWIFTHeader)it.next();
                set.remove(sh);
            }
        } else {
            DiskLog.log(DiskLog.WARNING, getClass().getName(), "deleteMessage INTERSSANT");
        } 
            
    }
    
    
    public void addSWIFT(SWIFTHeader sh) {
        if (sh != null)
            swiftInputMap.put(sh.getTOSN(), sh);        
        
        String d = dateFormat.format(sh.getReceiveDate().getTime());
        OSHashSet set;
        
        set = (OSHashSet)datesMap.get(d);
        if (set != null)
            set.add(sh);
        else {
            set = new OSHashSet();
            set.add(sh);
            datesMap.put(d, set);
        }        
    }
    
    private void readBookings(File file) {
        String line;
        String macct = null;
        OSHashMap hm = new OSHashMap();
        
        try {                
            BufferedReader dis = new BufferedReader(new FileReader(file));  
            
            int counter = 0;
            
            while ((line = dis.readLine()) != null) {
                if (line.length() >= 109) {
                    try {              
                        String acct = line.substring(0,19);
                        
                        Booking b = new Booking();
                        b.setAccount(acct);
                        b.setOrderno(line.substring(20,47));
                        
                        String t = line.substring(49,85).trim();
                        int pos1 = t.indexOf("//");
                        if (pos1 != -1) {
                            int pos2 = t.indexOf("CAHA");
                            if (pos2 != -1) {                                
                                b.setText(t.substring(pos2, pos1));
                            } else {
                                b.setText(t);
                            }
                        } else {
                            b.setText(t);
                        }                        
                        
                        String d = line.substring(104,112);
                        /*
                        int day   = Integer.parseInt(line.substring(108,110));
                        int month = Integer.parseInt(line.substring(106,108)) - 1;
                        int year  = Integer.parseInt(line.substring(102,106));
                        
                        b.setValueDate(new GregorianCalendar(year, month, day));
                        */
                        b.setValueDate(d);                        
                        b.setAmount(new BigDecimal(line.substring(86,103).trim()));

                        if (macct == null) {
                            macct = acct;   
                        }
                        else if (!macct.equals(acct)) {                            
                            bookingList.add(hm);
                            hm = new OSHashMap();
                            macct = acct;
                        }    

                        if (((b.getText().indexOf("CAHA")) == -1) && 
                            ((b.getText().indexOf("HT")) == -1)) {
                            specialList.add(b);                                        
                        } else {                                               
                            List vl = (List)hm.get(b.getText());
                            if (vl != null) {
                                vl.add(b);
                            } else {
                                vl = new OSVectorList();
                                vl.add(b);
                                hm.put(b.getText(), vl);
                            }
                        }                                                
                                                                        
           	    		if (counter > maxLoopToCheckPoint) {
         		            tr.checkpoint(ObjectStore.RETAIN_HOLLOW);
		        	    	counter = 0;
    			        }
                                                                        
                    } catch (NumberFormatException nfe) {                        
                    }
                }
	        }	  
            if (hm.size() > 0) {                
                bookingList.add(hm);
            }
	        
            dis.close();
        } catch (FileNotFoundException e) {
            DiskLog.log(DiskLog.ERROR, getClass().getName(), "readBookings " + e);
        }
        catch (IOException e) {
             DiskLog.log(DiskLog.ERROR, getClass().getName(), "readBookings " + e);
        }        

    }
    
    private void readStates(File file) {
        String line;
        int year,month,day,h,m;
        
        try {
            BufferedReader dis = new BufferedReader(new FileReader(file));     
            while ((line = dis.readLine()) != null) {
                
                State state = new State();
                state.setProcCenter(line.substring(0,4)); 
                state.setOrderNumber(line.substring(4,39)); 
                state.setStateNum(line.substring(39,41));
                state.setStateTran(line.substring(41,44));
                state.setMessageType(line.substring(44,45));
                
                StringTokenizer st = new StringTokenizer(line.substring(4,39),".");
                if (st.hasMoreTokens())
                    state.setGtfNumber(Integer.parseInt(st.nextToken()));
                else
                    state.setGtfNumber(0);

                year  = Integer.parseInt(line.substring(45,49));
                month = Integer.parseInt(line.substring(49,51))-1;
                day   = Integer.parseInt(line.substring(51,53));
                h     = Integer.parseInt(line.substring(53,55));
                m     = Integer.parseInt(line.substring(55,57));
                
                Calendar date = new GregorianCalendar(year, month, day, h, m, 0);
                state.setStateDate(date);
                
                stateSet.add(state);
            }
        }
        catch (FileNotFoundException e) {
            DiskLog.log(DiskLog.ERROR, getClass().getName(), "readStates " + e);
        }
        catch (IOException e) {
            DiskLog.log(DiskLog.ERROR, getClass().getName(), "readStates " + e);
        }
        catch (NumberFormatException nfe) {
            DiskLog.log(DiskLog.ERROR, getClass().getName(), "readStates " + nfe);
        }

        
    }
        
    private void startReading(File f1, File f2, Calendar old) {
        int year, month, day, h, m;
        GregorianCalendar date;
        BufferedReader dis;
        String line;
      	
        SWIFTHeader shObj;
        MessageLine mlObj;
        Set tempSet = new OSHashSet();

        try {                
            dis = new BufferedReader(new FileReader(f1));     
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
                        if (swiftInputMap.get(shObj.getTOSN()) == null) {
                            addSWIFT(shObj);
                            tempSet.add(shObj.getTOSN());
                        }
                    }
	            }
	        }
            dis.close();

            tr.checkpoint(ObjectStore.RETAIN_HOLLOW);

            dis = new BufferedReader(new FileReader(f2));
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
                    SWIFTHeader sh = (SWIFTHeader)swiftInputMap.get(mtosn);
      	            if (sh != null) {
        				counter++;
                        sh.setMessage(vect);
				    }
			        else {
			            DiskLog.log(DiskLog.ERROR, getClass().getName(), "startReading INTERESSANT");
			        }
    			}

	    		if (counter > maxLoopToCheckPoint) {
 		            tr.checkpoint(ObjectStore.RETAIN_HOLLOW);
			    	counter = 0;
    			}
                        					                                
                if (line != null)
                    mtosn = new String(tosn);
            }
            dis.close();
        }
        catch (FileNotFoundException e) {
            DiskLog.log(DiskLog.ERROR, getClass().getName(), "startReading " + e);
        }
        catch (IOException e) {
            DiskLog.log(DiskLog.ERROR, getClass().getName(), "startReading " + e);
        }
        catch (NumberFormatException nfe) {
            DiskLog.log(DiskLog.ERROR, getClass().getName(), "startReading " + nfe);
        }

    }
    
    
    private void deleteMessagesOlderThan(Calendar old) {
        Set set = datesMap.keySet();
        ParsePosition pos = new ParsePosition(0);
        Calendar cal = new GregorianCalendar();

        Iterator it = set.iterator();
        while(it.hasNext()) {
            String dateStr = (String)it.next();
            pos = new ParsePosition(0);
            Date d = dateFormat.parse(dateStr, pos);
            cal.setTime(d);
            if (cal.before(old))  {
                DiskLog.log(DiskLog.INFO, getClass().getName(), "deleteMessagesOlderThan :" + dateStr);
                deleteMessages(dateStr);
            }
        }

    }


}
