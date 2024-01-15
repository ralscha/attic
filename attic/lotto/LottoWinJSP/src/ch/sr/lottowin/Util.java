package ch.sr.lottowin;

import org.apache.log4j.*;

import com.objectmatter.bsf.*;

public class Util {

  public static void dbMsg(Category cat, BODBException dbEx) {
    cat.error("Database exception(s) thrown:");
    BODBException exHolder = dbEx ;
    while (exHolder != null) {
      cat.error(exHolder.getCode() + "  " + exHolder.getMessage(), exHolder);
      exHolder = dbEx.getNext();
      dbEx = exHolder ;
    }
  }

}