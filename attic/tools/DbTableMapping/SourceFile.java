import java.io.*;
import java.util.*;

public class SourceFile {

	private PrintWriter out = null;
	private PrintWriter outex = null;
	private Table table;
	private String tn;
	
	public SourceFile(Table table) throws IOException {
		this.table = table;
		tn = table.getName().substring(table.getName().indexOf('.')+1);
		tn = tn.substring(0,1).toUpperCase() + tn.substring(1);
		
		String path = "";
		if (table.getOutput() != null) 
			path = table.getOutput();
		
		out = new PrintWriter(new BufferedWriter(new FileWriter(path+"gen/_"+tn+".java")));
		outex = new PrintWriter(new BufferedWriter(new FileWriter(path+tn+".java")));
		
		if (table.getPackage() != null) {
			out.println("package " + table.getPackage() + ".gen;");
			out.println();
			outex.println("package " + table.getPackage() + ";");
			outex.println();
		}
		
		out.println("import java.sql.*;");
		out.println("import java.util.*;");
		out.println("import java.lang.ref.SoftReference;");	
    out.println("import com.codestudio.util.PoolPropsException;");			
		out.println("import "+table.getPackage()+".*;");
		out.println();
		out.println("public class _"+tn+" {");
		out.println();
		outex.println("public class "+tn+" extends "+table.getPackage()+".gen._"+tn+" {");
		outex.println();
	}
	
	public static void buildSource(Table table, List fieldsList) throws IOException {
		SourceFile sf = new SourceFile(table);
		sf.writeDeclaration(table, fieldsList);
		sf.writeConstructor(fieldsList);
		sf.writeGetSet(fieldsList);
		sf.writeToString(fieldsList);
		sf.writeManyRelations(table);
		sf.writeOneRelations(table);
		sf.close();
	}
		
	public void writeDeclaration(Table table, List fieldsList) {
		Iterator it = fieldsList.iterator();
		while(it.hasNext()) {
			Field f = (Field)it.next();
			if (f.isBool()) {
				out.println("\tprivate boolean "+f.getColumnName()+";");
			} else {
				out.println("\tprivate "+f.getType()+" "+f.getColumnName()+";");
			}
		}
		out.println();
	}

	public void writeConstructor(List fieldsList) {
		Iterator it = fieldsList.iterator();
		List defaultList = new ArrayList();
		List thisList = new ArrayList();
		StringBuffer sb = new StringBuffer();
		StringBuffer supersb = new StringBuffer();
		
		while(it.hasNext()) {
			Field f = (Field)it.next();
			sb.append(f.getType()+" "+f.getColumnName());
			supersb.append(f.getColumnName());
			if (it.hasNext()) {
				sb.append(", ");
				supersb.append(", ");
			}		
			
			defaultList.add("\t\tthis."+f.getColumnName()+" = "+f.getDefaultValue()+";");
			
			if (f.isBool()) {
				thisList.add("\t\tset"+f.getColumnNameFirst()+"("+f.getColumnName()+");");
			} else {
				thisList.add("\t\tthis."+f.getColumnName()+" = "+f.getColumnName()+";");	
			}
		}
		
		out.println("\tpublic _"+tn+"() {");
		it = defaultList.iterator();
		while(it.hasNext()) {
			out.println((String)it.next());
		}				
		out.println("\t}");
		out.println();
		
		out.println("\tpublic _"+tn+"("+sb.toString()+") {");
		it = thisList.iterator();
		while(it.hasNext()) {
			out.println((String)it.next());
		}
		out.println("\t}");
		out.println();
		
		outex.println("\tpublic "+tn+"() {");
		outex.println("\t\tsuper();");
		outex.println("\t}");
		outex.println();
		
		outex.println("\tpublic "+tn+"("+sb.toString()+") {");
		outex.println("\t\tsuper("+supersb.toString()+");");
		outex.println("\t}");
		outex.println();
	}

