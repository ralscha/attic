package com.taglib.wdjsp.faqtool;

import javax.servlet.http.*;
import java.security.*;
  

public class CommandToken {
  public static void set(HttpServletRequest req) {
    HttpSession session = req.getSession(true);
    long systime = System.currentTimeMillis();
    byte[] time  = new Long(systime).toString().getBytes();
    byte[] id = session.getId().getBytes();
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(id);
      md5.update(time);
      String token = toHex(md5.digest());
      req.setAttribute("token", token);
      session.setAttribute("token", token);
    }
    catch (Exception e) {
      System.err.println("Unable to calculate MD5 Digests");
    }
  }

  public static boolean isValid(HttpServletRequest req) {
    HttpSession session = req.getSession(true);
    String requestToken = req.getParameter("token");
    String sessionToken = (String)session.getAttribute("token");
    if (requestToken == null || sessionToken == null)
      return false;
    else
      return requestToken.equals(sessionToken);
  }

  private static String toHex(byte[] digest) {
    StringBuffer buf = new StringBuffer();
    for (int i=0; i < digest.length; i++)
      buf.append(Integer.toHexString((int)digest[i] & 0x00ff));
    return buf.toString();
  }
}
