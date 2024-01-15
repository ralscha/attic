


public class Test {

	public static void main(String[] args) {
	    
    if (args.length == 1) {
      long n = Long.parseLong(args[0]);

      long start = System.currentTimeMillis();

	    while (n % 2 == 0) {
	      n = n / 2;
	      System.out.print(2);
	    }
	    
	    long p = 3;
	    while (1 < n) {
	      while(n % p == 0) {
	        n = n / p;
          System.out.print(" ");
	        System.out.print(p);
	      }
	      p += 2;
	    }

      long stop = System.currentTimeMillis()-start;
      System.out.println();
	    System.out.print(stop);
      
      System.out.println(" ms");
    } else {
      System.out.println("java Test <zahl>");
    }

	}
}