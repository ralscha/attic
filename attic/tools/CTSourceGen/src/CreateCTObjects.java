import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;


public class CreateCTObjects {
  
  private static String[] templateFiles = {"EditObjectAction.vm",
                                           "ListObjectsAction.vm",
                                           "ListObjectsForm.vm",
                                           "ObjectForm.vm",                                           
                                           "editobjectjsp.vm",
                                           "objectsjsp.vm",
                                           "strutsconfig.vm",
                                           "ObjectDeleter.vm",
                                           "ObjectsFinder.vm",
                                           "res.vm"};

  private static String[] outputFiles   = {
                                           "Edit${objectName}Action.java",
                                           "List${dbClassName}Action.java",
                                           "List${dbClassName}Form.java",
                                           "${objectName}Form.java",                                           
                                           "edit${objectLowerLowerName}.jsp",
                                           "${dbClassLowerLowerName}.jsp",
                                           "strutsconfig.txt",
                                           "${objectName}Deleter.java",
                                           "${dbClassName}Finder.java",
                                           "ct.props"};

  
  public static void main(String args[]) {

    try {
      File propsFile = new File(args[0]);
      Properties contextProps = new Properties();
      FileInputStream is = new FileInputStream(propsFile);
      contextProps.load(is);
      is.close();
  
      Properties props = new Properties();
      props.put("resource.loader", "class");
      props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

      Velocity.init(props);

      /* lets make a Context and put data into it */
  
      VelocityContext context = new VelocityContext();
      Iterator it = contextProps.keySet().iterator();
      while (it.hasNext()) {
        String key = (String)it.next();
        String value = (String)contextProps.get(key);
        
        if (key.equals("showConstants")) {
          List constantsList = new ArrayList();
          StringTokenizer st = new StringTokenizer(value, ",");
          while (st.hasMoreTokens()) {
            String token = st.nextToken();
            constantsList.add(token);
          }
          context.put("constantsList", constantsList);
        } else if (key.equals("showConstantsMessageKey")) {
          List cstKeyList = new ArrayList();
          StringTokenizer st = new StringTokenizer(value, ",");
          while (st.hasMoreTokens()) {
            String token = st.nextToken();
            cstKeyList.add(token);
          }
          context.put("constantsMsgKeyList", cstKeyList);
          System.out.println("set: " + cstKeyList.size());
        } else {        
          context.put(key, value);      
        }
      }
   
  
      for (int i = 0; i < templateFiles.length; i++) {
        
        StringWriter w = new StringWriter();
        Velocity.evaluate(context, w, "output", outputFiles[i]);
        String output = w.getBuffer().toString();
          
        
        PrintWriter pw = new PrintWriter(new FileWriter("output/"+output));
        Velocity.mergeTemplate("template/"+templateFiles[i], "UTF-8", context, pw);
        pw.close();
      }

      

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
