package org.apache.struts.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.FormSet;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.Var;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.validator.ValidatorPlugIn;


public class ActionAnnotationPlugin implements PlugIn {

  private static final String ACTION = "Action";

  private ActionServlet servlet;
  private ModuleConfig module;
  private String packageroot;
  private String delegate;

  public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) {
    
    servlet = actionServlet;
    module = moduleConfig;

    if (StringUtils.isBlank(packageroot)) {
      packageroot = "";
    }

    String root = packageroot;
    if (StringUtils.isEmpty(packageroot)) {
      root = "/WEB-INF/classes";
    } 
    
    Set<String> classes = actionServlet.getServletContext().getResourcePaths(root);
    traverse(actionServlet.getServletContext(), classes, "", root);

    ValidatorResources vr = (ValidatorResources)servlet.getServletContext().getAttribute(
        ValidatorPlugIn.VALIDATOR_KEY + moduleConfig.getPrefix());
    vr.process();
       
  }

  public void destroy() {
    //no action
  }

  private void traverse(ServletContext servletContext, Set<String>ressources, String parent, String root) {
    
    for (String clazz : ressources) {
      
      if (clazz.endsWith(".class")) {
        int rootLength = root.length();
        if (!root.endsWith("/")) {
          rootLength++;
        }
        String cl = clazz.substring(rootLength, clazz.length()-".class".length());
        
        if (cl.endsWith(ACTION)) {

          try {
            registerAction(module, Class.forName(parent + cl));
          } catch (ClassNotFoundException e) {
            servlet.log(parent + cl + " not found");
          }

        }
        
        
      } else if (clazz.endsWith("/")) {
        
        int pos = clazz.lastIndexOf("/", clazz.length()-2);
        if (pos != -1) {
          String add = clazz.substring(pos+1, clazz.length()-1);
          Set<String> classes = servletContext.getResourcePaths(clazz);
          traverse(servletContext, classes, parent + add + ".", clazz);
        }
      }
      
    }
    
  }
  
