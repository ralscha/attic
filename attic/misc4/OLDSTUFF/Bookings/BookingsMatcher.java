import java.io.*;
import java.math.*;
import com.sun.java.util.collections.*;
import java.util.GregorianCalendar;

public class BookingsMatcher {

    Map bookingsMap = new HashMap();
    List specialList = new Vector();
    List okList = new Vector();
    
    public BookingsMatcher(String fileName) {   
        String line;
        String macct = null;
        BigDecimal gtotal = new BigDecimal(0.0d);
        
        try {     
            File okFile = new File("ok.txt");
            if (okFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(okFile));
                while ((line = br.readLine()) != null) {
                    if (line.length() >= 26) {
                        okList.add(line.trim());
                    }
                }
            }                        
            
            BufferedReader dis = new BufferedReader(new FileReader(fileName));     
            while ((line = dis.readLine()) != null) {
                if (line.length() >= 109) {
                    try {
                        
                        String acct = line.substring(0,19);
                        
                        Booking b = new Booking();
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


                        
                        if (macct == null) 
                            macct = acct;                            
                        else if (!macct.equals(acct)) {
                            System.out.println("Account: " + macct + "  DIFFERENZ : " + gtotal);
                            match();
                            printSpecialList();
                            
                            specialList.clear();
                            bookingsMap.clear();
                            gtotal = new BigDecimal(0.0d);
                            macct = acct;
                            System.out.println("***************************************");
                        }    
                        
                        if (!okList.contains(b.getOrderno())) {
                        
                            gtotal = gtotal.add(b.getAmount());
                        
                            
                            if (((b.getText().indexOf("CAHA")) == -1) && 
                                ((b.getText().indexOf("HT")) == -1)) {
                                specialList.add(b);                                        
                            } else {                       
                            
                                List vl = (List)bookingsMap.get(b.getText());
                                if (vl != null) {
                                    vl.add(b);
                                } else {
                                    vl = new Vector();
                                    vl.add(b);
                                    bookingsMap.put(b.getText(), vl);
                                }
                            }
                        }
                                                    
                    } catch (NumberFormatException nfe) {
                        System.out.println(nfe);
                    }
                }
	        }	    
            if (bookingsMap.size() > 0) {                
                System.out.println("Account: " + macct + "  DIFFERENZ : " + gtotal);
                match();
                printSpecialList();                            
            }

            dis.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
        catch (IOException e) {
             System.err.println(e);
        }        
        
    }
    
    private void match() {
        BigDecimal zero = new BigDecimal(0.0d);       
        Iterator it = bookingsMap.values().iterator();
        Map newMap = new HashMap();
        Map newMap2 = new HashMap();
        
        while(it.hasNext()) {
            List vl = (List)it.next();
            BigDecimal total = new BigDecimal(0.0d);
            for (int i = 0; i < vl.size(); i++) {
                Booking b = (Booking)vl.get(i);
                total = total.add(b.getAmount());                
            }
            if (total.compareTo(zero) != 0) {                    
                Booking speB = searchSpecialList(total.negate());   
                if (speB == null) {
                    
                    Map splitmap = split(vl);
                                        
                    Iterator itm = splitmap.values().iterator();
                    while(itm.hasNext()) {
                        List sl = (List)itm.next();
                        total = new BigDecimal(0.0d);
                        for (int i = 0; i < sl.size(); i++) {
                            Booking b = (Booking)sl.get(i);
                            total = total.add(b.getAmount());                
                        }    
                        if (total.compareTo(zero) != 0) {
                            Booking sb = searchSpecialList(total.negate());
                            
                            if (sb == null) {
                                Booking b = (Booking)sl.get(0);
                                if (!searchSpecialList(total.negate(), b.getOrderDate())) {                              
                                    newMap.put(b.getKey(), sl);
                                }
                            }
                        }
                        
                    }  
                }
            }
        }        
        
        Iterator itm = newMap.values().iterator();
        while(itm.hasNext()) {
            List sl = (List)itm.next();
            BigDecimal total = new BigDecimal(0.0d);
            for (int i = 0; i < sl.size(); i++) {
                Booking b = (Booking)sl.get(i);
                total = total.add(b.getAmount());                
            }    
            if (total.compareTo(zero) != 0) {
                Booking sb = searchSpecialList(total.negate());
                
                if (sb == null) {
                    Booking b = (Booking)sl.get(0);
                    if (!searchSpecialList(total.negate(), b.getOrderDate())) {
                        List hl = (List)newMap2.get(b.getText());
                        if (hl != null) {
                            hl.addAll(sl);
                        } else {
                            hl = new Vector();
                            hl.addAll(sl);
                            newMap2.put(b.getText(), hl);
                        }
                    }
                }
            }
            
        }  
                

        itm = newMap2.values().iterator();
        while(itm.hasNext()) {
            List sl = (List)itm.next();
            BigDecimal total = new BigDecimal(0.0d);
            for (int i = 0; i < sl.size(); i++) {
                Booking b = (Booking)sl.get(i);
                total = total.add(b.getAmount());                
            }    
            if (total.compareTo(zero) != 0) {
                Booking sb = searchSpecialList(total.negate());
                
                if (sb == null) {
                    Booking b = (Booking)sl.get(0);
                    if (!searchSpecialList(total.negate(), b.getOrderDate()))                                
                        printList(sl);
                }
            }
            
        }                    

        
        
    }
    
    private Map split(List list) {
        Map m = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            Booking b = (Booking)list.get(i);
            
            List vl = (List)m.get(b.getKey());
            if (vl != null) {
               vl.add(b);
            } else {
               vl = new Vector();
               vl.add(b);
               m.put(b.getKey(), vl);
            }
        }
        return m;
    }
    
    private Booking searchSpecialList(BigDecimal amount) {
        for (int i = 0; i < specialList.size(); i++) {
            Booking b = (Booking)specialList.get(i);
            if (b.getAmount().compareTo(amount) == 0) {
                specialList.remove(i);
                return b;
            }            
        }
        return null;
    }

    private boolean searchSpecialList(BigDecimal amount, String orderDate) {
        
        List l = new Vector();
        BigDecimal total = new BigDecimal(0.0d);
        for (int i = 0; i < specialList.size(); i++) {
            Booking b = (Booking)specialList.get(i);
            if (b.getOrderDate().equals(orderDate)) {
                l.add(b);
                total = total.add(b.getAmount());
            }
        }            
                
        if (total.compareTo(amount) == 0) {
            specialList.removeAll(l);
            return true;
        }            
        return false;
    }

    
    private void printList(List vl) {
        Iterator it = vl.iterator();
        BigDecimal total = new BigDecimal(0.0d);        
        
        while(it.hasNext()) {
            Booking b = (Booking)it.next();
            total = total.add(b.getAmount());
            System.out.println(b);            
        }
        System.out.println("TOTAL " + total);
        System.out.println();
    }
    
    private void printSpecialList() {
        if (specialList.size() > 0) {
            System.out.println("Special List");
            Iterator it = specialList.iterator();
            while(it.hasNext()) {
                System.out.println((Booking)it.next());
            }
            System.out.println("End Special List");
        }
    }
        
    public static void main(String args[]) {
        if (args.length == 1)
            new BookingsMatcher(args[0]);  
        else 
            System.out.println("java BookingsMatcher <file>");
    }

}
