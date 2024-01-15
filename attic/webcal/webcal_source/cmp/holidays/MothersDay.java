package cmp.holidays;

// cmp.holidays.MothersDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class MothersDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1907;
   }
   public String getName() {
      return "Mothers Day";
   }
   public String getRule() {
      return "Second Sunday in May.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return  shiftSatToFriSunToMon(BigDate.ordinalOfnthXXXDay(2 /* second */,
                                                               0 /* sunday */,
                                                               year,
                                                               5 /* may */),
                                    shift);
   } // end when.
}