//  private void traverse(File f, String pack) {
//    if (!f.isDirectory()) {
//      return;
//    }
//
//    for (File sub : f.listFiles()) {
//      String name = pack + (pack.equals("") ? "" : ".") + sub.getName();
//      if (sub.isDirectory()) {
//        traverse(sub, name);
//      } else {
//
//        if (!name.endsWith(ACTION + ".class")) {
//          continue;
//        }
//
//        String cname = name.substring(0, name.length() - ".class".length());
//
//        try {
//          registerAction(module, Class.forName(cname));
//        } catch (ClassNotFoundException e) {
//          servlet.log(cname + " not found");
//        }
//      }
//    }
//  }

  private void registerAction(ModuleConfig moduleConfig, Class clazz) {
    if (clazz.isInterface()) {
      return;
    }

    if (!isSubclass(clazz, Action.class)) {
      return;
    }

    List<StrutsAction> salist = new ArrayList<StrutsAction>();
    StrutsActions sas = (StrutsActions)clazz.getAnnotation(StrutsActions.class);
    if (sas != null) {
      StrutsAction[] saa = sas.actions();
      if (saa != null) {
        for (StrutsAction sa : saa)
          salist.add(sa);
      }
    }
    StrutsAction sac = (StrutsAction)clazz.getAnnotation(StrutsAction.class);
    if (sac != null) {
      salist.add(sac);
    }

    for (StrutsAction sa : salist) {
      ActionMapping ac = new ActionMapping();
      String fqn = clazz.getName();
      ac.setType(StringUtils.isBlank(delegate) ? fqn : delegate);
      ac.setUnknown(sa.unknown());
      ac.setParameter(sa.parameter());
      
      if (StringUtils.isNotBlank(sa.roles())) {
        ac.setRoles(sa.roles());
      }

      Class form = sa.form();
      String input = sa.input();
      ac.setScope(sa.scope().toString());
      ac.setInput(input);
      ac.setPath(sa.path());

      moduleConfig.addActionConfig(ac);

      servlet.log(String.format("%s -> %s", fqn, ac.getPath()));

      String formName = null;
      if (form != Object.class) {
        formName = registerForm(moduleConfig, form);
      } else if (fqn.endsWith(ACTION)) {
        String formClass = getFormFromAction(fqn);
        try {
          form = Class.forName(formClass);
          formName = registerForm(moduleConfig, form);
        } catch (ClassNotFoundException e) {
          //no action
        }
      }

      if (StringUtils.isNotBlank(formName)) {
        ac.setName(formName);
        servlet.log(formName);
        if (StringUtils.isNotBlank(input)) {
          ac.setValidate(sa.validate());
        } else {
          ac.setValidate(false);
        }
      }

      Forward[] forwards = sa.forwards();
      if (forwards != null) {
        for (Forward f : forwards) {
          ActionForward af = new ActionForward();
          af.setName(f.name());
          af.setPath(f.path());
          af.setRedirect(f.redirect());
          ac.addForwardConfig(af);
        }
      }

    }

  }

  private String registerForm(ModuleConfig moduleConfig, Class c) {
    if (c.isInterface()) {
      return null;
    }

    if (!isSubclass(c, ActionForm.class)) {
      return null;
    }

    StrutsForm sf = (StrutsForm)c.getAnnotation(StrutsForm.class);
    String fqn = c.getName();

    String formName = null;
    if (sf != null) {
      formName = sf.name();
    }

    if (StringUtils.isBlank(formName)) {
      formName = getClassName(fqn);
    }

    FormBeanConfig formBean = new FormBeanConfig();
    formBean.setName(formName);

    ValidatorResources vr = (ValidatorResources)servlet.getServletContext().getAttribute(
        ValidatorPlugIn.VALIDATOR_KEY + moduleConfig.getPrefix());
    FormSet formSet = new FormSet();
    formBean.setType(fqn);

    Form fm = getValidatorFormFromSetter(c, formName);
    servlet.log("form:" + fqn);

    if (fm != null) {
      formSet.addForm(fm);
      vr.addFormSet(formSet);
    }

    moduleConfig.addFormBeanConfig(formBean);
    return formName;
  }

  private Form getValidatorFormFromSetter(Class c, String formName) {
    Map<String, ValidatorField> vm = new LinkedHashMap<String, ValidatorField>();
    for (Method method : c.getDeclaredMethods()) {
      String methodName = method.getName();

      String propName = getSetterPropertyName(methodName);
      if (propName == null) {
        continue;
      }

      ValidatorField vf = method.getAnnotation(ValidatorField.class);
      if (vf == null) {
        continue;
      }

      vm.put(propName, vf);
    }
    return getValidatorForm(vm, formName);
  }

  private Form getValidatorForm(Map<String, ValidatorField> vm, String formName) {
    Form fm = new Form();
    fm.setName(formName);
    boolean registed = false;
    for (Map.Entry<String, ValidatorField> me : vm.entrySet()) {

      String propName = me.getKey();
      if (propName == null) {
        continue;
      }

      ValidatorField vf = me.getValue();
      if (vf == null) {
        continue;
      }

      Field fld = new Field();
      fld.setProperty(propName);

      int args = 0;
      String depends = "";

      Arg arg = new Arg();
      String key = vf.key();
      boolean resource = vf.resource();
      
      if (StringUtils.isBlank(key)) {
        key = formName + "." + propName;
        resource = true;
      }
      
      arg.setKey(key);
      arg.setResource(resource);
      arg.setPosition(args);
      fld.addArg(arg);
      ++args;
      servlet.log(String.format("%s:%b", key, resource));

      //required
      if (vf.required()) {
        
        if (depends.length() > 0) {
          depends += ".";
        }
        
        depends += "required";
      }

      for (Validator val : vf.validators()) {
        String valiName = val.name();
        
        if (depends.length() > 0) {
          depends += ",";
        }
        
        depends += valiName;

        String valiValue = val.value();
        if (StringUtils.isNotBlank(valiValue)) {
          Var v = new Var();
          v.setName(valiName);
          v.setValue(valiValue);
          fld.addVar(v);
          if (StringUtils.isNotBlank(val.key()) || val.arg()) {
            arg = new Arg();
            arg.setName(valiName);
            if (StringUtils.isBlank(val.key())) {
              arg.setKey("${var:" + valiName + "}");
              arg.setResource(false);
            } else {
              arg.setKey(val.key());
              arg.setResource(val.resource());
            }
            arg.setPosition(args);
            fld.addArg(arg);
            ++args;
          }
        }

        for (Variable var : val.vars()) {
          String name = var.name();
          Var v = new Var();
          v.setName(name);
          v.setValue(var.value());
          fld.addVar(v);
      
          if (StringUtils.isNotBlank(var.key()) || var.arg()) {
            arg = new Arg();
            arg.setName(valiName);
            if (StringUtils.isBlank(var.key())) {
              arg.setKey("${var:" + name + "}");
              arg.setResource(false);
            } else {
              arg.setKey(var.key());
              arg.setResource(var.resource());
            }
            arg.setPosition(args);
            fld.addArg(arg);
            ++args;
          }
        }
      }

      if (depends.length() == 0) {
        continue;
      }

      servlet.log(String.format("%s:%s", formName, depends));
      fld.setDepends(depends);
      fm.addField(fld);
      registed = true;
    }
    if (registed) {
      return fm;
    }

    return null;
  }

  private String getFormFromAction(String actionClass) {
    if (actionClass.endsWith(ACTION) && !actionClass.equals(ACTION)) {
      actionClass = actionClass.substring(0, actionClass.length() - ACTION.length());
    }

    return actionClass + "Form";
  }

  private boolean isSubclass(Class target, Class superclass) {
    for (Class s = target; s != Object.class; s = s.getSuperclass()) {
      if (s == superclass)
        return true;
    }
    return false;
  }

  private String getClassName(String fqn) {
    String className = fqn;
    if (className.indexOf('.') > 0) {
      className = className.substring(className.lastIndexOf('.') + 1);
    }
    return className;
  }

  private String getSetterPropertyName(String setterName) {
    if (!setterName.startsWith("set"))
      return null;
    if (setterName.length() <= 3)
      return null;
    if (setterName.charAt(3) < 'A' || setterName.charAt(3) > 'Z')
      return null;
    return setterName.substring(3, 4).toLowerCase() + setterName.substring(4);
  }

//  private String normalizePath(String path) {
//    String ret = path;
//    if (ret == null)
//      return "";
//    if (!ret.startsWith("/"))
//      ret = "/" + ret;
//    if (ret.endsWith("/"))
//      ret = ret.substring(0, ret.length() - 1);
//    if (ret.equals("/"))
//      ret = "";
//    return ret;
//  }

  public void setPackageroot(String packageroot) {
    this.packageroot = packageroot;
  }

  public void setDelegate(String delegate) {
    this.delegate = delegate;
  }
}
