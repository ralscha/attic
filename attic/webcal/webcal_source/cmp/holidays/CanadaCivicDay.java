package cmp.holidays;

// cmp.holidays.CanadaCivicDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class CanadaCivicDay extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1970;
   }
   public String getName() {
      return "Canada Civic Day";
   }
   public String getRule() {
      return "first Monday in August";
   }
   public int when(int year, boolean shift, int base) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(1 /* first */, 1 /* monday */, year, 8 /* august */);
   } // end when.
}