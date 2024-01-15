package cmp.holidays;

// cmp.holidays.CanadianThanksgiving
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class CanadianThanksgiving extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1700; /* ??? */
   }
   public String getName() {
      return "Canadian Thanksgiving";
   }
   public String getRule() {
      return "Second Monday in October";
   }
   public int when(int year, boolean shift, int base) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(2 /* second */, 1 /* monday */, year, 10 /* oct */);
   } // end when.
}