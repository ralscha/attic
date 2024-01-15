package cmp.holidays;

// cmp.holidays.VictoriaDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class VictoriaDay extends HolInfo {

   public String getAuthority() {
      return "http://www.pch.gc.ca/ceremonial-symb/english/day_vic.html";
   }
   public int getFirstYear(int base) {
      switch ( base ) {
      default:
      case CELEBRATED:
         return 1837;
      case PROCLAIMED:
         return 1845;
      }
   }
   public String getName() {
      return "Victoria Day";
   }
   public String getRule() {
      return   "The Sovereign\'s birthday has been celebrated in Canada\n"
      + "since the reign of Queen Victoria (1837-1901).\n"
      + "May 24, Queen Victoria\'s birthday, was declared a holiday\n"
      + "by the Legislature of the Province of Canada in 1845.\n"
      + "After Confederation, the Queen\'s birthday was celebrated\n"
      + "every year on May 24 unless that date was a Sunday,\n"
      + "in which case a proclamation was issued providing\n"
      + "for the celebration on May 25.\n"
      + "After the death of Queen Victoria in 1901,\n"
      + "an Act was passed by the Parliament of Canada\n"
      + "establishing a legal holiday on May 24 in each year\n"
      + "(or May 25 if May 24 fell on a Sunday) under the name Victoria Day.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      int ord = BigDate.toOrdinal(year, 5, 24);
      if ( BigDate.dayOfWeek(ord) == 0 ) {
         ord++;
      }
      return shiftSatToFriSunToMon(ord, shift);

   } // end when.
}