	public void writeToString(List fieldsList) {
		out.println();
		Iterator it = fieldsList.iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("\t\treturn \"_"+tn+"(\"+ ");
		while(it.hasNext()) {
			Field f = (Field)it.next();
			sb.append(f.getColumnName());
			if (it.hasNext())
				sb.append(" + \" \" + ");	
		}
		sb.append("+\")\";");
		out.println("\tpublic String toString() {");
		out.println(sb.toString());
		out.println("\t}");
	}
	
	public void writeGetSet(List fieldsList) {
		Iterator it = fieldsList.iterator();
		while(it.hasNext()) {
			Field f = (Field)it.next();
			writeGetMethod(f);
			out.println();
			writeSetMethod(f);
			out.println();
		}
	}
	
	private void writeGetMethod(Field f) {
		if (f.isBool()) {
			out.println("\tpublic boolean is"+f.getColumnNameFirst()+"() {");
			out.println("\t\treturn this."+f.getColumnName()+";");
			out.println("\t}");			
			out.println();
			out.println("\tpublic "+f.getType()+" get"+f.getColumnNameFirst()+"() {");			
			out.println("\t\tif ("+f.getColumnName()+")");
			if (f.getType().equals("byte"))
				out.println("\t\t\treturn ("+f.getType()+")1;");
			else if (f.getType().equals("String"))
				out.println("\t\t\treturn \"1\";");
			else
				out.println("\t\t\treturn 1;");
					
			out.println("\t\telse");
			if (f.getType().equals("byte"))
				out.println("\t\t\treturn ("+f.getType()+")0;");
			else if (f.getType().equals("String"))
				out.println("\t\t\treturn \"0\";");
			else
				out.println("\t\t\treturn 0;");
				
			out.println("\t}");
		} else {		
			out.println("\tpublic "+f.getType()+" get"+f.getColumnNameFirst()+"() {");
			out.println("\t\treturn "+f.getColumnName()+";");
			out.println("\t}");
		}
	}
	
	private void writeSetMethod(Field f) {
		if (f.isBool()) {
			out.println("\tpublic void set"+f.getColumnNameFirst()+"("+f.getType()+" "+f.getColumnName()+") {");
			if (f.getType().equals("String"))
				out.println("\t\tif (\"0\".equals("+f.getColumnName()+"))");
			else
				out.println("\t\tif ("+f.getColumnName()+" == 0)");	
				
			out.println("\t\t\tthis."+f.getColumnName()+" = false;");
			out.println("\t\telse");
			out.println("\t\t\tthis."+f.getColumnName()+" = true;");
			out.println("\t}");		
			out.println();
			out.println("\tpublic void set"+f.getColumnNameFirst()+"(boolean "+f.getColumnName()+") {");
			out.println("\t\tthis."+f.getColumnName()+" = "+f.getColumnName()+";");
			out.println("\t}");				
		} else {
			out.println("\tpublic void set"+f.getColumnNameFirst()+"("+f.getType()+" "+f.getColumnName()+") {");
			out.println("\t\tthis."+f.getColumnName()+" = "+f.getColumnName()+";");
			out.println("\t}");		
		}
	}
	
	public void close() throws IOException {
		out.println("}");
		out.close();
		outex.println("}");
		outex.close();
	}

