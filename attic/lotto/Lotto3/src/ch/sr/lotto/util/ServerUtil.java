package ch.sr.lotto.util;

import java.io.*;

import com.objectmatter.bsf.*;
import com.objectmatter.bsf.mapping.repository.*;
import com.objectmatter.bsf.mapping.schema.*;

public class ServerUtil {

  public static Server createServer() 
         throws IOException, ClassNotFoundException {
     
    String schemaName = AppProperties.getStringProperty("vbsf.schema");
    String dbDriver = AppProperties.getStringProperty("db.driver");
    String dbUrl = AppProperties.getStringProperty("db.url");
    String user = AppProperties.getStringProperty("db.user");            
    String password = AppProperties.getStringProperty("db.password");                
         
    //load schema directly from repository directory
    AppSchema sch = (AppSchema) Repository.read(schemaName);

    //update URL, username & password
    if (dbUrl != null) {
      DBConfiguration dbConfig = sch.getDBConfiguration(); //get the default DBconfig
      dbConfig.setDBDriver(dbDriver);
      dbConfig.setDBURL(dbUrl);
      dbConfig.setLoginName(user);
      dbConfig.setLoginPassword(password);

      return new Server(schemaName, dbConfig);
    } else {
      return new Server(schemaName);
    }

  }
}