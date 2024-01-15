package cmp.holidays;

// cmp.holidays.HolInfo
import cmp.business.BigDate;


/**
 * Information about a single holiday.
 * base class for various holiday calculators.
 * See Class cmp.Holiday.Christmas for sample implementation
 *
 * For rules about how various holidays are computed see:
 *   http://www.mnsinc.com/utopia/Calendar/Holiday_Dates/Holiday_Determinations.html
 * To get a list of US Federal Statutory holidays see:
 *   http://www.opm.gov/fedhol/1999.htm  or
 *   http://aa.usno.navy.mil/AA/faq/docs/holidays.html.
 * To get a list of US Federal Observances (not holidays) see:
 *   http://www4.law.cornell.edu/uscode/unframed/36/ch9.html
 *   http://www.askjeeves.com/ was very helpful in tracking down
 * information about the various holidays.
 * For a list of Canadian Bank holidays see:
 *   http://infoservice.gc.ca/canadiana/bochol-99_e.html
 * For a list of Canadian provincial holidays see:
 *   http://www.pch.gc.ca/ceremonial-symb/english/day_prv.html
 * For a list of Canadian Federal Holidays see:
 *   http://www.pch.gc.ca/ceremonial-symb/english/day.html
 * Calendrical Calculations by Dershowitz and Reingold handles Chinese New Year.
 *   http://emr.cs.uiuc.edu/home/reingold/calendar-book/index.shtml
 * For astronomical calculations see:
 *   http://www.ccs.neu.edu/home/ramsdell/jdk1.1/lunisolar/lunisolar.html
 * For C Calendar code see:
 *   http://www.magnet.ch/serendipity/hermetic/index.html
 * For a large collection of calendar code see:
 *   http://www.hiline.net/users/rms/ section 4.2 Cool Stuff in SCDTL
 * For various calendar links see:
 *   http://www.calendarzone.com/
 * For various calendar links see:
 *   http://dir.yahoo.com/Reference/Calendars/
 */


public abstract class HolInfo {

   public static final boolean debugging = true;

   /**
        * base calculations on date holiday was first officially proclaimed.
        */
   public static final int PROCLAIMED=0;

   /**
        * base calculations on date holiday was first celebrated.
        */
   public static final int CELEBRATED=1;

   /**
        * base calculations on the actual date the holiday is observed.
        */
   public static final boolean ACTUAL = false;

   // base calculations on the nearest weekday to the holiday.
   public static final boolean SHIFTED = true;

   /** Get authority who provided the information about the holiday.
        *
        * @return name of person, email address, website etc. that describes the
        * rules about the holiday.  ""  if no one in particular.
        *
        */
   abstract public String getAuthority();
   /**
        * Get year holiday first proclaimed or first celebrated.
        *
        * @param base PROCLAIMED=based on date holiday was officially proclaimed
        * CELEBRATED=based on date holiday was first celebrated
        *
        * @return year first proclaimed, or first celebrated.
        *
        */
   abstract public int getFirstYear(int base);


   /**
        * Get name of holiday e.g. "Christmas"
        *
        * @return English language name of the holiday.
        *
        */
   abstract public String getName();
   /**
        * Get rule in English for how the holiday is calculated. e.g.
        * "Always on Dec 25." or "Third Monday in March."
        * may contain embedded \n characters.
        *
        *
        * @return rule for how holiday is computed.
        *
        */
   abstract public String getRule();
   /**
        * Is year valid for this holiday?
        */
   final protected boolean isYearValid( int year, int base) {
      return (year != 0) &&  (year >= getFirstYear(base));
   } // isYearValid
   /**
        * convert Saturdays to preceeding Friday, Sundays to following Monday.
        * @param ordinal days since Jan 1, 1970.
        * @param shift= ACTUAL = false if you want the actual date of the holiday.
        *      = SHIFTED = true if you want the date taken off work.
        * @return adjusted ordinal
        */
   final protected int shiftSatToFriSunToMon(int ordinal, boolean shift) {
      if ( shift ) {
         switch ( BigDate.dayOfWeek(ordinal) ) {
         case 0: /* sunday */
            /* shift to Monday */
            return ordinal+1;

         case 1: /* monday */
         case 2: /* tuesday */
         case 3: /* wednesday */
         case 4: /* thursday */
         case 5: /* friday */
         default:
            /* leave as is */
            return ordinal;

         case 6: /* saturday */
            /* shift to Friday */
            return ordinal-1;

         } // end switch
      } // end if
      else return ordinal;

   } // end shiftSatToFriSunToMon
   /**
        * When was this holiday in a given year?, based on PROCLAIMED date.
        * @param year must be 1583 or later.
        * @return ordinal days since Jan 1, 1970.
        */
   final public int when( int year ) {
      return when(year, false, PROCLAIMED);

   }
   /**
        * When was this holiday in a given year?, based on PROCLAIMED date.
        * @param year must be 1583 or later.
        * * @param shift= ACTUAL = false if you want the actual date of the holiday.
        *      = SHIFTED = true if you want the date taken off work.
        * @return ordinal days since Jan 1, 1970.
        *
        */
   final public int when( int year, boolean shift ) {
      return when(year, shift, PROCLAIMED);

   }
   /**
        * When was this holiday in a given year?
        * @param year (-ve means BC, +ve means AD, 0 not permitted.)
        * @param shift= ACTUAL = false if you want the actual date of the holiday.
        *      = SHIFTED = true if you want the date taken off work.
        * @param base PROCLAIMED=based on date holiday was officially proclaimed
        * CELEBRATED=based on date holiday was first celebrated
        * @return ordinal days since Jan 1, 1970.
        * return NULL_ORDINAL if the holiday was not celebrated in that year.
        *
        */
   abstract public int when( int year, boolean shift, int base );
}