package cmp.holidays;

import cmp.business.BigDate;

/**
 * This type was created in VisualAge.
 */
public class BoxingDay extends HolInfo {

   /**
    * convert Saturdays to following Monday,  Sundays to following Monday,
    * Mondays to Tuesday.
    * @param ordinal days since Jan 1, 1970.
    * @param shift false if you want the actual date of the holiday.
    *      true if you want the date taken off work.
    * @return adjusted ordinal
    */
   static int boxingDayShift(int ordinal, boolean shift) {
      // This is complicated because preceeding Christmas day may be shifted too.
      if ( shift ) {
         switch ( BigDate.dayOfWeek(ordinal) ) {
         case 0 :
            /* sunday */
            /* shift to Monday */
            return ordinal + 1;
         case 1 :
            /* monday */
            /* shift to Tuesday */
            return ordinal + 1;
         case 2 :
            /* tuesday */
         case 3 :
            /* wednesday */
         case 4 :
            /* thursday */
         case 5 :
            /* friday */
         default :
            return ordinal;
         case 6 :
            /* saturday */
            /* shift to Monday */
            return ordinal + 2;
         } // end switch
      } // end if
      else
         return ordinal;
   } // end boxingDayShift
   public String getAuthority() {
      return "";
   }
   public int getFirstYear(int base) {
      return 1;
   }
   public String getName() {
      return "Boxing Day";
   }
   public String getRule() {
      return "Day after Christmas.";
   }
   public int when(int year, boolean shift, int base) {
      if ( !isYearValid(year, base) ) {
         return BigDate.NULL_ORDINAL;
      }
      return boxingDayShift(BigDate.toOrdinal(year, 12, 26), shift);
   } // end when.
}