package cmp.holidays;

// cmp.holidays.LabourDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class LabourDay extends HolInfo {

   public String getAuthority() {
      return "http://www.dol.gov/dol/opa/public/aboutdol/laborday.htm";
   }
   public int getFirstYear(int base) {
      return 1884;
   }
   public String getName() {
      return "Canadian Labour Day";
   }
   public String getRule() {
      return "First Monday in September.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(1 /* first */,
                                        1 /* monday */,
                                        year,
                                        9 /* sept */);
   } // end when.
}