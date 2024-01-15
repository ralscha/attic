package cmp.holidays;

// cmp.holidays.RobbieBurnsDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class RobbieBurnsDay extends HolInfo {

   public String getAuthority() {
      return "";
   }

   public int getFirstYear(int base) {
      return 1759;
   }

   public String getName() {
      return "Robbie Burns Day";
   }

   public String getRule() {
      return "Always January 25. Burns lived 1759/01/25 to 1796/07/21.";
   }

   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 1, 25), shift);
   } // end when.
}