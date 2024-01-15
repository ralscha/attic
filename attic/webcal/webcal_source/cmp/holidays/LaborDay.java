package cmp.holidays;

// cmp.holidays.LaborDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class LaborDay extends HolInfo {

   public String getAuthority() {
      return "http://www.dol.gov/dol/opa/public/aboutdol/laborday.htm";
   }
   public int getFirstYear(int base) {
      return 1882;
   }
   public String getName() {
      return "American Labor Day";
   }
   public String getRule() {
      return "First Monday in September. In 1882..1883 it was celebrated on September 5.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      if ( year < 1882 ) {
         return BigDate.NULL_ORDINAL;
      }
      if ( 1882 <= year && year <= 1883 ) {
         return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 9, 5), shift);

      }
      if ( 1884 <= year ) {
         return BigDate.ordinalOfnthXXXDay(1 /* first */,
                                           1 /* monday */,
                                           year,
                                           9 /* sept */);
      }
      return BigDate.NULL_ORDINAL;
   } // end when.
}