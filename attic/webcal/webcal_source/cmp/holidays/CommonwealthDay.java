package cmp.holidays;

// cmp.holidays.CommonwealthDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class CommonwealthDay extends HolInfo {

   public String getAuthority() {
      return "http://www.pch.gc.ca/ceremonial-symb/english/day_com.html";
   }
   public int getFirstYear(int base) {
      return 1976;
   }
   public String getName() {
      return "Commonwealth Day";
   }
   public String getRule() {
      return "second Monday in March.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return BigDate.ordinalOfnthXXXDay(2 /* second */,
                                        1 /* monday */,
                                        year,
                                        3 /* march */);

   } // end when.
}