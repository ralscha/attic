package dbtests;

	import java.sql.*;
	import java.io.*;
	import java.util.*;

	/** Reads data from a text file (delimiter specified at construction)
         * for some table. Data file is of the following format (delims not shown):                            
 	 *      <tablename>                                                 
 	 *      <colname> <colname> .... <colname>                          
 	 *      <colval>  <colval>  .... <colval>                           
 	 *      <colval>  <colval>  .... <colval>                           
 	 *       ...       ...       ...  ....
 	 *      <colval>  <colval>  .... <colval>                           
 	 */	
	public class TableBuilder
	{
		protected Connection conn;
		protected BufferedReader br;
		protected String delims;
		protected String table;
		protected String[] activeColumnNames;
		protected TableMediator tm;

		public TableBuilder(Connection conn, BufferedReader br, String delims)
		{
			this.conn = conn;
			this.br = br;
			this.delims = delims;
		}

		public void buildTableInfo()
		throws IOException, SQLException
		{	
			// read table name
			String table = br.readLine();  

			// create table mediator
			tm = new TableMediator(conn, table);
		}

		public void buildActiveColumns()
		throws IOException
		{
			// read the active column names 
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line, delims);

			activeColumnNames = new String[st.countTokens()];
			for (int i=0; i < activeColumnNames.length; i++)
			{
				activeColumnNames[i] = st.nextToken();
			}
			tm.setActiveColumns(activeColumnNames);
		}	


		public void buildTable()
		throws IOException, SQLException
		{
			tm.prepareRowInserts();

			String[] values = new String[activeColumnNames.length];

			String line;
			while ((line = br.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, delims);
				for (int i=0; i < values.length; i++)
				{
					values[i] = st.nextToken();
				}
				tm.insertRow(values);
			}
		
		}
	}
