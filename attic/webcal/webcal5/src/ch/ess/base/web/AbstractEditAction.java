package ch.ess.base.web;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.dao.AbstractDao;
import ch.ess.base.model.BaseObject;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;

@SpringAction
public abstract class AbstractEditAction<T extends BaseObject> extends FWAction {

  private AbstractDao<T> dao;

  protected AbstractDao<T> getDao() {
    return dao;
  }

  public void setDao(final AbstractDao<T> dao) {
    this.dao = dao;
  }

  public void delete_onClick(final FormActionContext ctx) {
    String id = ctx.request().getParameter("modelId");
    if (StringUtils.isNotBlank(id)) {
      beforeDelete(ctx, id);
      dao.delete(id);

      //update crumb
      CallStackObject stackObject = CrumbsUtil.getCallStackTopObject(ctx);
      if (stackObject != null) {
        stackObject.setId(null);
      }

      ctx.addGlobalMessage("common.deleteOK");
      ctx.forwardByName("back");
      return;
    }
    ctx.forwardToInput();

  }

  public void back_onClick(final FormActionContext ctx) {
    ctx.forwardByName("back");
  }

  @Override
  public void doExecute(final ActionContext ctx) throws Exception {
    try {
      String id = ctx.request().getParameter("id");
      String copyid = ctx.request().getParameter("copyid");
      if (StringUtils.isNotBlank(id)) {
        T model = dao.findById(id);

        AbstractForm form = (AbstractForm)ctx.form();
        form.setModelId(model.getId().toString());
        form.setVersion(String.valueOf(model.getVersion()));
        form.setDeletable(getDao().canDelete(model));
        modelToForm(ctx, model);
      } else if (StringUtils.isNotBlank(copyid)) {
        T model = dao.findById(copyid);
        AbstractForm form = (AbstractForm)ctx.form();
        form.setModelId(null);
        form.setVersion(null);
        modelToForm(ctx, model);
        afterCopy(ctx, model);
      } else {
        //new 
        newItem(ctx);
      }

      ctx.forwardToInput();
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("doExecute", e);
      throw e;
    }

  }

  public abstract void modelToForm(final ActionContext ctx, final T model) throws Exception ;

  public abstract void formToModel(final ActionContext ctx, final T model) throws Exception ;

  public void saveAndNew_onClick(final FormActionContext ctx) throws Exception {
    try {

      ActionForm form = ctx.form();
      ActionErrors errors = callValidate(ctx, form);
      ctx.addErrors(errors);
      if (ctx.hasErrors()) {
        ctx.forwardToInput();
        return;
      }

      save_onClick(ctx);
      newItem(ctx);
      
      CallStackObject stackObject = CrumbsUtil.getCallStackTopObject(ctx);
      if (stackObject != null) {
        stackObject.setId(null);
      }

      
      ctx.forwardToInput();
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("doExecute", e);
      throw e;
    }
  }

  public void save_onClick(final FormActionContext ctx) throws Exception {

    try {
      AbstractForm form = (AbstractForm)ctx.form();

      ActionErrors errors = callValidate(ctx, form);
      ctx.addErrors(errors);

      // If there are any Errors return and display an Errormessage
      if (ctx.hasErrors()) {
        ctx.forwardToInput();
        return;
      }

      T model = null;
      String id = form.getModelId();

      if (StringUtils.isNotBlank(id)) {
        model = dao.findById(id);

        //edit mode, check version
        int version = Integer.parseInt(form.getVersion());
        if (version != model.getVersion()) {

          form.setVersion(String.valueOf(model.getVersion()));
          modelToForm(ctx, model);

          errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.concurrent.update"));
          ctx.addErrors(errors);
          ctx.forwardToInput();
          return;
        }
      }

      if (model == null) {
        model = dao.newInstance();
      }
      formToModel(ctx, model);

      dao.save(model);

      form.setModelId(model.getId().toString());
      form.setVersion(String.valueOf(model.getVersion()));
      form.setDeletable(getDao().canDelete(model));

      //update crumb
      CallStackObject stackObject = CrumbsUtil.getCallStackTopObject(ctx);
      if (stackObject != null) {
        stackObject.setId(form.getModelId());
      }

      ctx.addGlobalMessage("common.updateOK");
      
      afterSave(ctx, model);

      ctx.forwardToInput();
      back_onClick(ctx);
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("doExecute", e);
      throw e;
    }

  }

  protected ActionErrors callValidate(final ActionContext ctx, final ActionForm form) {
    return form.validate(ctx.mapping(), ctx.request());
  }

  protected void afterCopy(final ActionContext ctx, T model) {
   //empty default action
  }
  
  protected void afterSave(final FormActionContext ctx, T model) {
    //empty default action
  }

  protected void beforeDelete(final FormActionContext ctx, String id) {
    //empty default action
  }

  private void newItem(final ActionContext ctx) throws InstantiationException, IllegalAccessException {
    ActionForm emptyForm = ctx.form().getClass().newInstance();

    afterNewItem(ctx, emptyForm);

    if ("request".equals(ctx.mapping().getScope())) {
      ctx.request().setAttribute(ctx.mapping().getName(), emptyForm);
    } else {
      ctx.session().setAttribute(ctx.mapping().getName(), emptyForm);
    }
  }

  protected void afterNewItem(final ActionContext ctx, ActionForm form) {
    //empty default action
  }

  @Override
  public boolean doPreExecute(final ActionContext ctx) throws Exception {
    boolean flag = super.doPreExecute(ctx);
    CrumbsUtil.updateCallStack(this, ctx);
    return flag;
  }

  @Override
  public void doPostExecute(final ActionContext ctx) throws Exception {
    CrumbsUtil.updateCrumbs(ctx);
    super.doPostExecute(ctx);
  }

}
