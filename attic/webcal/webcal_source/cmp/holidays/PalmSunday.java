package cmp.holidays;

// cmp.holidays.PalmSunday
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class PalmSunday extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1583 /* our calculations only work this far back */;
   }
   public String getName() {
      return "Palm Sunday";
   }
   public String getRule() {
      return "One week prior to Easter.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(new EasterSunday().when(year, false) - 7, shift);
   } // end when.
}