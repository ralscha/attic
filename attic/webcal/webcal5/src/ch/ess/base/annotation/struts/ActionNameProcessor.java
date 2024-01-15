package ch.ess.base.annotation.struts;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.FormSet;
import org.apache.commons.validator.Var;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;

import ch.ess.base.annotation.NameProcessor;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.AbstractTreeListAction;
import ch.ess.base.web.MapForm;

public class ActionNameProcessor implements NameProcessor {

  private static final Log log = LogFactory.getLog(ActionNameProcessor.class);
  
  private ModuleConfig module;
  private FormSet formSet;

  public ActionNameProcessor(ModuleConfig module, FormSet formSet) {
    this.module = module;
    this.formSet = formSet;
  }

  public boolean matches(String className) {
    return (className.endsWith(ProcessorUtil.ACTION));
  }

  public void process(Class< ? > clazz) {

    if (isAction(clazz)) {

      List<StrutsAction> salist = ProcessorUtil.extractStrutsActionAnnotation(clazz);
      
      if (!salist.isEmpty()) {
        registerAnnotatedAction(clazz, salist);
      } else {
        registerAction(clazz);
      }
    }

  }

  private void registerAnnotatedAction(Class< ? > clazz, List<StrutsAction> salist) {

    for (StrutsAction sa : salist) {
      ActionMapping ac = new ActionMapping();
      String fqn = clazz.getName();
      ac.setType(fqn);
      ac.setUnknown(sa.unknown());
      ac.setParameter(sa.parameter());

      if (StringUtils.isNotBlank(sa.roles())) {
        ac.setRoles(sa.roles());
      }

      Class< ? > form = sa.form();
      String input = sa.input();
      ac.setScope(sa.scope().toString());
      ac.setInput(input);
      ac.setPath(sa.path());

      module.addActionConfig(ac);

      log.info(String.format("%s -> %s", fqn, ac.getPath()));

      String formName = null;
      if (form != Object.class) {
        formName = registerForm(form);
      } else if (fqn.endsWith(ProcessorUtil.ACTION)) {
        String formClass = getFormFromAction(fqn);
        try {
          form = Class.forName(formClass);
          formName = registerForm(form);
        } catch (ClassNotFoundException e) {
          //no action
        }
      }

      if (StringUtils.isNotBlank(formName)) {
        ac.setName(formName);
        log.info(formName);
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

  @SuppressWarnings("unchecked")
  private void registerAction(Class clazz) {
    
    String base = ProcessorUtil.getPath(clazz);
    String path = "/" + base;
    String input = path.toLowerCase() + ".jsp";

    String annotationRole = null;
    if (clazz.isAnnotationPresent(Role.class)) {
      Role role = (Role)clazz.getAnnotation(Role.class);
      annotationRole = role.value();
      if (!annotationRole.startsWith("$")) {
        annotationRole = "$" + annotationRole;
      }
    }
    
    if (ClassUtils.isAssignable(clazz, AbstractEditAction.class)) {
      String part = base.substring(0, base.length() - "Edit".length());
      String role;
      if (annotationRole != null) {
        role = annotationRole;
      } else {
        role = "$" + part.toLowerCase();
      }
      String back = "/" + part + "List.do";
      String formName = part.substring(0, 1).toUpperCase() + part.substring(1) + "Form";

      ActionMapping ac = new ActionMapping();
      ac.setPath(path);
      ac.setType(clazz.getName());
      ac.setUnknown(false);
      ac.setRoles(role);
      ac.setInput(input);
      ac.setScope("session");

      String packageName = StringUtils.replace(ClassUtils.getPackageName(clazz), "/", ".");

      try {
        Class form = Class.forName(packageName + "." + formName);
        String formClass = registerForm(form);
        ac.setName(formClass);
      } catch (ClassNotFoundException e) {
        log.info("configureNotAnnotatedAction", e);
      }
      ac.setValidate(false);

      ActionForward af = new ActionForward();
      af.setName("back");
      af.setPath(back);
      af.setRedirect(true);
      ac.addForwardConfig(af);

      module.addActionConfig(ac);

    } else if (ClassUtils.isAssignable(clazz, AbstractListAction.class) || ClassUtils.isAssignable(clazz, AbstractTreeListAction.class)) {

      String part = base.substring(0, base.length() - "List".length());
      
      String role;
      if (annotationRole != null) {
        role = annotationRole;
      } else {
        role = "$" + part.toLowerCase();
      }
      
      String edit = "/" + part + "Edit.do?id={0}";
      String copy = "/" + part + "Edit.do?copyid={0}";
      String create = "/" + part + "Edit.do";

      ActionMapping ac = new ActionMapping();
      ac.setPath(path);
      ac.setType(clazz.getName());
      ac.setUnknown(false);
      ac.setRoles(role);
      ac.setInput(input);
      ac.setScope("session");

      String formClass = registerForm(MapForm.class);
      ac.setName(formClass);
      ac.setValidate(false);

      ActionForward af = new ActionForward();
      af.setName("edit");
      af.setPath(edit);
      af.setRedirect(true);
      ac.addForwardConfig(af);

      af = new ActionForward();
      af.setName("copy");
      af.setPath(copy);
      af.setRedirect(true);
      ac.addForwardConfig(af);
      
      af = new ActionForward();
      af.setName("create");
      af.setPath(create);
      af.setRedirect(true);
      ac.addForwardConfig(af);

      module.addActionConfig(ac);

    }
    
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
      log.info(String.format("%s:%b", key, resource));

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

      log.info(String.format("%s:%s", formName, depends));
      fld.setDepends(depends);
      fm.addField(fld);
      registed = true;
    }
    if (registed) {
      return fm;
    }

    return null;
  }

  private boolean isAction(Class< ? > clazz) {
    if (clazz.isInterface()) {
      return false;
    }

    if (Modifier.isAbstract(clazz.getModifiers())) {
      return false;
    }

    if (!ClassUtils.isAssignable(clazz, Action.class)) {
      return false;
    }

    return true;
  }

  private String getFormFromAction(final String actionClass) {
    String ac = actionClass;
    if (actionClass.endsWith(ProcessorUtil.ACTION) && !actionClass.equals(ProcessorUtil.ACTION)) {
      ac = actionClass.substring(0, actionClass.length() - ProcessorUtil.ACTION.length());
    }

    return ac + "Form";
  }

  private Form getValidatorFormFromSetter(Class< ? > c, String formName) {
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

  private String getSetterPropertyName(String setterName) {
    if (!setterName.startsWith("set")) {
      return null;
    }
    if (setterName.length() <= 3) {
      return null;
    }
    if (setterName.charAt(3) < 'A' || setterName.charAt(3) > 'Z') {
      return null;
    }
    return setterName.substring(3, 4).toLowerCase() + setterName.substring(4);
  }

  @SuppressWarnings("unchecked")
  private String registerForm(Class clazz) {
    if (clazz.isInterface() || !ClassUtils.isAssignable(clazz, ActionForm.class)) {
      return null;
    }

    StrutsForm sf = (StrutsForm)clazz.getAnnotation(StrutsForm.class);
    String fqn = clazz.getName();

    String formName = null;
    if (sf != null) {
      formName = sf.name();
    }

    if (StringUtils.isBlank(formName)) {
      formName = ProcessorUtil.getClassName(fqn);
    }

    FormBeanConfig formBean = new FormBeanConfig();
    formBean.setName(formName);

    formBean.setType(fqn);

    Form fm = getValidatorFormFromSetter(clazz, formName);
    log.info("form:" + fqn);

    if (fm != null) {
      formSet.addForm(fm);
    }

    module.addFormBeanConfig(formBean);
    return formName;
  }
}
