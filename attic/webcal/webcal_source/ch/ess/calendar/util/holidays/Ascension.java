package ch.ess.calendar.util.holidays;

import cmp.business.BigDate;
import cmp.holidays.HolInfo;
import cmp.holidays.EasterSunday;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class Ascension extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1583 /* our calculations only work this far back. */;
   }
   public String getName() {
      return "Ascension";
   }
   public String getRule() {
      return "39 days after Easter.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return new EasterSunday().when(year, false) + 39;

   } // end when.
}