	public void writeManyRelations(Table table) {
		List relations = table.getManyReferenceList();
		if (relations == null) return;
				
		for (int i = 0; i < relations.size(); i++) {
			Relation rel = (Relation)relations.get(i);
			String var = rel.getRefTable().toLowerCase();
			String tableName = var;
			
      if (Constants.WITHS) {
			  if (!var.endsWith("s"))
				  var = var + "s";
      }
      			
			String capitalVarName = var.substring(0,1).toUpperCase() + var.substring(1);			
			String capitalTableName = tableName.substring(0,1).toUpperCase() + tableName.substring(1);	
			String col = rel.getColumn().toLowerCase();	
			col = "get" + col.substring(0,1).toUpperCase() + col.substring(1) + "()";	
			
			String myCapitalTableName = table.getName().toLowerCase();
			myCapitalTableName = myCapitalTableName.substring(0,1).toUpperCase() + myCapitalTableName.substring(1);
				
			out.println();	
			out.println("\tprivate SoftReference "+var+" = null;");
			out.println();

			out.println("\tpublic int get"+capitalVarName+"Size() throws SQLException, PoolPropsException {");
			out.println("\t\tList t;");
			out.println();
			out.println("\t\tif ( ("+var+" != null) && ((t = (List)"+var+".get()) != null) ) {");
			out.println("\t\t\treturn t.size();");
			out.println("\t\t} else {");
			out.println("\t\t\treturn "+table.getPackage()+".Db.get"+capitalTableName+"Table().count(\""+rel.getRefColumn()+" = \" + "+col+");");
			out.println("\t\t}");
			out.println("\t}");
			out.println();

			out.println("\tpublic List get"+capitalVarName+"(String whereClause) throws SQLException, PoolPropsException {");
			out.println("\t\tif (whereClause != null)");
			out.println("\t\t\treturn "+table.getPackage()+".Db.get"+capitalTableName+"Table().select(\""+rel.getRefColumn()+" = \" + "+col+" + \" AND \" + whereClause);");
			out.println("\t\telse");
			out.println("\t\t\treturn "+table.getPackage()+".Db.get"+capitalTableName+"Table().select(\""+rel.getRefColumn()+" = \" + "+col+");");			
			out.println("\t}");
			out.println(); 

			out.println("\tpublic boolean has"+capitalVarName+"() throws SQLException, PoolPropsException {");
			out.println("\t\treturn (get"+capitalVarName+"Size() > 0);");
			out.println("\t}");
			
			out.println();
			out.println("\tpublic List get"+capitalVarName+"() throws SQLException, PoolPropsException {");
			out.println("\t\tList resultList;");
			out.println();	
			out.println("\t\tif ("+var+" == null) {");
			out.println("\t\t\tresultList = "+table.getPackage()+".Db.get"+capitalTableName+"Table().select(\""+rel.getRefColumn()+" = \" + "+col+");");
			out.println("\t\t\t"+var+" = new SoftReference(resultList);");
			out.println("\t\t} else {");
			out.println("\t\t\tresultList = (List)"+var+".get();");
		  out.println();
			out.println("\t\t\tif (resultList == null) {");
			out.println("\t\t\t\tresultList = "+table.getPackage()+".Db.get"+capitalTableName+"Table().select(\""+rel.getRefColumn()+" = \" + "+col+");");
			out.println("\t\t\t\t"+var+" = new SoftReference(resultList);");
			out.println("\t\t\t}");
			out.println("\t\t}");
			out.println("\t\treturn resultList;");
			out.println("\t}");
			out.println();
			out.println("\tpublic void invalidate"+capitalVarName+"() {");
			out.println("\t\t"+var+" = null;");
			out.println("\t}");
			out.println();
		}

		if (table.getManyReferenceList() != null) {
			out.println("\tpublic void invalidateBackRelations() throws SQLException, PoolPropsException {");		
			for (int i = 0; i < relations.size(); i++) {
				Relation rel = (Relation)relations.get(i);
				String var = rel.getRefTable().toLowerCase();
				String tableName = var;
				
        if (Constants.WITHS) {
				  if (!var.endsWith("s"))
					  var = var + "s";
        }
        				
				String capitalVarName = var.substring(0,1).toUpperCase() + var.substring(1);			
				String capitalTableName = tableName.substring(0,1).toUpperCase() + tableName.substring(1);	
				
				String myCapitalTableName = table.getName().toLowerCase();
				myCapitalTableName = myCapitalTableName.substring(0,1).toUpperCase() + myCapitalTableName.substring(1);
			
				if (i == 0) {
					out.println("\t\tList resultList = get"+capitalVarName+"();");
				} else {
					out.println("\t\tresultList = get"+capitalVarName+"();");
				}
				out.println("\t\tif (resultList != null) {");
				out.println("\t\t\tIterator it = resultList.iterator();");
				out.println("\t\t\twhile(it.hasNext()) {");
				out.println("\t\t\t\t(("+capitalTableName+")it.next()).invalidate"+myCapitalTableName+"();");
				out.println("\t\t\t}");
				out.println("\t\t}");
				out.println();
			}
			out.println("\t}");
			out.println();
		}
	}
	
