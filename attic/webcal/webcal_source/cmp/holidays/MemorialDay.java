package cmp.holidays;

// cmp.holidays.MemorialDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class MemorialDay extends HolInfo {

   public String getAuthority() {
      return "http://www.geocities.com/~angel-pie/memorial/origins.html";
   }
   public int getFirstYear(int base) {
      return 1868;
   }
   public String getName() {
      return "Memorial Day";
   }
   public String getRule() {
      return "Last Monday in May. Originally May 31";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(5 /* last */,
                                        1 /* monday */,
                                        year,
                                        5 /* may */);
   } // end when.
}