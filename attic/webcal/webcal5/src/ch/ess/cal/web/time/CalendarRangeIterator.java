package ch.ess.cal.web.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.lang.time.DateFormatUtils;

import ch.ess.base.Constants;
import ch.ess.cal.enums.TimeRangeEnum;


public class CalendarRangeIterator implements Iterator<CalendarRange> {

  private Calendar endFinal;
  private Calendar spot;
  private TimeRangeEnum typ;
  private int firstDayOfWeek;
  private int endDayOfWeek;
  
  public CalendarRangeIterator(final Calendar sf, final Calendar ef, final TimeRangeEnum typ) {
    this(sf, ef, typ, Calendar.MONDAY);
  }
  
  public CalendarRangeIterator(final Calendar sf, final Calendar ef, final TimeRangeEnum typ, final int firstDayOfWeek) {
    this.endFinal = (Calendar)ef.clone();
    this.spot = (Calendar)sf.clone();
    
    if (typ.equals(TimeRangeEnum.DAY)) {
      spot.add(Calendar.DATE, -1);
    }
    
    this.typ = typ;
    this.firstDayOfWeek = firstDayOfWeek;
    
    
    endDayOfWeek = ((firstDayOfWeek + 5) % 7) + 1;
    
    
    if (!(typ.equals(TimeRangeEnum.DAY) ||typ.equals(TimeRangeEnum.WEEK) || typ.equals(TimeRangeEnum.MONTH) || typ.equals(TimeRangeEnum.QUARTER) 
         || typ.equals(TimeRangeEnum.SEMESTER) || typ.equals(TimeRangeEnum.YEAR))) {
      throw new IllegalArgumentException("Typ " + typ.getKey() + " not support");
    }
  }

  public boolean hasNext() {
    return spot.before(endFinal);
  }

  public CalendarRange next() {
    if (!spot.before(endFinal)) {
      throw new NoSuchElementException();
    }

    CalendarRange range = getRange();
    spot = (Calendar)range.getTo().clone();
    
    if (!typ.equals(TimeRangeEnum.DAY)) {
      spot.add(Calendar.DATE, 1);
    }
    
    return range;
  }

  private CalendarRange getRange() {

    Calendar from = (Calendar)spot.clone();
    Calendar to = (Calendar)spot.clone();

    String description = null;
    String internalDescription = null;
    
    boolean end = false;
    
    if (typ.equals(TimeRangeEnum.DAY)) {    
    
      from.add(Calendar.DATE, 1);
      to.add(Calendar.DATE, 1);
      
      DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());
      description = df.format(from.getTime());
      internalDescription = DateFormatUtils.ISO_DATE_FORMAT.format(from.getTime());
    
      if (to.get(Calendar.DAY_OF_WEEK) == endDayOfWeek) {
        end = true;
      }      
      
    } else if (typ.equals(TimeRangeEnum.WEEK)) {
              
      Calendar tmpCal = (Calendar)from.clone();
      tmpCal.setFirstDayOfWeek(firstDayOfWeek);
      tmpCal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);

      from = (Calendar)tmpCal.clone();
      tmpCal.add(Calendar.DATE, +6);
      to = (Calendar)tmpCal.clone();
      
      description = Constants.WEEK_DATE_FORMAT.format(from.getTime());
      internalDescription = DateFormatUtils.ISO_DATE_FORMAT.format(from.getTime());
      
    } else if (typ.equals(TimeRangeEnum.MONTH)) {
      from.set(Calendar.DATE, spot.getActualMinimum(Calendar.DATE));
      to.set(Calendar.DATE, spot.getActualMaximum(Calendar.DATE));      
    
      description = Constants.MONTH_DATE_FORMAT.format(from.getTime());
      internalDescription = DateFormatUtils.ISO_DATE_FORMAT.format(from.getTime());
      
    } else if (typ.equals(TimeRangeEnum.QUARTER)) {
      int month = from.get(Calendar.MONTH);      
      int quartal = month / 3;
      
      int startmonth = quartal * 3;
      int endmonth = quartal * 3 + 2;
      
      from.set(Calendar.DATE, 1);
      from.set(Calendar.MONTH, startmonth);

      to.set(Calendar.DATE, 20);
      to.set(Calendar.MONTH, endmonth);        

      to.set(Calendar.DATE, to.getActualMaximum(Calendar.DATE));
   
      description = (quartal+1) + ".Q " + from.get(Calendar.YEAR);
      internalDescription = DateFormatUtils.ISO_DATE_FORMAT.format(from.getTime());
      
    } else if (typ.equals(TimeRangeEnum.SEMESTER)) {
      
      if (from.get(Calendar.MONTH) <= Calendar.JUNE) {
        from.set(Calendar.DATE, 1);
        from.set(Calendar.MONTH, Calendar.JANUARY);
        to.set(Calendar.DATE, 30);
        to.set(Calendar.MONTH, Calendar.JUNE);
        description = "1.S " + from.get(Calendar.YEAR);
      } else {
        from.set(Calendar.DATE, 1);
        from.set(Calendar.MONTH, Calendar.JULY);
        to.set(Calendar.DATE, 31);
        to.set(Calendar.MONTH, Calendar.DECEMBER);
        description = "2.S " + from.get(Calendar.YEAR);
      }
      
      internalDescription = DateFormatUtils.ISO_DATE_FORMAT.format(from.getTime());
            
    } else if (typ.equals(TimeRangeEnum.YEAR)) {
            
      from.set(Calendar.DATE, 1);
      from.set(Calendar.MONTH, Calendar.JANUARY);
      
            
      to.set(Calendar.DATE, 31);
      to.set(Calendar.MONTH, Calendar.DECEMBER);
                  
      description = Constants.YEAR_DATE_FORMAT.format(from.getTime());
      internalDescription = DateFormatUtils.ISO_DATE_FORMAT.format(from.getTime());
    
    }
    
    
    if (from.before(spot)) {
      from = (Calendar)spot.clone();
    }
    if (to.after(endFinal)) {
      to = (Calendar)endFinal.clone();
    }
    return new CalendarRange(from, to, description, internalDescription, end);
        
  }
  
  public void remove() {
    throw new UnsupportedOperationException();
  }

}