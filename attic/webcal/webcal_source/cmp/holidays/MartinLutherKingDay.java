package cmp.holidays;

// cmp.holidays.MartinLutherKingDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class MartinLutherKingDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1986;
   }
   public String getName() {
      return "Martin Luther King Day";
   }
   public String getRule() {
      return "celebrated as third Monday in January, King's birthday is Jan 18.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(3 /* third */,
                                        1 /* monday */,
                                        year,
                                        1 /* january */);

   } // end when.
}