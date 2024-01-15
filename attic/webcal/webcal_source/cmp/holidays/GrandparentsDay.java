package cmp.holidays;

// cmp.holidays.GrandparentsDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class GrandparentsDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1978;
   }
   public String getName() {
      return "Grandparents Day";
   }
   public String getRule() {
      return "First Monday after Labour Day.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(2 /* second */,
                                        1 /* monday */,
                                        year,
                                        9 /* sept */);

   } // end when.
}