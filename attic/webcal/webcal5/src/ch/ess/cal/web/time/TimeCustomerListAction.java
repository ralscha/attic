package ch.ess.cal.web.time;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.Util;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.AbstractTreeListAction;
import ch.ess.base.web.CallStackObject;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.base.web.DynaTreeDataModel;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.TreelistControl;
import com.cc.framework.ui.model.DataModel;
import com.cc.framework.ui.model.TreeGroupDataModel;

@StrutsAction(path = "/timeCustomerList", 
    form = MapForm.class, 
    input = "/timecustomerlist.jsp", 
    scope = ActionScope.SESSION, 
    roles = "$timeadmin", 
    forwards = {
      @Forward(name = "edit", path = "/timeCustomerEdit.do?id={0}"), 
      @Forward(name = "create", path = "/timeCustomerEdit.do"),
      @Forward(name = "editProject", path = "/timeProjectEdit.do?id={0}"),
      @Forward(name = "editTask", path = "/timeTaskEdit.do?id={0}"),
      @Forward(name = "createProject", path = "/timeProjectEdit.do?customerId={0}"),
      @Forward(name = "createTask", path = "/timeTaskEdit.do?projectId={0}")      
    })
public class TimeCustomerListAction extends AbstractTreeListAction {

  private TimeCustomerDao timeCustomerDao;
  private TimeProjectDao timeProjectDao;
  private TimeTaskDao timeTaskDao;
  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;
  private Config appConfig;

