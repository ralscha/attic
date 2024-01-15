package cmp.holidays;

// cmp.holidays.CanadaDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class CanadaDay extends HolInfo {

   public String getAuthority() {
      return "Canada Holidays Act";
   }
   public int getFirstYear(int base) {
      return 1867;
   }
   public String getName() {
      return "Canada Day";
   }
   public String getRule() {
      return "July 1, or July 2 when July 1 is a Sunday.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      BigDate d = new BigDate(year, 7, 1);
      int ord = d.getOrdinal();
      if ( d.getDayOfWeek() == 0 /* Sunday */ ) {
         ord++; /* adjust to Monday */
      }
      return  shiftSatToFriSunToMon(ord, shift);

   } // end when.
}