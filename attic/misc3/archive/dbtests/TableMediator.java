package dbtests;

	import java.sql.*;
	import java.util.*;

	/**
 	 * This class is used to prepare for populating a table from a data file.  
 	 * Any set of columns of the table can be populated, based on the information
 	 * coded in the input file. 
 	 * @see TableColumns
 	 * @author Sesh Venugopal
 	 */
	public class TableMediator
	{
		// the table that is populated
		protected String table;

		// the connection to the database
		protected Connection conn;

		// the table columns information
		protected TableColumns tc;

		// the columns that are populated
		protected boolean[] activeColumns;
		protected int[] activeColumnOrder;

		// prepared statement to insert columns
		protected PreparedStatement ps;

		/**
	 	 * Initializes a new TableMediator object with the a given
	 	 * database connection and given table name, and retrieves
	 	 * database meta data
	 	 * @param conn	database connection
	 	 * @param table	table name
	 	 * @exception SQLException	if there is a problem retrieving
	 	 *				database meta data
	 	 */
		public TableMediator(Connection conn, String table)
		throws SQLException
		{
			this.table = table;
			this.conn = conn;

			DatabaseMetaData dbMeta = conn.getMetaData();
			tc = new TableColumns(dbMeta, table);
		}

		/**
	 	 * Sets the active columns to the given columns
	 	 * @param activeColumnPositions	positions of the columns that
	 	 *				are to be populated
	 	 */
		public void setActiveColumns(int[] activeColumnPositions)
		{
			activeColumnOrder = activeColumnPositions;
			activeColumns = new boolean[tc.columnCount()+1];
			for (int i=1; i < activeColumns.length; i++)
			{
				activeColumns[i] = false;
			}	
			for (int i=0; i < activeColumnPositions.length; i++)
			{
				activeColumns[activeColumnPositions[i]] = true;
			}
		}

		/**
	  	 * Sets the active columns to the given columns
	 	 * @param activeColumnNames	names of the columns that
	 	 *				are to be populated
	 	 * @NoSuchElementException	if a given active column name
	 	 *				does not exist in the table
	  	 */
		public void setActiveColumns(String[] activeColumnNames)
		throws NoSuchElementException
		{
			int[] activeColumnPositions = 
					new int[activeColumnNames.length];
			for (int i=0; i < activeColumnNames.length; i++)
			{
				int columnPos = tc.getColumnPosition(
							activeColumnNames[i]);
				if (columnPos == 0)
				{
					throw new NoSuchElementException(
							activeColumnNames[i]);
				}
				activeColumnPositions[i] = columnPos;
			}

			setActiveColumns(activeColumnPositions);
		}

		/**
	 	 * Creates a prepared statement to insert active columns.
	 	 * @exception SQLException	if the prepared statement cannot
	 	 *				be constructed due to database error
	 	 */
		public void prepareRowInserts()
		throws SQLException
		{
			String prepCols = "(";
			String prepPlaces = "(";

			for (int i=1; i < (tc.columnNames.length-1); i++)
			{
				prepCols += tc.columnNames[i] + ", ";
				prepPlaces += "?, ";
			}
			prepCols += tc.columnNames[tc.columnNames.length-1] + ")";
			prepPlaces += "?)";

			ps = conn.prepareStatement("insert into " + table + " " +
					   	prepCols + " " +
					   	"values " +
					   	prepPlaces);

		}

		/**
	 	 * Inserts the next row of column values in the table
	 	 * @param columnValues	values for the columns that need to be
	 	 *			populated
	 	 * @exception SQLException	if the prepared statement cannot
	 	 *				be executed due to database error
	 	 */
		public void insertRow(String[] columnValues)
		throws SQLException
		{
			// first set all the active columns
			for (int i=0; i < columnValues.length; i++)
			{
			 	if (columnValues[i] == null)
			 	{
					ps.setNull(activeColumnOrder[i], 
				   	tc.columnTypeCodes[activeColumnOrder[i]]);
				}
				else
				{
					ps.setObject(activeColumnOrder[i], 
							columnValues[i]);					 
				}
			}

			// set the remaining to null
			for (int i=1; i < activeColumns.length; i++)
			{		
				if (!activeColumns[i])
				{
					ps.setNull(i, tc.columnTypeCodes[i]);
				}
			}

			ps.executeUpdate();
		}
	} 
