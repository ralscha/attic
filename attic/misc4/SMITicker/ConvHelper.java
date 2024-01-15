import java.text.*;
import java.util.*;

public class ConvHelper {
    
    public static double getNetChg(String in) throws NumberFormatException {
        String netStr = in.substring(0, in.indexOf("("));
        return (string2double(netStr));
    }
    
    public static double getPctChg(String in) throws NumberFormatException {
        String pctStr = in.substring(in.indexOf("(")+1, in.indexOf(")"));
        return (string2double(pctStr));
    }
            
    public static double string2double(String in) throws NumberFormatException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < in.length(); i++) {
            if (Character.isDigit(in.charAt(i)) || (in.charAt(i) == '.') ||
                (in.charAt(i) == '-') || in.charAt(i) == '+')
                sb.append(in.charAt(i));
        }
        return new Double(sb.toString()).doubleValue();
    }
    
    public static Calendar getCalendar(String in) throws NumberFormatException {
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd ");
        
        Calendar rightNow = new GregorianCalendar();
        
        in = formatDate.format(rightNow.getTime()) + in;
        ParsePosition pos = new ParsePosition(0);
        Date help = formatTime.parse(in, pos);
        if (help == null) throw new NumberFormatException();
        
        Calendar cal = new GregorianCalendar();
        cal.setTime(help);
        
        return cal;      
    }
}