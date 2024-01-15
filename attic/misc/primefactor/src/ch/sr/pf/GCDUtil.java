
package ch.sr.pf;
public class GCDUtil {

	public static long GCD(long n1, long n2) {
		if (n2 == 0) {
			return n1;
		} else {
			return GCD(n2, n1 % n2);
		}
	}

	public static String GCD(String n1str, String n2str) {

    try {
      long n1 = Long.parseLong(n1str);
      long n2 = Long.parseLong(n2str);
      return String.valueOf(GCD(n1,n2));
    } catch (NumberFormatException nfe) {
      return "";
    }

	}


  public static void main(String[] args) {
    System.out.println(GCD(10,15));
  }
}