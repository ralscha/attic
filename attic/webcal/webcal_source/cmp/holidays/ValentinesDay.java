package cmp.holidays;

// cmp.holidays.ValentinesDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class ValentinesDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 200;
   }
   public String getName() {
      return "Valentines Day";
   }
   public String getRule() {
      return "Always February 14";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 2, 14), shift);

   } // end when.
}