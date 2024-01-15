

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;

public class UserStatReport {

	public final static SimpleDateFormat dateFormat = new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss", Locale.GERMAN);

  public final static int STAT_HOUR = 0;
  public final static int STAT_WEEKDAY = 1;
  public final static int STAT_DAY = 2;
  public final static int STAT_MONTH = 3;
  public final static int STAT_TOTAL = 4;
  public final static int STAT_DURATION = 5;

  private Calendar reportFrom;
  private Calendar reportTo;

  private int[][] hour = new int[5][24];
  private int[][] weekday = new int[5][7];
  private int[][] day = new int[5][31];
  private int[][] month = new int[5][12];

  private int[][] duration = new int[5][121];

  private int[] total = new int[4];


  private Component parent;

  public UserStatReport(Component frame) throws InterruptedIOException {
    this.parent = frame;
    init();
  }

  public Integer[][] getTotal() {
    Integer[][] result = new Integer[total.length][1];
    for (int i = 0; i < total.length; i++) {
      result[i][0] = new Integer(total[i]);
    }
    return result;
  }

  public Integer[][] getData(int stat_typ, int typ) {
    int[] arr = null;

    switch (stat_typ) {
      case STAT_HOUR : 
        arr = hour[typ];
        break;

      case STAT_WEEKDAY : 
        arr = weekday[typ];
        break;

      case STAT_DAY : 
        arr = day[typ];
        break;

      case STAT_MONTH : 
        arr = month[typ];
        break;

      case STAT_DURATION :
        arr = duration[typ];
        break;

    }

    Integer[][] result = new Integer[1][arr.length];

    for (int i = 0; i < arr.length; i++) {
      result[0][i] = new Integer(arr[i]);
    }

    return result;
  }


  private void init() throws InterruptedIOException {

    reportFrom = new GregorianCalendar();
    reportTo   = new GregorianCalendar(1970, Calendar.JANUARY, 1);

    try {

      InputStream resourceStream = null;
      if (System.getProperty("test") != null) {
        resourceStream = new FileInputStream("d:/javaprojects/ess/pbroker/userstat/user_prod_stat.log");
      } else {
        URL resourceURL = new URL("http://www.pbroker.ch/stats/user_prod_stat.log");
        resourceStream = resourceURL.openStream();
      }


     InputStream in = new ProgressMonitorInputStream(parent,"reading stat file...", resourceStream);
      

      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      Calendar cal = new GregorianCalendar();
      String line;
      while((line = br.readLine()) != null) {
        StatItem si = StatItem.create(line);
        if (si != null) {
          cal.setTime(si.getInDate());

          if (cal.before(reportFrom)) {
            reportFrom = (Calendar)cal.clone();
          }

          if (cal.after(reportTo)) {
            reportTo = (Calendar)cal.clone();
          }

          int h = cal.get(Calendar.HOUR_OF_DAY);
          int w = cal.get(Calendar.DAY_OF_WEEK);
          if (w == Calendar.SUNDAY)
            w = 6;
          else
            w = w - 2;

          int m = cal.get(Calendar.MONTH);
          int d = cal.get(Calendar.DATE)-1;

          hour[0][h]++;
          weekday[0][w]++;
          month[0][m]++;
          day[0][d]++;

          int dur = si.getDuration();
          dur = dur / 60;
          
          if (dur < 120) {
            duration[0][dur]++;
            duration[si.getTyp()][dur]++;
          } else {
            duration[0][120]++;
            duration[si.getTyp()][120]++;
          }

          hour[si.getTyp()][h]++;
          weekday[si.getTyp()][w]++;
          month[si.getTyp()][m]++;
          day[si.getTyp()][d]++;

          total[si.getTyp()-1]++;
          
        }
      }
    } catch (InterruptedIOException iioe) {
      throw (iioe);    
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  
  }

  public int getMax(int stat_typ) {  

    int[] arr = null;

    switch (stat_typ) {
      case STAT_HOUR : arr = hour[0]; break;
      case STAT_WEEKDAY : arr = weekday[0]; break;
      case STAT_DAY : arr = day[0]; break;
      case STAT_MONTH : arr = month[0]; break;
      case STAT_TOTAL : arr = total; break;
      case STAT_DURATION : arr = duration[0]; break;
    }

    int max = 0;

    if (arr != null) {
      for (int i = 0; i < arr.length; i++) {
        max = Math.max(max, arr[i]);  
      }
    }

    int t = 10;
    if (max >= 100)
      t = 100;

    max = (int)((max * 1.1 / t) + 1) * t;
    return max;
  }


  public Calendar getReportFrom() {
    return reportFrom;
  }

  public Calendar getReportTo() {
    return reportTo;
  }
  

}
