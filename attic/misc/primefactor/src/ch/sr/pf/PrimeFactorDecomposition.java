
package ch.sr.pf;
import java.util.*;

public class PrimeFactorDecomposition {

  private static Primes primes = new Primes(0, 100);

  public static Map getPfd(String n) {
    try {
      return getPfd(Long.parseLong(n));
    } catch (NumberFormatException nfe) {
      return null;
    }
  }

	public static Map getPfd(long n) {
		Map resultMap = new TreeMap();
    
    while (n % 2 == 0) {
      n = n / 2;
      put(resultMap, 2);
    }
    
    long nlimit = 0;
    if (n < 9)
      nlimit = n;
    else
      nlimit = (long)Math.sqrt(n) + 1l;

    long p = 3;
    while (1 < nlimit) {
      while(n % p == 0) {
        n = n / p;
        nlimit = n;
        put(resultMap, p);
      }
      p += 2;
    }

    return resultMap;
	}

  public static String toString(Map map) {
    if ((map == null) || (map.isEmpty()))
      return "";

    StringBuffer sb = new StringBuffer();
    
    Iterator it = map.keySet().iterator();
    while(it.hasNext()) {
      Long lvalue = (Long)it.next();
      Counter count = (Counter)map.get(lvalue);
      
      if (sb.length() > 0)
        sb.append(" x ");

      sb.append(lvalue);
      if (count.getCount() > 1) {
        sb.append("<sup>").append(count.getCount()).append("</sup>");  
      }      
    }

    return sb.toString();
  }

  public static String getPfdString(String n) {
    try {

      long nl = Long.parseLong(n);

      if (n.length() > 5) {
        if (primes.isPrime(nl)) {
          return n;
        }
      }

      Map map = getPfd(nl);
      return toString(map);
    } catch (NumberFormatException nfe) {
      return "";
    }
  }

  public static String getKgV(Map mone, Map mtwo) {

    if ((mone != null) && (mtwo != null)) {
      
      long result = 1;
      Map monec = new TreeMap(mone);

      Iterator it = monec.keySet().iterator(); 
      while (it.hasNext()) {
        Long lvalue = (Long)it.next();
        Counter countone = (Counter)mone.get(lvalue);
        Counter counttwo = (Counter)mtwo.get(lvalue);

        if (counttwo != null) {
          if (countone.getCount() > counttwo.getCount()) {
            for (int i = 0; i < countone.getCount(); i++)
              result = result * lvalue.longValue();      

            mtwo.remove(lvalue);    
          } else {
            mone.remove(lvalue);          
          }
               
        } else {
          for (int i = 0; i < countone.getCount(); i++)
            result = result * lvalue.longValue();
        }
      }

      it = mtwo.keySet().iterator();
      while (it.hasNext()) {
        Long lvalue = (Long)it.next();
        Counter counttwo = (Counter)mtwo.get(lvalue);
        for (int i = 0; i < counttwo.getCount(); i++)
          result = result * lvalue.longValue();
      }

      return String.valueOf(result);

    } else {
      return "";
    }
  }

  public static String getKgVPrimFactors(Map mone, Map mtwo) {
    if ((mone != null) && (mtwo != null)) {
      Map m = new TreeMap();
      m.putAll(mone);
      m.putAll(mtwo);
      return toString(m);
    } else {
      return "";
    }
  }

  public static void put(Map map, long l) {
    Long llong = new Long(l);
    Counter count = (Counter)map.get(llong);
    if (count != null) {
      count.inc();
    } else {
      map.put(llong, new Counter());
    }  
  }

  public static void main(String[] args) {

    for (int i = 10000; i < 1000000; i++) {
      Map resultMap = getPfd(i);

      long result = 1;

      Iterator it = resultMap.keySet().iterator();
      while(it.hasNext()) {
        Long value = (Long)it.next();
        Counter counter = (Counter)resultMap.get(value);
        result *= Math.pow(value.longValue(), counter.getCount());
      }
    
      if (result != i) {
        System.out.println("i=" + i + "; result="+result);
      } 

    }

/*

    Map m1 = getPfd(3948l);
    Map m2 = getPfd(888l);
    System.out.println(toString(m1));
    System.out.println(toString(m2));
    System.out.println(getKgV(m1, m2));
    System.out.println(getKgVPrimFactors(m1, m2));
*/
  }
}