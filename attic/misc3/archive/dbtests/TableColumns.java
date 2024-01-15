package dbtests;

	import java.sql.*;
	import java.util.*;

	/**
 	 * This class builds information about the columns of any given table
 	 * in a database by querying on the given database meta data. This class
	 * is typically used to build a table using data from a file, by 
 	 * populating the appropriate columns as required, without having to
 	 * hardcode any column information in the table builder application.
 	 *
 	 * @author Sesh Venugopal
  	 */
	public class TableColumns
	{
		// the database meta data used to get column information
		protected DatabaseMetaData dbMeta;

		// parallel arrays for column information
		protected String[] columnNames;
		protected short[] columnTypeCodes;

		/**
	 	 * Initializes a new TableColumns object with the given
	 	 * meta data and the given table.
	 	 * @param dbMeta	the database meta data
	 	 * @param table		the table for which column information
	 	 *			needs to be obtained
	 	 */
		public TableColumns(DatabaseMetaData dbMeta, String table)
		throws SQLException
		{
			this.dbMeta = dbMeta;

			// get all columns
			ResultSet rset = dbMeta.getColumns(null, null, 
						table.toUpperCase(), "%");

			// set up the columnName and columnTypeCodes vectors
			Vector nameVector = new Vector();
			Vector typeVector = new Vector();
		
			// column name and type code are columns 4 and 5, resp
			while (rset.next())
			{
				nameVector.addElement(rset.getString(4));
				typeVector.addElement(
						new Short(rset.getShort(5)));
			}

			// convert to arrays to avoid a lot of casting back and forth
			columnNames = new String[nameVector.size()+1];
			columnTypeCodes = new short[typeVector.size()+1];

			for (int i=1; i < columnTypeCodes.length; i++)
			{
				columnNames[i] = (String)nameVector.elementAt(i-1);
				columnTypeCodes[i] = 
			    		((Short)typeVector.elementAt(i-1)).shortValue();
			}
		}
	
		/**
	 	* Retrieves the column type for a given column
	 	* @param columnPos	index of the column
	 	* @return	the column type code
	 	*/
		public short getColumnType(int columnPos)
		throws NoSuchElementException
		{
			if (columnPos <= 0)
			{				
				throw new NoSuchElementException();
			}
			return columnTypeCodes[columnPos];
		}

		/**
	 	 * Retrieves the column type for a given column
	 	 * @param columnName	name of the column
	 	 * @return	the column type code
	 	 */	
		public short getColumnType(String columnName)
		throws NoSuchElementException
		{
			// search in columnNames, and use retrieved index as
			// subscript in columnTypeCodes
			return getColumnType(getColumnPosition(columnName));
		}

		/**
	 	* Retrieves the column position for a given column
	 	* @param columnName	name of the column
	 	* @return	the column position
	  	*/	
		public int getColumnPosition(String columnName)
		{
			for (int i=1; i < columnNames.length; i++)
			{
				if (columnName.equalsIgnoreCase(columnNames[i]))
				{
					return i;
				}
			}
			return 0;
		}

		/**
	 	 * Retrieves the column name for a given column
	 	 * @param columnPosition	position of the column
	 	 * @return	the column name
	 	 */	
		public String getColumnName(int columnPosition)
		{
			return columnNames[columnPosition];
		}

		/**
	 	 * Retrieves the number of columns
	 	 * @return	the number of columns
	 	 */	
		public int columnCount()
		{
		 	return columnNames.length-1;
		}
	}
