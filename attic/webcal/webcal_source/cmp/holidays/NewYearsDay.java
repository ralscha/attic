package cmp.holidays;

// cmp.holidays.NewYearsDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class NewYearsDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return -153;
   }
   public String getName() {
      return "New Years Day";
   }
   public String getRule() {
      return "Always on January 1";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 1, 1), shift);

   } // end when.
}