package cmp.holidays;

// cmp.holidays.FathersDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class FathersDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1910;
   }
   public String getName() {
      return "Fathers Day";
   }
   public String getRule() {
      return "Third Sunday in June";
   }
   public int when(int year, boolean shift, int base) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.ordinalOfnthXXXDay(3 /* third */, 0 /* sunday */, year, 6 /* june */), shift);
   } // end when.
}