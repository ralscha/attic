package cmp.business;

// TestDate.java
import java.io.*;
import java.util.Random;
import java.util.TimeZone;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
  * Validate the BigDate class it is bug-free.
  * Also demonstrate and exercise the various methods.
  * @author copyright (c) 1997-1999 Roedy Green of Canadian Mind Products
  */
public class TestDate
{

    static final boolean debugging = false;

    /**
         * show off some of the things BigDate can do
         * See also cmp.holidays.Holiday to compute various holidays
         * and cmp.holidays.IsHoliday to tell if a given day is a holiday.
         */
    public static void demoDate()
    {

        // How Many days since 1970 is 2000/2/29?
        {
            BigDate d = new BigDate(2000, 2, 29);
            System.out.println("1999-12-29 is " + d.getOrdinal() + " days since 1970");
        }

        // How Many milliseconds since 1970 is 1999/11/7
        {
            BigDate d = new BigDate(1999, 11, 7);
            System.out.println("1999-11-7 is " + d.getLocalTimeStamp() + " milliseconds since 1970");
        }


        // What is the Julian Day number of various dates 1970/1/1
        // Cross check these with the US Naval Observatory at:
        // http://aa.usno.navy.mil/AA/data/docs/JulianDate.html
        {
            BigDate d = new BigDate(2000,3,20);
            System.out.println("Astronomical Julian Propleptic calendar day for 2000/03/20 is " +
                               d.getProplepticJulianDay());
            d = new BigDate(1970,1,1);
            System.out.println("Astronomical Julian Propleptic calendar day for 1970/01/01 is " +
                               d.getProplepticJulianDay());
            d = new BigDate(1600,1,1);
            System.out.println("Astronomical Julian Propleptic calendar day for 1600/01/01 is " +
                               d.getProplepticJulianDay());
            d = new BigDate(1500,1,1);
            System.out.println("Astronomical Julian Propleptic calendar day for 1500/01/01 is " +
                               d.getProplepticJulianDay());
            d = new BigDate(1,1,1);
            System.out.println("Astronomical Julian Propleptic calendar day for 0001/01/01 is " +
                               d.getProplepticJulianDay());
            d = new BigDate(-1,12,31);
            System.out.println("Astronomical Julian Propleptic calendar day for -0001/01/01 is " +
                               d.getProplepticJulianDay());
            d = new BigDate(-6,1,1);
            System.out.println("Astronomical Julian Propleptic calendar day for -0006/01/01 is " +
                               d.getProplepticJulianDay());
            d = new BigDate(-4713,1,1);
            System.out.println("Astronomical Julian Propleptic calendar day for -4713/01/01 is " +
                               d.getProplepticJulianDay());
        }
        pause();

        // Display a BigDate with SimpleDateFormat using local time
        {
            BigDate bigDate = new BigDate(1999, 12, 31);
            Date date = bigDate.getLocalDate();
            SimpleDateFormat sdf = new SimpleDateFormat( "EEEE yyyy/MM/dd G hh:mm:ss aa zz : zzzzzz" );
            sdf.setTimeZone(TimeZone.getDefault()); // local time
            String dateString = sdf.format(date);
            System.out.println("Local SimpleDateFormat: " + dateString);
        }


        // Display a BigDate with SimpleDateFormat using UTC (Greenwich GMT) time
        {
            BigDate bigDate = new BigDate(1999, 12, 31);
            Date date = bigDate.getUTCDate();
            SimpleDateFormat sdf = new SimpleDateFormat( "EEEE yyyy/MM/dd G hh:mm:ss aa zz : zzzzzz" );
            sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // GMT time
            String dateString = sdf.format(date);
            System.out.println("UTC/GMT SimpleDateFormat: " + dateString);
        }


        // Display a BigDate with default-locale DateFormat using UTC (Greenwich GMT) time
        {
            BigDate bigDate = new BigDate(1999, 12, 31);
            Date date = bigDate.getUTCDate();
            DateFormat df = DateFormat.getDateInstance();
            df.setTimeZone(TimeZone.getTimeZone("GMT")); // GMT time
            String dateString = df.format(date);
            System.out.println("UTC/GMT locale DateFormat: " + dateString);
        }


        // What are the earliest and latest dates BigDate can handle
        {
            BigDate d = new BigDate(BigDate.MIN_ORDINAL);
            System.out.println("Earliest BigDate possible is " + d.toString());
            d.setOrdinal(BigDate.MAX_ORDINAL);
            System.out.println("Latest BigDate possible is " + d.toString());
        }

        pause();

        // how many seconds between Jan 1, 1900 0:00 and Jan 1, 1970 0:00
        // i.e. difference between SNTP and Java timestamp bases.
        // No leap seconds to worry about.
        {
            long diffInDays = BigDate.toOrdinal(1970,1,1) - BigDate.toOrdinal(1900,1,1);
            long diffInSecs = diffInDays * (24 * 60 * 60L);
            System.out.println(diffInDays
                               + " days or "
                               + diffInSecs
                               + " seconds between 1900 Jan 1 and 1970 Jan 1");
        }

        // What day of the week is today?
        // display ISO 8601:1988 international standard format: yyyy-mm-dd.
        {
            BigDate d = BigDate.localToday();
            System.out.println("Today = " + d.toString() + ".");
            /* 0=Sunday to 6=Saturday */
            int dayOfWeek = d.getDayOfWeek();
            String[] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            String dayName = daysOfTheWeek[dayOfWeek];
            System.out.println("Today is " + dayName + ".");
            System.out.println("Today is dayOfWeek number " + d.getDayOfWeek() + " if Sunday=0.");
            System.out.println("Today is dayOfWeek number " + d.getCalendarDayOfWeek() + " if Sunday=1.");
        }

        // What is the last day of this week, e.g. this Saturday?
        // display yyyy/mm/dd format.
        {
            BigDate d = BigDate.localToday();
            /* 0=Sunday to 6=Saturday */
            d.setOrdinal(d.getOrdinal() + 6 - d.getDayOfWeek());
            System.out.println("This Saturday is " + d.toString() + ".");
        }

        // In what week number did 1999/08/20 fall?

        {
            BigDate d = new BigDate(1999,8,20);
            System.out.println("1999/08/20 fell in ISO week number "+ d.getISOWeekNumber()
                               + " on ISO day of week number " + d.getISODayOfWeek() + ".");

            System.out.println(" According to BigDate.getWeekNumber that is week number " + d.getWeekNumber());

            GregorianCalendar g = new GregorianCalendar(1999,8-1,20);
            System.out.println(" According to java.util.Calendar that is week number "  + g.get(Calendar.WEEK_OF_YEAR)+".");
        }

        // What day of the week was Jesus born on?
        {
            // assume Jesus was born on December 25, 0001.
            // Scholars assure us he was NOT actually born then.
            int dayOfWeek = BigDate.dayOfWeek(BigDate.toOrdinal( 1, 12, 25));
            String[] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            String dayName = daysOfTheWeek[dayOfWeek];
            System.out.println("Jesus was born on a " + dayName + ".");
        }

        // How much time elapsed between December 25 4 BC and April 30, 28 AD
        {
            BigDate fromDate = new BigDate(-4, 12, 25);
            BigDate toDate = new BigDate(28, 4, 30);
            System.out.println(toDate.getOrdinal() - fromDate.getOrdinal()
                               + " days elapsed between 0004/12/25 BC and 0028/04/30 AD,");
            int[] age = BigDate.age(fromDate, toDate);
            System.out.println("or put another way, " + age[0] +
                               " years and " + age[1] + " months and " +
                               age[2] + " days.");
        }

        // what is the next business day?
        {
            BigDate d = BigDate.localToday();
            int interval;
            switch ( d.getDayOfWeek() )
            {

                case 5 /* Friday -> Monday */:
                    interval = 3;
                    break;

                case 6 /* Saturday -> Monday */:
                    interval = 2;
                    break;

                case 0 /* Sunday -> Monday */:
                case 1 /* Monday -> Tuesday */:
                case 2 /* Tuesday -> Wednesday */:
                case 3 /* Wednesday -> Thursday */:
                case 4 /* Thursday -> Friday */:
                default:
                    interval = 1;
                    break;
            } // end switch
            d.setOrdinal(d.getOrdinal()+interval);
            System.out.println("Next business day = " + d.toString() + ".");
        }

        // What date is it in Greenwich?
        {
            BigDate d = BigDate.UTCToday();
            System.out.println("Today in Greenwich England = " + d.toString() + ".");
            /* 0=Sunday to 6=Saturday */
            int dayOfWeek = d.getDayOfWeek();
            String[] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            String dayName = daysOfTheWeek[dayOfWeek];
            System.out.println("In Greenwich it is " + dayName + ".");
        }

        // How many Days Until Christmas?
        {
            BigDate today = BigDate.localToday();
            BigDate christmas = new BigDate(today.getYYYY(), 12, 25);
            int howLongToWait = christmas.getOrdinal() - today.getOrdinal();
            System.out.println(howLongToWait + " days till Christmas.");
        }

        pause();

        // What is the last day of February in 2000, preferred method
        {
            BigDate d = new BigDate(2000, 3, 1); /* 2000, March 1 */
            d.setOrdinal(d.getOrdinal() - 1); /* last day of Feb */
            System.out.println("Last day in Feb 2000 = " + d.toString() + ".");
        }

        // What is the last day of February in 2004, alternate less safe, no-check method
        {
            BigDate d = new BigDate(2004, 3, 0, BigDate.NORMALIZE); /* 2004, March 0 */
            System.out.println("Last day in Feb 2004 = " + d.toString() + ".");
        }


        // What Date was it yesterday?
        {
            BigDate d = BigDate.localToday();
            d.setOrdinal(d.getOrdinal() - 1 );
            System.out.println("Yesterday = " + d.toString() + ".");
        }


        // What Date will it be tomorrow?
        {
            BigDate d = BigDate.localToday();
            d.setOrdinal(d.getOrdinal() + 1 );
            System.out.println("Tomorrow = " + d.toString() + ".");
        }


        // What Date will it be 1000 days from now?
        {
            BigDate d = BigDate.localToday();
            d.setOrdinal(d.getOrdinal() + 1000 );
            System.out.println("Today+1000 days = " + d.toString() + ".");
        }

        pause();

        // What date will it be 50 months after 1998-01-03
        // You can think of this as how to turn a possibly invalid date into
        // the equivalent valid one.
        {
            BigDate d = new BigDate( 1998, 1 + 50, 3, BigDate.NORMALISE);
            System.out.println("1998-01-03+50 months = " + d.toString() + ".");
        }

        // Was 1830-02-29 a valid date?
        {
            System.out.println("True or false: 1830/02/29 was a valid date: " +
                               BigDate.isValid(1830, 2, 29) + ".");
        }

        // how long has it been since since John F. Kennedy was
        // assassinated?
        {
            // John F. Kennedy was assassinated on November 22, 1963
            System.out.println("John F. Kennedy was assassinated "
                               + (BigDate.localToday().getOrdinal() -
                                  BigDate.toOrdinal(1963, 11, 22))
                               + " days ago.");
        }

        // How long after Martin Luther King's assassination
        // was Bobby Kennedy assassinated?
        {
            // MLK was assassinated April 4, 1968.
            // RK was assassinatet June 4, 1968.
            System.out.println("Robert Kennedy was assassinated "
                               + (BigDate.toOrdinal(1968, 6, 4) -
                                  BigDate.toOrdinal(1968, 4, 4))
                               + " days after Martin Luther King.");

        }

        // Is the year 2000 a leap year?
        {
            System.out.println("True or false: 2000 is a leap year: " +
                               BigDate.isLeap(2000) + ".");
        }

        // Precisely how old is Roedy Green, in years, months and days
        {
            // Roedy was born on February 4, 1948.
            BigDate birthDate = new BigDate(1948, 2, 4);
            BigDate today = BigDate.localToday();
            int [] age = BigDate.age( birthDate, today);
            System.out.println("Today Roedy is " + age[0] +
                               " years and " + age[1] + " months and " +
                               age[2] + " days old.");
        }

        // What Day of the Month Is The Second Thursday Of This Month?
        // This is when the Vancouver PC User Society meets.
        {
            BigDate today = BigDate.localToday();
            int dayOfMonthOfSecondThursday = BigDate.nthXXXDay( 2 /* second */,
                                                                4 /* thursday */,
                                                                today.getYYYY(),
                                                                today.getMM());

            System.out.println("The second Thursday of the month is on the "
                               + dayOfMonthOfSecondThursday + "th.");
        }


        // What Day of the Month Is The Last Friday Of This Month?
        {
            BigDate today = BigDate.localToday();
            int dayOfMonthOfLastFriday = BigDate.nthXXXDay( 5 /* last */,
                                                            5 /* friday */,
                                                            today.getYYYY(),
                                                            today.getMM());

            System.out.println("The last Friday of the month is on the "
                               + dayOfMonthOfLastFriday + "th.");
        }


    } // end demoDate
    /**
         * Display an error message and exit.
         * @param why the error message explaining why we are terminating.
         */
    protected static void fail(String why)
    {
        System.out.println(why);
        pause();
        System.exit(99);
    }
    /**
         * Run a series of tests on the BigDate class to ensure it is bug-free.
         * @param args command line arguments, not used.
         */
    public static void main (String [] args)
    {



        demoDate();

        pause();

        show("NULL date = ", 0,0,0);
        show("Jan 01, 999999BC = ", -999999,1,1);
        show("Jan 01, 04BC = ", -4,1,1);
        show("Dec 31, 01BC = ", -1,12,31);
        show("Jan 01, 01AD = ", 1,1,1);
        show("Oct 04, 1582 = ", 1582,10,4);
        show("Oct 15, 1582 = ", 1582,10,15);
        show("Jan 01, 1860 = ", 1860,1,1);
        show("Dec 31  1969 = ", 1969,12,31);
        show("Jan 01, 1970 = ", 1970,1,1);
        show("Jan 02, 1970 = ", 1970,1,2);
        show("Jun 18, 1998 = ", 1998,6,18);
        show("Dec 31, 999999AD = ", 999999,12,31);

        pause();

        testAge(new BigDate(1990,01,01),     new BigDate(2006,12,31),   10, true);
        testAge(new BigDate(-999999,01,01),  new BigDate(999999,12,31), 1000000, false);
        testAge(new BigDate(1900,01,01),     new BigDate(2050,12,31),   1000000, false);
        testISOWeekNumber(1600,+9999);


        pause();


        testDate(-2100,+2100);
        testDate(-999999,-999000);
        testDate(+999000,+999999);
        testDate(-99999,+99999);

        pause();

    } // end main
    /**
         * Pauses the display until the user hits enter.
         */
    protected static void pause()
    {
        // This routine won't work if the AWT is active.
        // Hitting the space bar will not work.
        // Disable for single step tracing
        if ( false ) return;
        System.out.println ("Hit Enter to continue");
        try
        {
            System.in.read(); // wait for user to hit key
        }
        catch ( IOException e )
        {
            System.out.println ("keyboard failed");
        }
    } // end pause
    /**
         * Display a date and its ordinal on System.out.
         * @param desc a description of the date to label it.
         * @param yyyy the year to display.
         * @param mm the month to display.
         * @param dd the day of the month to display.
         */
    public static void show(String desc, int yyyy, int mm, int dd)
    {
        System.out.println(desc + BigDate.toOrdinal(yyyy,mm,dd));
    }
    /**
         * Test that the age routine generates plausible results.
         * @param fromDate earliest date to test.
         * @param toDate last date to test.
         * @param samples how many random pairs to test.
         * @param showDetails true if want details of individual tests
         */
    public static void testAge(BigDate fromDate, BigDate toDate, int samples, boolean showDetails)
    {
        // test with a random sampling of date pairs.

        System.out.println("Testing age from: "
                           + fromDate.toString()
                           + " to: "
                           + toDate.toString()
                           + " using " + samples + " random samples.");

        Random wheel = new Random();
        BigDate birth = new BigDate();
        BigDate asof = new BigDate();
        // date after which our approximations hold.
        int plausibleYear = BigDate.isBritish ? 1752 : 1582;

        // calculate transform to take result of Random.nextInt into our range.
        int base = fromDate.getOrdinal();
        int divisor = toDate.getOrdinal() - base + 1;
        if ( divisor < 2 ) divisor = 2;

        // Any difference 4 or less is reasonable because of the way months have
        // unequal lengths. Only flag really bad cases. There should not be any.
        int worstApprox = 4;
        for ( int i=0; i < samples; i++ )
        {
            // Generate a pair of random dates in range. Might not be in order.
            int ord1 = (wheel.nextInt() & Integer.MAX_VALUE) % divisor + base;
            int ord2 = (wheel.nextInt() & Integer.MAX_VALUE) % divisor + base;
            // put them in order
            if ( ord1 > ord2 )
            {
                int temp = ord1;
                ord1 = ord2;
                ord2 = temp;
            }
            birth.set(ord1);
            asof.set(ord2);

            int[] age = BigDate.age( birth, asof );

            if ( showDetails )
            {
                System.out.print("birth: " + birth.toString() + " ");
                System.out.print("as of: " + asof.toString() + " ");
                System.out.println( age[0] + " years and "
                                    + age[1] + " months and "
                                    + age[2] + " days old.");
            }


            if ( age[0] < 0 ) fail ("Negative age in years");
            if ( age[1] < 0 ) fail ("Negative age in months");
            if ( age[2] < 0 ) fail ("Negative age in days");

            if ( age[1] > 11 ) fail ("Age > 11 months");
            if ( age[2] > 31 ) fail ("Age > 31 days");
            if ( age[0] > 0 && age[1] > 0 && age[2] > 0
                 && birth.getYYYY() > plausibleYear )
            {
                // plausibility test, approximation works after Gregorian instituted.
                int roughAgeInDays = (int)Math.round(
                                                    age[0] * 365.2425d
                                                    + age[1] * 30.436875d
                                                    + age[2]);
                int exactAgeInDays = asof.getOrdinal() - birth.getOrdinal();
                int approx = Math.abs(roughAgeInDays - exactAgeInDays);
                if ( approx > worstApprox )
                {
                    worstApprox = approx;
                    System.out.print("birth: " + birth.toString() + " ");
                    System.out.print("as of: " + asof.toString() + " ");
                    System.out.println( age[0] + " years and "
                                        + age[1] + " months and "
                                        + age[2] + " days old. Differs from approx by "
                                        + approx + ".");

                } // end if got a new worst approximation
            }// end if plausibility test

        } // end for
    } // end testAge
    /**
          * Check for bugs in toOrdinal and toGregorian.
          * @param fromYear the first year to test.
          * @param toYear the last year to test.
          */
    public static void testDate(int fromYear, int toYear)
    {
        int gotOrdinal, expectedOrdinal;
        BigDate gotGregorian, expectedGregorian;
        int gotYear, expectedYear;
        int gotMonth, expectedMonth;
        int gotDay, expectedDay;

        System.out.println("Testing toOrdinal and toGregorian " +
                           fromYear + " .. " + toYear);

        System.out.println("This could take a while...");
        try
        {
            expectedOrdinal = BigDate.toOrdinal(fromYear,1,1);

            for ( expectedYear  = fromYear; expectedYear <= toYear; expectedYear++ )
            {
                if ( expectedYear % 100 == 0 ) System.out.println("reached "+expectedYear);

                for ( expectedMonth = 1; expectedMonth <= 12; expectedMonth++ )
                {
                    for ( expectedDay   = 1; expectedDay <= 31; expectedDay++ )
                    {

                        if ( BigDate.isValid(expectedYear, expectedMonth, expectedDay) )
                        {

                            // test toOrdinal
                            gotOrdinal = BigDate.toOrdinal(expectedYear,expectedMonth,expectedDay);
                            if ( gotOrdinal != expectedOrdinal )
                            {
                                fail("toOrdinal oops " +
                                     " expected: " +
                                     " YYYY:" + expectedYear + " MM:" + expectedMonth +
                                     " DD:" + expectedDay +
                                     " JJJJ:" + expectedOrdinal +
                                     " got: " +
                                     " JJJJ:"  + gotOrdinal);
                            } // end if

                            // test toGregorian
                            gotGregorian = new BigDate(expectedOrdinal);
                            gotYear = gotGregorian.getYYYY();
                            gotMonth = gotGregorian.getMM();
                            gotDay = gotGregorian.getDD();

                            if ( (gotYear != expectedYear) || (gotMonth != expectedMonth)
                                 || (gotDay != expectedDay) )
                            {
                                fail("toGregorian failed" +
                                     " expected: " +
                                     " JJJJ:"  + expectedOrdinal +
                                     " YYYY:"  + expectedYear   +
                                     " MM:"    + expectedMonth  +
                                     " DD:"    + expectedDay    +
                                     " got: "  +
                                     " YYYY:"  + gotYear        +
                                     " MM:"    + gotMonth       +
                                     " DD:"    + gotDay);
                            }  // end if

                            // increment only for valid dates
                            expectedOrdinal = gotOrdinal + 1;
                        } // end if isValid

                    }
                }
            } // all three for loops
        } // end try
        catch ( IllegalArgumentException e )
        {
            fail("test failed " + e.getMessage());
        }
        System.out.println("BigDate toOrdinal and toGregorian test completed successfully");

    } // end testDate
    /**
         * Test getISOWeekNumber and getISODayOfWeek.
         * @param fromYear first year to test.
         * @param toYear last year to test.
         */
    public static void testISOWeekNumber(int fromYear, int toYear)
    {

        BigDate start = new BigDate(fromYear,01,01);
        BigDate stop = new BigDate(toYear,12,31);
        System.out.println("Testing getISOWeekNumber and getISODayOfWeek " + start.toString() + " .. " + stop.toString());

        BigDate b = new BigDate(start);
        int expectedWeekNumber = b.getISOWeekNumber();
        int expectedDayOfWeek = b.getISODayOfWeek();

        for ( int i=start.getOrdinal(); i< stop.getOrdinal(); i++ )
        {
            b.setOrdinal(i);
            int weekNumber = b.getISOWeekNumber();
            int dayOfWeek = b.getISODayOfWeek();
            if ( expectedWeekNumber != weekNumber )
            {
                if ( expectedWeekNumber >= 53 && weekNumber == 1 ) expectedWeekNumber=1;
                else fail("getISOWeekNumber bug at " + b.toString() );
            }
            if ( expectedDayOfWeek != expectedDayOfWeek ) fail("getISoDayOfWeek bug at " + b.toString() );
            expectedDayOfWeek = dayOfWeek + 1;
            if ( expectedDayOfWeek == 8 )
            {
                expectedDayOfWeek = 1;
                expectedWeekNumber++ ;
            }
        }
    } // end testIOSWeekNumber
    /**
         * Test Normalize by displaying how an invalid date is converted.
         * @param yyyy the year to test, may be negative or bigger than 4 digits.
         * @param mm the month to test.
         * @param dd the day of the month to test.
         */
    public static void testNormalize(int yyyy,  int mm, int dd)
    {

        BigDate g = new BigDate(yyyy, mm, dd, BigDate.NORMALIZE);

        System.out.println( " input: "       +
                            " YYYY:"  + yyyy +
                            " MM:"    + mm   +
                            " DD:"    + dd);
        System.out.println( "output: "      +
                            " YYYY:"  + g.getYYYY()   +
                            " MM:"    + g.getMM()     +
                            " DD:"    + g.getDD()     +
                            " ORD:"   + g.getOrdinal() +
                            " DOW:"   + g.getDayOfWeek() +
                            " DDD:"   + g.getDDD()    +
                            " TIMESTAMP:" + g.getUTCTimeStamp());

    } // end testNormalize
}