package ch.ess.base.web;

import org.springframework.context.support.StaticApplicationContext;

import com.cc.framework.ui.control.ControlActionDef;

public final class WebConstants {

  public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = StaticApplicationContext.class + ".ch.ess.web";

  public static final String SESSION_CRUMBS_CONTROL = "crumbs";
  public static final String SESSION_CALL_STACK = "ch.ess.web.callstack";
  
  public static final ControlActionDef ACTION_COPY = new ControlActionDef("Copy", new Class[] {
      java.lang.String.class
  }); 
  
}
