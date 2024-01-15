package lottowin.resource;

import java.util.*;
import java.io.*;
import java.text.*;
import com.objectmatter.bsf.*;
import org.log4j.*;

public final class AppConfig extends Properties {

  private static Category CAT = Category.getInstance(AppConfig.class.getName());

  private String schema;
  private Database db;

	private static AppConfig instance;

	private AppConfig() {
    schema = "lottowin.schema";
    db = new Database("lottowin.schema");
    CAT.debug("constructor");
	}

	public static void setSchema(String schema) {
		getInstance().schema = schema;
    getInstance().db = new Database(schema);
    
    if (CAT.isDebugEnabled())
      CAT.debug("setSchema : " + schema);
	}



  public static com.objectmatter.bsf.Database getDatabase() {
    try {
      if (getInstance().db.isClosed()) {
        CAT.debug("database is closed, create new");
      	getInstance().db = null;
      	getInstance().db = new Database(getInstance().schema);
        getInstance().db.open();
      }

      return getInstance().db;
    } catch (BODBException e) {
      dbMsg(e);
      return null;
    } catch (Exception e) {
      CAT.error("getDatabase", e);
      return null;
    }
  }

  public static void closeDatabase() {
    getInstance().db.close();
  }

	public static AppConfig getInstance() {
		if (instance == null) {
			synchronized (AppConfig.class) {
				if (instance == null)
					instance = new AppConfig();
			}
		}
		return instance;
	}
	
  public static void dbMsg(BODBException dbEx) {
    CAT.error("Database exception(s) thrown:");
    BODBException exHolder = dbEx ;
    while (exHolder != null) {
      CAT.error(exHolder.getCode() + "  " + exHolder.getMessage(), exHolder);
      exHolder = dbEx.getNext();
      dbEx = exHolder ;
    }
  }

}