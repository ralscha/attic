package cmp.holidays;

// cmp.holidays.GoodFriday
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class GoodFriday extends HolInfo {

   public String getAuthority() {
      return "Felix Gursky\'s interpretation of:\n"
      + " 1. Christophorus Clavius: Calendarium Gregorianum Perpetuum.\n"
      + "    Cum Summi Pontificis Et Aliorum Principum. Romae, Ex Officina\n"
      + "    Dominicae Basae, MDLXXXII, Cum Licentia Superiorum.\n"
      + " 2. Christophorus Clavius: Romani Calendarii A Gregorio XIII.\n"
      + "    Pontifice Maximo Restituti Explicatio. Romae, MDCIII.;\n";
   }
   public int getFirstYear(int base) {
      return 1583 /* our calculations only work this far back */;
   }
   public String getName() {
      return "Good Friday";
   }
   public String getRule() {
      return "Friday prior to Easter.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return shiftSatToFriSunToMon(new EasterSunday().when(year, false) - 2, shift);
   } // end when.
}