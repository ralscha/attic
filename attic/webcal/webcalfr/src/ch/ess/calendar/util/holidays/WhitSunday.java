package ch.ess.calendar.util.holidays;

import cmp.business.*;
import cmp.holidays.*;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class WhitSunday extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1583 /* our calculations only work this far back. */;
   }
   public String getName() {
      return "Whit Sunday";
   }
   public String getRule() {
      return "49 days after Easter.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return new EasterSunday().when(year, false) + 49;

   } // end when.
}