package ch.ess.cal.web.task;

import java.awt.Font;
import java.io.OutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ecs.AlignType;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.TranslationService;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.DocumentSizeConverter;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.TaskDao;
import ch.ess.cal.enums.StatusEnum;
import ch.ess.cal.enums.TaskViewEnum;
import ch.ess.cal.model.Attachment;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Task;
import ch.ess.cal.service.EventListDateObject;
import ch.ess.cal.service.EventListDateObjectConverter;
import ch.ess.cal.service.EventUtil;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.taglib.TagHelp;
import com.cc.framework.ui.AlignmentType;
import com.cc.framework.ui.ImageMap;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.ControlButton;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ClientEvent;
import com.cc.framework.ui.model.ColumnCheckDesignModel;
import com.cc.framework.ui.model.ColumnDesignModel;
import com.cc.framework.ui.model.ColumnGroupDesignModel;
import com.cc.framework.ui.model.ColumnImageDesignModel;
import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.ui.model.ListDesignModel;
import com.cc.framework.ui.model.imp.ColumnCheckDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnDeleteDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnEditDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnGroupDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnHtmlDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnImageDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnTextDesignModelImp;
import com.cc.framework.ui.model.imp.ImageModelImp;
import com.cc.framework.ui.model.imp.ListDesignModelImp;

public class TaskListAction extends AbstractListAction {

  private TaskDao taskDao;
  private UserDao userDao;
  private TranslationService translationService;
  private UserConfigurationDao userConfigurationDao;

