package ch.ess.base.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.chain.Constants;
import org.apache.struts.chain.commands.AbstractCreateAction;
import org.apache.struts.chain.commands.util.ClassUtils;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;
import org.springframework.context.ApplicationContext;

public class CreateAction extends AbstractCreateAction {

  @Override
  protected synchronized Action getAction(ActionContext context, String type, ActionConfig actionConfig)
      throws Exception {

    ServletActionContext servletActionContext = (ServletActionContext)context;

    ApplicationContext actionContext = (ApplicationContext)servletActionContext.getContext().getAttribute(
        WebConstants.WEB_APPLICATION_CONTEXT_ATTRIBUTE);

    String beanName = determineActionBeanName(actionConfig);
    if (actionContext.containsBean(beanName)) {
      return (Action)actionContext.getBean(beanName, Action.class);
    }

    return defaultGetAction(context, type, actionConfig);

  }

  private String determineActionBeanName(final ActionConfig mapping) {
    String prefix = mapping.getModuleConfig().getPrefix();
    String path = mapping.getPath();
    return (prefix != null && prefix.length() > 0) ? prefix + path : path;
  }

  @SuppressWarnings("unchecked")
  private synchronized Action defaultGetAction(ActionContext context, String type, ActionConfig actionConfig)
      throws Exception {
    ModuleConfig moduleConfig = actionConfig.getModuleConfig();
    String actionsKey = Constants.ACTIONS_KEY + moduleConfig.getPrefix();
    Map<String, Action> actions = (Map<String, Action>)context.getApplicationScope().get(actionsKey);

    if (actions == null) {
      actions = new HashMap<String, Action>();
      context.getApplicationScope().put(actionsKey, actions);
    }

    Action action = null;

    synchronized (actions) {
      action = actions.get(type);

      if (action == null) {
        action = (Action)ClassUtils.getApplicationInstance(type);
        actions.put(type, action);
      }
    }

    if (action.getServlet() == null) {
      ServletActionContext saContext = (ServletActionContext)context;
      ActionServlet actionServlet = saContext.getActionServlet();

      action.setServlet(actionServlet);
    }

    return (action);
  }

}
