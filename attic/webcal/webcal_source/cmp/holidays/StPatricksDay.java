package cmp.holidays;

// cmp.holidays.StPatricksDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class StPatricksDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 400;
   }
   public String getName() {
      return "St. Patrick's day";
   }
   public String getRule() {
      return "Always March 17.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 3, 17), shift);
   } // end when.
}