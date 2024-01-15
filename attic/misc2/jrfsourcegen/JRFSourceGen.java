import org.exolab.javasource.*;
import net.sourceforge.osage.attributed.*;
import java.util.*;
import java.sql.*;

public class JRFSourceGen {

  private String outputDir;
  private String packageName;
  private String policy;

  public JRFSourceGen(String outputDir, String packageName, String policy) {
    this.outputDir = outputDir;
    this.packageName = packageName;
    this.policy = policy;
  }

	public void generateSource(String tableName, List colList) {
	  try {
      generatePersistentObject(tableName, colList);
      generateDomainObject(tableName, colList);
	  } catch (Exception e) {
	    System.err.println(e);
	  }	
	}

  public void generatePersistentObject(String tableName, List colList) 
            throws AttributeNotFoundException {
    JClass newClass;
    if ((packageName != null) && (packageName.length() > 0)) {
      newClass = new JClass(packageName + "." + SourceGenHelper.capitalize(tableName));
    } else {
      newClass = new JClass(SourceGenHelper.capitalize(tableName));
    }

    newClass.addImport("com.is.jrf.PersistentObject");
    newClass.getModifiers().makePublic();
    newClass.setSuperClass("PersistentObject");

    for (int i = 0; i < colList.size(); i++) {
      Attributed col = (Attributed)colList.get(i);
      Attr colName = col.find(Constants.COLUMN_NAME);
      Attr type = col.find(Constants.COLUMN_TYPE);

      String varName = SourceGenHelper.uncapitalize(colName.getStringValue());
      JMember member = new JMember(getJClass(type.getShortValue()), 
          "i_"+varName);
      member.getModifiers().makePrivate();
      member.setInitString("null");
      newClass.addMember(member);              

      JMethod meth = new JMethod(getJClass(type.getShortValue()), 
              "get"+SourceGenHelper.capitalize(varName));
      meth.getModifiers().makePublic();
      meth.getSourceCode().add("return i_"+varName+";");
      newClass.addMethod(meth);        

      meth = new JMethod(null, "set"+SourceGenHelper.capitalize(varName));
      meth.addParameter(new JParameter(getJClass(type.getShortValue()), varName));
      meth.getModifiers().makePublic();
      meth.getSourceCode().add("i_"+varName+" = "+varName+";");
      meth.getSourceCode().add("this.markModifiedPersistentState();");
      newClass.addMethod(meth);
    }

    newClass.print(outputDir, null, 2);
  
  }

  public void generateDomainObject(String tableName, List colList) 
            throws AttributeNotFoundException {
    JClass newClass;

    if ((packageName != null) && (packageName.length() > 0)) {
      newClass = new JClass(packageName + "." + SourceGenHelper.capitalize(tableName+"Domain"));
    } else {
      newClass = new JClass(SourceGenHelper.capitalize(tableName+"Domain"));
    }

    newClass.addImport("com.is.jrf.*");
    newClass.addImport("java.util.*");

    newClass.getModifiers().makePublic();
    newClass.setSuperClass("AbstractDomain");

    JMethod meth = new JMethod(null, "setup");
    meth.getModifiers().makePublic();
    JSourceCode sc = new JSourceCode();

    sc.add("this.setDatabasePolicy(new "+getPolicy()+"());");
    sc.add("this.setJDBCHelper(JDBCHelperFactory.create());");
    sc.add("");
    sc.add("this.setTableName(\""+SourceGenHelper.capitalize(tableName)+"\");");
    sc.add("");


    for (int i = 0; i < colList.size(); i++) {
      Attributed col = (Attributed)colList.get(i);
      Attr colName = col.find(Constants.COLUMN_NAME);
      Attr type = col.find(Constants.COLUMN_TYPE);
      Attr nullable = col.find(Constants.NULLABLE);
      Attr key = col.find(Constants.PRIMARY_KEY);

      String columnSpec = getColumnSpec(type.getShortValue());
      if (columnSpec != null) {
        sc.add("this.addColumnSpec(");
        sc.add("new "+columnSpec+"(", (short)2);
        sc.add("\""+colName.getStringValue()+"\",", (short)4);
        sc.add("\"get"+SourceGenHelper.capitalize(colName.getStringValue())+"\",", (short)4);
        sc.add("\"set"+SourceGenHelper.capitalize(colName.getStringValue())+"\",", (short)4);
        sc.add(getDefaultValue(type.getShortValue(), nullable.getBooleanValue())+",", (short)4);
        
        if (key.getBooleanValue()) {
          sc.add("???_PRIMARY_KEY));", (short)4);
        } else {
          if (!nullable.getBooleanValue()) {
            sc.add("REQUIRED));", (short)4);
          }
        }
      }
    }
    meth.setSourceCode(sc);
    newClass.addMethod(meth);

    meth = new JMethod(new JClass("PersistentObject"), "newPersistentObject");
    meth.getModifiers().makePublic();
    meth.getSourceCode().add("return new "+SourceGenHelper.capitalize(tableName)+"();");
    newClass.addMethod(meth);

    newClass.print(outputDir, null, 2);
  
  }


