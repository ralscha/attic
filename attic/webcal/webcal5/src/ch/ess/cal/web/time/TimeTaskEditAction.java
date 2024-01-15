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
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

@StrutsAction(path = "/timeTaskEdit", 
             form = TimeTaskForm.class, 
             input = "/timetaskedit.jsp", 
             scope = ActionScope.SESSION, 
             roles = "$timeadmin", 
             forwards = @Forward(name = "back", path = "/timeCustomerList.do", redirect = true))
public class TimeTaskEditAction extends AbstractEditAction<TimeTask> {

  private TimeProjectDao timeProjectDao;
  private TimeTaskDao timeTaskDao;


  public void setTimeProjectDao(TimeProjectDao timeProjectDao) {
    this.timeProjectDao = timeProjectDao;
  }
  public void setTimeTaskDao(TimeTaskDao timeTaskDao) {
	    this.timeTaskDao = timeTaskDao;
	  }

  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    TimeTaskForm timeTaskForm = (TimeTaskForm)form;
    String projectId = ctx.request().getParameter("projectId");
    TimeProject project = timeProjectDao.findById(projectId);
    timeTaskForm.setCustomer(project.getTimeCustomer().getName());
    timeTaskForm.setProjectId(projectId);
    timeTaskForm.setActive(true);
  }

  @Override
  public void modelToForm(ActionContext ctx, TimeTask timeTask) {
    TimeTaskForm timeTaskForm = (TimeTaskForm)ctx.form();
    timeTaskForm.setName(timeTask.getName());
    timeTaskForm.setDescription(timeTask.getDescription());
    timeTaskForm.setProjectId(timeTask.getTimeProject().getId().toString());
    timeTaskForm.setCustomer(timeTask.getTimeProject().getTimeCustomer().getName());
    timeTaskForm.setActive(timeTask.isActive());
    
    if (timeTask.getHourRate() != null) {
      timeTaskForm.setHourRate(Constants.TWO_DECIMAL_FORMAT.format(timeTask.getHourRate()));
    } else {
      timeTaskForm.setHourRate(null);
    } 
  }

  @Override
  public void formToModel(ActionContext ctx, TimeTask timeTask) {

    TimeTaskForm timeTaskForm = (TimeTaskForm)ctx.form();
    timeTask.setName(timeTaskForm.getName());
    timeTask.setDescription(timeTaskForm.getDescription());
    timeTask.setTimeProject(timeProjectDao.findById(timeTaskForm.getProjectId()));
    timeTask.setActive(timeTaskForm.isActive());
    
    if(timeTask.isActive()){
    	timeTask.getTimeProject().setActive(true);
    	timeTask.getTimeProject().getTimeCustomer().setActive(true);
    }
    
    if (StringUtils.isNotBlank(timeTaskForm.getHourRate())) {
      timeTask.setHourRate(new BigDecimal(timeTaskForm.getHourRate()));
    } else {
      timeTask.setHourRate(null);
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

        TimeTask model = null;
        String id = form.getModelId();

        if (StringUtils.isNotBlank(id)) {
          model = timeTaskDao.findById(id);

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
          model = timeTaskDao.newInstance();
        }
        formToModel(ctx, model);

        timeTaskDao.save(model);

        form.setModelId(model.getId().toString());
        form.setVersion(String.valueOf(model.getVersion()));
        form.setDeletable(getDao().canDelete(model));
        
        ctx.session().setAttribute("timeCustomerEditId", model.getTimeProject().getTimeCustomer().getId());
        ctx.session().setAttribute("timeProjectEditId", model.getTimeProject().getId());  
        ctx.session().setAttribute("timeTaskEditId", model.getId());  

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
