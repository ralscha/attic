

package ch.sr.pf;
import java.util.*;

/// An enumerator yielding prime numbers.
// <P>
// Enumerates all the prime numbers in a given range.
// <P>
// Sample code:
// <BLOCKQUOTE><PRE>
// Primes primes = new Acme.Primes( 1000, 2000 );
// while ( primes.hasMoreElements() )
//     {
//     // Get the next element as an Object:
//     long prime = ((Long) primes.nextElement()).longValue();
//     // Or alternatively, as a long:
//     long prime = primes.nextElementL();
//     // Then do whatever you like with prime.
//     }
// </PRE></BLOCKQUOTE>
// <P>
// <A HREF="/resources/classes/Acme/Primes.java">Fetch the software.</A><BR>
// <A HREF="/resources/classes/Acme.tar.Z">Fetch the entire Acme package.</A>

public class Primes implements Enumeration {

  private long from;
  private long to;
  private long n;
  private long nextEle;

  public Primes(long from, long to) {
    this.from = from;
    this.to = to;
    n = from;
  }

  private boolean gotOne = false;

  private void getOne() {
    if (n == 2L) {
      nextEle = 2L;
      n = 3L;
      gotOne = true;
      return;
    }
    if (n == 3L) {
      nextEle = 3L;
      n = 5L;
      gotOne = true;
      return;
    }
    n = sixPM1(n);
    long nextInc = sixPM1Inc(n);
    while (n <= to) {
      boolean is = isPrime(n);
      if (is) {
        nextEle = n;
        n += nextInc;
        nextInc = 6L - nextInc;
        gotOne = true;
        return;
      }
      n += nextInc;
      nextInc = 6L - nextInc;
    }
    gotOne = false;
    return;
  }

  public boolean hasMoreElements() {
    if (! gotOne)
      getOne();
    return gotOne;
  }

  public Object nextElement() {
    return new Long(nextElementL());
  }

  public long nextElementL() {
    if (! gotOne)
      getOne();
    gotOne = false;
    return nextEle;
  }

  private final long sixPM1(long n) {
    long m = n % 6L;
    switch ((int) m) {
      case 0:
        return n + 1L;
      case 1:
        return n;
      case 2:
        return n + 3L;
      case 3:
        return n + 2L;
      case 4:
        return n + 1L;
      case 5:
        return n;
    }
    return n;
  }
  private final long sixPM1Inc(long n) {
    long m = n % 6L;
    switch ((int) m) {
      case 1:
        return 4L;
      case 5:
        return 2L;
    }
    return n;
  }

  private static final int tableSize = 10000;
  private static byte[] table = new byte[tableSize];
  private static final byte DUNNO = 0;
  private static final byte YES = 1;
  private static final byte NO = 2;

  static {
    // Initialize the static table of small primes.
    for (int i = 0; i < tableSize; ++i)
      table[i] = DUNNO;
    table[0] = NO;
    table[1] = NO;
    table[2] = YES;
    table[3] = YES;
    table[4] = NO;
    table[5] = YES;
    table[6] = NO;
    table[7] = YES;
    table[8] = NO;
    table[9] = NO;
    table[10] = NO;
    table[11] = YES;
    table[12] = NO;
    table[13] = YES;
    table[14] = NO;
    table[15] = NO;
    table[16] = NO;
    table[17] = YES;
    table[18] = NO;
    table[19] = YES;
    table[20] = NO;
    table[21] = NO;
    table[22] = NO;
    table[23] = YES;
    table[24] = NO;
    table[25] = NO;
    table[26] = NO;
    table[27] = NO;
    table[28] = NO;
    table[29] = YES;
    table[30] = NO;
    table[31] = YES;
  }

  public synchronized boolean isPrime(long n) {
    // Ignore negatives.
    if (n < 0L)
      return false;

    // Use the saved table, if possible.
    if (n < tableSize) {
      if (table[(int) n] != DUNNO)
        return (table[(int) n] == YES);
      boolean is = isPrime2(n);
      if (is)
        table[(int) n] = YES;
      else
        table[(int) n] = NO;
      return is;
    }

    // Just call the internal routine.
    return isPrime2(n);
  }

  private final boolean isPrime2(long n) {
    // Do trial-division by all primes up to the square root.
    // Get the list of divisors first from the table.
    long prime, cofactor;
    for (prime = 2; prime < tableSize && table[(int) prime] != DUNNO; ++prime) {
      if (table[(int) prime] == YES) {
        cofactor = n / prime;
        if (cofactor < prime)
          return true;
        if (cofactor * prime == n)
          return false;
      }
    }
    // Ran out of table entries.  Generate new possible primes.
    prime = sixPM1(prime);
    long nextInc = sixPM1Inc(prime);
    for (;;) {
      cofactor = n / prime;
      if (cofactor < prime)
        return true;
      if (cofactor * prime == n)
        return false;
      prime += nextInc;
      nextInc = 6L - nextInc;
    }
  }

}