	public void writeOneRelations(Table table) {
		List relations = table.getOneReferenceList();		
		if (relations == null) return;
		for (int i = 0; i < relations.size(); i++) {

			Relation rel = (Relation)relations.get(i);
			String var = rel.getRefTable().toLowerCase();
						
			String capitalVarName = var.substring(0,1).toUpperCase() + var.substring(1);			
			String col = rel.getRefColumn().toLowerCase();	
			col = "get" + col.substring(0,1).toUpperCase() + col.substring(1) + "()";	
			
			String myCol = rel.getColumn().toLowerCase();
			myCol = "get" + myCol.substring(0,1).toUpperCase() + myCol.substring(1) + "()";
			
			String myCapitalTableName = table.getName().toLowerCase();
			myCapitalTableName = myCapitalTableName.substring(0,1).toUpperCase() + myCapitalTableName.substring(1);

      if (Constants.WITHS) {
			  if (!myCapitalTableName.endsWith("s"))
				  myCapitalTableName += "s";
      }

			out.println("\tprivate SoftReference "+var+" = null;");
			out.println();
			out.println("\tpublic boolean has"+capitalVarName+"() throws SQLException, PoolPropsException {");
			out.println("\t\treturn (get"+capitalVarName+"() != null);");
			out.println("\t}");			
			out.println();
			out.println("\tpublic "+capitalVarName+" get"+capitalVarName+"() throws SQLException, PoolPropsException {");
			out.println();
			out.println("\t\t"+capitalVarName+" t;");
			out.println();
			out.println("\t\tif ("+var+" == null) {");
			out.println("\t\t\tt = "+table.getPackage()+".Db.get"+capitalVarName+"Table().selectOne(\""+rel.getRefColumn()+" = \" + "+myCol+");");
			out.println("\t\t\t"+var+" = new SoftReference(t);");
			out.println("\t\t} else {");
			out.println("\t\t\tt = ("+capitalVarName+")"+var+".get();");
			out.println();
			out.println("\t\t\tif (t == null) {");
			out.println("\t\t\t\tt = "+table.getPackage()+".Db.get"+capitalVarName+"Table().selectOne(\""+rel.getRefColumn()+" = \" + "+myCol+");");
			out.println("\t\t\t\t"+var+" = new SoftReference(t);");
			out.println("\t\t\t}");
			out.println("\t\t}");
			out.println();
			out.println("\t\treturn t;");
			out.println("\t}");
			out.println();
			out.println("\tpublic void set"+capitalVarName+"("+capitalVarName+" new"+capitalVarName+") throws SQLException, PoolPropsException {");
			out.println("\t\tif ("+var+" != null) {");
			out.println("\t\t\t"+capitalVarName+" t = ("+capitalVarName+")"+var+".get();");
			out.println();
			out.println("\t\t\tif (t != null) {");
			out.println("\t\t\t\tt.invalidate"+myCapitalTableName+"();");
			out.println("\t\t\t}");
			out.println("\t\t}");
			out.println();
			out.println("\t\t"+var+" = new SoftReference(new"+capitalVarName+");");
			out.println();	
			out.println("\t\tif (new"+capitalVarName+" != null) {");
			out.println("\t\t\tnew"+capitalVarName+".invalidate"+myCapitalTableName+"();");
			out.println("\t\t\t"+rel.getColumn().toLowerCase()+" = new"+capitalVarName+"."+col+";");
			out.println("\t\t}"); 
			out.println("\t}"); 
			out.println();
			out.println("\tpublic void invalidate"+capitalVarName+"() {");
			out.println("\t\t"+var+" = null;");
			out.println("\t}");
			out.println();
		}		
	}	
}