package ch2;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author sr
 */
public class Main {

  public static void main(String[] args) {
    double d1 = 3.2;
    double d2 = 1.06;
    double d3 = 0.94;
    
    System.out.println(d1 + d2 + d3);
    
    
    BigDecimal bd1 = new BigDecimal("3.2");
    BigDecimal bd2 = new BigDecimal("1.06");
    BigDecimal bd3 = new BigDecimal("0.94");
    
    BigDecimal total = bd1.add(bd2).add(bd3);
    System.out.println(total);
    
    DecimalFormat df = new DecimalFormat("000000000000000.000000000");
    System.out.println(df.format(total));
    
  }
}
