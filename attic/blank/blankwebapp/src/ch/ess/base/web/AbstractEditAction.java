package ch.ess.base.web;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.base.model.BaseObject;
import ch.ess.base.persistence.Dao;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:20 $
 */
public abstract class AbstractEditAction<T extends BaseObject> extends FWAction {

  private Dao<T> dao;

  protected Dao<T> getDao() {
    return dao;
  }

  public void setDao(final Dao<T> dao) {
    this.dao = dao;
  }

  public void delete_onClick(final FormActionContext ctx) {
    String id = ctx.request().getParameter("modelId");
    if (!GenericValidator.isBlankOrNull(id)) {
      if (dao.deleteCond(id)) {
        ctx.addGlobalMessage("common.deleteOK");
        ctx.forwardByName("back");
      } else {
        ctx.addGlobalMessage("common.deleteNOK");
        ctx.forwardToInput();
      }
      return;
    }
    ctx.forwardToInput();

  }

  public void back_onClick(final FormActionContext ctx) {
    ctx.forwardByName("back");
  }

  public void doExecute(final ActionContext ctx) throws Exception {

    try {
      String id = ctx.request().getParameter("id");
      if (!GenericValidator.isBlankOrNull(id)) {
        T model = dao.get(id);
        
        AbstractForm form = (AbstractForm)ctx.form();
        form.setModelId(model.getId().toString());
        form.setVersion(String.valueOf(model.getVersion()));

        modelToForm(ctx, model);
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

  public abstract void modelToForm(final ActionContext ctx, final T model);

  public abstract T formToModel(final ActionContext ctx, final T model);

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
      clearForm(ctx.form());
      newItem(ctx);
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
        model = dao.get(id);

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

      model = formToModel(ctx, model);

      dao.saveOrUpdate(model);

      
      form.setModelId(model.getId().toString());
      form.setVersion(String.valueOf(model.getVersion()));

      ctx.addGlobalMessage("common.updateOK");

      afterSave(ctx);

      ctx.forwardToInput();
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("doExecute", e);
      throw e;
    }

  }

  protected ActionErrors callValidate(final FormActionContext ctx, final ActionForm form) {
    return form.validate(ctx.mapping(), ctx.request());
  }

  protected void afterSave(final FormActionContext ctx) {
    //empty default action
  }

  protected void newItem(final ActionContext ctx) {
    //empty default action
  }

  protected void clearForm(final ActionForm form) {

    try {
      ActionForm emptyForm = form.getClass().newInstance();
      PropertyUtils.copyProperties(form, emptyForm);
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("clearForm", e);
    }
  }
}
