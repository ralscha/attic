package cmp.holidays;

// cmp.holidays.CincoDeMayo
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class CincoDeMayo extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1809;
   }
   public String getName() {
      return "Cinco de Mayo";
   }
   public String getRule() {
      return "Always May 5.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 5, 5), shift);
   } // end when.
}