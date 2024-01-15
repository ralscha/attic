package cmp.holidays;

// cmp.holidays.PresidentsDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class PresidentsDay extends HolInfo {

   public String getAuthority() {
      return "http://www.usis.usemb.se/Holidays/presiden.htm";
   }
   public int getFirstYear(int base) {
      return 1971;
   }
   public String getName() {
      return "Presidents Day";
   }
   public String getRule() {
      return "Third Monday in February.\nReplaced Lincoln and Washington's birthday in 1971.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(3 /* third */,
                                        1 /* monday */,
                                        year,
                                        2 /* february */);

   } // end when.
}