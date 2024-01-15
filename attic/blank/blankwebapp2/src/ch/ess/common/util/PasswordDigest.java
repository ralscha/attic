package ch.ess.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:17 $
 */
public class PasswordDigest {

  public static synchronized String createLoginToken(String[] data) {
    if (data == null) {
      return null;
    }

    StringBuffer sb = new StringBuffer(100);
    for (int i = 0; i < data.length; i++) {
      sb.append(data[i]);
    }

    return DigestUtils.shaHex(sb.toString());

  }

  public static boolean validatePassword(String hashPassword, String clearTextPassword) {

    if (clearTextPassword == null) {
      return false;
    }

    String digest = DigestUtils.shaHex(clearTextPassword);
    return digest.equals(hashPassword);
  }

}