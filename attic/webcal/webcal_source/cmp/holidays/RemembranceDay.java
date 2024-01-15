package cmp.holidays;

// cmp.holidays.RemembranceDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class RemembranceDay extends HolInfo {

   public String getAuthority() {
      return "Canadian Encyclopedia";
   }
   public int getFirstYear(int base) {
      switch ( base ) {
      default:
      case CELEBRATED:
         return 1918;
      case PROCLAIMED:
         return 1931;
      }
   }
   public String getName() {
      return "Remembrance Day";
   }
   public String getRule() {
      return "Always November 11. From 1923 to 1931 it was called Armistice Day\n" +
      "and merged with Canadian Thanksgiving.\n" +
      "In 1931, it was moved to November 11, and renamed.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      // 1931 actually had two celebrations, we return the Nov 11 one.
      if ( 1923 <= year && year <= 1930 ) {
         return  BigDate.ordinalOfnthXXXDay(2 /* second */, 1 /* monday */, year, 10 /* oct */);
      }
      return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 11, 11), shift);
   } // end when.
}