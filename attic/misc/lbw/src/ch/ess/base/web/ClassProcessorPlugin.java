package ch.ess.base.web;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.validator.FormSet;
import org.apache.commons.validator.ValidatorResources;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.validator.ValidatorPlugIn;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.base.annotation.ClassProcessor;
import ch.ess.base.annotation.option.OptionAnnotationProcessor;
import ch.ess.base.annotation.struts.ActionNameProcessor;
import ch.ess.base.annotation.struts.SpringActionAnnotationProcessor;


public class ClassProcessorPlugin implements PlugIn {

  public void destroy() {
    // no action
  }

  public void init(ActionServlet servlet, ModuleConfig moduleConfig) {

    StaticApplicationContext webContext = (StaticApplicationContext)servlet.getServletContext()
    .getAttribute(WebConstants.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext());

    if (webContext == null) {
      webContext = new StaticApplicationContext(ctx);
      servlet.getServletContext().setAttribute(WebConstants.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);
    }
    
    
    Set<String> locationFilters = new HashSet<String>();
    locationFilters.add("classes");
    
    ClassProcessor processor = new ClassProcessor(locationFilters);

    processor.addAnnotationProcessor(new OptionAnnotationProcessor(webContext));
    
    FormSet formSet = new FormSet();

    processor.addNameProcessor(new ActionNameProcessor(moduleConfig, formSet));
    processor.addAnnotationProcessor(new SpringActionAnnotationProcessor(webContext));
       
    processor.process();
           
    ValidatorResources vr = (ValidatorResources)servlet.getServletContext().getAttribute(
        ValidatorPlugIn.VALIDATOR_KEY + moduleConfig.getPrefix());
    
    vr.addFormSet(formSet);    
    vr.process();


  }

}
