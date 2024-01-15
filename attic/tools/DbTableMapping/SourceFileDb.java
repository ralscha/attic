import java.io.*;
import java.util.*;

public class SourceFileDb {

	
	public SourceFileDb(List tableList) throws IOException {
		
		Iterator it = tableList.iterator();
		if (it.hasNext()) {
		
			Table table = (Table)it.next();
		
			String path = "";
			if (table.getOutput() != null) 
				path = table.getOutput();
		
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path + "Db.java")));
					
			out.println("package "+table.getPackage()+";");
			out.println();
			out.println("public class Db {");
			out.println();
			out.println("\tprivate Db() { }");
			out.println();
			
			it = tableList.iterator();
			while (it.hasNext())	{
				table = (Table)it.next();
				String tableName = table.getName().toLowerCase();
				String capTableName = tableName.substring(0,1).toUpperCase() + tableName.substring(1);
				out.println("\tprivate static "+capTableName+"Table "+tableName+"Table;");
			} 
			out.println();
			
			it = tableList.iterator();
			while (it.hasNext()) {
				table = (Table)it.next();
				String tableName = table.getName().toLowerCase();
				String capTableName = tableName.substring(0,1).toUpperCase() + tableName.substring(1);
				
				out.println("\tpublic static "+capTableName+"Table get"+capTableName+"Table() {");
				out.println("\t\tif ("+tableName+"Table == null)");
				out.println("\t\t\t"+tableName+"Table = new "+capTableName+"Table();");
				out.println();
				out.println("\t\treturn "+tableName+"Table;");
				out.println("\t}");
				out.println(); 
			}
			out.println("}");
			out.close();
		}		
	}
	
	public static void buildSource(List tableList) throws IOException {
		new SourceFileDb(tableList);
	}
	
}