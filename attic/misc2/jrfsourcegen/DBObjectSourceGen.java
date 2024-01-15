import org.exolab.javasource.*;
import net.sourceforge.osage.attributed.*;
import java.util.*;
import java.sql.*;

public class DBObjectSourceGen {

  private String outputDir;
  private String packageName;

  public DBObjectSourceGen(String outputDir, String packageName) {
    this.outputDir = outputDir;
    this.packageName = packageName;
  }

	public void generateSource(String tableName, List colList) throws AttributeNotFoundException {
    JClass newClass;
    if ((packageName != null) && (packageName.length() > 0)) {
      newClass = new JClass(packageName + "." + SourceGenHelper.capitalize(tableName));
    } else {
      newClass = new JClass(SourceGenHelper.capitalize(tableName));
    }

    newClass.addImport("org.gjt.tw.dbobjects.*");
    newClass.addImport("java.util.*");
    newClass.getModifiers().makePublic();
    newClass.setSuperClass("StorableObject");    

    JMember member = new JMember(new JClass("ObjectMapping"), "mapping");
    member.getModifiers().makePrivate();
    member.getModifiers().setStatic(true);
    newClass.addMember(member);


    JSourceCode sc = new JSourceCode();    
    JSourceCode initsc = new JSourceCode();
    sc.add("super();");

    initsc.add("mapping = new ObjectMapping();");
    initsc.add("mapping.setTableName(\""+tableName+"\");");
    initsc.add("mapping.setObjectClass("+SourceGenHelper.capitalize(tableName)+".class);");

    for (int i = 0; i < colList.size(); i++) {
      Attributed col = (Attributed)colList.get(i);
      Attr colName = col.find(Constants.COLUMN_NAME);
      Attr type = col.find(Constants.COLUMN_TYPE);
      Attr key = col.find(Constants.PRIMARY_KEY);

      String varName = SourceGenHelper.uncapitalize(colName.getStringValue());
      member = new JMember(getJClass(type.getShortValue()), varName);
      member.getModifiers().makePrivate();
      newClass.addMember(member);              

      sc.add(varName + " = " + getDefaultValue(type.getShortValue()) + ";");

      JMethod meth = new JMethod(getJClass(type.getShortValue()), 
              "get"+SourceGenHelper.capitalize(varName));
      meth.getModifiers().makePublic();
      meth.getSourceCode().add("return "+varName+";");
      newClass.addMethod(meth);        

      meth = new JMethod(null, "set"+SourceGenHelper.capitalize(varName));
      meth.addParameter(new JParameter(getJClass(type.getShortValue()), varName));
      meth.getModifiers().makePublic();
      meth.getSourceCode().add("this."+varName+" = "+varName+";");
      newClass.addMethod(meth);


      //init source
      initsc.add("mapping.addField(\""+varName+"\", "+getJavaClass(type.getShortValue())
                    +", \""+varName+"\");");

      if (key.getBooleanValue())
        initsc.add("mapping.setKeyField(\""+varName+"\");");
    }

    
    initsc.add("mapping.useMySQLAutoIncrement(true);");
    initsc.add("mapping.prepareSQLStatements();");

    JConstructor constructor = new JConstructor(newClass);
    constructor.setSourceCode(sc);
    newClass.addConstructor(constructor);

    JMethod meth = new JMethod(new JClass("ObjectMapping"), "getMapping");
    meth.getModifiers().makeProtected();
    sc = new JSourceCode();
    sc.add("if (mapping == null) {");
    sc.add("mapping = new ObjectMapping();", (short)2);
    sc.add("}");
    sc.add("return mapping;");
    meth.setSourceCode(sc);
    newClass.addMethod(meth);         


    meth = new JMethod(null, "init");
    meth.addException(new JClass("ObjectException"));
    meth.addException(new JClass("IncompleteDefinitionException"));
    meth.getModifiers().makePublic();
    meth.getModifiers().setStatic(true);
    meth.setSourceCode(initsc);
    newClass.addMethod(meth);

    newClass.print(outputDir, null, 2);


	}

	private JType getJClass(short type) {
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
				return JType.Byte.createArray();

			case Types.NUMERIC:
			case Types.DECIMAL:
				return JClass.BigDecimal;

			case Types.BIT:
				return JType.Boolean;

			case Types.TINYINT:
				return JType.Byte;

			case Types.SMALLINT:
				return JType.Short;

			case Types.INTEGER:
				return JType.Int;

			case Types.BIGINT:
				return JType.Long;

			case Types.REAL:
				return JType.Float;

			case Types.FLOAT:
			case Types.DOUBLE:
				return JType.Double;

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

	private String getDefaultValue(short type) {
		switch (type) {

	    case -9:
	    case -10:
			case Types.LONGVARCHAR:
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.BINARY :
			case Types.VARBINARY :
			case Types.LONGVARBINARY :
			case Types.NUMERIC:
			case Types.DECIMAL:
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
			case Types.OTHER:			
				return "null";

			case Types.BIT:
				return "false";

			case Types.TINYINT:
				return "(byte)0";

			case Types.SMALLINT:
				return "(short)0";

			case Types.INTEGER:
				return "0";

			case Types.BIGINT:
				return "0";

			case Types.REAL:
				return "0.0f";

			case Types.FLOAT:
			case Types.DOUBLE:
				return "0.0";

		}
		return "null";
	}


	private String getJavaClass(short type) {
		switch (type) {

	    case -9:
	    case -10:
			case Types.LONGVARCHAR:
			case Types.CHAR:
			case Types.VARCHAR:
				return "String.class";

			case Types.BINARY :
			case Types.VARBINARY :
			case Types.LONGVARBINARY :
				return "Byte.TYPE";

			case Types.NUMERIC:
			case Types.DECIMAL:
				return "java.math.BigDecimal.class";

			case Types.BIT:
				return "Boolean.TYPE";

			case Types.TINYINT:
				return "Byte.TYPE";

			case Types.SMALLINT:
				return "Short.TYPE";

			case Types.INTEGER:
				return "Integer.TYPE";

			case Types.BIGINT:
				return "Long.TYPE";

			case Types.REAL:
				return "Float.TYPE";

			case Types.FLOAT:
			case Types.DOUBLE:
				return "Double.TYPE";

			case Types.DATE:
				return "java.sql.Date.class";

			case Types.TIME:
				return "java.sql.Time.class";

			case Types.TIMESTAMP:
				return "java.sql.Timestamp.class";

			case Types.OTHER:
				return "Object.class";

		}
		return "";
	}



  public static void main(String[] args) {
    try {
      if (args.length == 1) {
        ConfigReader rc = new ConfigReader();
        rc.read(args[0]);
        DatabaseConfig dc = rc.getDatabaseConfig();
        MetaDataReader meta = new MetaDataReader(dc);
        DBObjectSourceGen gen = new DBObjectSourceGen(dc.getOutputDir(), dc.getPackageName());

        List tablesList = meta.getTables();
        for (int i = 0; i < tablesList.size(); i++) {
          String tableName = (String)tablesList.get(i);
          List columnList = meta.getColumnData(tableName);
          gen.generateSource(tableName, columnList);
        }
      } else {
        System.err.println("java DBObjectSourceGen <configFile>");
      }
    } catch (Exception e) {
      System.err.println(e);
    }
  }
	
}