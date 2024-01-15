import java.io.*;
import java.util.*;

public class SourceFile2 {

	private PrintWriter out = null;
	private PrintWriter outex = null;
	private Table table;
	private String tn;
	private String var;
	private boolean hasUpdateStatement = true;
	
	public SourceFile2(Table table) throws IOException {
		this.table = table;
		tn = table.getName().substring(table.getName().indexOf('.')+1);
		tn = tn.substring(0,1).toUpperCase() + tn.substring(1);
		
		String path = "";
		if (table.getOutput() != null) 
			path = table.getOutput();
		
		out = new PrintWriter(new BufferedWriter(new FileWriter(path + "gen/_" +tn+"Table.java")));
		outex = new PrintWriter(new BufferedWriter(new FileWriter(path + tn + "Table.java")));
		var = tn.toLowerCase();		
	}
	
		
	public static void buildSource(Table table, List fieldsList) throws IOException {
		SourceFile2 sf = new SourceFile2(table);
		sf.writeImportStatements(table);
		sf.writeClassHeader();
		sf.writeDeclaration(table, fieldsList);
		sf.writeConstructor(fieldsList);
		sf.writeMethods(table);
		sf.writeMakeObject(fieldsList);
		sf.writePrepareInsertStmt(fieldsList);
		
		if (sf.hasUpdateStatement) {
			sf.writePrepareUpdateStmt(fieldsList);
			sf.writePrepareDeleteStmt(fieldsList);
		}
		sf.close();
	}
	
	public void writeImportStatements(Table table) {
		if (table.getPackage() != null) {
			out.println("package " + table.getPackage() + ".gen;");			
			out.println();
			outex.println("package " + table.getPackage() + ";");			
			outex.println();
		}
		
		out.println("import java.sql.*;");
		out.println("import java.util.List;");
		out.println("import java.util.ArrayList;");
    out.println("import com.codestudio.util.SQLManager;");
    out.println("import com.codestudio.util.PoolPropsException;");
		out.println("import "+table.getPackage()+".support.*;");
		out.println("import "+table.getPackage()+".*;");
		out.println();
	}
	
	public void writeClassHeader() {
		out.println("public class _"+tn+"Table {");
		out.println();
		outex.println("public class "+tn+"Table extends "+table.getPackage()+".gen._"+tn+"Table {");
		outex.println();		
	}
	
