package common.util;

import java.util.*;
import java.text.*;

/**
* This Calendar provides Julian Day and "Calendar Day" methods,
* The Calendar Day starts at _local_midnight.
*
* @author Paul A. Hill
* @version 1.1
* @since JDK 1.1.7 3/99
* @see java.util.Calendar
*/


public class JulianCalendar extends GregorianCalendar implements Cloneable {
	/**
	 ** this constant and the ones that follow are actually private
	 ** in the GregorianCalendar, so we'll redefine them here.
	 **/

	public static final long ONE_SECOND = 1000, ONE_MINUTE = ONE_SECOND * 60, ONE_HOUR = ONE_MINUTE * 60;

	/**
	* this next constant and the others with it have their uses,
	* but watch out for DST days and the weeks	that contain them.
	* Note also that 1/1/1970 doesn't start on the first day of the week.
	*/
	protected static final long ONE_DAY = ONE_HOUR * 24, ONE_WEEK = ONE_DAY * 7;

	/** The number of days from Jan 1, 4713 BC (Proleptic Julian)
	** to 1/1/1970 AD (Gregorian).  1/1/1970 is	time 0 for a java.util.Date.
	**/
	public static final long JULIAN_DAY_OFFSET = 2440588L;

	/**
	* Same as GregorianCalendar(). Creates a calendar with the time set to
	* the moment it was created.
	*	Note: for Brevety I have not provided all of the other
	* constructors that you can find in GregorianCalendar.
	*
	* @see java.util.GregorianCalendar#GregorianCalendar
	*/
	public JulianCalendar() {
		super();
	}

	public JulianCalendar(int year, int month, int date) {
		super(year, month, date);
	}

	/**
	 * A calendar day as defined here, is like the Julian Day but it starts
	 * and ends at _local_ 12 midnight,	 as defined by the timezone which is
	 * attached to this calendar.  This value is useful for comparing
	 *	 any date in the calendar to any other date to determine how many days
	 * between them, i.e. tomorrow - today = 1
	 *
	 * @return the calendar day (see above).
	 * @see #getCalendarDay
	 */
	public long getCalendarDay() {
		TimeZone tz = getTimeZone();
		// Figure the exact offset for the exact time of day.
		int offset = tz.getOffset(get(ERA), get(YEAR), get(MONTH), get(DAY_OF_MONTH),
                          		get(DAY_OF_WEEK),
                          		(int)((long) get(HOUR_OF_DAY) * ONE_HOUR + get(MINUTE) * ONE_MINUTE +
                                		get(SECOND) * ONE_SECOND));
		return round(ONE_DAY, getTime().getTime() + offset) + JULIAN_DAY_OFFSET;
	}

	/**
	 * Sets the date in the calendar to 00:00 (midnight) on the local calendar day.
	 * See getCalendarDay for the definition of calendar day as used in this class.
	 *
	 * @param calendarDay the day to set the calendar to.
	 * @see #setCalendarDay
	 * @see java.util.TimeZone#getRawOffset
	 */
	public void setCalendarDay(long calendarDay) {
		// Set to the beginning of the Julian day.
		// Then add in the difference to make it 00:00 local time.
		setJulianDay(calendarDay);
		setTimeInMillis(getTime().getTime() - getTimeZone().getRawOffset());
		// we may have gone slightly too far, because we used the
		// raw offset (diff between Standard time to GMT/UT, instead of the
		// actual value for this day, so during DLS we may be at 1 AM or whatever
		// the local DLS offset is), so we'll	just drop back to midnight. 
		set(HOUR_OF_DAY, 0);
	}

	/**
	 * Finds the number of days after 12/31/4312 BC 24:00 GMT on a proleptic
	 * Julian Calendar (i.e. extending the Julian Calendar into pre-history)
	 * to the current time.
	 * The Astronomers Julian Day begins at noon.  The Julian Day used here
	 * sometimes called the Chronologists or Historians Julian Day
	 * starts at midnight.  For more information see
	 * http://www.magnet.ch/serendipity/hermetic/cal_stud/jdn.htm#astronomical
	 * Note: This routine does NOT take into consideration
	 * leap seconds.
	 *
	 * @return the day number of the current time from 1/
	 * @see #getCalendarDay
	 */
	public long getJulianDay() {
		return round(ONE_DAY, getTime().getTime()) + JULIAN_DAY_OFFSET;
	}

	/**
	 * Sets the current date contained in this calendar to exactly
	 * 00:00 GMT on the date defined by the Julian Day provided.
	 *
	 * @param julianDay the Julian Day to set the calendar to
	 * @see #setCalendarDay
	 */
	public void setJulianDay(long julianDay) {
		setTimeInMillis((julianDay - JULIAN_DAY_OFFSET) * ONE_DAY);
	}

	/**
	  * This is a utility routine for rounding (toward negative) to the nearest
	  * multiple of the conversion factor.
	  *  BUG? Why is this different than the formula given in
	  * java.util.GregorianCalendar private millisToJulianDay?
	  *
	  * @param conversion typically one of the constants defined
	  * above ONE_MINUTE, ONE_DAY etc.
	  * @param fractions the value to convert expressed in the same units
	  * as the conversion factor (i.e milliseconds).
	  *
	  * @return the value divided by the conversion factor, rounded to the negative.
	  * @see java.util.Calendar
	  */
	protected static final long round(long conversion, long fractions) {
		long wholeUnits;

		// round toward negative:
		// For secs rounded to minutes -61/60=-1, -60/60=-1, -59/60=0,
		//      but we want -2, -1, -1 not -1,-1,0
		// or month 0..11 => year 1; -12..-1 => 0; -24..-13 => -1

		if (fractions >= 0) {
			wholeUnits = fractions / conversion;
		} else {
			wholeUnits = (fractions + 1) / conversion - 1;
		}
		return wholeUnits;
	}
	
}