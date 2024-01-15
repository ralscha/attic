package cmp.holidays;

// cmp.holidays.GeneralElectionDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class GeneralElectionDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1789;
   }
   public String getName() {
      return "General Election Day";
   }
   public String getRule() {
      return "First Tuesday after first Monday in November, in even numbered years.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      if ( year % 2 != 0 ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(1 /* first */,
                                        1 /* monday */,
                                        year,
                                        11 /* november */)+1; /* adjust to tuesday */


   } // end when.
}