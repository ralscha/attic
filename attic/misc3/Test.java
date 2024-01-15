
import java.math.*;
//import com.ibm.math.*;
import java.util.*;
import java.text.*;

public class Test {

  public static void main(String[] args) {

    BigDecimal mwst = new BigDecimal("1.0000000");
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00####");

    System.out.println(decimalFormat.format(mwst));

    mwst = new BigDecimal("1");
    System.out.println(decimalFormat.format(mwst));

    mwst = new BigDecimal("1.");
    System.out.println(decimalFormat.format(mwst));
    mwst = new BigDecimal("1.0");
    System.out.println(decimalFormat.format(mwst));
    mwst = new BigDecimal("1.1");
    System.out.println(decimalFormat.format(mwst));
    mwst = new BigDecimal("1.12");
    System.out.println(decimalFormat.format(mwst));
    mwst = new BigDecimal("1.123");
    System.out.println(decimalFormat.format(mwst));
    mwst = new BigDecimal("1.1234");
    System.out.println(decimalFormat.format(mwst));
    mwst = new BigDecimal("1.12345");
    System.out.println(decimalFormat.format(mwst));
    mwst = new BigDecimal("1.123456");
    System.out.println(decimalFormat.format(mwst));
    mwst = new BigDecimal("1.1234567");
    System.out.println(decimalFormat.format(mwst));

/*
    int vor = 0;
    int nach = 0;
    

    for (nach = 0; nach < 100; nach++) {
      String vorStr = String.valueOf(vor);
      String nachStr = "";
      if (nach < 10)
        nachStr = "0";
      nachStr += String.valueOf(nach);

      BigDecimal bd = new BigDecimal("1." + nachStr);

      BigDecimal mwst = bd.multiply(new BigDecimal(20));
      mwst = mwst.setScale(0, BigDecimal.ROUND_HALF_UP);
      mwst = mwst.setScale(2);
      mwst = mwst.divide(new BigDecimal(20), BigDecimal.ROUND_HALF_UP);
    
      System.out.print(bd);
      System.out.print("---->");
      System.out.println(mwst);
    }
    */
    /*

    for (vor = 0; vor <= 10000; vor++) {
      for (nach = 0; nach < 100; nach++) {
        String vorStr = String.valueOf(vor);
        String nachStr = "";
        if (nach < 10)
          nachStr = "0";
        nachStr += String.valueOf(nach);

        BigDecimal bd = new BigDecimal(vorStr + "." + nachStr);
        

        BigDecimal d = bd.divide(mwst, 3, BigDecimal.ROUND_HALF_UP);
        BigDecimal m = d.multiply(mwst);
        BigDecimal result = m.setScale(2, BigDecimal.ROUND_HALF_UP);

        if (!bd.equals(result)) {
          System.out.print(bd);
          System.out.print("!=");
          System.out.print(result);
          System.out.println();
        }


      
      }
    
    }
    */
/*

    BigDecimal d1 = bd.divide(mwst, 2, BigDecimal.ROUND_HALF_EVEN);

    System.out.println(d1);
    System.out.println(d2);

    BigDecimal d3 = d1.multiply(mwst);
    BigDecimal d4 = d2.multiply(mwst);
    

    System.out.println(d3.setScale(2, BigDecimal.ROUND_HALF_UP));
    System.out.println(d4.setScale(2, BigDecimal.ROUND_HALF_UP));
*/
  }
  
}