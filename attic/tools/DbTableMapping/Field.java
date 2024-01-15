

public class Field {

	private int no;
	private String columnName;
  private String origColumnName;
	private String type;
	private String sqltype;
	private String method;
	private int scale;
	private boolean isKeyUpdate;
	private boolean isAutoInc;
	private String defaultValue;
	private String subst;
	private boolean bool;
	private boolean nullable;
	
	public Field(int no, String columnName, String type, String method, int scale, boolean kup, boolean auto, String defaultValue, String subst, boolean bool, boolean nullable, String sqltype) {
		this.no = no;
    this.origColumnName = columnName;
		this.columnName = columnName.toLowerCase();
		this.type = type;
		this.sqltype = sqltype;
		this.method = method;
		this.scale = scale;
		this.isKeyUpdate = kup;
		this.isAutoInc = auto;		
		this.defaultValue = defaultValue;
		this.subst = subst;	
		this.bool = bool;
		this.nullable = nullable;
	}
	
	int getNo() {
		return no;
	}
	
	String getColumnName() {
		if (subst == null)
			return columnName;
		else
			return subst;	
	}

  String getOrigColumnName() {
    return origColumnName;
  }
	
	String getColumnNameFirst() {
		String first = columnName.substring(0,1).toUpperCase();
		return (first + columnName.substring(1));
	}
	
	String getType() {
		return type;
	}
	
	String getMethod() {
		return method;
	}
	
	int getScale() {
		return scale;
	}
	
	boolean isKeyUpdate() {
		return isKeyUpdate;
	}
	
	boolean isAutoInc() {
		return isAutoInc;
	}
	
	String getDefaultValue() {
		return defaultValue;
	}
	
	boolean isBool() {
		return (bool);
	}
	
	boolean isNullable() {
		return nullable;
	}

	String getSqlType() {
		return sqltype;
	}

}