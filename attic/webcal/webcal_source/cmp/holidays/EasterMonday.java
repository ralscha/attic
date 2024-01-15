package cmp.holidays;

// cmp.holidays.EasterMonday
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class EasterMonday extends HolInfo {

   public String getAuthority() {
      return
      "Felix Gursky\'s interpretation of:\n"
      + " 1. Christophorus Clavius: Calendarium Gregorianum Perpetuum.\n"
      + "    Cum Summi Pontificis Et Aliorum Principum. Romae, Ex Officina\n"
      + "    Dominicae Basae, MDLXXXII, Cum Licentia Superiorum.\n"
      + " 2. Christophorus Clavius: Romani Calendarii A Gregorio XIII.\n"
      + "    Pontifice Maximo Restituti Explicatio. Romae, MDCIII.;\n";
   }
   public int getFirstYear(int base) {
      return 1583 /* our calculations only work this far back. */;
   }
   public String getName() {
      return "Easter Monday";
   }
   public String getRule() {
      return "Monday after Easter.";
   }
   public int when( int year, boolean shift, int base ) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return new EasterSunday().when(year, false) + 1;

   } // end when.
}