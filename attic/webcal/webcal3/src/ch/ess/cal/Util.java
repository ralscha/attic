package ch.ess.cal;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.web.UserPrincipal;

import com.cc.framework.security.SecurityUtil;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:33 $
 */
public class Util {

  private Util() {
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

  public static boolean isEqual(final String one, final String two) {
    if (one == null) {
      if (two == null) {
        return true;
      }
      return false;
    }

    return one.equals(two);
  }

  public static Date clone(final Date date) {
    if (date != null) {
      return (Date)date.clone();
    }
    return null;
  }

  public static User getUser(final HttpSession session, final UserDao userDao) {
    UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(session);
    User user = userDao.get(userPrincipal.getUserId());
    return user;
  }

  public static Locale getLocale(final String str) {

    if (StringUtils.isNotBlank(str)) {
      StringTokenizer tokens = new StringTokenizer(str, "_");
      String language = tokens.hasMoreTokens() ? tokens.nextToken() : StringUtils.EMPTY;
      String country = tokens.hasMoreTokens() ? tokens.nextToken() : StringUtils.EMPTY;
      String variant = tokens.hasMoreTokens() ? tokens.nextToken() : StringUtils.EMPTY;
      return new Locale(language, country, variant);
    }
    return Locale.getDefault();

  }

  //disable cache
  public static void setExportHeader(HttpServletResponse response, String mimeType, String filename)
      throws ServletException {

    // response can't be already committed at this time
    if (response.isCommitted()) {
      throw new ServletException("response can't be already committed at this time");
    }

    // if cache is disabled using http header, export will not work.
    // Try to remove bad headers overwriting them, since there is no way to remove a single header and reset()
    // could remove other "useful" headers like content encoding
    if (response.containsHeader("Cache-Control")) {
      response.setHeader("Cache-Control", "public");
    }
    if (response.containsHeader("Expires")) {
      //response.setHeader("Expires", "Thu, 01 Dec 2069 16:00:00 GMT");
      response.setHeader("Expires", "");
    }
    if (response.containsHeader("Pragma")) {
      // Pragma: no-cache
      // http 1.0 equivalent of Cache-Control: no-cache
      // there is no "Cache-Control: public" equivalent, so just try to set it to an empty String (note
      // this is NOT a valid header)
      response.setHeader("Pragma", "");
    }

    response.setContentType(mimeType);

    if (StringUtils.isNotEmpty(filename)) {
      response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
    }

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

  public static String javascriptEscape(String str) {

    if (str == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder(str.length());

    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '\'') {
        sb.append("\\'");
      } else if (str.charAt(i) == '"') {
        sb.append("\\'");
      } else {
        sb.append(str.charAt(i));
      }

    }

    return sb.toString();
  }

  private final static Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
  private final static Graphics2D g2d = (Graphics2D)image.getGraphics();

  public static int getStrLenPcx(String string, String fontname, int fontsize) {
    Font font = new Font(fontname, Font.PLAIN, fontsize);
    FontMetrics fontMetrics = g2d.getFontMetrics(font);
    return fontMetrics.stringWidth(string);
  }
}
