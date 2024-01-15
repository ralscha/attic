
import java.util.*;
import java.io.*;

public class SourceFile {
  
  private String name;
  private Set nameSet;
  private List fields;

  public SourceFile(String name) {
    this.name = name;
    fields = new ArrayList();
    nameSet = new HashSet();
  }

  public void addField(Field field) {
    if (!nameSet.contains(field.getFieldName())) {
      fields.add(field);
      nameSet.add(field.getFieldName());
    }
  }

  public Field[] getFields() {
    return (Field[])fields.toArray(new Field[fields.size()]);
  }

  public void write() throws IOException {
    write("  "); //two spaces for indent
  }

  public void write(String indent) throws IOException {

    String capitalName = name.substring(0,1).toUpperCase() + name.substring(1);

    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(capitalName+".java")));
    out.println("import org.apache.struts.action.ActionForm;");
    out.println();
    out.println("public final class "+capitalName+" implements ActionForm  {");
    out.println();

    //Declaration
    Field[] fields = getFields();
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getType().equals("checkbox"))
        out.println(indent+"private String[] "+fields[i].getFieldName()+" = new String[0];");
      else
        out.println(indent+"private String "+fields[i].getFieldName()+" = \"\";");
    }
  
    //Get/Set Methods
    for (int i = 0; i < fields.length; i++) {
      out.println();
      if (fields[i].getType().equals("checkbox"))
        out.println(indent+"public String[] get"+fields[i].getMethodName()+"() {");
      else
        out.println(indent+"public String get"+fields[i].getMethodName()+"() {");

      out.println(indent+indent+"return "+fields[i].getFieldName()+";");
      out.println(indent+"}");
      out.println();
      
      if (fields[i].getType().equals("checkbox"))
        out.println(indent+"public void set"+fields[i].getMethodName()
                  + "(String[] "+fields[i].getFieldName()+") {");
      else 
        out.println(indent+"public void set"+fields[i].getMethodName()
                  + "(String "+fields[i].getFieldName()+") {");

      out.println(indent+indent+"if ("+fields[i].getFieldName()+" != null)");

	    out.println(indent+indent+indent+"this."+fields[i].getFieldName()
	              +" = "+fields[i].getFieldName()+";");

	    out.println(indent+indent+"else");

      if (fields[i].getType().equals("checkbox"))
  	    out.println(indent+indent+indent+"this."+fields[i].getFieldName()+" = new String[0];");
      else
        out.println(indent+indent+indent+"this."+fields[i].getFieldName()+" = \"\";");
      
      out.println(indent+"}");

    } // for fields.length

    out.println();
    out.println("}");
    out.close();

  }
    

}