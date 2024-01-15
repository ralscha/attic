package cmp.holidays;

// cmp.holidays.ChristmasEve
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class ChristmasEve extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1;
   }
   public String getName() {
      return "Christmas Eve";
   }
   public String getRule() {
      return "Day before Christmas";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 12, 24), shift);
   } // end when.
}