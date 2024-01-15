package cmp.holidays;

// cmp.holidays.IsHoliday.java

import cmp.business.BigDate;
import cmp.holidays.*;

/**
 * Lets you easily define a set of holidays,
 * then rapidly determine if any given date is a holiday.
 * It is quite easy to specify which days you want
 * considered holidays using methods like addHoliday where you
 * specify the date or the name of the Holiday,
 * addAmericanFederalHolidays and addWeekDaysAsHolidays.
 *
 *
 * @author  copyright (c) 1999 Roedy Green of Canadian Mind Products
 * may be copied and used freely for any purpose but military.
 *
 * Shareware: If you use any of the Holiday classes for more that casual use, please
 * remit $10 to the address below in Canadian or US funds.  That fee covers use of
 * all the classes in the Holidays package.
 *
 *
 * Roedy Green
 * Canadian Mind Products
 * 5317 Barker Avenue
 * Burnaby, BC Canada V5H 2N6
 * tel: (604) 435-3052
 * mailto:roedy@mindprod.com
 * http://mindprod.com
 *
 * PLEASE REPORT ANY ERRORS OR OMISSIONS TO roedy@mindprod.com
 *
 */

public class IsHoliday {

   static final boolean debugging = false;
   // range of dates that isHoliday supports
   int firstYear;
   int lastYear;
   int firstOrd;
   int lastOrd;


   // bit true means day is a holiday
   java.util.BitSet holidayBits;

