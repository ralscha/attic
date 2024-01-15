package cmp.holidays;

// cmp.holidays.IndependenceDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class IndependenceDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1776;
   }
   public String getName() {
      return "Independence Day";
   }
   public String getRule() {
      return "Always July 4";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 7, 4), shift);

   } // end when.
}