package cmp.holidays;

// cmp.holidays.LincolnsBirthday
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class LincolnsBirthday extends HolInfo {

   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      switch ( base ) {
      default:
      case CELEBRATED:
         return 1809; // his birthday
      case PROCLAIMED:
         return 1809; // not known ??
      }
   }
   public String getName() {
      return "Lincoln's Birthday";
   }
   public String getRule() {
      return "always on February 12, replaced by President's Day in 1971";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) || (base == PROCLAIMED && year >= 1971) ) {
         return BigDate.NULL_ORDINAL;
      }
      return  shiftSatToFriSunToMon(BigDate.toOrdinal(year, 2, 12), shift);

   } // end when.
}