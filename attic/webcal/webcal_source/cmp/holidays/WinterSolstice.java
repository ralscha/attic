package cmp.holidays;

// cmp.holidays.WinterSolstice
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class WinterSolstice extends HolInfo {

   public String getAuthority() {
      return "Jean Meeus's \"Astronomical Algorithms\"";
   }
   public int getFirstYear(int base) {
      return -10000;
   }
   public String getName() {
      return "Winter Solstice";
   }
   public String getRule() {
      return "December 19 to 21, based on earth's revolution around the sun.\n"
      + "Formulas we use are only valid 1000 .. 30000";
   }
   public static void main (String[] args) {
      if ( debugging ) {

         HolInfo h = new WinterSolstice();
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

      // Roughly each solstice is 365241.62603 days after the previous,
      // with some second, third and fourth order tweaking.
      // The base equinox is 2000 Dec 21.

      double m = ((double)year-2000)/1000;
      // result ve is astronomical Propleptic Julian day number
      double ws = 2451900.05952+365242.74049*m-0.06223*m*m-0.00823*m*m*m+0.00032*m*m*m*m;
      BigDate d = new BigDate(ws);
      return shiftSatToFriSunToMon(d.getOrdinal(), shift);

   } // end when
}