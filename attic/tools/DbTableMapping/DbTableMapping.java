
import java.sql.*;
import java.util.*;
import java.io.*;

public class DbTableMapping {

	private Connection conn;
	private ORMappingHandler mappingHandler;
	
	public static void main(String args[]) {

		if (args.length == 1) {
			new DbTableMapping(args[0]);
		} else {
			System.out.println("java DbTableMapping <configxmlfile>");
		}
	}

	private DbTableMapping(String fileName) {
		Lax lax = new Lax();

		mappingHandler = new ORMappingHandler();
		lax.addHandler(mappingHandler);
		
		File file = new File(fileName);
		lax.parseDocument(false, lax, file);	
		
		

		try {
			Class.forName(mappingHandler.getDriver());
															
			List tableList = mappingHandler.getTableList();
			SourceFileDb.buildSource(tableList);
			
			for (int i = 0; i < tableList.size(); i++) {
				conn = DriverManager.getConnection(mappingHandler.getURL(), 
																mappingHandler.getUser(),
																mappingHandler.getPassword());
				Table table = (Table)tableList.get(i);
				createSourceFile(table);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	

	private void createSourceFile(Table table) {
		try {
			DatabaseMetaData dmb = conn.getMetaData();
			
			Map substMap = table.getSubstMap();
			Map dbsubstMap = table.getDBSubstMap();
			Set nullableSet = table.getNullableSet();	
			Set ignoreSet = table.getIgnoreSet();

			Set boolSet = table.getBoolSet();
			
			String path = "";
			if (table.getOutput() != null) {
				path = table.getOutput();
			}
			File fpath = new File(path+"gen");
			if (!fpath.exists()) {
				fpath.mkdir();
			}
			
			String schema = "%";
      if (table.getSchema() != null) {
        schema = table.getSchema();
      }


      ResultSet rs = dmb.getPrimaryKeys(null, schema, table.getOrigName());
      while (rs.next()) {

        String colName = rs.getString("COLUMN_NAME");        
        Key k = new Key(colName, false);
        table.getKeyMap().put(colName.toUpperCase(), k);
      }
      /*
      System.out.println(schema);
      System.out.println(table.getOrigName());
      rs = dmb.getImportedKeys(null, schema, table.getOrigName());
      while(rs.next()) {
        System.out.println(rs.getString(1));
      }
      */

			rs = dmb.getColumns(null, schema, table.getOrigName(),"%");
			List fieldList = new ArrayList();
			int no = 1;

      boolean found = false;
			while (rs.next()) {        
        found = true;
				String columnName = rs.getString("COLUMN_NAME");
				short type = rs.getShort("DATA_TYPE");
				//int no = rs.getInt("ORDINAL_POSITION");
				int scale = rs.getInt("DECIMAL_DIGITS");
				String nullableStr = rs.getString("IS_NULLABLE");
				

				Field f;

				String subst = null;
				if (substMap != null) {
					subst = (String)substMap.get(columnName);
				}
        if (ignoreSet != null) {
          if (!ignoreSet.contains(columnName)) {

				    boolean nullable = false;
				    if (nullableSet != null) {
					    nullable = nullableSet.contains(columnName);
					
					    if (!nullable) {
						    nullable = "YES".equals(nullableStr);
					    }
				    }
				
				    String javaType = null;
				    if (dbsubstMap != null) {
					    javaType = (String)dbsubstMap.get(columnName);
					    if (javaType == null) {
						    javaType = getJavaType(type);
					    } else {
						    type = getType(javaType);
					    }
				    } else {
					    javaType = getJavaType(type);
				    }
				
				    boolean bool = false;
				    if (boolSet != null) {
					    bool = boolSet.contains(columnName);
				    }
				
				    Key key = (Key)table.getKeyMap().get(columnName.toUpperCase());				
				    if (key != null) {
					    if (bool)
						    f = new Field(no++, columnName, javaType, getMethod(type), scale, true, key.isAutoInc(), "false", subst, true, nullable, getTypeString(type));
					    else	
						    f = new Field(no++, columnName, javaType, getMethod(type), scale, true, key.isAutoInc(), getDefaultValue(type), subst, false, nullable, getTypeString(type));
				    } else {
					    if (bool) 
						    f = new Field(no++, columnName, javaType, getMethod(type), scale, false, false, "false", subst, true, nullable, getTypeString(type));
					    else
						    f = new Field(no++, columnName, javaType, getMethod(type), scale, false, false, getDefaultValue(type), subst, false, nullable, getTypeString(type));	
				    }
					
				    fieldList.add(f);
          }
        }
			}
			if (fieldList.size() > 0) {
				SourceFile.buildSource(table, fieldList);
				SourceFile2.buildSource(table, fieldList);
			}

      if (!found)
        System.out.println("not found: "+table.getName());
			
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally { 
			try {
				conn.close();
      		} catch (SQLException sqle) {}
		} 
	}

	private String getJavaType(short type) {
		switch (type) {

      case -9:
      case -10:
			case Types.LONGVARCHAR:
			case Types.CHAR:
			case Types.VARCHAR:
				return "String";

			case Types.BINARY :
			case Types.VARBINARY :
			case Types.LONGVARBINARY :
				return "byte[]";

			case Types.NUMERIC:
			case Types.DECIMAL:
				return "java.math.BigDecimal";

			case Types.BIT:
				return "boolean";

			case Types.TINYINT:
				return "byte";

			case Types.SMALLINT:
				return "short";

			case Types.INTEGER:
				return "int";

			case Types.BIGINT:
				return "long";

			case Types.REAL:
				return "float";

			case Types.FLOAT:
			case Types.DOUBLE:
				return "double";

			case Types.DATE:
				return "java.sql.Date";

			case Types.TIME:
				return "java.sql.Time";

			case Types.TIMESTAMP:
				return "java.sql.Timestamp";

			case Types.OTHER:
				return "Object";

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

	private String getMethod(short type) {
		switch (type) {

      case -9:
      case -10:
			case Types.LONGVARCHAR:
			case Types.CHAR:
			case Types.VARCHAR:
				return "String";

			case Types.BINARY :
			case Types.VARBINARY :
			case Types.LONGVARBINARY :
				return "Bytes";

			case Types.NUMERIC:
			case Types.DECIMAL:
				return "BigDecimal";

			case Types.BIT:
				return "Boolean";

			case Types.TINYINT:
				return "Byte";

			case Types.SMALLINT:
				return "Short";

			case Types.INTEGER:
				return "Int";

			case Types.BIGINT:
				return "Long";

			case Types.REAL:
				return "Float";

			case Types.FLOAT:
			case Types.DOUBLE:
				return "Double";

			case Types.DATE:
				return "Date";

			case Types.TIME:
				return "Time";

			case Types.TIMESTAMP:
				return "Timestamp";

			case Types.OTHER:
				return "Object";

		}
		return null;
	}

	private String getTypeString(short type) {
		switch (type) {

      case -9: return "VARCHAR";
      case -10: return "VARCHAR";
			case Types.LONGVARCHAR: return "LONGVARCHAR";
			case Types.CHAR:        return "CHAR";
			case Types.VARCHAR:     return "VARCHAR";
			case Types.BINARY :     return "BINARY";
			case Types.VARBINARY :  return "VARBINARY";
			case Types.LONGVARBINARY : return "LONGVARBINARY";
			case Types.NUMERIC:     return "NUMERIC";
			case Types.DECIMAL:     return "DECIMAL";
			case Types.BIT:     		return "BIT";
			case Types.TINYINT:     return "TINYINT";
			case Types.SMALLINT:    return "SMALLINT";
			case Types.INTEGER:     return "INTEGER";
			case Types.BIGINT:      return "BIGINT";
			case Types.REAL:        return "REAL";
			case Types.FLOAT:       return "FLOAT";
			case Types.DOUBLE:      return "DOUBLE";
			case Types.DATE:        return "DATE";
			case Types.TIME:        return "TIME";
			case Types.TIMESTAMP:   return "TIMESTAMP";
			case Types.OTHER:       return "OTHER";
		}
		return "OTHER";
	}

	public short getType(String javaType) {
		if (javaType.equals("java.sql.Timestamp")) 
			return Types.TIMESTAMP;
		else
			return 0; 	
	}
}

