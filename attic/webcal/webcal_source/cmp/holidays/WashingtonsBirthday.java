package cmp.holidays;

// cmp.holidays.WashingtonsBirthday
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class WashingtonsBirthday extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1796;
   }
   public String getName() {
      return "Washington's Birthday";
   }
   public String getRule() {
      return  "Celebrated as third Monday in February.\n" +
      "Replaced by President's day in 1971.\n" +
      "Washington's actual birthday is February 22.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) || (base == PROCLAIMED && year >= 1971) ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(3 /* third */,
                                        1 /* monday */,
                                        year,
                                        2 /* february */);

   } // end when.
}