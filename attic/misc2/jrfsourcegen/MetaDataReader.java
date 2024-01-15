
import java.sql.*;
import java.util.*;
import java.io.*;
import net.sourceforge.osage.attributed.*;

public class MetaDataReader {

  private DatabaseMetaData metaData;

	public MetaDataReader(DatabaseConfig dc) {
		try {
			Class.forName(dc.getDriver());														
			Connection conn = DriverManager.getConnection(dc.getUrl(), dc.getUser(), dc.getPassword());	
      metaData = conn.getMetaData();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	

	public List getTables() throws SQLException {
	  String[] tableTypes = { "TABLE" };
    List tableList = new ArrayList();

	  ResultSet tables = metaData.getTables(null, null, "%", tableTypes);

	  String tableName = null;
	  while (tables.next()) {
	    tableName = tables.getString("TABLE_NAME");
      tableList.add(tableName);
	  }
	  tables.close();
    return tableList;
  }


  public List getColumnData(String tableName) throws SQLException, AttributeNotFoundException {

    //first read the primary keys
    Set keys = new HashSet();
    ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);
    while (rs.next()) {
      String col = rs.getString("COLUMN_NAME");
      keys.add(col);
    }

	  rs = metaData.getColumns(null , null, tableName, "%");
    List columnData = new ArrayList();

	  while (rs.next()) {
	    Attributed attributed = AttributedFactory.getAttributed();
      String colName = rs.getString("COLUMN_NAME");
	    attributed.add(new Attr(Constants.COLUMN_NAME, colName));
	    attributed.add(new Attr(Constants.COLUMN_TYPE, rs.getShort("DATA_TYPE")));
	    attributed.add(new Attr(Constants.COLUMN_SIZE, rs.getInt("COLUMN_SIZE")));      
	    
	    String nullable = rs.getString("IS_NULLABLE");
	    attributed.add(new Attr(Constants.NULLABLE, 
	        nullable != null && !Constants.NO.equals(nullable.trim())));
	    attributed.add(new Attr(Constants.DECIMAL_DIGITS, rs.getInt("DECIMAL_DIGITS")));
      
      attributed.add(new Attr(Constants.PRIMARY_KEY, keys.contains(colName)));

      columnData.add(attributed);
	  }
    return columnData;
	}



}

