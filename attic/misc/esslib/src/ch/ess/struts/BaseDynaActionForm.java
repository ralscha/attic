package ch.ess.struts;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

public class BaseDynaActionForm extends DynaActionForm {
  
  private boolean mutable = true;
  
  public String getString(String name) {
    return (String)this.get(name);
  }
  
  public String getString(String name, int ix) {
    return (String)this.get(name, ix);
  }
  
  public Integer getInteger(String name) {
    return (Integer)this.get(name);
  }

  public Integer getInteger(String name, int ix) {
    return (Integer)this.get(name, ix);
  }  



  public boolean isMutable() {
    return mutable;
  }

  public void setMutable(boolean mutable) {    
    this.mutable = mutable;
  }


  public void reset(ActionMapping arg0, HttpServletRequest arg1) {
    if (mutable) {
      super.reset(arg0, arg1);
    }
  }

  public void reset(ActionMapping arg0, ServletRequest arg1) {
    if (mutable) {
      super.reset(arg0, arg1);
    }
  }

  public void set(String arg0, int arg1, Object arg2) {
    if (mutable) {
      super.set(arg0, arg1, arg2);
    }
  }

  public void set(String arg0, Object arg1) {
    if (mutable) {
      super.set(arg0, arg1);
    }
  }

  public void set(String arg0, String arg1, Object arg2) {
    if (mutable) {
      super.set(arg0, arg1, arg2);
    }
  }

}
