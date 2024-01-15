package cmp.holidays;

// cmp.holidays.VeteransDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class VeteransDay extends HolInfo {

   public String getAuthority() {
      return "http://www.usis.usemb.se/Holidays/celebrate/Veterans.htm";
   }
   public int getFirstYear(int base) {
      switch ( base ) {
      default:
      case CELEBRATED:
         return 1918; // end of WWI
      case PROCLAIMED:
         return 1938;
      }
   }
   public String getName() {
      return "Veterans Day";
   }
   public String getRule() {
      return "Always November 11";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 11, 11), shift);
   } // end when.
}