package cmp.holidays;

// cmp.holidays.FamilyDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class FamilyDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1900 /* ??? */;
   }
   public String getName() {
      return "Alberta Family Day";
   }
   public String getRule() {
      return "Third monday in February.";
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