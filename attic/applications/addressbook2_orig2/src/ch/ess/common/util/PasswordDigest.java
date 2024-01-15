package ch.ess.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.5 $ $Date: 2003/11/11 19:14:33 $ 
  */
public class PasswordDigest {

  private static final Log LOG = LogFactory.getLog(PasswordDigest.class);
  private static MessageDigest algorithm = null;

  static {
    try {
      algorithm = MessageDigest.getInstance("SHA");
    } catch (NoSuchAlgorithmException nsae) {
      LOG.error("MessageDigest.getInstance", nsae);
    }
  }

  public static synchronized String createLoginToken(String[] data) {
    if (data == null) {
      return null;
    }
    
    algorithm.reset();
    
    for (int i = 0; i < data.length; i++) {
      algorithm.update(data[i].getBytes());
    }
    
    return toHex(algorithm.digest());
  }

  public static boolean validatePassword(String hashPassword, String clearTextPassword) {

    if (clearTextPassword == null) {
      return false;
    }

    String digest = getDigestString(clearTextPassword);
    return digest.equals(hashPassword);
  }

  public static synchronized String getDigestString(String str) {
    algorithm.reset();
    algorithm.update(str.getBytes());

    return toHex(algorithm.digest());
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
