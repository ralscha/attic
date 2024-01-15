
package ch.ess.cal.vcal;

public interface VCalendarConstants {

  public static final String VCAL_VERSION = "1.0";
  public static final String VCAL_PRODUCTIDENTIFIER = "-//ESS Development AG//WebCal//EN";
  public static final char[] NONSEMI = { ';', '\r', '\n' };
  public static final char[] WORD = { '[', ']', '=', ':', '.', ',' };

  public static final String BEGIN = "BEGIN";
  public static final String END = "END";
  public static final String VCALENDAR = "VCALENDAR";
  public static final String VTODO = "VTODO";
  public static final String VEVENT = "VEVENT";


  public static final String EXTENSION = "X-";

  public static final String CAL_DAYLIGHT = "DAYLIGHT";
  public static final String CAL_GEO = "GEO";
  public static final String CAL_PRODID = "PRODID";
  public static final String CAL_TZ = "TZ";
  public static final String CAL_VERSION = "VERSION";

  public static final String PARAM_TYPE = "TYPE";
  public static final String PARAM_VALUE = "VALUE";
  public static final String PARAM_ENCODING = "ENCODING";
  public static final String PARAM_CHARSET = "CHARSET";
  public static final String PARAM_LANGUAGE = "LANGUAGE";
  public static final String PARAM_ROLE = "ROLE";
  public static final String PARAM_RSVP = "RSVP";
  public static final String PARAM_EXPECT = "EXPECT";
  public static final String PARAM_STATUS = "STATUS";
  public static final String PARAM_X = EXTENSION;

  public static final String FIELD_STATUS = "STATUS"; // required
  public static final String FIELD_COMPLETED = "COMPLETED"; // required
  public static final String FIELD_DESCRIPTION = "DESCRIPTION"; // required
  public static final String FIELD_DTSTART = "DTSTART"; // required
  public static final String FIELD_DTEND = "DTEND"; // required
  public static final String FIELD_DUE = "DUE"; // required

  public static final String FIELD_DCREATED = "DCREATED";
  public static final String FIELD_LASTMODIFIED = "LAST-MODIFIED";
  public static final String FIELD_EXRULE = "EXRULE";

  public static final String FIELD_CLASS = "CLASS";
  public static final String FIELD_LOCATION = "LOCATION";
  public static final String FIELD_RNUM = "RNUM";
  public static final String FIELD_PRIORITY = "PRIORITY";
  public static final String FIELD_RELATEDTO = "RELATED-TO";
  public static final String FIELD_RRULE = "RRULE";
  public static final String FIELD_SEQUENCE = "SEQUENCE";
  public static final String FIELD_SUMMARY = "SUMMARY";
  public static final String FIELD_TRANSP = "TRANSP";
  public static final String FIELD_URL = "URL";
  public static final String FIELD_UID = "UID";
  public static final String FIELD_X = EXTENSION;

  //// These are list values
  public static final String FIELD_CATEGORIES = "CATEGORIES"; // required

  public static final String FIELD_AALARM = "AALARM";
  public static final String FIELD_DALARM = "DALARM";
  public static final String FIELD_MALARM = "MALARM";
  public static final String FIELD_PALARM = "PALARM";
  public static final String FIELD_EXDATE = "EXDATE";
  public static final String FIELD_RDATE = "RDATE";

  public static final String FIELD_ATTACH = "ATTACH";
  public static final String FIELD_RESOURCES = "RESOURCES";
  public static final String FIELD_ATTENDEE = "ATTENDEE";
  
  public static final String CLASS_PUBLIC = "PUBLIC";
  public static final String CLASS_PRIVATE = "PRIVATE";
  public static final String CLASS_CONFIDENTAIL = "CONFIDENTIAL";
  


} 