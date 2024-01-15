package cmp.holidays;

// cmp.holidays.VernalEquinox
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class VernalEquinox extends HolInfo {

   public String getAuthority() {
      return "Jean Meeus's \"Astronomical Algorithms\"";
   }
   public int getFirstYear(int base) {
      return -10000;
   }
   public String getName() {
      return "Vernal Equinox";
   }
   public String getRule() {
      return "March 19 to 21, based on earth's revolution around the sun.\n"
      + "Formulas we use are only valid 1000 .. 30000";
   }
   public static void main (String[] args) {
      if ( debugging ) {

         HolInfo h = new VernalEquinox();
         BigDate d = new BigDate();
         for ( int year=1999; year<=2012; year++ ) {
            d.setOrdinal(h.when(year, false));
            System.out.println(d.getYYYY() + "-" + d.getMM() + "-" + d.getDD());
         }
      }
   } // end main
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      if ( year < 1000 || year > 3000 ) {
         // our formula is not currently accurate outside this range.
         return BigDate.NULL_ORDINAL;

      }

      // Roughly each equinox is 365.2423704 days after the previous,
      // with some second, third and fourth order tweaking.
      // The base equinox is 2000 March 20.

      double m = ((double)year-2000)/1000;
      // result ve is astronomical Propleptic Julian day number
      double ve = 2451623.80984+365242.37404*m+0.05169*m*m-0.00411*m*m*m-0.00057*m*m*m*m;
      BigDate d = new BigDate(ve);
      return shiftSatToFriSunToMon(d.getOrdinal(), shift);

   } // end when
}