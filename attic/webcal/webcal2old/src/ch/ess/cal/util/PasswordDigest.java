package ch.ess.cal.util;

import java.security.*;
import java.util.*;

import org.apache.commons.logging.*;

import ch.ess.cal.db.*;

public class PasswordDigest {
  
  private static final Log logger = LogFactory.getLog(PasswordDigest.class);  
  private static MessageDigest algorithm = null;
  private static MessageDigest algorithmLT = null;

  static {
    try {
      algorithm = MessageDigest.getInstance("MD5");
      algorithmLT = MessageDigest.getInstance("SHA");
    } catch (NoSuchAlgorithmException nsae) {
      logger.error("MessageDigest.getInstance", nsae);
    }
  }

  public static boolean validatePassword(String hashPassword, String clearTextPassword) {

    if (clearTextPassword == null) {
      return false;
    }
    
    String digest = getDigestString(clearTextPassword);
    return digest.equals(hashPassword);
  }

  public synchronized static String getDigestString(String str) {
    algorithm.reset();
    algorithm.update(str.getBytes());

    return toHex(algorithm.digest());
  }
  
  public synchronized static String getLogonToken(User user) {
    algorithmLT.reset(); 
    
    algorithmLT.update(user.getName().getBytes());
    algorithmLT.update(user.getUserName().getBytes());
    algorithmLT.update(new Date().toString().getBytes());
    algorithmLT.update(user.getUserId().toString().getBytes());
    
    return toHex(algorithmLT.digest());
  }
 

  private static String toHex(byte[] bytes) {

    StringBuffer sb = new StringBuffer();
    char c;

    for (int i = 0; i < bytes.length; i++) {

      // First nibble
      c = (char) ((bytes[i] >> 4) & 0xf);

      if (c > 9) {
        c = (char) ((c - 10) + 'A');
      } else {
        c = (char) (c + '0');
      }

      sb.append(c);

      // Second nibble
      c = (char) (bytes[i] & 0xf);

      if (c > 9) {
        c = (char) ((c - 10) + 'A');
      } else {
        c = (char) (c + '0');
      }

      sb.append(c);
    }

    return sb.toString();
  }
  
}
