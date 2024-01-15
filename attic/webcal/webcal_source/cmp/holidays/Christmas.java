package cmp.holidays;

// cmp.holidays.Christmas
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class Christmas extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1;
   }
   public String getName() {
      return "Christmas";
   }
   public String getRule() {
      return "Always on Dec 25.";
   }
   /**
        * debugging test driver
        */
   public static void main(String[] args) {
      HolInfo h = new Christmas();
      System.out.println(h.getName());
      System.out.println(h.getFirstYear(CELEBRATED));
      System.out.println(h.getFirstYear(PROCLAIMED));
      System.out.println(h.getRule());
      System.out.println(h.getAuthority());
      BigDate d = new BigDate(h.when(1999, ACTUAL, CELEBRATED));
      System.out.println(d.getYYYY() +"/"+d.getMM() + "/" + d.getDD());
      d.setOrdinal(h.when(1999, SHIFTED, CELEBRATED));
      System.out.println(d.getYYYY() +"/"+d.getMM() + "/" + d.getDD());
   } // end main
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 12, 25), shift);

   } // end when.
}