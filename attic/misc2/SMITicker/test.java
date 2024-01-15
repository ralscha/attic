import java.io.*;
import java.util.*;
import java.text.*;

public class test {
    
    private final static String fileInput = "parsefquote.html";
    
    public static void main(String args[]) {
        BufferedReader inStream = null;
        String inputLine;
        StringTokenizer st;
        Vector vect = new Vector();
        Vector okvect = new Vector();
    
        String time, last, pctChg, netChg, change, high, low;
        time = last = pctChg = netChg = high = low = change = "";
        
    
            
        try {
            inStream = new BufferedReader(new TagFilterReader(new FileReader(fileInput)));
            
            while ((inputLine = inStream.readLine()) != null) {
                st = new StringTokenizer(inputLine.trim());
                while (st.hasMoreTokens()) {
                    vect.addElement(st.nextToken());
                }
            }
            inStream.close();

            int i = 0;
            while (i < vect.size()) {
                if (((String)vect.elementAt(i)).equalsIgnoreCase("low")) {
       
                    time = (String)vect.elementAt(i+1);
                    last = (String)vect.elementAt(i+2);
                    change = (String)vect.elementAt(i+3);
                    high = (String)vect.elementAt(i+4);
                    low = (String)vect.elementAt(i+5);
                    break;
                }
                i++;
            }
            
            try {
                System.out.println("TIME   "+time);        
                System.out.println("LAST   "+last);
                System.out.println("CHANGE "+change);
                System.out.println("HIGH   "+high);            
                System.out.println("LOW    "+low);            
                
                System.out.println(getCalendar(time).getTime());
                System.out.println(string2double(last));
                System.out.println(string2double(high));
                System.out.println(string2double(low));
                System.out.println(getNetChg(change));
                System.out.println(getPctChg(change));
                
            } catch (NumberFormatException nfe) {
                System.out.println(nfe);
            }
        
/*            Enumeration e = vect.elements();
            int x = 0;
            while(e.hasMoreElements()) {
                System.out.println(x + "  " + e.nextElement());
                x++;
            }*/
            
        } catch (IOException e) {
            System.out.println(e);
        }
           
        
    }
        

    private static Calendar getCalendar(String in) {
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd ");
        
        Calendar rightNow = new GregorianCalendar();
        
        in = formatDate.format(rightNow.getTime()) + in;
        ParsePosition pos = new ParsePosition(0);
        Date help = formatTime.parse(in, pos);
        
        Calendar cal = new GregorianCalendar();
        cal.setTime(help);
        
        return cal;      
    }
        
    private static double getNetChg(String in) throws NumberFormatException {
        String netStr = in.substring(0, in.indexOf("("));
        return (string2double(netStr));
    }
    
    private static double getPctChg(String in) throws NumberFormatException {
        String pctStr = in.substring(in.indexOf("(")+1, in.indexOf(")"));
        return (string2double(pctStr));
    }
            
    private static double string2double(String in) throws NumberFormatException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < in.length(); i++) {
            if (Character.isDigit(in.charAt(i)) || (in.charAt(i) == '.') ||
                (in.charAt(i) == '-') || in.charAt(i) == '+')
                sb.append(in.charAt(i));
        }
        return new Double(sb.toString()).doubleValue();
    }
}
