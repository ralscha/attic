package ch.ess.common.db;

import java.sql.Types;

import net.sf.hibernate.cfg.Environment;
import net.sf.hibernate.dialect.Dialect;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.4 $ $Date: 2003/11/11 19:07:56 $ 
  */
public class MSSql2000UnicodeDialect extends Dialect {

  public MSSql2000UnicodeDialect() {

    registerColumnType(Types.BIT, "BIT");
    registerColumnType(Types.BIGINT, "BIGINT");
    registerColumnType(Types.SMALLINT, "SMALLINT");
    registerColumnType(Types.TINYINT, "TINYINT");
    registerColumnType(Types.INTEGER, "INT");
    registerColumnType(Types.CHAR, "NCHAR(1)");
    registerColumnType(Types.VARCHAR, "NVARCHAR($l)");
    registerColumnType(Types.FLOAT, "REAL");
    registerColumnType(Types.DOUBLE, "FLOAT");
    registerColumnType(Types.DATE, "DATETIME");
    registerColumnType(Types.TIME, "DATETIME");
    registerColumnType(Types.TIMESTAMP, "DATETIME");
    registerColumnType(Types.VARBINARY, "VARBINARY($l)");
    registerColumnType(Types.NUMERIC, "DECIMAL(19,$l)");
    registerColumnType(Types.BLOB, "IMAGE");
    registerColumnType(Types.CLOB, "NTEXT");

    getDefaultProperties().setProperty(Environment.MAX_FETCH_DEPTH, "1");
    getDefaultProperties().setProperty(Environment.STATEMENT_BATCH_SIZE, "15");
    //getDefaultProperties().setProperty(Environment.STATEMENT_BATCH_SIZE, NO_BATCH);    
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

  public char closeQuote() {
    return ']';
  }

  public char openQuote() {
    return '[';
  }

}