  public void setTaskDao(final TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  @Override
  public void doExecute(final ActionContext ctx) throws Exception {
    if (ctx.request().getParameter("nolist") == null) {

      MapForm searchForm = (MapForm)ctx.form();
      String view = searchForm.getStringValue("view");

      TaskViewEnum taskView;
      if (view != null) {
        taskView = StringValuedEnumReflect.getEnum(TaskViewEnum.class, view);
      } else {
        User user = Util.getUser(ctx.session(), userDao);
        Config config = userConfigurationDao.getUserConfig(user);

        String defaultView = config.getStringProperty(UserConfig.TASK_DEFAULT_VIEW);
        if (defaultView != null) {
          taskView = StringValuedEnumReflect.getEnum(TaskViewEnum.class, defaultView);
        } else {
          taskView = TaskViewEnum.SIMPLE_LIST;
        }

        searchForm.setValue("view", taskView.getValue());
      }

      SimpleListControl listControl = new SimpleListControl();

      ListDesignModel designModel = new ListDesignModelImp();
      designModel.setName("listControl");
      designModel.setTitle("task.tasks");
      designModel.setRowCount(Integer.parseInt((String)ctx.session().getAttribute("noRows")));
      designModel.setButtonPermission(ControlButton.REFRESH, TagHelp.toPermission("true"));
      designModel.setButtonPermission(ControlButton.CREATE, TagHelp.toPermission("true"));
      designModel.setButtonPermission(ControlButton.PRINTLIST, TagHelp.toPermission("true"));
      designModel.setId("taskList");

      ImageMap imageMap = new ImageMap();
      ImageModelImp low = new ImageModelImp();
      low.setHeight(16);
      low.setWidth(12);
      low.setTooltip("event.low");
      low.setSource("images/prioritylow.gif");

      ImageModelImp high = new ImageModelImp();
      high.setHeight(16);
      high.setWidth(12);
      high.setTooltip("event.high");
      high.setSource("images/priorityhigh.gif");

      imageMap.addImage("L", low);
      imageMap.addImage("H", high);

      ColumnImageDesignModel columnImageDesignModel = new ColumnImageDesignModelImp();
      columnImageDesignModel.setTitle("");
      columnImageDesignModel.setProperty("priority");
      columnImageDesignModel.setAlignment(AlignmentType.CENTER);
      columnImageDesignModel.setSortable(true);
      columnImageDesignModel.setImageMap(imageMap);
      designModel.addColumn(columnImageDesignModel);

      ColumnDesignModel columnDesignModel = new ColumnTextDesignModelImp();
      columnDesignModel.setTitle("event.subject");
      columnDesignModel.setProperty("subject");
      if (taskView == TaskViewEnum.SIMPLE_LIST) {
        columnDesignModel.setWidth(450);
        columnDesignModel.setMaxLength(100);
      } else {
        columnDesignModel.setWidth(225);
        columnDesignModel.setMaxLength(42);
      }
      columnDesignModel.setSortable(true);
      columnDesignModel.setHandler(ClientEvent.ONMOUSEOVER, "return overlib('@{bean.tooltip}', CAPTION, '@{bean.tooltipHeader}', FGCOLOR, '#EEEEEE', WIDTH, @{bean.tooltipWidth});");
      columnDesignModel.setHandler(ClientEvent.ONMOUSEOUT, "return nd();");
      designModel.addColumn(columnDesignModel);

      if (taskView != TaskViewEnum.SIMPLE_LIST) {
        columnDesignModel = new ColumnTextDesignModelImp();
        columnDesignModel.setTitle("task.status");
        columnDesignModel.setProperty("status");
        columnDesignModel.setWidth(160);
        columnDesignModel.setSortable(true);
        designModel.addColumn(columnDesignModel);
      }

      columnDesignModel = new ColumnTextDesignModelImp();
      columnDesignModel.setTitle("task.due");
      columnDesignModel.setProperty("due");
      columnDesignModel.setWidth(100);
      columnDesignModel.setSortable(true);
      columnDesignModel.setConverter(new EventListDateObjectConverter());
      designModel.addColumn(columnDesignModel);

      if (taskView != TaskViewEnum.SIMPLE_LIST) {
        columnDesignModel = new ColumnTextDesignModelImp();
        columnDesignModel.setTitle("task.complete");
        columnDesignModel.setProperty("complete");
        columnDesignModel.setWidth(100);
        columnDesignModel.setSortable(true);
        columnDesignModel.setAlignment(AlignmentType.RIGHT);
        designModel.addColumn(columnDesignModel);

        columnDesignModel = new ColumnTextDesignModelImp();
        columnDesignModel.setTitle("category");
        columnDesignModel.setProperty("category");
        columnDesignModel.setWidth(100);
        columnDesignModel.setMaxLength(30);
        columnDesignModel.setSortable(true);
        designModel.addColumn(columnDesignModel);
      }

      columnDesignModel = new ColumnHtmlDesignModelImp();
      columnDesignModel.setTitle("event.reminder");
      columnDesignModel.setProperty("reminderHtml");
      columnDesignModel.setAlignment(AlignmentType.CENTER);
      designModel.addColumn(columnDesignModel);

      columnDesignModel = new ColumnHtmlDesignModelImp();
      columnDesignModel.setTitle("task.attachment");
      columnDesignModel.setProperty("attachmentHtml");
      columnDesignModel.setAlignment(AlignmentType.CENTER);
      designModel.addColumn(columnDesignModel);
      
      ColumnCheckDesignModel columnCheckDesignModel = new ColumnCheckDesignModelImp();
      columnCheckDesignModel.setTitle("task.completeDate");
      columnCheckDesignModel.setProperty("checkState");
      columnCheckDesignModel.setWidth(100);
      columnCheckDesignModel.setSortable(true);
      designModel.addColumn(columnCheckDesignModel);
            
      ColumnGroupDesignModel columnGroupdesignModel = new ColumnGroupDesignModelImp();
      columnGroupdesignModel.setTitle("common.action");
      columnGroupdesignModel.setAlignment(AlignType.CENTER);
      
      columnDesignModel = new ColumnEditDesignModelImp();
      columnDesignModel.setTooltip("common.edit");
      columnDesignModel.setWidth(20);
      
      columnGroupdesignModel.addColumn(columnDesignModel);
      
      
      columnDesignModel = new ColumnDeleteDesignModelImp();
      columnDesignModel.setTooltip("common.delete");
      columnDesignModel.setWidth(20);
      columnDesignModel.setHandler(ClientEvent.ONCLICK, "return confirmRequest('@{bean.deletesubject}');");
      
      columnGroupdesignModel.addColumn(columnDesignModel);
      designModel.addColumn(columnGroupdesignModel);
      
      
      listControl.setDesignModel(designModel);
      listControl.setDataModel(getDataModel(ctx));
      ctx.session().setAttribute(getListAttributeName(), listControl);

    }
    ctx.forwardToInput();
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {
    Font tooltipFont = new Font("Arial", Font.PLAIN, 14);
    
    SimpleListDataModel dataModel = new SimpleListDataModel();

    User user = Util.getUser(ctx.session(), userDao);
    TimeZone timeZone = user.getTimeZone();

    MapForm searchForm = (MapForm)ctx.form();
    Locale locale = getLocale(ctx.request());
    MessageResources messages = getResources(ctx.request());

    DocumentSizeConverter sizeConverter = new DocumentSizeConverter();
    
    String subject = null;
    String categoryId = null;
    String view = null;
    String taskId = null;
    
    if (searchForm != null) {
      subject = searchForm.getStringValue("subject");
      categoryId = searchForm.getStringValue("categoryId");
      view = searchForm.getStringValue("view");
      taskId = searchForm.getStringValue("taskId");
    }

    TaskViewEnum taskView;
    if (view != null) {
      taskView = StringValuedEnumReflect.getEnum(TaskViewEnum.class, view);
    } else {
      taskView = TaskViewEnum.SIMPLE_LIST;
    }

    
    
    List<Task> tasks = taskDao.findUserTasks(user, subject, categoryId, taskView, taskId);

    for (Task task : tasks) {

      TaskRow row = new TaskRow();
      row.setId(task.getId().toString());
      row.setSubject(task.getSubject());
      row.setDeletesubject(StringEscapeUtils.escapeJavaScript(task.getSubject()));

      if (!task.getEventCategories().isEmpty()) {
        EventCategory eventCategory = task.getEventCategories().iterator().next();
        row.setCategory(translationService.getText(eventCategory.getCategory(), locale));
      }

      if (task.getStartDate() != null) {
        Calendar startCal = CalUtil.utcLong2UserCalendar(task.getStartDate(), timeZone);
        EventListDateObject obj = new EventListDateObject(startCal, task.isAllDay());
        row.setStart(obj);
      }

      if (task.getDueDate() != null) {
        Calendar dueCal = CalUtil.utcLong2UserCalendar(task.getDueDate(), timeZone);
        EventListDateObject obj = new EventListDateObject(dueCal, task.getAllDayDue());
        row.setDue(obj);
      }

      row.setStatus(messages.getMessage(locale, task.getStatus().getKey()));
      row.setComplete(task.getComplete());
      row.setPriority(task.getImportance().getValue());
      row.setTooltip(EventUtil.getTaskTooltip(task));
      row.setTooltipHeader(task.getSubject());
      if (StringUtils.isNotBlank(task.getDescription())) {
        int length = Util.getStrLenPcx(task.getDescription(), tooltipFont);
        length = Math.min(length, 500);
        row.setTooltipWidth(length);
      } else {
        row.setTooltipWidth(Util.getStrLenPcx(task.getSubject(), tooltipFont));
      }

      if (task.getReminders().size() > 0) {

        String tooltip = EventUtil.getReminderTooltip(messages, locale, task.getReminders(), false);
        String caption = messages.getMessage(locale,  "event.reminder");
        String html = "<img align='absmiddle' vspace='0' border='0' src='images/reminder.gif'"
            + "onmouseover=\"return overlib('" + tooltip + "', FGCOLOR, '#EEEEEE', WIDTH, 380, CAPTION, '"
            + caption + "');\"" + " onmouseout=\"return nd();\" alt=\"\" width=\"20\" height=\"12\" border=\"0\">";

        row.setReminderHtml(html);
      } else {
        row.setReminderHtml("<img src=\"images/x.gif\" alt=\"\" width=\"20\" height=\"12\" border=\"0\">");
      }

      if (task.getCompleteDate() != null) {
        row.setCheckState(1);
      } else {
        row.setCheckState(0);
      }
      
      
      if (task.getAttachments().size() > 0) {
        StringBuilder sb = new StringBuilder();
        
        for (Attachment attachment : task.getAttachments()) {    
          if (sb.length() > 0) {
            sb.append("&nbsp;&nbsp;");
          }
          
          sb.append("<a href=\"taskDocument.do?id=");
          sb.append(attachment.getId());
          sb.append("\"");

          sb.append(" onmouseover=\"return overlib('");
          sb.append("<table class=\\'list\\'><tr><td nowrap>");
          sb.append(attachment.getDocument().getFileName());
          sb.append("</td><td align=\\'right\\' nowrap>");
          sb.append(sizeConverter.getAsString(null, attachment.getDocument().getContentSize()));
          sb.append("</td></tr></table>");
          sb.append("', FGCOLOR, '#EEEEEE', LEFT, CAPTION, '");
          sb.append(messages.getMessage(locale, "task.attachment"));
          sb.append("');\"");
          sb.append("onmouseout=\"return nd();\"");
          
          sb.append("target=\"_parent\"><img src=\"images/download.gif\" border=\"0\"></a>");         
        }        
                
        row.setAttachmentHtml(sb.toString());
      } else {
        row.setAttachmentHtml("<img src=\"images/x.gif\" alt=\"\" width=\"20\" height=\"12\" border=\"0\">");
      }
      

      dataModel.add(row);

    }
    dataModel.sort("subject", SortOrder.ASCENDING);

    return dataModel;
  }

  public void listControl_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked)
      throws Exception {
    Task task = taskDao.findById(key);

    if (checked) {
      task.setComplete(100);
      Calendar today = new GregorianCalendar(Constants.UTC_TZ);
      task.setCompleteDate(today.getTimeInMillis());
      task.setStatus(StatusEnum.COMPLETED);
    } else {
      if (task.getComplete() >= 100) {
        task.setComplete(75);
      }
      task.setCompleteDate(null);
      task.setStatus(StatusEnum.INPROCESS);
    }

    taskDao.save(task);

    onRefresh(ctx);
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    taskDao.delete(key);
  }

  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      Task task = taskDao.findById(id);
      if (task != null) {
        return task.getSubject();
      }
    }
    return null;
  }
  
  
  @Override
  public void onPrintList(ControlActionContext ctx) throws Exception {
    
    MapForm searchForm = (MapForm)ctx.session().getAttribute("MapForm");
    String view = searchForm.getStringValue("view");

    TaskViewEnum taskView;
    if (view != null) {
      taskView = StringValuedEnumReflect.getEnum(TaskViewEnum.class, view);
    } else {
      taskView = TaskViewEnum.SIMPLE_LIST;
    }
    
    String reportTemplate = "/WEB-INF/reports/tasklistdetail.jasper";
    if (taskView == TaskViewEnum.SIMPLE_LIST) {
      reportTemplate = "/WEB-INF/reports/tasklist.jasper";
    }
    
    String filename = "tasklist.pdf";
    Util.setExportHeader(ctx.response(), "application/pdf", filename);        
    ctx.response().setHeader("extension", "pdf");

    
    Map<String,Object> parameters = new HashMap<String,Object>();
    parameters.put("REPORT_LOCALE", getLocale(ctx.request()));  
    
    SimpleListDataModel dataModel = (SimpleListDataModel)ctx.control().getDataModel();
    
    URL url = ctx.session().getServletContext().getResource("/images/checked.gif");
    TaskListDataSource dataSource = new TaskListDataSource(dataModel.getObjectList(), url);
    EventListDateObjectConverter converter = new EventListDateObjectConverter();
    dataSource.addConverter("start", converter);
    dataSource.addConverter("due", converter);
    
    
    
    JasperPrint print = JasperFillManager.fillReport(ctx.session().getServletContext().getResourceAsStream(reportTemplate),
        parameters, dataSource);
    
    
    OutputStream out = ctx.response().getOutputStream();
    JasperExportManager.exportReportToPdfStream(print, out);
    out.close();
    
    ctx.forwardToResponse();
  }

}
