
import java.util.*;

public class String1 {

  public static byte[] asciiGetBytes(String buf) {
    int size = buf.length();
    int i;
    byte[] bytebuf = new byte[size];
    for (i = 0; i < size; i++) {
      bytebuf[i] = (byte)buf.charAt(i);
    }
    return bytebuf;
  }

  public static int i = 0;

  public static void to(Object o) {
    i++;
  }

  public static void ti(Object o) {
    if (o instanceof Date) {
      i++;
    } 
  }

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

    String1 s = new String1();

		for (int i = 0; i < 1000000; i++) {
      to(s);
   	}


		System.out.println(System.currentTimeMillis() - start);

		start = System.currentTimeMillis();

		for (int i = 0; i < 1000000; i++) {
      ti(s);
   	}

		System.out.println(System.currentTimeMillis() - start);

	}
}