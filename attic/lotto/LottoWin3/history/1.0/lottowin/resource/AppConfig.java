package lottowin.resource;

import java.util.*;
import java.io.*;
import java.text.*;
import org.log4j.*;
import ch.ess.util.pool.*;
import com.objectmatter.bsf.*;

public final class AppConfig extends Properties {
    
  static {  
    //Debug.setDebugging(Debugger.PERSISTENCE + Debugger.DATABASE, false, false);
  }

  private static Category CAT = Category.getInstance(AppConfig.class.getName());

  private String schema;

	private static AppConfig instance;

	private AppConfig() {
    schema = "lottowin.schema";
    PoolManager.initPool(schema);
    CAT.debug("constructor");
	}

	public static void setSchema(String schema) {
		getInstance().schema = schema;    
    
    if (CAT.isDebugEnabled())
      CAT.debug("setSchema : " + schema);
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
	


}