  private String getPolicy() {
    if (policy != null) {
      if ("hypersonic".equalsIgnoreCase(policy)) {
        return ("HypersonicDatabasePolicy");
      } else if ("instantdb".equalsIgnoreCase(policy)) {
        return ("InstantDBDatabasePolicy");
      } else if ("oracle".equalsIgnoreCase(policy)) {
        return ("OracleDatabasePolicy");
      } else if ("sqlserver".equalsIgnoreCase(policy)) {
        return ("SQLServerSybaseDatabasePolicy");
      } else if ("mysql".equalsIgnoreCase(policy)) {
        return ("MySQLDatabasePolicy");
      }
    }

    return ("????DatabasePolicy");

  }

	private JClass getJClass(short type) {
		switch (type) {

	    case -9:
	    case -10:
			case Types.LONGVARCHAR:
			case Types.CHAR:
			case Types.VARCHAR:
				return JClass.String;

			case Types.BINARY :
			case Types.VARBINARY :
			case Types.LONGVARBINARY :
				return new JClass("Byte[]");

			case Types.NUMERIC:
			case Types.DECIMAL:
				return JClass.BigDecimal;

			case Types.BIT:
				return JClass.Boolean;

			case Types.TINYINT:
				return JClass.Byte;

			case Types.SMALLINT:
				return JClass.Short;

			case Types.INTEGER:
				return JClass.Integer;

			case Types.BIGINT:
				return JClass.Long;

			case Types.REAL:
				return JClass.Float;

			case Types.FLOAT:
			case Types.DOUBLE:
				return JClass.Double;

			case Types.DATE:
				return new JClass("java.sql.Date");

			case Types.TIME:
				return new JClass("java.sql.Time");

			case Types.TIMESTAMP:
				return new JClass("java.sql.Timestamp");

			case Types.OTHER:
				return JClass.Object;

		}
		return null;
	}

  private String getDefaultValue(short type, boolean nullable) {
	  switch (type) {

	    case -9:
	    case -10:
			case Types.LONGVARCHAR:
			case Types.CHAR:
			case Types.VARCHAR:
        if (nullable)
				  return "DEFAULT_TO_NULL";
        else
          return "DEFAULT_TO_EMPTY_STRING";

			case Types.INTEGER:
        if (nullable)
				  return "DEFAULT_TO_NULL";
        else
          return "DEFAULT_TO_ZERO";

			case Types.TIMESTAMP:
        if (nullable)
				  return "DEFAULT_TO_NULL";
        else
          return "DEFAULT_TO_NOW";

		}
		return null;  
  }

	private String getColumnSpec(short type) {
		switch (type) {

	    case -9:
	    case -10:
			case Types.LONGVARCHAR:
			case Types.CHAR:
			case Types.VARCHAR:
				return "StringColumnSpec";

			case Types.INTEGER:
				return "IntegerColumnSpec";

			case Types.TIMESTAMP:
				return "TimestampColumnSpec";

		}
		return null;
	}


  public static void main(String[] args) {
    try {
      if (args.length == 1) {
        ConfigReader rc = new ConfigReader();
        rc.read(args[0]);
        DatabaseConfig dc = rc.getDatabaseConfig();
        MetaDataReader meta = new MetaDataReader(dc);
        JRFSourceGen jrfsg = new JRFSourceGen(dc.getOutputDir(), dc.getPackageName(), dc.getPolicy());

        List tablesList = meta.getTables();
        for (int i = 0; i < tablesList.size(); i++) {
          String tableName = (String)tablesList.get(i);
          List columnList = meta.getColumnData(tableName);
          jrfsg.generateSource(tableName, columnList);
        }
      } else {
        System.err.println("java JRFSourceGen <configFile>");
      }
    } catch (Exception e) {
      System.err.println(e);
    }
  }
	
}