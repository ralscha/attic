package cmp.holidays;

// cmp.holidays.GroundhogDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class GroundhogDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1841;
   }
   public String getName() {
      return "Groundhog Day";
   }
   public String getRule() {
      return "Always on February 2.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 2, 2), shift);

   } // end when.
}