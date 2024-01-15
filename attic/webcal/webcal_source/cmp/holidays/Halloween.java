package cmp.holidays;

// cmp.holidays.Halloween
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class Halloween extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return -2000;
   }
   public String getName() {
      return "Halloween";
   }
   public String getRule() {
      return "October 31";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 10, 31), shift);

   } // end when.
}