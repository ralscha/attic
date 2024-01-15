package ch.ess.cal.common;

import java.sql.*;

import net.sf.hibernate.cfg.*;
import net.sf.hibernate.dialect.*;

public class MSSQL2000Dialect extends Dialect {


  public MSSQL2000Dialect() {
    super();
    register( Types.BIT, "BIT" ); 
    register( Types.BIGINT, "BIGINT" );
    register( Types.SMALLINT, "SMALLINT" );
    register( Types.TINYINT, "TINYINT" );
    register( Types.INTEGER, "INT" );
    register( Types.CHAR, "CHAR(1)" );
    register( Types.VARCHAR, "VARCHAR($l)" );
    register( Types.FLOAT, "REAL" );
    register( Types.DOUBLE, "FLOAT" );
    register( Types.DATE, "DATETIME" );
    register( Types.TIME, "DATETIME" );
    register( Types.TIMESTAMP, "DATETIME" );
    register( Types.VARBINARY, "VARBINARY($l)" );
    register( Types.NUMERIC, "DECIMAL(19,$l)" );
    register( Types.BLOB, "IMAGE" );
    register( Types.CLOB, "TEXT" );
    
    getDefaultProperties().setProperty(Environment.USE_OUTER_JOIN, "true");
    getDefaultProperties().setProperty(Environment.STATEMENT_BATCH_SIZE, "15");
        
  }
  
  public String getAddColumnString() {
    return "add";
  }
  public String getNullColumnString() {
    return " null";
  }
  public boolean qualifyIndexName() {
    return false;
  }
  
  public boolean supportsForUpdate() {
    return true;
  }
  
  public boolean supportsIdentityColumns() {
    return true;
  }
  public String getIdentitySelectString() {
    return "select @@identity";
  }
  public String getIdentityColumnString() {
    return "IDENTITY NOT NULL";
  }
  
  public String getNoColumnsInsertString() {
    return "DEFAULT VALUES";
  }
  
}


