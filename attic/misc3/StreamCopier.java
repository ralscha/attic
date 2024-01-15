

import java.io.*;


public class StreamCopier {

  public static void main(String[] args) {

    try {
      copy(System.in, System.out);
    }
    catch (IOException e) {
      System.err.println(e);
    }
    
  }

  public static void copy(InputStream in, OutputStream out) 
   throws IOException {

    // do not allow other threads to read from the
    // input or write to the output while copying is
    // taking place
    
    synchronized (in) {
      synchronized (out) {

        byte[] buffer = new byte[256];
        while (true) {
          int bytesRead = in.read(buffer);
          if (bytesRead == -1) break;
          out.write(buffer, 0, bytesRead);
        }
      }
    }
  }

}
