package cmp.holidays;

// cmp.holidays.ColumbusDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class ColumbusDay extends HolInfo {

   public String getAuthority() {
      return "http://www.usis.usemb.se/Holidays/celebrate/Columbus.htm";
   }
   public int getFirstYear(int base) {
      return 1905;
   }
   public String getName() {
      return "Columbus Day";
   }
   public String getRule() {
      return "1905 .. 1970 -> October 12\n"
      + "1971 .. now  -> Second Monday in October.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      if ( 1905 <= year && year <= 1970 ) {
         return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 10, 12), shift);
      }


      if ( 1971 <= year ) {
         return BigDate.ordinalOfnthXXXDay(2 /* second */,
                                           1 /* monday */,
                                           year,
                                           10 /* oct */);
      }

      return BigDate.NULL_ORDINAL;
   } // end when.
}