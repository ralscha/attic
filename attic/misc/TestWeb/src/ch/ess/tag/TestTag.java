package ch.ess.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;



public class TestTag extends TagSupport {

  private String arg1;
  private String arg2;
  private String arg3;
  
  @Override
  public void setPageContext(PageContext arg0) {
    System.out.println("-------------");
    System.out.println("SET PAGECONTEXT: " + this);
    super.setPageContext(arg0);
  }
  
  
  @Override
  public int doStartTag() throws JspException {
    
    System.out.println("DO START TAG : " + this);
    JspWriter writer = pageContext.getOut();

    try {

      writer.println("ARG1: " + arg1 + "<br>");
      writer.println("ARG2: " + arg2 + "<br>");
      writer.println("ARG3: " + arg3 + "<br>");
      
    } catch (IOException e) {
      throw new JspException(e.toString());
    }
    
    return SKIP_BODY;
  }
  
  @Override
  public void release() {
    System.out.println("RELEASE");
    arg1 = null;
    arg2 = null;
    arg3 = null;    
  }
  
  
  public String getArg1() {
    return arg1;
  }
  public void setArg1(String arg1) {
    System.out.println("SET ARG1: " + this);
    this.arg1 = arg1;
  }
  public String getArg2() {
    return arg2;
  }
  public void setArg2(String arg2) {
    System.out.println("SET ARG2: " + this);
    this.arg2 = arg2;
  }
  public String getArg3() {
    return arg3;
  }
  public void setArg3(String arg3) {
    System.out.println("SET ARG3: " + this);
    this.arg3 = arg3;
  }
  

  
  
}
