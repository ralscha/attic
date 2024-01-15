package ch.ess.cal.web.time;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.web.AbstractForm;
import ch.ess.base.web.CallStackObject;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

@StrutsAction(path = "/timeProjectEdit", 
              form = TimeProjectForm.class, 
              input = "/timeprojectedit.jsp", 
              scope = ActionScope.SESSION, 
              roles = "$timeadmin", 
              forwards = @Forward(name = "back", path = "/timeCustomerList.do", redirect = true))
public class TimeProjectEditAction extends AbstractEditAction<TimeProject> {

  private TimeCustomerDao timeCustomerDao;
  private TimeProjectDao timeProjectDao;

  public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
    this.timeCustomerDao = timeCustomerDao;
  }
  public void setTimeProjectDao(TimeProjectDao timeProjectDao) {
	    this.timeProjectDao = timeProjectDao;
	  }

  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    TimeProjectForm timeProjectForm = (TimeProjectForm)form;
    timeProjectForm.setCustomerId(ctx.request().getParameter("customerId"));
    timeProjectForm.setActive(true);
  }

  @Override
  public void modelToForm(ActionContext ctx, TimeProject timeProject) {
    TimeProjectForm timeProjectForm = (TimeProjectForm)ctx.form();
    timeProjectForm.setName(timeProject.getName());
    timeProjectForm.setDescription(timeProject.getDescription());
    timeProjectForm.setCustomerId(timeProject.getTimeCustomer().getId().toString());
    timeProjectForm.setActive(timeProject.isActive());
    timeProjectForm.setProjectNumber(timeProject.getProjectNumber());
    
    if (timeProject.getHourRate() != null) {
      timeProjectForm.setHourRate(Constants.TWO_DECIMAL_FORMAT.format(timeProject.getHourRate()));
    } else {
      timeProjectForm.setHourRate(null);
    }    
  }

  @Override
  public void formToModel(ActionContext ctx, TimeProject timeProject) {

    TimeProjectForm timeProjectForm = (TimeProjectForm)ctx.form();
    timeProject.setName(timeProjectForm.getName());
    timeProject.setDescription(timeProjectForm.getDescription());
    timeProject.setTimeCustomer(timeCustomerDao.findById(timeProjectForm.getCustomerId()));
    timeProject.setActive(timeProjectForm.isActive());
    timeProject.setProjectNumber(timeProjectForm.getProjectNumber());
    
    if(timeProject.isActive()){
    	timeProject.getTimeCustomer().setActive(true);
    }
    
    if (StringUtils.isNotBlank(timeProjectForm.getHourRate())) {
      timeProject.setHourRate(new BigDecimal(timeProjectForm.getHourRate()));
    } else {
      timeProject.setHourRate(null);
    }    
    
  }

  @Override
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

        TimeProject model = null;
        String id = form.getModelId();

        if (StringUtils.isNotBlank(id)) {
          model = timeProjectDao.findById(id);

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
          model = timeProjectDao.newInstance();
        }
        formToModel(ctx, model);

        timeProjectDao.save(model);

        form.setModelId(model.getId().toString());
        form.setVersion(String.valueOf(model.getVersion()));
        form.setDeletable(getDao().canDelete(model));
        
        ctx.session().setAttribute("timeCustomerEditId", model.getTimeCustomer().getId());
        ctx.session().setAttribute("timeProjectEditId", model.getId());  

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
}
