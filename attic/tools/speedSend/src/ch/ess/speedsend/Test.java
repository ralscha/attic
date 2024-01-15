package ch.ess.speedsend;

import java.io.File;

public class Test {
  public static void main(String[] args) {
    File f = new File("D:\\eclipse\\workspace\\pbroker\\build\\dist\\pbroker.war");
    for (int i = 0; i < 2000; i++) {
          
      System.out.println((int)((100l * (i * 16384)) / f.length()));
    }
  }
}
