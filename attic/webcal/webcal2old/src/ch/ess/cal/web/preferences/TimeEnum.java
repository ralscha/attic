package ch.ess.cal.web.preferences;

import java.util.*;

import org.apache.commons.lang.enum.*;

 public final class TimeEnum extends Enum {
  
   public static final TimeEnum MINUTE = new TimeEnum("minute");
   public static final TimeEnum HOUR = new TimeEnum("hour");
   public static final TimeEnum DAY = new TimeEnum("day");
   public static final TimeEnum WEEK = new TimeEnum("week");
   
   private TimeEnum(String attributeName) {
     super(attributeName);
   }
 
   public static TimeEnum getEnum(String attributName) {
     return (TimeEnum)getEnum(TimeEnum.class, attributName);
   }
 
   public static Map getEnumMap() {
     return getEnumMap(TimeEnum.class);
   }
 
   public static List getEnumList() {
     return getEnumList(TimeEnum.class);     
   }
 
   public static Iterator iterator() {
     return iterator(TimeEnum.class);
   }
 }
