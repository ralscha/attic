package cmp.holidays;

// cmp.holidays.NewYearsEve
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class NewYearsEve extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return -154;
   }
   public String getName() {
      return "New Years Eve";
   }
   public String getRule() {
      return "Always December 31";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 12, 31), shift);
   } // end when.
}