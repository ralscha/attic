package ch.ess.util;

import java.io.*;

public class IOUtil {
  
  private final static int BUFFER_SIZE = 8192;
  
  public static void copy(InputStream is, OutputStream out) throws IOException {
    int bytesRead = 0;
    byte[] buffer = new byte[BUFFER_SIZE];
    
    while ((bytesRead = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
      out.write(buffer, 0, bytesRead);
    }
  }


}