	public void writeDeclaration(Table table, List fieldsList) {
		Iterator it = fieldsList.iterator();
		StringBuffer selectsb = new StringBuffer();
		StringBuffer insertsb = new StringBuffer();
		StringBuffer valsb = new StringBuffer();
		
		StringBuffer sbup = new StringBuffer();
		StringBuffer sbupkey = new StringBuffer();
		
	
		while(it.hasNext()) {
			Field f = (Field)it.next();

			selectsb.append(f.getOrigColumnName());

			if (!f.isAutoInc()) {
				insertsb.append(f.getOrigColumnName());
				valsb.append("?");
			}
			
			if (f.isKeyUpdate()) {
				if (sbupkey.length() > 0)
					sbupkey.append(" AND ");
				else		
					sbupkey.append(" WHERE ");
				sbupkey.append(f.getOrigColumnName()).append("=?");
			} else {
				sbup.append(f.getOrigColumnName()).append("=?, ");
			}
							
			if (it.hasNext()) {
				selectsb.append(",");
				if (!f.isAutoInc()) {
					insertsb.append(",");
					valsb.append(",");
				}
			}
		}
	
		String valstr = valsb.toString();
		if (valstr.endsWith(","))
			valstr = valstr.substring(0, valstr.length()-1);
		
		String insertstr = insertsb.toString();
		if (insertstr.endsWith(","))
			insertstr = insertstr.substring(0, insertstr.length()-1);
		
		if (sbupkey.length() == 0)
			hasUpdateStatement = false;
		
		out.println("\tprivate PreparedStatement insertPS;");
		if (hasUpdateStatement) {
			out.println("\tprivate PreparedStatement updatePS;");
			out.println("\tprivate PreparedStatement deletePS;");
		}


    String tablefull = null;
    if (table.getSchema() != null)
      tablefull = table.getSchema() + "." + table.getOrigName();
    else
      tablefull = table.getOrigName();

		out.println("\tprivate final static String deleteSQL = \"DELETE FROM "+tablefull+"\";");
		out.println("\tprivate final static String whereSQL  = \" WHERE \";");
		out.println("\tprivate final static String selectSQL = \"SELECT "+selectsb.toString()+" FROM "+tablefull+"\";");
		out.println("\tprivate final static String countSQL = \"SELECT count(*) FROM "+tablefull+"\";");
		out.println("\tprivate final static String orderSQL  = \" ORDER BY \";");
		out.println();

		if (table.getKeyGen() != null) {
			out.println("\tprivate final static IDKeyGenerator idGen = new "+table.getKeyGen()+"();");
			out.println();
		}

		out.println("\tprivate final static String insertSQL = ");
		out.println("\t\t\"INSERT INTO "+tablefull+"("+insertsb.toString()+") VALUES("+valstr+")\";");
	
	
		if (hasUpdateStatement) {
			out.println();
      if (sbup.length() >= 2) {
  			sbup.delete(sbup.length()-2, sbup.length());
      }
		
			out.println("\tprivate final static String updateSQL = ");
			out.println("\t\t\"UPDATE "+tablefull+" SET "+sbup.toString() + sbupkey.toString()+"\";");	
			out.println();
			out.println("\tprivate final static String deleteSQL2 = ");	
			out.println("\t\tdeleteSQL + \"" + sbupkey.toString()+"\";");	
		}
		
	}

	public void writeConstructor(List fieldsList) {
		out.println();
		out.println("\tpublic _"+tn+"Table() {");
		out.println("\t\tinsertPS = null;");
		if (hasUpdateStatement) {
			out.println("\t\tupdatePS = null;");		
			out.println("\t\tdeletePS = null;");					
		}
		out.println("\t}");
		
		outex.println("\tpublic "+tn+"Table() {");
		outex.println("\t\tsuper();");
		outex.println("\t}");
		
	}
		