  public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
    this.timeCustomerDao = timeCustomerDao;
  }

  public void setTimeProjectDao(TimeProjectDao timeProjectDao) {
    this.timeProjectDao = timeProjectDao;
  }

  public void setTimeTaskDao(TimeTaskDao timeTaskDao) {
    this.timeTaskDao = timeTaskDao;
  }
  
  public void setAppConfig(final Config appConfig) {
	this.appConfig = appConfig;
  }  
  
  public void setUserDao(UserDao userDao) {
	this.userDao = userDao;
  }  
  
  public void setUserConfigurationDao(UserConfigurationDao userConfigurationDao) {
		this.userConfigurationDao = userConfigurationDao;
	  }  
  
	@Override
	public void doExecute(ActionContext ctx) throws Exception {
		String callFromMenu = ctx.request().getParameter("startCrumb");
		MapForm searchForm = (MapForm)ctx.form();
		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		User user = userDao.findById(userPrincipal.getUserId());
		Config userConfig = userConfigurationDao.getUserConfig(user);
		//Suchmasken setzen
		if(callFromMenu == null) {
			searchForm = UserHelper.saveAttributes(user, userConfig, searchForm, userConfigurationDao);
		}

		searchForm = UserHelper.loadAttributes(user, userConfig, searchForm);	
		super.doExecute(ctx);

	}


  public void listControl_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked) throws Exception {
  
    if (key.startsWith("task_")) {
      timeTaskDao.setActive(key.substring(5), checked);
    } else if (key.startsWith("proj_")) {
      timeProjectDao.setActive(key.substring(5), checked);
    } else {
      timeCustomerDao.setActive(key, checked);
    }   
    onRefresh(ctx);
  }

  @Override
  public TreeGroupDataModel getDataModel(ActionContext ctx) throws Exception {

    MapForm searchForm = (MapForm)ctx.form();
    String customerString = searchForm.getStringValue("customer");
    String projectString = searchForm.getStringValue("project");
	UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
	User user = userDao.findById(userPrincipal.getUserId());
	Config userConfig = userConfigurationDao.getUserConfig(user);
    
    DynaTreeDataModel root = new DynaTreeDataModel("root");
    root.set("id", "ROOT");
    root.set("name", "<root>");
    root.setEditable(false);
    root.setDeletable(false);
	String searchWithInactive = userConfig.getStringProperty(UserConfig.ATTRIBUTE_INACTIVE);

    List<TimeCustomer> customers = timeCustomerDao.findCustomerProjectTime(customerString, searchWithInactive);    

    //Sortieren, da ï¿½ber Hibernate HashMap nicht immer korrekt sortiert ist.
    Comparator<TimeCustomer> compTC = new TimeCustomerNumberNameComparator();     
    Collections.sort(customers, compTC);
    
    HashMap<String, String> hm = new HashMap<String, String>();
    
    //Sortiert über Hibernate (oberste Objekte OK)
    for (TimeCustomer customer : customers) {
      boolean deletable = true;
      if(hm.containsValue(customer.getId().toString())){
    	  continue;
      }
      
      DynaTreeDataModel cust = new DynaTreeDataModel(customer.getName());
      cust.set("id", customer.getId().toString());
      cust.set("db_id", customer.getId().toString());
      hm.put(customer.getName(), customer.getId().toString());
      if(customer.getCustomerNumber() != null && !StringUtils.isEmpty(customer.getCustomerNumber())){
          cust.set("name", appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG,	"<")
        		  +customer.getCustomerNumber()+appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">")
        		  +" "+customer.getName());
      } else {
    	  cust.set("name", customer.getName());
      }
      cust.set("hourRate", customer.getHourRate());
      cust.set("image", "customer");
      cust.setChecked(customer.isActive());
      cust.setAddable(true);
      root.addChild(cust);
      
      //Sortieren, da über Hibernate HashMap nicht korrekt sortiert ist.
      Comparator<TimeProject> compTP = new TimeProjectNumberNameComparator();
      ArrayList<TimeProject> projects = new ArrayList<TimeProject>();
      for(TimeProject proj : customer.getTimeProjects()){
    	  if( projectString != null &&
    			  (proj.getName() != null && StringUtils.contains(proj.getName().toLowerCase(), projectString.toLowerCase())) 
    	  ){
    		  projects.add(proj);
    	  }
    	  else if (projectString == null || StringUtils.isEmpty(projectString)){
    		  projects.add(proj);
    	  }
      }      
      Collections.sort(projects, compTP);
      
      for (TimeProject project : projects) {
        boolean projectDeletable = timeProjectDao.canDelete(project);
        if(!projectDeletable){
        	deletable = false;
        }
        
        boolean projectAddable = true;
        if(project.getTimeTasks().isEmpty() && !projectDeletable){
        	projectAddable = false;
        }
        
        DynaTreeDataModel proj = new DynaTreeDataModel(project.getName());
        proj.set("id", "proj_" + project.getId().toString());
        proj.set("db_id", project.getId().toString());
        if(project.getProjectNumber() != null && !StringUtils.isEmpty(project.getProjectNumber())){
            proj.set("name", appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG, "<")
          		  +project.getProjectNumber()+appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">")
          		  +" "+project.getName());
        } else {
      	  proj.set("name", project.getName());
        }
        proj.set("hourRate", project.getHourRate());
        proj.setAddable(projectAddable);
        proj.setChecked(project.isActive());
        cust.addChild(proj);

        //Sortieren, da über Hibernate HashMap nicht korrekt sortiert ist.
        Comparator<TimeTask> compTT = new TimeTaskNameComparator();
        ArrayList<TimeTask> tasks = new ArrayList<TimeTask>();
        for(TimeTask task : project.getTimeTasks()){
        	tasks.add(task);
        }      
        Collections.sort(tasks, compTT);
        
        for (TimeTask task : tasks) {
          DynaTreeDataModel t = new DynaTreeDataModel(task.getName());
          t.set("id", "task_" + task.getId().toString());
          t.set("db_id", task.getId().toString());
          t.set("name", task.getName());
          t.set("hourRate", task.getHourRate());
          t.set("image", "task");
          t.setChecked(task.isActive());
          t.setAddable(false);
          
          boolean taskDeletable = timeTaskDao.canDelete(task);
          t.setDeletable(taskDeletable);
          
          deletable = deletable && taskDeletable;
          projectDeletable = projectDeletable && taskDeletable;
          
          proj.addChild(t);
                    
        }
        
        proj.setDeletable(projectDeletable);
      }
      
      cust.setDeletable(deletable);      
    }  
    
   
    return root;
 }

  @Override
  protected void setTreeListControlAttributes(ActionContext ctx, TreelistControl listControl) {
	String callFromMenu = ctx.request().getParameter("startCrumb");
	if(callFromMenu == null) {
		int timeCustomerEditId = 0;
		int timeProjectEditId = 0;
		int timeTaskEditId = 0;

		try {
			timeCustomerEditId = (Integer) ctx.session().getAttribute("timeCustomerEditId"); 	  	
		} catch (Exception e) {
			timeCustomerEditId = 0;  	
		}
		try {
			timeProjectEditId = (Integer) ctx.session().getAttribute("timeProjectEditId");  	  		  	
		} catch (Exception e) {
			timeProjectEditId = 0;  	
		}
		try {
			timeTaskEditId = (Integer) ctx.session().getAttribute("timeTaskEditId");  	  	
		} catch (Exception e) {
			timeTaskEditId = 0;  	
		}
		DynaTreeDataModel model = getTreeNode(timeCustomerEditId, timeProjectEditId, timeTaskEditId, listControl.getDataModel());
		listControl.expand(model);

		ctx.session().removeAttribute("timeCustomerEditId");
		ctx.session().removeAttribute("timeProjectEditId");
		ctx.session().removeAttribute("timeTaskEditId");
	}
  }
  private DynaTreeDataModel getTreeNode(int customerId, int projectId, int taskId, DataModel dataModel){
	  DynaTreeDataModel data = (DynaTreeDataModel) dataModel;
	  if(taskId > 0){
			for(int i = 0; i < data.size(); i++){
				DynaTreeDataModel cust = (DynaTreeDataModel) data.getChild(i);
				if(Integer.valueOf((String) cust.get("db_id")) == customerId){
					for(int j = 0; j < cust.size(); j++){
						DynaTreeDataModel proj = (DynaTreeDataModel) cust.getChild(j);
						if(Integer.valueOf((String) proj.get("db_id")) == projectId){
							for(int z = 0; z < proj.size(); z++){
								DynaTreeDataModel task = (DynaTreeDataModel) proj.getChild(z);
								if(Integer.valueOf((String)  task.get("db_id")) == taskId){
									return task;
								}
						
							}
						}
					}
				}
			}				
		}else if(projectId > 0){
			for(int i = 0; i < data.size(); i++){
				DynaTreeDataModel cust = (DynaTreeDataModel) data.getChild(i);
				if(Integer.valueOf((String) cust.get("db_id")) == customerId){
					for(int j = 0; j < cust.size(); j++){
						DynaTreeDataModel proj = (DynaTreeDataModel) cust.getChild(j);
						if(Integer.valueOf((String) proj.get("db_id")) == projectId){
							return proj;
						}
					}
				}
			}
		}else{
			for(int i = 0; i < data.size(); i++){
				DynaTreeDataModel cust = (DynaTreeDataModel) data.getChild(i);
				if(Integer.valueOf((String) cust.get("db_id")) == customerId){
					return cust;
				}
			}
		}
			
	  return null;
  }
 
  @Override
  public void onEdit(final ControlActionContext ctx, final String key) {
    if (key.startsWith("task_")) {
      ctx.forwardByName("editTask", key.substring(5));
    } else if (key.startsWith("proj_")) {
      ctx.forwardByName("editProject", key.substring(5));
    } else {
      ctx.forwardByName("edit", key);
    }
  }
  
  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    if (key.startsWith("task_")) {
      timeTaskDao.delete(key.substring(5));
    } else if (key.startsWith("proj_")) {
      timeProjectDao.delete(key.substring(5));
    } else {
      timeCustomerDao.delete(key);
    }
  }

  public void listControl_onAdd(ControlActionContext ctx, String key) throws Exception {
    if (key.startsWith("proj_")) {
      ctx.forwardByName("createTask", key.substring(5));
    } else {
      ctx.forwardByName("createProject", key);
    }
  }

  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      CallStackObject top = CrumbsUtil.getCallStackTopObject(ctx);
      if (top.getPath().contains("timeTaskEdit")) {
        TimeTask task = timeTaskDao.findById(id);
        if (task != null) {
          return task.getName();
        }
      } else if (top.getPath().contains("timeProjectEdit")) {
        TimeProject prj = timeProjectDao.findById(id);
        if (prj != null) {
          return prj.getName();
        }
      } else {
        TimeCustomer customer = timeCustomerDao.findById(id);
        if (customer != null) {
          return customer.getName();
        }
      }
    }
    return null;
  }

  
  @Override
  public void onExportList(ControlActionContext ctx) throws Exception {

    String filename = "customers.xls";
    Util.setExportHeader(ctx.response(), "application/vnd.ms-excel", filename);
    ctx.response().setHeader("extension", "xls");
    
    OutputStream out = ctx.response().getOutputStream();
    
    WritableWorkbook workbook = Workbook.createWorkbook(out);
    WritableSheet sheet = workbook.createSheet("Customers", 0);
    
    sheet.setColumnView(0, 25);
    sheet.setColumnView(1, 25);
    sheet.setColumnView(2, 25);
    sheet.setColumnView(3, 10);

    WritableFont arial10fontbold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
    WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
    WritableCellFormat arial10formatbold = new WritableCellFormat(arial10fontbold);
    WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        
    NumberFormat nf = new NumberFormat("#,##0.00"); 
    WritableCellFormat arial10Decimalformat = new WritableCellFormat(nf);
    arial10Decimalformat.setFont(arial10font);
    
    int row = 0;
    
    sheet.addCell(new Label(0, row, Util.translate(ctx.request(), "time.customer"), arial10formatbold));
    sheet.addCell(new Label(1, row, Util.translate(ctx.request(), "time.project"), arial10formatbold));
    sheet.addCell(new Label(2, row, Util.translate(ctx.request(), "time.timeTask"), arial10formatbold));
    sheet.addCell(new Label(3, row, Util.translate(ctx.request(), "time.hourRate"), arial10formatbold));
    
    row++;
    
    DynaTreeDataModel root = (DynaTreeDataModel)ctx.control().getDataModel();
    for (int i = 0; i < root.size(); i++) {
      
      DynaTreeDataModel customer = (DynaTreeDataModel)root.getChild(i);      
      sheet.addCell(new Label(0, row, (String)customer.get("name"), arial10format));
      BigDecimal hourRate = (BigDecimal)customer.get("hourRate");
      if (hourRate != null) {
        sheet.addCell(new Number(3, row, hourRate.floatValue(), arial10Decimalformat));
      }
      row++;
      
      for (int j = 0; j < customer.size(); j++) {
        DynaTreeDataModel project = (DynaTreeDataModel)customer.getChild(j);
        sheet.addCell(new Label(1, row, (String)project.get("name"), arial10format));
        hourRate = (BigDecimal)project.get("hourRate");
        if (hourRate != null) {
          sheet.addCell(new Number(3, row, hourRate.floatValue(), arial10Decimalformat));
        }
        row++;
        
        for (int k = 0; k < project.size(); k++) {
          DynaTreeDataModel task = (DynaTreeDataModel)project.getChild(k);
          sheet.addCell(new Label(2, row, (String)task.get("name"), arial10format));
          hourRate = (BigDecimal)task.get("hourRate");
          if (hourRate != null) {
            sheet.addCell(new Number(3, row, hourRate.floatValue(), arial10Decimalformat));
          }
          row++; 
        }
      }
      row++;
    }

    workbook.write();
    workbook.close();
    
    out.close();

    ctx.forwardToResponse();
  }
}
