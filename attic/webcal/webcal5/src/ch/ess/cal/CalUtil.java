package ch.ess.cal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.Constants;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.cal.model.BaseEvent;
import ch.ess.cal.service.EventDistribution;
import ch.ess.cal.service.EventDistributionItem;


public class CalUtil {

  private final static int BOX_WIDTH = 3;
  private final static int BOX_HEIGHT = 3;

  
  private CalUtil() {
    //it's a singleton
  }

  public static Calendar newCalendar(TimeZone tz, int year, int month, int day) {
    return newCalendar(tz, year, month, day, 0, 0, 0, 0);
  }

  public static Calendar newCalendar(TimeZone tz, int year, int month, int day, int hour, int minute, int second,
      int millis) {
    Calendar c = new GregorianCalendar(tz);
    c.set(Calendar.YEAR, year);
    c.set(Calendar.MONTH, month);
    c.set(Calendar.DATE, day);
    c.set(Calendar.HOUR_OF_DAY, hour);
    c.set(Calendar.MINUTE, minute);
    c.set(Calendar.SECOND, second);
    c.set(Calendar.MILLISECOND, millis);
    return c;
  }
  
  public static Calendar newCalendarStartWeek(TimeZone tz, int year, int week) {
    Calendar cal = newCalendar(tz, year, Calendar.JANUARY, 1, 0, 0, 0, 0);
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.set(Calendar.WEEK_OF_YEAR, week);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return cal;
  }
  
  public static Calendar newCalendarEndWeek(TimeZone tz, int year, int week) {
    Calendar cal = newCalendar(tz, year, Calendar.JANUARY, 1, 23, 59, 59, 999);
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.set(Calendar.WEEK_OF_YEAR, week);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    return cal;
  }  


  //Calendar functions
  public static Calendar string2Calendar(String calStr, TimeZone tz) {
    if (StringUtils.isBlank(calStr)) {
      return null;
    }
    SimpleDateFormat format = new SimpleDateFormat(Constants.getParseDateFormatPattern());
    format.setLenient(false);
    format.setTimeZone(tz);
    try {
      Date d = format.parse(calStr);
      Calendar cal = new GregorianCalendar(tz);
      cal.setTime(d);
      return cal;
    } catch (ParseException e) {
      return null;
    }
  }
  
