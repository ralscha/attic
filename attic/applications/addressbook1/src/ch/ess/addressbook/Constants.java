package ch.ess.addressbook;

import java.text.*;
import java.util.*;

import ch.ess.util.*;


public final class Constants {

  public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat2k("dd.MM.yyyy");
  public static final DateFormat PARSE_DATE_FORMAT = new SimpleDateFormat2k("dd.MM.yy");
  
  static {
    Locale.setDefault(new Locale("de", "CH"));
    PARSE_DATE_FORMAT.setLenient(false);
  }
    
}