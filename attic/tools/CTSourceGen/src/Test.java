import java.io.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

public class Test {

  public static void main(String[] args) {

    try {  
      Velocity.init();
      VelocityContext context = new VelocityContext();
      context.put("objectName", "ONE");
      
      
      StringWriter w = new StringWriter();

      Velocity.evaluate(context, w, "log", "Edit${objectName}Action.java");
      Velocity.evaluate(context, w, "log", "Edit${objectName}Action.java");
      Velocity.evaluate(context, w, "log", "Edit${objectName}Action.java");
      Velocity.evaluate(context, w, "log", "Edit${objectName}Action.java");                  
      String output = w.getBuffer().toString();
  
      System.out.println(output);

    } catch (Exception e) {
      e.printStackTrace();
    }
  
  }
}
