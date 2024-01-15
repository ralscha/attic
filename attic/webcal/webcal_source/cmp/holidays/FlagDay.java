package cmp.holidays;

// cmp.holidays.FlagDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class FlagDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1877;
   }
   public String getName() {
      return "Flag Day";
   }
   public String getRule() {
      return "June 14";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 6, 14), shift);
   } // end when.
}