	public void writeMethods(Table table) {

		if (hasUpdateStatement) {
			out.println();			
		
			out.println("\tpublic int delete("+tn+" "+var+") throws SQLException, PoolPropsException {");
			out.println("\t\tConnection conn = null;");
			out.println("\t\ttry {");
			out.println("\t\t\tif (deletePS == null) {");
			out.println("\t\t\t\tconn = SQLManager.getInstance().requestConnection();");
			out.println("\t\t\t\tdeletePS = conn.prepareStatement(deleteSQL2);");
			out.println("\t\t\t}");
			out.println();
			
			if (table.getManyReferenceList() != null) {
				out.println("\t\t\t"+var+".invalidateBackRelations();");
				out.println();
			}
			
			out.println("\t\t\tsynchronized (deletePS) {");
			out.println("\t\t\t\tprepareDeleteStatement(deletePS, "+var+");");
			out.println("\t\t\t\treturn deletePS.executeUpdate();");
			out.println("\t\t\t}");
			out.println("\t\t} finally {");
			out.println("\t\t\tif (conn != null)");
			out.println("\t\t\t\tSQLManager.getInstance().returnConnection(conn);");
			out.println("\t\t}");			
			out.println("\t}");		
		}
	
		out.println();
		out.println("\tpublic int delete() throws SQLException, PoolPropsException {");
		out.println("\t\treturn (delete((String)null));");
		out.println("\t}");
		out.println();
	
		out.println("\tpublic int delete(String whereClause) throws SQLException, PoolPropsException {");
		out.println("\t\tConnection conn = null;");
		out.println("\t\ttry {");
		out.println("\t\t\tconn = SQLManager.getInstance().requestConnection();");
		out.println("\t\t\tStatement stmt = conn.createStatement();");
		out.println();
		
		if (table.getManyReferenceList() != null) {
			out.println("\t\t\tinvalidateBackRelations(whereClause);");
			out.println();
		}
		out.println("\t\t\tif (whereClause == null)");
		out.println("\t\t\t\treturn (stmt.executeUpdate(deleteSQL));");
		out.println("\t\t\telse");
		out.println("\t\t\t\treturn (stmt.executeUpdate(deleteSQL+whereSQL+whereClause));");
		out.println("\t\t} finally {");
		out.println("\t\t\tSQLManager.getInstance().returnConnection(conn);");
		out.println("\t\t}");
		out.println("\t}");		
		out.println();

		if (table.getManyReferenceList() != null) {
			out.println("\tprivate void invalidateBackRelations(String whereClause) throws SQLException, PoolPropsException {");
			out.println("\t\tList resultList = select(whereClause);");
			out.println("\t\tfor (int i = 0; i < resultList.size(); i++) {");
			out.println("\t\t\t"+tn+" "+var+" = ("+tn+")resultList.get(i);");
			out.println("\t\t\t"+var+".invalidateBackRelations();");
			out.println("\t\t}");
			out.println("\t}");
			out.println();
		}
				
		out.println("\tpublic int count() throws SQLException, PoolPropsException {");
		out.println("\t\treturn count(null);");
		out.println("\t}");
		out.println();
		out.println("\tpublic int count(String whereClause) throws SQLException, PoolPropsException {");
		out.println("\t\tStringBuffer sb = new StringBuffer(countSQL);");
		out.println();
		out.println("\t\tif (whereClause != null)");
		out.println("\t\t\tsb.append(whereSQL).append(whereClause);");
		out.println();
		out.println("\t\tConnection conn = SQLManager.getInstance().requestConnection();");
		out.println("\t\ttry {");
		out.println("\t\t\tStatement stmt = conn.createStatement();");
		out.println("\t\t\tResultSet rs = stmt.executeQuery(sb.toString());");
		out.println();		
		out.println("\t\t\tint count = -1;");
		out.println("\t\t\tif (rs.next()) {");
		out.println("\t\t\t\tcount = rs.getInt(1);");
		out.println("\t\t\t}");
		out.println("\t\t\trs.close();");
		out.println("\t\t\tstmt.close();");
		out.println();
		out.println("\t\t\treturn count;");
		out.println();		
		out.println("\t\t} finally {");
		out.println("\t\t\tSQLManager.getInstance().returnConnection(conn);");
		out.println("\t\t}");
		out.println("\t}");
		out.println();

		out.println("\tpublic "+tn+" selectOne(String whereClause) throws SQLException, PoolPropsException {");
		out.println("\t\tList resultList = select(whereClause, null);");
		out.println("\t\tif (resultList.size() > 0) {");
		out.println("\t\t\treturn ("+tn+")resultList.get(0);");
		out.println("\t\t} else {");
		out.println("\t\t\treturn null;");
		out.println("\t\t}");
		out.println("\t}");
		
		out.println();
		out.println("\tpublic List select() throws SQLException, PoolPropsException {");
		out.println("\t\treturn select(null, null);");
		out.println("\t}");
		out.println();
		out.println("\tpublic List select(String whereClause) throws SQLException, PoolPropsException{");
		out.println("\t\treturn select(whereClause, null);");
		out.println("\t}");
		out.println();
		
		out.println("\tpublic List select(String whereClause, String orderClause) throws SQLException, PoolPropsException {");
		out.println("\t\tStringBuffer sb = new StringBuffer(selectSQL);");
		out.println();
		out.println("\t\tif (whereClause != null)");
		out.println("\t\t\tsb.append(whereSQL).append(whereClause);");
		out.println("\t\tif (orderClause != null)");
		out.println("\t\t\tsb.append(orderSQL).append(orderClause);");
		out.println();
		out.println("\t\tConnection conn = SQLManager.getInstance().requestConnection();");
		out.println("\t\ttry {");
		out.println("\t\t\tStatement stmt = conn.createStatement();");
		out.println("\t\t\tResultSet rs = stmt.executeQuery(sb.toString());");
		out.println("\t\t\tList resultList = new ArrayList();");
		out.println();		
		out.println("\t\t\twhile(rs.next()) {");
		out.println("\t\t\t\tresultList.add(makeObject(rs));");
		out.println("\t\t\t}");
		out.println("\t\t\trs.close();");
		out.println("\t\t\tstmt.close();");
		out.println();
		out.println("\t\t\treturn resultList;");
		out.println();	
		out.println("\t\t} finally {");
		out.println("\t\t\tSQLManager.getInstance().returnConnection(conn);");
		out.println("\t\t}");
		out.println("\t}");
		out.println();
				
		out.println("\tpublic int insert("+tn+" "+var+") throws SQLException, PoolPropsException {");
		out.println("\t\tConnection conn = null; ");
		out.println("\t\ttry {");
		out.println("\t\t\tif (insertPS == null) {");
		out.println("\t\t\t\tconn = SQLManager.getInstance().requestConnection();");
		out.println("\t\t\t\tinsertPS = conn.prepareStatement(insertSQL);");
		out.println("\t\t\t}");
		out.println();
		
		if (table.getKeyGen() != null) {
			Map keyMap = table.getKeyMap();
			
			if ((keyMap != null) && (keyMap.size() == 1)) {
				Collection collec = keyMap.values();
				Iterator it = collec.iterator();
				Key key = (Key)it.next();
				String keyName = key.getKeyName();
				keyName = "set" + keyName.substring(0,1).toUpperCase() + keyName.substring(1).toLowerCase();
				out.println("\t\t\t"+var+"."+keyName+"(idGen.generate(\""+table.getName()+"\", "+var+"));");
				out.println();
			}
		}
		
		out.println("\t\t\tsynchronized (insertPS) {");
		out.println("\t\t\t\tprepareInsertStatement(insertPS, "+var+");");
		out.println("\t\t\t\treturn insertPS.executeUpdate();");
		out.println("\t\t\t}");
		out.println("\t\t} finally {");
		out.println("\t\t\tif (conn != null)");
		out.println("\t\t\t\tSQLManager.getInstance().returnConnection(conn);");
		out.println("\t\t}");
		out.println("\t}");
		
		if (hasUpdateStatement) {
			out.println();
			out.println("\tpublic int update("+tn+" "+var+") throws SQLException, PoolPropsException {");
			out.println("\t\tConnection conn = null;");
			out.println("\t\ttry {");
			out.println("\t\tif (updatePS == null) {");
			out.println("\t\t\t\tconn = SQLManager.getInstance().requestConnection();");
			out.println("\t\t\t\tupdatePS = conn.prepareStatement(updateSQL);");
			out.println("\t\t\t}");
			out.println();
			if (table.getManyReferenceList() != null) {
				out.println("\t\t\t"+var+".invalidateBackRelations();");
				out.println();						
			}
			out.println("\t\t\tsynchronized (updatePS) {");
			out.println("\t\t\t\tprepareUpdateStatement(updatePS, "+var+");");
			out.println("\t\t\t\treturn updatePS.executeUpdate();");
			out.println("\t\t\t}");
			out.println("\t\t} finally {");
			out.println("\t\t\tif (conn != null)");
			out.println("\t\t\t\tSQLManager.getInstance().returnConnection(conn);");
			out.println("\t\t}");
			out.println("\t}");
			
		}
		out.println();
	}
	