   /**
        * Constructor to define range of years over which we wish to compute holidays.
        * negative is AD.
        * @param firstYear first year to cover, must be > 0
        * @param lastYear  last year to cover
        */
   public IsHoliday ( int firstYear, int lastYear) {
      // avoid problem of non-existent year 0,  only support AD
      if ( firstYear < 1 ) {
         throw new IllegalArgumentException(firstYear + " firstYear must be > 0.");
      }

      if ( lastYear > BigDate.MAX_YEAR ) {
         throw new IllegalArgumentException(lastYear + " lastYear must be <= "+ BigDate.MAX_YEAR + ".");
      }

      if ( lastYear < firstYear ) {
         throw new IllegalArgumentException("firstYear must be <= lastYear.");
      }

      firstOrd = BigDate.toOrdinal(firstYear,1,1);
      lastOrd = BigDate.toOrdinal(lastYear,12,31);
      // starts off all zeros, nothing is declared a holiday yet
      holidayBits = new java.util.BitSet(lastOrd-firstOrd+1);
   } // end constructor
   /**
        * add all American Federal holidays
        * To get a list of US Federal Statutory holidays see:
        * http://www.opm.gov/fedhol/1999.htm  or
        * http://aa.usno.navy.mil/AA/faq/docs/holidays.html.
        * @param shift, do you want the shifted holiday to the nearest weekday or the
        * actual holiday.  Usually you want true for the shifted.
        */
   public  void addAmericanFederalHolidays(boolean shift ) {
      for ( int year=firstYear; year<=lastYear; year++ ) {
         /* want shifted days that you take the day off if falls on a weekend,
          holiday itself is alw */
         addHoliday(new NewYearsDay().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new MartinLutherKingDay().when(year, shift, HolInfo.PROCLAIMED));
         // now obsolete, code handles switchover
         addHoliday(new LincolnsBirthday().when(year, shift, HolInfo.PROCLAIMED));

         addHoliday(new PresidentsDay().when(year, shift, HolInfo.PROCLAIMED));

         // now obsolete, code handles switchover
         addHoliday(new WashingtonsBirthday().when(year, shift, HolInfo.PROCLAIMED));

         addHoliday(new MemorialDay().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new IndependenceDay().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new LaborDay().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new ColumbusDay().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new VeteransDay().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new AmericanThanksgiving().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new Christmas().when(year, shift, HolInfo.PROCLAIMED));

      } // end for
   }  /* end addAmericanFederalHolidays */
   /**
        * add all Canadian Federal holidays
        * To get a list of US Federal Statutory holidays see:
        * http://www.opm.gov/fedhol/1999.htm  or
        * http://aa.usno.navy.mil/AA/faq/docs/holidays.html.
        * @param shift, do you want the shifted holiday to the nearest weekday or the
        * actual holiday.  Usually you want true for the shifted.
        */
   public  void addCanadianFederalHolidays(boolean shift ) {
      for ( int year=firstYear; year<=lastYear; year++ ) {
         /* want shifted days that you take the day off if falls on a weekend */
         addHoliday(new NewYearsDay().when(year,shift, HolInfo.PROCLAIMED));

         addHoliday(new GoodFriday().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new EasterMonday().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new VictoriaDay().when(year,shift));
         addHoliday(new CanadaDay().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new LabourDay().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new CanadianThanksgiving().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new RemembranceDay().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new Christmas().when(year, shift, HolInfo.PROCLAIMED));
         addHoliday(new BoxingDay().when(year, shift, HolInfo.PROCLAIMED));

      } // end for
   }  /* end addCanadianFederalHolidays */
   /**
        * add another holiday to the holiday table.
        * @param ordinal days since Jan 1, 1970
        */
   public  void addHoliday( int ordinal) {
      if ( ordinal < firstOrd || ordinal > lastOrd ) {
         // just ignore out of range or NULL_ORDINAL.
         // It is possible for shifted dates to wander a tad out of range.
         return;
      }
      holidayBits.set(ordinal-firstOrd);
   }  // end addHoliday
   /**
        * add all Saturdays and Sundays in the year range as holidays
        */
   public  void addWeekendsAsHolidays( ) {
      int ordFirstSunday = BigDate.ordinalOfnthXXXDay(
                                                     1 /* first */,
                                                     0 /* Sunday */,
                                                     firstYear,
                                                     1 /* January */);
      for ( int i=ordFirstSunday; i<=lastOrd; i+=7 ) {
         addHoliday(i);
      } // end for

      int ordFirstSaturday = BigDate.ordinalOfnthXXXDay(
                                                       1 /* first */,
                                                       6 /* Saturday */,
                                                       firstYear,
                                                       1 /* January */);
      for ( int i=ordFirstSaturday; i<=lastOrd; i+=7 ) {
         addHoliday(i);
      } // end for

   }  /* end addWeekendsAsHolidays */
   /**
        * Is the given day a holiday?
        * What constitutes a holiday is determined by the setHoliday calls
        * @param ordinal days since Jan 1, 1970
        * @return true if that day is a holiday
        */
   public  boolean isHoliday( int ordinal) {
      if ( ordinal < firstOrd || ordinal > lastOrd ) {
         throw new IllegalArgumentException("out of range ordinal date: " + ordinal);
      }
      return holidayBits.get(ordinal-firstOrd);
   }  // end isHoliday
   /**
        * Is the given day a holiday?
        * What constitutes a holiday is determined by the setHoliday calls
        * @param bigDate date to test
        * @return true if that day is a holiday
        */
   public  boolean isHoliday( BigDate bigDate) {
      return isHoliday(bigDate.getOrdinal());
   }  // end isHoliday
   /**
        * test driver
        */
   public static void main(String[] args) {

      if ( debugging ) {

         // prepare list of holidays
         IsHoliday h = new IsHoliday(1990, 2010);
         h.addWeekendsAsHolidays();
         // add nearest weekday to the actual holiday, shift=true
         h.addAmericanFederalHolidays(HolInfo.SHIFTED);
         for ( int year=1990; year<= 2010; year++ ) {
            h.addHoliday(new GroundhogDay().when(year, HolInfo.SHIFTED, HolInfo.PROCLAIMED));
            h.addHoliday(new GeneralElectionDay().when(year, HolInfo.SHIFTED, HolInfo.PROCLAIMED));
         } // end for
         // declare New Year's eve for the millennium a holiday
         h.addHoliday(BigDate.toOrdinal(1999,12,31));

         // test if any given day is a holiday
         System.out.println("Today "
                            + ( h.isHoliday(BigDate.localToday())
                                ? "is": "is not")
                            + " a holiday.");
         System.out.println("2005/12/25 "
                            + ( h.isHoliday(BigDate.toOrdinal(2005,12,25))
                                ? "is": "is not")
                            + " a holiday.");

         // count business days
         int startOrd=BigDate.toOrdinal(1999,1,1);
         int endOrd=BigDate.toOrdinal(1999,12,31);
         int count = 0;
         for ( int i=startOrd; i<=endOrd; i++ ) {
            if ( !h.isHoliday(i) ) count++;
         }
         System.out.println("There were " + count + " business days in 1999");

      } // end if

   } // end main
}