

public class Relation {
	private String column;
	private String reftable;
   private String refcol;

	public Relation() {
		column = null;
		reftable = null;
		refcol = null;
	}

	public void setColumn(String str) {
		column = str;
	}

	public void setRefTable(String str) {
		reftable = str;
	}

	public String getColumn() {
		return column;
	}

	public String getRefTable() {
		return reftable;
	}
	
	public void setRefColumn(String str) {
		refcol = str;
	}
	
	public String getRefColumn() {
		return refcol;
	}
}