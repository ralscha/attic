package ch.ess.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:30 $ 
  */
public class IOUtil {

  private static final int BUFFER_SIZE = 8192;

  public static void copy(InputStream is, OutputStream out) throws IOException {
    int bytesRead = 0;
    byte[] buffer = new byte[BUFFER_SIZE];

    while ((bytesRead = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
      out.write(buffer, 0, bytesRead);
    }
  }

}