  public static Calendar string2Calendar(String calStr, TimeZone tz, String dateFormatPattern) {
    if (StringUtils.isBlank(calStr)) {
      return null;
    }
    SimpleDateFormat format = new SimpleDateFormat(dateFormatPattern);
    format.setLenient(false);
    format.setTimeZone(tz);
    try {
      Date d = format.parse(calStr);
      Calendar cal = new GregorianCalendar(tz);
      cal.setTime(d);
      return cal;
    } catch (ParseException e) {
      return null;
    }
  }

  
  public static Calendar string2Calendar(String calStr, Integer hstr, Integer mstr, TimeZone tz, String dateFormatPattern) {
    
    if (StringUtils.isBlank(calStr)) {
      return null;
    }

    int h = 0;
    int m = 0;
    if (hstr != null) {
      h = hstr;
    }

    if (mstr != null) {
      m = mstr;
    }

    SimpleDateFormat format = new SimpleDateFormat(dateFormatPattern);
    format.setLenient(false);
    format.setTimeZone(tz);
    try {
      Date d = format.parse(calStr);
      Calendar cal = new GregorianCalendar(tz);
      cal.setTime(d);
      cal.set(Calendar.HOUR_OF_DAY, h);
      cal.set(Calendar.MINUTE, m);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);

      return cal;
    } catch (ParseException e) {
      return null;
    }
  }

  public static Calendar string2Calendar(String calStr, String hstr, String mstr, TimeZone tz) {
    if (StringUtils.isBlank(calStr)) {
      return null;
    }

    int h = 0;
    int m = 0;
    if (StringUtils.isNotBlank(hstr)) {
      h = Integer.parseInt(hstr);
    }

    if (StringUtils.isNotBlank(mstr)) {
      m = Integer.parseInt(mstr);
    }

    SimpleDateFormat format = new SimpleDateFormat(Constants.getDateFormatPattern());
    format.setLenient(false);
    format.setTimeZone(tz);
    try {
      Date d = format.parse(calStr);
      Calendar cal = new GregorianCalendar(tz);
      cal.setTime(d);
      cal.set(Calendar.HOUR_OF_DAY, h);
      cal.set(Calendar.MINUTE, m);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);

      return cal;
    } catch (ParseException e) {
      return null;
    }

  }

  public static Calendar utcLong2UtcCalendar(long time) {
    return utcLong2UserCalendar(time, Constants.UTC_TZ);
  }
  
  public static Calendar utcLong2UserCalendar(long time, TimeZone tz) {
    Calendar cal = new GregorianCalendar(tz);
    cal.setTimeInMillis(time);
    return cal;
  }

  public static String userCalendar2String(Calendar cal) {
    return userCalendar2String(cal, Constants.getDateFormatPattern());
  }

  public static String userCalendar2String(Calendar cal, String pattern) {

    if (cal == null) {
      return null;
    }

    SimpleDateFormat format = new SimpleDateFormat(pattern);
    format.setTimeZone(cal.getTimeZone());
    return format.format(cal.getTime());
  }

  public static boolean isSameDay(Calendar c1, Calendar c2) {
    return (c1.get(Calendar.DATE) == c2.get(Calendar.DATE) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1
        .get(Calendar.YEAR) == c2.get(Calendar.YEAR));
  }


  public static String getPicFileName(EventDistribution ed) {
  
    boolean hasAnAllDayEvent = false;
    int noAm = 0;
    int noPm = 0;

    Set<String> colourAmSet = new HashSet<String>();
    Set<String> colourPmSet = new HashSet<String>();
    List<EventDistributionItem> eventCols = ed.getEvents();
    
    for (EventDistributionItem edi : eventCols) {

      int no = 0;
      String[] arr = edi.getArray();
      for (int i = 0; i < arr.length; i++) {
        if (arr[i] != null) {
          if (i <= 11) {
            colourAmSet.add(arr[i]);
            noAm++;
          } else {
            colourPmSet.add(arr[i]);
            noPm++;
          }
          no++;
        }
      }
      if (no == 24) {
        hasAnAllDayEvent = true;
      }
    }
    
    if (hasAnAllDayEvent) {
      if (colourAmSet.size() == 1 && colourPmSet.size() == 1) {
        String amColour = colourAmSet.iterator().next();
        if (amColour.equals(colourPmSet.iterator().next())) {
          return amColour + ".gif";
        }
      }
      return null;
    } 
    
    if ((colourAmSet.size() == 1) && (colourPmSet.size() == 0)) {
      if (noAm < 12 * eventCols.size()) {
        //top half
        return colourAmSet.iterator().next() +"th.gif";      
      } 
      //top full
      return colourAmSet.iterator().next() +"tf.gif";
    } else if ((colourAmSet.size() == 0) && (colourPmSet.size() == 1)) { 
      if (noPm < 12 * eventCols.size()) {
        //bottom half
        return colourPmSet.iterator().next() +"bh.gif";
      } 
      //bottom full
      return colourPmSet.iterator().next() +"bf.gif";
    }
    
    return null;

  }
  
  public static byte[] createImage(EventDistribution ed, int graphicTyp, int width, int height) throws IOException {

System.out.println("createImage");
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    Graphics gr = image.getGraphics();

    gr.setColor(Color.WHITE);
    gr.fillRect(0, 0, width, height);

    List<EventDistributionItem> eventCols = ed.getEvents();

    boolean hasAnAllDayEvent = false;

    if (graphicTyp == 1) {

      int noAm = 0;
      int noPm = 0;

      List<String> colourAmSet = new ArrayList<String>();
      List<String> colourPmSet = new ArrayList<String>();

      for (EventDistributionItem edi : eventCols) {
        Set<String> eventAmSet = new HashSet<String>();
        Set<String> eventPmSet = new HashSet<String>();
        int no = 0;
        String[] arr = edi.getArray();
        for (int i = 0; i < arr.length; i++) {
          if (arr[i] != null) {
            if (i <= 11) {
              eventAmSet.add(arr[i]);
              noAm++;
            } else {
              eventPmSet.add(arr[i]);
              noPm++;
            }
            no++;
          }
        }
        colourAmSet.addAll(eventAmSet);
        colourPmSet.addAll(eventPmSet);
        if (no == 24) {
          hasAnAllDayEvent = true;
        }
      }

      if (colourAmSet.size() == 1) {
        gr.setColor(getColor(colourAmSet.iterator().next()));
      } else {
        gr.setColor(Color.BLACK);
      }
      

      int halfHeight = height / 2;
      if (noAm > 0) {
        if (noAm < 12 * eventCols.size() && !hasAnAllDayEvent) {
          boolean oddrow = false;
          int curX = 0;
          int curY = 0;
          while (curY <= halfHeight) {
            int drawHeight = Math.min(BOX_HEIGHT, halfHeight - curY);
            gr.fillRect(curX, curY, BOX_WIDTH, drawHeight);
            curX = curX + (BOX_WIDTH * 2);
            if (curX > width) {
              curY = curY + (BOX_HEIGHT);
              if (oddrow) {
                curX = 0;
                oddrow = false;
              } else {
                curX = BOX_WIDTH;
                oddrow = true;
              }
            }
          }
        } else {
          gr.fillRect(0, 0, width, halfHeight); 
        }
      }

      if (noPm > 0) {
        if (colourPmSet.size() == 1) {
          gr.setColor(getColor(colourPmSet.iterator().next()));
        } else {          
          gr.setColor(Color.BLACK);
        }

        if (noPm < 12 * eventCols.size() && !hasAnAllDayEvent) {
          boolean oddrow = false;
          int curX = 0;
          int curY = halfHeight;
          while (curY <= height) {
            int drawHeight = Math.min(BOX_HEIGHT, height - curY);
            gr.fillRect(curX, curY, BOX_WIDTH, drawHeight);
            curX = curX + (BOX_WIDTH * 2);
            if (curX > width) {
              curY = curY + (BOX_HEIGHT);
              if (oddrow) {
                curX = 0;
                oddrow = false;
              } else {
                curX = BOX_WIDTH;
                oddrow = true;
              }
            }
          }
        } else {
          gr.fillRect(0, halfHeight, width, height);
        }
      }
      if(colourAmSet.size() > 1 || colourPmSet.size() > 1){
          drawCross(gr,width, height);
      }
    } else {
    	 
      int widthPerEvent = (width - 2) / eventCols.size();
      int heightPerHour = (height - 2) / 24;

      int col = 0;
      
      for (EventDistributionItem edi : eventCols) {

        String[] arr = edi.getArray();
        for (int i = 0; i < arr.length; i++) {
          if (arr[i] != null) {
            gr.setColor(getColor(arr[i]));
            gr.fillRect((col * widthPerEvent) + 1, (i * heightPerHour) + 1, widthPerEvent, heightPerHour);
          }
        }

        col++;
      }
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "GIF", baos);

    return baos.toByteArray();
  }
  
  private static Color getColor(String cstr) {
    int r = Integer.parseInt(cstr.substring(0, 2), 16);
    int g = Integer.parseInt(cstr.substring(2, 4), 16);
    int b = Integer.parseInt(cstr.substring(4), 16);
    return new Color(r, g, b);
  }
  
  public static void drawCross(Graphics gr, int width, int height){
      gr.setColor(Color.RED);
      gr.drawLine(0, 5, width, height -5);
      gr.drawLine(1, 4, width -1, height -4);
      gr.drawLine(2, 3, width -2, height -3);
      gr.drawLine(3, 2, width -3, height -2);
      gr.drawLine(4, 1, width -4, height -1);
      gr.drawLine(5, 0, width -5, height);   
      gr.drawLine(0,height -5,width,5);
      gr.drawLine(1,height -4,width -1,4);
      gr.drawLine(2,height -3,width -2,3);
      gr.drawLine(3,height -2,width -3,2);
      gr.drawLine(4,height -1,width -4,1);
      gr.drawLine(5,height,width -5,0);
  }
  
  public static String createUid(BaseEvent event) {
    String ia = "localhost";
    
    try {
      InetAddress inetAddress = InetAddress.getLocalHost();
      ia = inetAddress.toString();
    } catch (UnknownHostException e) {
      //no action
    }
    
    Random random = new Random();
    String uid = ia + random.nextDouble() + System.currentTimeMillis(); 

    if (event != null) {
      uid += event.getId() + event.getSubject() + event.getLocation();
      uid += event.getModificationDate();
      uid += event.getCreateDate();
    }
    
    byte[] result = null;
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      result =  sha.digest( uid.getBytes() );
    } catch (NoSuchAlgorithmException e) {
      result = uid.getBytes();
    }
    
    return hexEncode(result);  
  }
  
  private static String hexEncode( byte[] aInput){
    StringBuilder result = new StringBuilder();
    char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','A','B','C','D','E','F'};
    for (byte b : aInput) {
      result.append( digits[ (b&0xf0) >> 4 ] );
      result.append( digits[ b&0x0f] );
    }
    return result.toString();
  }
  
  
  public static boolean isValidTime(String hstr, String mstr) {
    try {
      int h = 0;
      if (StringUtils.isNotBlank(hstr)) {
        h = Integer.parseInt(hstr);
      }

      int m = 0;
      if (StringUtils.isNotBlank(mstr)) {
        m = Integer.parseInt(mstr);
      }

      if ((h < 0) || (h > 23)) {
        return false;
      }
      if ((m < 0) || (m > 59)) {
        return false;
      }
    } catch (NumberFormatException nfe) {
      return false;
    }

    return true;
  }  
  
  
  public static BigDecimal hourRateTimeFactor(BigDecimal orig, Config userConfig) {
    BigDecimal timeFactor = new BigDecimal(userConfig.getStringProperty(UserConfig.TIME_FACTOR, "1"));
    if (timeFactor.compareTo(new BigDecimal("1")) != 0) {
      BigDecimal total = orig.multiply(timeFactor);
      total = total.setScale(0, BigDecimal.ROUND_HALF_UP);
      return total;
    }
    
    return orig;
    
  }
  
  public static int countWeeks(Calendar day) {
    Set<Integer> weekSet = new HashSet<Integer>();
    Calendar ixCal = new GregorianCalendar(day.get(Calendar.YEAR), day.get(Calendar.MONTH), 1);
    int last = ixCal.getActualMaximum(Calendar.DATE);
    for (int i = 0; i < last; i++) {
      weekSet.add(ixCal.get(Calendar.WEEK_OF_YEAR));
      ixCal.add(Calendar.DATE, 1);
    }
    return weekSet.size();
  }
}
