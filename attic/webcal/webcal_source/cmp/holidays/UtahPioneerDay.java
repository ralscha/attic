package cmp.holidays;

// cmp.holidays.UtahPioneerDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class UtahPioneerDay extends HolInfo {

   public String getAuthority() {
      return "Paul Hill <phill@myriad.com>";
   }
   public int getFirstYear(int base) {
      return 1847;
   }
   public String getName() {
      return "Utah Pioneer Day";
   }
   public String getRule() {
      return "Always July 24";
   }
   // All Utah state employees and nearly all commercial
   // employees get the day off.
   // Pioneer Day is to celebrate the Mormon Pioneers
   // invading the area.  The 24th is the day their leader
   // old Brigham Young himself come into the Salt
   // Lake Valley and is quoted as saying 'This is the Place."
   // first celebrated ???  Date of trek was 1847.
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 7, 24), shift);
   } // end when.
}