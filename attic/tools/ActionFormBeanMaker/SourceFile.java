
import java.util.*;
import java.io.*;

public class SourceFile {
  
  private String className;
  private String packageName;
  private Set nameSet;
  private List fields;

  public static String TAG_CHECKBOX = "struts:checkbox";
  public static String TAG_HIDDEN   = "struts:hidden";
  public static String TAG_MULTIBOX = "struts:multibox";
  public static String TAG_RADIO    = "struts:radio";
  public static String TAG_SELECT   = "struts:select";
  public static String TAG_TEXT     = "struts:text";
  public static String TAG_TEXTAREA = "struts:textarea";
  public static Set TAG_SET = new HashSet();

  static {
    TAG_SET.add(TAG_CHECKBOX);
    TAG_SET.add(TAG_HIDDEN);
    TAG_SET.add(TAG_MULTIBOX);
    TAG_SET.add(TAG_RADIO);
    TAG_SET.add(TAG_SELECT);
    TAG_SET.add(TAG_TEXT);
    TAG_SET.add(TAG_TEXTAREA);
  }


  public SourceFile(String fqn) {
	  int pos = fqn.lastIndexOf(".");
	  if (pos != -1) {
      className = fqn.substring(pos+1);
      packageName = fqn.substring(0, pos);		
	  } else {
	    className = fqn;
      packageName = null;
	  }
    
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

    String capitalName = className.substring(0,1).toUpperCase() + className.substring(1);

    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(capitalName+".java")));
    
    if (packageName != null) {
      out.println("package "+packageName+";");
      out.println();      
    }

    out.println("import javax.servlet.http.*;");
    out.println("import org.apache.struts.action.*;");
    out.println();
    out.println("public final class "+capitalName+" extends ActionForm  {");

    out.println();

    //Declaration
    Field[] fields = getFields();
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getType().equals(TAG_CHECKBOX))
        out.println(indent+"private boolean "+fields[i].getFieldName()+";");
      else if ( (fields[i].getType().equals(TAG_MULTIBOX)) ||
                (fields[i].getType().equals(TAG_SELECT) && (fields[i].isMultiple())) ) 
        out.println(indent+"private String[] "+fields[i].getFieldName()+";");
      else
        out.println(indent+"private String "+fields[i].getFieldName()+";");
    }
    out.println();

  // struts:checkbox               boolean
  // struts:hidden                 String
  // struts:multibox               String[]
  // struts:radio                  String
  // struts:select                 String
  // struts:select && multiple     String[] 
  // struts:text                   String
  // struts:textarea               String


    //Constructor
    out.println(indent+"public "+capitalName+"() {");
    out.println(indent+indent+"reset();");
    out.println(indent+"}");
  
    //Get/Set Methods
    for (int i = 0; i < fields.length; i++) {
      out.println();

      if (fields[i].getType().equals(TAG_CHECKBOX)) {
        out.println(indent+"public boolean get"+fields[i].getMethodName()+"() {");  
      } else if ( (fields[i].getType().equals(TAG_MULTIBOX)) ||
                (fields[i].getType().equals(TAG_SELECT) && (fields[i].isMultiple())) ) {
        out.println(indent+"public String[] get"+fields[i].getMethodName()+"() {");
      } else {
        out.println(indent+"public String get"+fields[i].getMethodName()+"() {");
      }
        
      out.println(indent+indent+"return "+fields[i].getFieldName()+";");
      out.println(indent+"}");
      out.println();
      
      if (fields[i].getType().equals(TAG_CHECKBOX)) {
        out.println(indent+"public void set"+fields[i].getMethodName()
                  + "(boolean "+fields[i].getFieldName()+") {");              
      } else if ( (fields[i].getType().equals(TAG_MULTIBOX)) ||
                (fields[i].getType().equals(TAG_SELECT) && (fields[i].isMultiple())) ) {
         out.println(indent+"public void set"+fields[i].getMethodName()
                  + "(String[] "+fields[i].getFieldName()+") {");       
      } else {
        out.println(indent+"public void set"+fields[i].getMethodName()
                  + "(String "+fields[i].getFieldName()+") {");        
      }

      if (!fields[i].getType().equals(TAG_CHECKBOX)) {       

	      out.println(indent+indent+"this."+fields[i].getFieldName()
	                +" = "+fields[i].getFieldName()+";");

      } else {
        out.println(indent+indent+"this."+fields[i].getFieldName()
                  +" = "+fields[i].getFieldName()+";");  
      }      
      out.println(indent+"}");

    } // for fields.length

    out.println();

    
    out.println(indent+"public void reset(ActionMapping mapping, HttpServletRequest request) {");
    out.println(indent+indent+"reset();");
    out.println(indent+"}");
    out.println();

    out.println(indent+"private void reset() {");
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getType().equals(TAG_CHECKBOX))
        out.println(indent+indent+fields[i].getFieldName()+" = false;");     
      else
        out.println(indent+indent+fields[i].getFieldName()+" = null;");
    }
    out.println(indent+"}");
    
    out.println();
         
    out.println(indent+"public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {");

    out.println(indent+indent+"ActionErrors errors = new ActionErrors();");
    out.println(indent+indent+"//errors.add(\"joker\", new ActionError(\"joker.no.input\"));");
    out.println(indent+indent+"//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(\"joker.wrong.input\"));");
    out.println(indent+indent+"return errors;");

    out.println(indent+"}");
    
    out.println();

    out.println(indent+"public String toString() {");
    out.println(indent+indent+"StringBuffer buffer = new StringBuffer(500);");

    out.println(indent+indent+"buffer.append(super.toString()).append(\";\");");

    for (int i = 0; i < fields.length; i++) {

      if ( (fields[i].getType().equals(TAG_MULTIBOX)) ||
           (fields[i].getType().equals(TAG_SELECT) && (fields[i].isMultiple())) ) {
        
        out.println(indent+indent+"for (int i = 0; i < "+fields[i].getFieldName()+".length; i++) {");
        out.println(indent+indent+indent+"buffer.append(\""+fields[i].getFieldName()+"[\").append(i).append(\"] = \").append(this."+fields[i].getFieldName()+"[i]).append(\";\");");
        out.println(indent+indent+"}");

      } else {
        out.println(indent+indent+"buffer.append(\""+fields[i].getFieldName()+" = \").append(this."+fields[i].getFieldName()+").append(\";\");");
      }
    }
    out.println(indent+indent+"return buffer.toString();");
    out.println(indent+"}");

    out.println();
    out.println("}");
    out.close();

  }
    

}