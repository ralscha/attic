package ch.ess.cal.web.time;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.web.AbstractForm;
import ch.ess.base.web.CallStackObject;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.base.web.DynaTreeDataModel;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.UserTimeCustomer;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

@Role("$timeadmin")
public class TimeCustomerEditAction extends AbstractEditAction<TimeCustomer> {

  private UserDao userDao;
  private Config appConfig;
  private TimeCustomerDao timeCustomerDao;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }
  
  public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
	    this.timeCustomerDao = timeCustomerDao;
  }

  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    TimeCustomerForm timeCustomerForm = (TimeCustomerForm)form;
    timeCustomerForm.setActive(true);
    Boolean newCustomer = Boolean.parseBoolean(appConfig.getStringProperty(AppConfig.NEW_CUSTOMER, "false"));
    
    if(newCustomer){
    	String[] userIds = null;
    	List<User> users = userDao.findAll();
    	if(!users.isEmpty()){
    		userIds = new String[users.size()];
    		int cnt = 0;
    		for(User user : users){
    			userIds[cnt++] = user.getId().toString();
    		}
    		timeCustomerForm.setUserIds(userIds);
    	}
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

      TimeCustomer model = null;
      String id = form.getModelId();

      if (StringUtils.isNotBlank(id)) {
        model = timeCustomerDao.findById(id);

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
        model = timeCustomerDao.newInstance();
      }
      formToModel(ctx, model);

      timeCustomerDao.save(model);

      form.setModelId(model.getId().toString());
      form.setVersion(String.valueOf(model.getVersion()));
      form.setDeletable(getDao().canDelete(model));
      ctx.session().setAttribute("timeCustomerEditId", model.getId());  

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
  
  @Override
  public void modelToForm(ActionContext ctx, TimeCustomer timeCustomer) {
    TimeCustomerForm timeCustomerForm = (TimeCustomerForm)ctx.form();
    timeCustomerForm.setName(timeCustomer.getName());
    timeCustomerForm.setDescription(timeCustomer.getDescription());
    timeCustomerForm.setActive(timeCustomer.isActive());
    if (timeCustomer.getHourRate() != null) {
      timeCustomerForm.setHourRate(Constants.TWO_DECIMAL_FORMAT.format(timeCustomer.getHourRate()));
    } else {
      timeCustomerForm.setHourRate(null);
    }
    timeCustomerForm.setTabset("general");
    
    timeCustomerForm.setCustomerNumber(timeCustomer.getCustomerNumber());
    
    Set<UserTimeCustomer> users = timeCustomer.getUserTimeCustomers();
    String[] userIds = null;

    if (!users.isEmpty()) {
      userIds = new String[users.size()];

      int ix = 0;
      for (UserTimeCustomer userTimeCustomer : users) {
        userIds[ix++] = userTimeCustomer.getUser().getId().toString();
      }
    } 
    timeCustomerForm.setUserIds(userIds);
  }

  @Override
  public void formToModel(ActionContext ctx, TimeCustomer timeCustomer) {

    TimeCustomerForm timeCustomerForm = (TimeCustomerForm)ctx.form();
    timeCustomer.setName(timeCustomerForm.getName());
    timeCustomer.setDescription(timeCustomerForm.getDescription());
    timeCustomer.setCustomerNumber(timeCustomerForm.getCustomerNumber());
    if(timeCustomer.getCustomerNumber().isEmpty()){
    	timeCustomer.setCustomerNumber(null);
    }
    timeCustomer.setActive(timeCustomerForm.isActive());

    if (StringUtils.isNotBlank(timeCustomerForm.getHourRate())) {
      timeCustomer.setHourRate(new BigDecimal(timeCustomerForm.getHourRate()));
    } else {
      timeCustomer.setHourRate(null);
    }

    timeCustomer.getUserTimeCustomers().clear();

    String[] userIds = timeCustomerForm.getUserIds();
    if (userIds != null) {

      for (String id : userIds) {
        if (StringUtils.isNotBlank(id)) {
          User user = userDao.findById(id);
          UserTimeCustomer userTimeCustomer = new UserTimeCustomer();
          userTimeCustomer.setTimeCustomer(timeCustomer);
          userTimeCustomer.setUser(user);          
          timeCustomer.getUserTimeCustomers().add(userTimeCustomer);
        }
      }
    }
 }



}
