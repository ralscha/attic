package cmp.holidays;

// cmp.holidays.StJeanBaptisteDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class StJeanBaptisteDay extends HolInfo {

   public String getAuthority() {
      return "http://frenchcaculture.miningco.com/library/weekly/aa062097.htm";
   }
   public int getFirstYear(int base) {
      return 1638;
   }
   public String getName() {
      return "St. Jean-Baptiste Day";
   }
   public String getRule() {
      return "Always June 24";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 6, 24), shift);

   } // end when.
}