package ch.ess.base;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.model.User;
import ch.ess.base.persistence.UserDao;
import ch.ess.base.web.UserPrincipal;

public class Util {

  private Util() {
    //it's a singleton
  }
  
  public static User getUser(final HttpSession session, final UserDao userDao) {
    UserPrincipal userPrincipal = (UserPrincipal)session.getAttribute(Constants.USER_SESSION);
    if (userPrincipal != null) {
      User user = userDao.get(userPrincipal.getUserId());
      return user;
    }
    
    return null;
  }
  
  public static Date clone(final Date date) {
    if (date != null) {
      return (Date)date.clone();
    }
    return null;
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
