package test;

import java.io.Serializable;
import java.util.Date;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.Name;

@Name("helloAction")
@TideEnabled
public class HelloAction implements Serializable {
    
  public String doSomething() {
    return new Date().toString();
  }
  
}