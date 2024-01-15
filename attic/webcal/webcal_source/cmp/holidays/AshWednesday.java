package cmp.holidays;

// cmp.holidays.AshWednesday
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class AshWednesday extends HolInfo {

   public String getAuthority() {
      return "Felix Gursky";
   }
   public int getFirstYear(int base) {
      return 1583 /* our calculations only work this far back. */;
   }
   public String getName() {
      return "Ash Wednesday";
   }
   public String getRule() {
      return "46 days prior to Easter.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return new EasterSunday().when(year, false) - 46;

   } // end when.
}