	public void writeMakeObject(List fieldsList) {
		Iterator it = fieldsList.iterator();
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()) {
			Field f = (Field)it.next();
			if ("BigDecimal".equals(f.getMethod()))
				sb.append("rs.get"+f.getMethod()+"(\""+f.getColumnName()+"\","+f.getScale()+")");
			else
				sb.append("rs.get"+f.getMethod()+"(\""+f.getColumnName()+"\")");
			
			if (it.hasNext())
				sb.append(", ");
		}
		out.println("\tpublic static "+tn+" makeObject(ResultSet rs) throws SQLException {");
		out.println("\t\treturn new "+tn+"("+sb.toString()+");");
		out.println("\t}");
	}
	
	public void writePrepareInsertStmt(List fieldsList) {
		out.println();	
		out.println("\tprivate void prepareInsertStatement(PreparedStatement ps, "+tn+" "+var+") throws SQLException {");
		
		Iterator it = fieldsList.iterator();
		int no = 1;
		while(it.hasNext()) {
			Field f = (Field)it.next();
			if (!f.isAutoInc()) {
				if (!f.isNullable()) {
					out.println("\t\tps.set"+f.getMethod()+"("+(no++)+", "+var+".get"+f.getColumnNameFirst()+"());");
				} else {
					out.println();
					out.println("\t\tif ("+var+".get"+f.getColumnNameFirst()+"() != null)");
					int tmpno = no++;
					out.println("\t\t\tps.setString("+tmpno+", "+var+".get"+f.getColumnNameFirst()+"());");
					out.println("\t\telse");
					out.println("\t\t\tps.setNull("+tmpno+", java.sql.Types."+f.getSqlType()+");");								
					out.println();
				}
			}
		}		
		out.println("\t}");
	}
	
	public void writePrepareUpdateStmt(List fieldsList) {
		if (hasUpdateStatement) {	
			out.println();
			out.println("\tprivate void prepareUpdateStatement(PreparedStatement ps, "+tn+" "+var+") throws SQLException {");
			int no = 1;
			
			Iterator it = fieldsList.iterator();
			while(it.hasNext()) {
				Field f = (Field)it.next();
				if (!f.isKeyUpdate())
					if (!f.isNullable()) {
						out.println("\t\tps.set"+f.getMethod()+"("+(no++)+", "+var+".get"+f.getColumnNameFirst()+"());");
					} else {
						out.println();
						out.println("\t\tif ("+var+".get"+f.getColumnNameFirst()+"() != null)");
						int tmpno = no++;
						out.println("\t\t\tps.setString("+tmpno+", "+var+".get"+f.getColumnNameFirst()+"());");
						out.println("\t\telse");
						out.println("\t\t\tps.setNull("+tmpno+", java.sql.Types."+f.getSqlType()+");");								
						out.println();					
					}
			}

			it = fieldsList.iterator();
			while(it.hasNext()) {
				Field f = (Field)it.next();
				if (f.isKeyUpdate())
					out.println("\t\tps.set"+f.getMethod()+"("+(no++)+", "+var+".get"+f.getColumnNameFirst()+"());");
			}
			out.println("\t}");

		}
	}
	
	public void writePrepareDeleteStmt(List fieldsList) {
		if (hasUpdateStatement) {	
			out.println();
			out.println("\tprivate void prepareDeleteStatement(PreparedStatement ps, "+tn+" "+var+") throws SQLException {");
			int no = 1;

			Iterator it = fieldsList.iterator();
			while(it.hasNext()) {
				Field f = (Field)it.next();
				if (f.isKeyUpdate())
					out.println("\t\tps.set"+f.getMethod()+"("+(no++)+", "+var+".get"+f.getColumnNameFirst()+"());");
			}
			out.println("\t}");

		}
	}
	
	public void close() throws IOException {
		out.println();
		out.println("}");
		out.close();
		outex.println();
		outex.println("}");
		outex.close();

	}

}