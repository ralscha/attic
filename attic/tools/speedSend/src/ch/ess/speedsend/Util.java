package ch.ess.speedsend;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

  public static String getHashcode(String fileName) throws NoSuchAlgorithmException, IOException {
    MessageDigest md = MessageDigest.getInstance("SHA");

    FileInputStream fis = new FileInputStream(fileName);

    byte[] buffer = new byte[16384];
    int n = 0;
    while (-1 != (n = fis.read(buffer))) {
        md.update(buffer, 0, n);
    }
    
    fis.close();
    
    return Util.hexEncode(md.digest());  
  }
  
  public static String hexEncode(byte[] aInput) {
    StringBuilder result = new StringBuilder();
    char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    for (int idx = 0; idx < aInput.length; ++idx) {
      byte b = aInput[idx];
      result.append(digits[(b & 0xf0) >> 4]);
      result.append(digits[b & 0x0f]);
    }
    return result.toString();
  }
}
