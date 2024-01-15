package cmp.holidays;

// cmp.holidays.AprilFoolsDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class AprilFoolsDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1564;
   }
   public String getName() {
      return "April Fools Day";
   }
   public String getRule() {
      return "Always April 1.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 4, 1), shift);
   } // end when.
}