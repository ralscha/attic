package ch.ess.cal.web.time;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Constants;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.DynaTreeDataModel;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;
import ch.ess.cal.web.TimeMonthViewForm;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

import com.cc.framework.ui.model.ListDataModel;

@SpringBean(id = "timeExcelCreator", autowire = Autowire.BYTYPE)
public class TimeExcelCreator {

  private TimeCustomerDao timeCustomerDao;
  private TimeProjectDao timeProjectDao;
  private TimeTaskDao timeTaskDao;
  private UserDao userDao;
  private GroupDao groupDao;
  private TranslationService translateService;

  public void setGroupDao(GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
    this.timeCustomerDao = timeCustomerDao;
  }

  public void setTimeProjectDao(TimeProjectDao timeProjectDao) {
    this.timeProjectDao = timeProjectDao;
  }

  public void setTimeTaskDao(TimeTaskDao timeTaskDao) {
    this.timeTaskDao = timeTaskDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setTranslateService(TranslationService translateService) {
    this.translateService = translateService;
  }

  public void createListExcel(TimeListForm searchForm, DynaTreeDataModel root, OutputStream out,
      MessageResources messages, Locale locale, boolean isAdmin, boolean charges, boolean activities, boolean comment)
  	throws RowsExceededException, WriteException,
      IOException {
	   WritableWorkbook workbook = Workbook.createWorkbook(out);
	    WritableSheet sheet = workbook.createSheet("Report", 0);

	    sheet.setColumnView(0, 20);
	    sheet.setColumnView(1, 20);
	    sheet.setColumnView(2, 20);
	    sheet.setColumnView(3, 15);
	    sheet.setColumnView(4, 15);
	    sheet.setColumnView(5, 15);
	    sheet.setColumnView(6, 15);
	    sheet.setColumnView(7, 15);
	    sheet.setColumnView(8, 15);

	    WritableFont arial10fontbold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
	    WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
	    WritableCellFormat arial10formatbold = new WritableCellFormat(arial10fontbold);
	    WritableCellFormat arial10format = new WritableCellFormat(arial10font);

	    NumberFormat nf = new NumberFormat("#,##0.00");
	    WritableCellFormat arial10Decimalformat = new WritableCellFormat(nf);
	    arial10Decimalformat.setFont(arial10font);

	    int row = writeFilter(searchForm, messages, locale, isAdmin, sheet, arial10formatbold, arial10format, 1);
	    row += 2;

	    sheet.addCell(new Label(0, row, messages.getMessage(locale, "header.date"), arial10formatbold));
	    sheet.addCell(new Label(1, row, messages.getMessage(locale, "time.customer"), arial10formatbold));
	    sheet.addCell(new Label(2, row, messages.getMessage(locale, "time.project"), arial10formatbold));
	    sheet.addCell(new Label(3, row, messages.getMessage(locale, "time.timeTask"), arial10formatbold));
	    if(activities)sheet.addCell(new Label(4, row, messages.getMessage(locale, "time"), arial10formatbold));
	    if(comment)sheet.addCell(new Label(5, row, messages.getMessage(locale, "time.comment"), arial10formatbold));
	    sheet.addCell(new Label(6, row, messages.getMessage(locale, "time.hour"), arial10formatbold));
	    if(charges)sheet.addCell(new Label(7, row, messages.getMessage(locale, "time.amount"), arial10formatbold));
	    if(charges)sheet.addCell(new Label(8, row, messages.getMessage(locale, "time.charges"), arial10formatbold));

	    row++;

	    for (int i = 0; i < root.size(); i++) {

	      DynaTreeDataModel customer = (DynaTreeDataModel)root.getChild(i);
	      sheet.addCell(new Label(0, row, (String)customer.get("date"), arial10format));
	      row++;

	      for (int j = 0; j < customer.size(); j++) {
	        DynaTreeDataModel project = (DynaTreeDataModel)customer.getChild(j);

		    sheet.addCell(new Label(0, row, (String)project.get("date"), arial10format));
	        sheet.addCell(new Label(1, row, (String)project.get("timeCustomer"), arial10format));
	        sheet.addCell(new Label(2, row, (String)project.get("timeProject"), arial10format));
	        sheet.addCell(new Label(3, row, (String)project.get("timeTask"), arial10format));
	        if(activities)sheet.addCell(new Label(4, row, (String)project.get("timeActivity"), arial10format));	
	        if(comment)sheet.addCell(new Label(5, row, (String)project.get("timeComment"), arial10format));	
	        sheet.addCell(new Label(6, row, (String)project.get("timeHour"), arial10format));	
	        if(charges)sheet.addCell(new Label(7, row, (String)project.get("chargesAmount"), arial10format));	
	        if(charges)sheet.addCell(new Label(8, row, (String)project.get("chargesStyle"), arial10format));	
	        row++;  
	      }

	      row++;
	    }

	    workbook.write();
	    workbook.close();
  }
  
  public void createExcelReport(TimeListForm searchForm, DynaTreeDataModel root, OutputStream out,
      MessageResources messages, Locale locale, boolean isAdmin, boolean hour, boolean cost, boolean budget, boolean charges) throws RowsExceededException, WriteException,
      IOException {

    WritableWorkbook workbook = Workbook.createWorkbook(out);
    WritableSheet sheet = workbook.createSheet("Report", 0);

    sheet.setColumnView(0, 20);
    sheet.setColumnView(1, 20);
    sheet.setColumnView(2, 20);
    sheet.setColumnView(3, 15);
    sheet.setColumnView(4, 15);
    sheet.setColumnView(5, 15);
    sheet.setColumnView(6, 15);
    sheet.setColumnView(7, 15);
    sheet.setColumnView(8, 15);

    WritableFont arial10fontbold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
    WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
    WritableCellFormat arial10formatbold = new WritableCellFormat(arial10fontbold);
    WritableCellFormat arial10format = new WritableCellFormat(arial10font);

    NumberFormat nf = new NumberFormat("#,##0.00");
    WritableCellFormat arial10Decimalformat = new WritableCellFormat(nf);
    arial10Decimalformat.setFont(arial10font);

    int row = writeFilter(searchForm, messages, locale, isAdmin, sheet, arial10formatbold, arial10format, 1);
    row += 2;

    sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.customer"), arial10formatbold));
    sheet.addCell(new Label(1, row, messages.getMessage(locale, "time.project"), arial10formatbold));
    sheet.addCell(new Label(2, row, messages.getMessage(locale, "time.timeTask"), arial10formatbold));

    if(hour)sheet.addCell(new Label(3, row, messages.getMessage(locale, "time.report.totalHourCustomer"), arial10formatbold));
    if(cost)sheet.addCell(new Label(4, row, messages.getMessage(locale, "time.report.totalCostCustomer"), arial10formatbold));
    if(hour)sheet.addCell(new Label(5, row, messages.getMessage(locale, "time.report.totalHourProject"), arial10formatbold));
    if(cost)sheet.addCell(new Label(6, row, messages.getMessage(locale, "time.report.totalCostProject"), arial10formatbold));
    if(hour)sheet.addCell(new Label(7, row, messages.getMessage(locale, "time.report.totalHourTask"), arial10formatbold));
    if(cost)sheet.addCell(new Label(8, row, messages.getMessage(locale, "time.report.totalCostTask"), arial10formatbold));

    row++;

    for (int i = 0; i < root.size(); i++) {

      DynaTreeDataModel customer = (DynaTreeDataModel)root.getChild(i);
      sheet.addCell(new Label(0, row, (String)customer.get("name"), arial10format));

      if(hour)sheet.addCell(new Number(3, row, ((BigDecimal)customer.get("totalCustomerHour")).floatValue(),
          arial10Decimalformat));
      if(cost)sheet.addCell(new Number(4, row, ((BigDecimal)customer.get("totalCustomerCost")).floatValue(),
          arial10Decimalformat));
      row++;

      for (int j = 0; j < customer.size(); j++) {
        DynaTreeDataModel project = (DynaTreeDataModel)customer.getChild(j);
        sheet.addCell(new Label(1, row, (String)project.get("name"), arial10format));

        if(hour)sheet.addCell(new Number(5, row, ((BigDecimal)project.get("totalProjectHour")).floatValue(),
            arial10Decimalformat));
        if(cost)sheet.addCell(new Number(6, row, ((BigDecimal)project.get("totalProjectCost")).floatValue(),
            arial10Decimalformat));
        row++;

        for (int k = 0; k < project.size(); k++) {

          DynaTreeDataModel task = (DynaTreeDataModel)project.getChild(k);
          sheet.addCell(new Label(2, row, (String)task.get("name"), arial10format));
          Object obj = task.get("totalCost");
          if(hour)sheet.addCell(new Number(7, row, ((BigDecimal)(task.get("totalHour")==null ? BigDecimal.ZERO : task.get("totalHour"))).floatValue(), arial10Decimalformat));
          if(cost)sheet.addCell(new Number(8, row, ((BigDecimal)(task.get("totalCost")==null ? BigDecimal.ZERO : task.get("totalCost"))).floatValue(), arial10Decimalformat));

          row++;
        }

      }

      row++;
    }

    workbook.write();
    workbook.close();

  }

  public void createMatrixExcelReport(TimeListForm searchForm, DynaTreeDataModel root, OutputStream out,
      MessageResources messages, Locale locale, boolean isAdmin, List<UserReportCol> colsList)
      throws RowsExceededException, WriteException, IOException {

    WritableWorkbook workbook = Workbook.createWorkbook(out);
    WritableSheet sheet = workbook.createSheet("Report", 0);

    sheet.setColumnView(0, 20);
    sheet.setColumnView(1, 20);
    sheet.setColumnView(2, 20);
    sheet.setColumnView(3, 15);
    sheet.setColumnView(4, 15);

    for (int i = 0; i < colsList.size(); i++) {
      sheet.setColumnView(i + 5, 11);
    }

    WritableFont arial10fontbold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
    WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
    WritableCellFormat arial10formatbold = new WritableCellFormat(arial10fontbold);
    WritableCellFormat arial10formatboldRight = new WritableCellFormat(arial10fontbold);
    WritableCellFormat arial10formatboldHoliday = new WritableCellFormat(arial10fontbold);
    WritableCellFormat arial10formatboldWeekend = new WritableCellFormat(arial10fontbold);
    
    WritableCellFormat arial10format = new WritableCellFormat(arial10font);

    arial10formatboldHoliday.setBackground(Colour.YELLOW);
    arial10formatboldWeekend.setBackground(Colour.GRAY_25);
    arial10formatboldHoliday.setAlignment(Alignment.RIGHT);
    arial10formatboldWeekend.setAlignment(Alignment.RIGHT);
    arial10formatboldRight.setAlignment(Alignment.RIGHT);

    NumberFormat nf = new NumberFormat("#,##0.00");
    WritableCellFormat arial10Decimalformat = new WritableCellFormat(nf);
    arial10Decimalformat.setFont(arial10font);
    WritableCellFormat arial10DecimalformatHoliday = new WritableCellFormat(nf);
    arial10DecimalformatHoliday.setFont(arial10font);
    arial10DecimalformatHoliday.setBackground(Colour.YELLOW);
    WritableCellFormat arial10DecimalformatWeekend = new WritableCellFormat(nf);
    arial10DecimalformatWeekend.setFont(arial10font);  
    arial10DecimalformatWeekend.setBackground(Colour.GRAY_25);

    int row = writeFilter(searchForm, messages, locale, isAdmin, sheet, arial10formatbold, arial10format, 1);
    row += 2;

    sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.customer"), arial10formatbold));
    sheet.addCell(new Label(1, row, messages.getMessage(locale, "time.project"), arial10formatbold));
    sheet.addCell(new Label(2, row, messages.getMessage(locale, "time.timeTask"), arial10formatbold));
    sheet.addCell(new Label(3, row, messages.getMessage(locale, "time.report.totalCustomer"), arial10formatbold));
    sheet.addCell(new Label(4, row, messages.getMessage(locale, "time.report.totalProject"), arial10formatbold));

    int col = 5;
    for (UserReportCol reportCol : colsList) {
      WritableCellFormat format = arial10formatboldRight;
      if (reportCol.isHoliday()) {
        format = arial10formatboldHoliday;
      } else if (reportCol.isWeekend()) {
        format = arial10formatboldWeekend;
      }
      sheet.addCell(new Label(col, row, reportCol.getTitle(), format));
      col++;
    }

    row++;
    for (int i = 0; i < root.size(); i++) {

      DynaTreeDataModel customer = (DynaTreeDataModel)root.getChild(i);
      sheet.addCell(new Label(0, row, (String)customer.get("name"), arial10format));

      sheet.addCell(new Number(3, row, ((BigDecimal)customer.get("totalCustomer")).floatValue(), arial10Decimalformat));
      row++;

      for (int j = 0; j < customer.size(); j++) {
        DynaTreeDataModel project = (DynaTreeDataModel)customer.getChild(j);
        sheet.addCell(new Label(1, row, (String)project.get("name"), arial10format));

        sheet.addCell(new Number(4, row, ((BigDecimal)project.get("totalProject")).floatValue(), arial10Decimalformat));
        row++;

        for (int k = 0; k < project.size(); k++) {

          DynaTreeDataModel task = (DynaTreeDataModel)project.getChild(k);
          sheet.addCell(new Label(2, row, (String)task.get("name"), arial10format));

          col = 5;
          for (UserReportCol reportCol : colsList) {
            BigDecimal value = (BigDecimal)task.get(reportCol.getProperty());
            if (value != null) {
              WritableCellFormat format = arial10Decimalformat;
              if (reportCol.isHoliday()) {
                format = arial10DecimalformatHoliday;
              } else if (reportCol.isWeekend()) {
                format = arial10DecimalformatWeekend;
              }                            
              sheet.addCell(new Number(col, row, value.floatValue(), format));
            } else {
              WritableCellFormat format = arial10formatboldRight;
              if (reportCol.isHoliday()) {
                format = arial10formatboldHoliday;
              } else if (reportCol.isWeekend()) {
                format = arial10formatboldWeekend;
              } 
              sheet.addCell(new Label(col, row, "", format));
            }
            col++;
          }

          row++;
        }

      }

      row++;
    }

    workbook.write();
    workbook.close();
  }

  
  public void createMonthViewExport(TimeMonthViewForm searchForm, DynaTreeDataModel root, OutputStream out,
	      MessageResources messages, Locale locale, boolean isAdmin, List<UserReportCol> colsList, String grandTotal)
	      throws RowsExceededException, WriteException, IOException {

	    WritableWorkbook workbook = Workbook.createWorkbook(out);
	    WritableSheet sheet = workbook.createSheet("Report", 0);

	    sheet.setColumnView(0, 20);
	   

	    for (int i = 0; i < colsList.size(); i++) {
	      sheet.setColumnView(i + 5, 11);
	    }

	    WritableFont arial10fontbold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
	    WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
	    WritableCellFormat arial10formatbold = new WritableCellFormat(arial10fontbold);
	    WritableCellFormat arial10formatboldRight = new WritableCellFormat(arial10fontbold);
	    WritableCellFormat arial10formatboldHoliday = new WritableCellFormat(arial10fontbold);
	    WritableCellFormat arial10formatboldWeekend = new WritableCellFormat(arial10fontbold);
	    
	    WritableCellFormat arial10format = new WritableCellFormat(arial10font);

	    arial10formatboldHoliday.setBackground(Colour.YELLOW);
	    arial10formatboldWeekend.setBackground(Colour.GRAY_25);
	    arial10formatboldHoliday.setAlignment(Alignment.RIGHT);
	    arial10formatboldWeekend.setAlignment(Alignment.RIGHT);
	    arial10formatboldRight.setAlignment(Alignment.CENTRE);

	    NumberFormat nf = new NumberFormat("#,##0.00");
	    WritableCellFormat arial10Decimalformat = new WritableCellFormat(nf);
	    arial10Decimalformat.setFont(arial10font);
	    WritableCellFormat arial10DecimalformatHoliday = new WritableCellFormat(nf);
	    arial10DecimalformatHoliday.setFont(arial10font);
	    arial10DecimalformatHoliday.setBackground(Colour.YELLOW);
	    WritableCellFormat arial10DecimalformatWeekend = new WritableCellFormat(nf);
	    arial10DecimalformatWeekend.setFont(arial10font);  
	    arial10DecimalformatWeekend.setBackground(Colour.GRAY_25);

	    int row = writeFilter(searchForm, messages, locale, isAdmin, sheet, arial10formatbold, arial10format, 1);
	    row += 2;

	    sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.customerProjectTask"), arial10formatbold));

	    int col = 5;
	    for (UserReportCol reportCol : colsList) {
	      WritableCellFormat format = arial10formatboldRight;
	      if (reportCol.isHoliday()) {
	        format = arial10formatboldHoliday;
	      } else if (reportCol.isWeekend()) {
	        format = arial10formatboldWeekend;
	      }
	      sheet.addCell(new Label(col, row, reportCol.getTitle(), format));
	      col++;
	    }

	    row++;
	    for (int i = 0; i < root.size(); i++) {
	    	  
	      DynaTreeDataModel customer = (DynaTreeDataModel)root.getChild(i);
	      sheet.addCell(new Label(0, row, (String)customer.get("name"), arial10format));

	      
	          col = 5;
	          for (UserReportCol reportCol : colsList) {
	            BigDecimal value = (BigDecimal)customer.get(reportCol.getProperty());
	            if (value != null) {
	              WritableCellFormat format = arial10Decimalformat;
	              if (reportCol.isHoliday()) {
	                format = arial10DecimalformatHoliday;
	              } else if (reportCol.isWeekend()) {
	                format = arial10DecimalformatWeekend;
	              }                            
	              sheet.addCell(new Number(col, row, value.floatValue(), format));
	            } else {
	              WritableCellFormat format = arial10formatboldRight;
	              if (reportCol.isHoliday()) {
	                format = arial10formatboldHoliday;
	              } else if (reportCol.isWeekend()) {
	                format = arial10formatboldWeekend;
	              } 
	              sheet.addCell(new Label(col, row, "", format));
	            }
	            col++;
	          }
	          row++;
	    }
	    row++;
	    sheet.addCell(new Label(0, row,  messages.getMessage(locale, "time.grandTotal")+" "+grandTotal, arial10format));
	    workbook.write();
	    workbook.close();
  }

  private int writeFilter(TimeListForm searchForm, MessageResources messages, Locale locale, boolean isAdmin, WritableSheet sheet, WritableCellFormat arial10formatbold, WritableCellFormat arial10format, int row) throws WriteException, RowsExceededException {
    sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.report.filter"), arial10formatbold));

    if (StringUtils.isNotBlank(searchForm.getFrom())) {
      DateFormat dateFormat = new SimpleDateFormat(Constants.getDateFormatPattern());
      row++;
      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.from"), arial10format));
      sheet.addCell(new Label(1, row, dateFormat.format(searchForm.getFromDate()), arial10format));
    }

    if (StringUtils.isNotBlank(searchForm.getTo())) {
      DateFormat dateFormat = new SimpleDateFormat(Constants.getDateFormatPattern());
      row++;
      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.to"), arial10format));
      sheet.addCell(new Label(1, row, dateFormat.format(searchForm.getToDate()), arial10format));
    }

    if (StringUtils.isNotBlank(searchForm.getWeek())) {
      row++;
      sheet.addCell(new Label(0, row, messages.getMessage(locale, "header.week"), arial10format));
      sheet.addCell(new Label(1, row, searchForm.getWeek(), arial10format));
    }

    if (StringUtils.isNotBlank(searchForm.getMonth())) {
      DateFormatSymbols dfs = new DateFormatSymbols(locale);

      row++;
      sheet.addCell(new Label(0, row, messages.getMessage(locale, "calendar.month"), arial10format));
      sheet.addCell(new Label(1, row, dfs.getMonths()[Integer.parseInt(searchForm.getMonth())], arial10format));
    }

    if (StringUtils.isNotBlank(searchForm.getYear())) {
      row++;
      sheet.addCell(new Label(0, row, messages.getMessage(locale, "calendar.year"), arial10format));
      sheet.addCell(new Label(1, row, searchForm.getYear(), arial10format));
    }

    if (StringUtils.isNotBlank(searchForm.getTimeCustomerId())) {
      row++;
      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.customer"), arial10format));
      TimeCustomer customer = timeCustomerDao.findById(searchForm.getTimeCustomerId());
      sheet.addCell(new Label(1, row, customer.getName(), arial10format));
    }

    if (StringUtils.isNotBlank(searchForm.getTimeProjectId())) {
      row++;
      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.project"), arial10format));
      TimeProject project = timeProjectDao.findById(searchForm.getTimeProjectId());
      sheet.addCell(new Label(1, row, project.getName(), arial10format));
    }

    if (StringUtils.isNotBlank(searchForm.getTimeTaskId())) {
      row++;
      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.timeTask"), arial10format));
      TimeTask task = timeTaskDao.findById(searchForm.getTimeTaskId());
      sheet.addCell(new Label(1, row, task.getName(), arial10format));
    }

    if (isAdmin) {
      if (StringUtils.isNotBlank(searchForm.getGroupId())) {
        row++;
        sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.group"), arial10format));
        Group group = groupDao.findById(searchForm.getGroupId());
        sheet.addCell(new Label(1, row, translateService.getText(group, locale), arial10format));
      }

      if (StringUtils.isNotBlank(searchForm.getUserId())) {
        row++;
        sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.user"), arial10format));
        User user = userDao.findById(searchForm.getUserId());
        sheet.addCell(new Label(1, row, user.getName() + " " + user.getFirstName(), arial10format));
      }
    }
    return row;
  }
  
  private int writeFilter(TimeMonthViewForm searchForm, MessageResources messages, Locale locale, boolean isAdmin, WritableSheet sheet, WritableCellFormat arial10formatbold, WritableCellFormat arial10format, int row) throws WriteException, RowsExceededException {
	    sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.report.filter"), arial10formatbold));

	    if (StringUtils.isNotBlank(searchForm.getFrom())) {
	      DateFormat dateFormat = new SimpleDateFormat(Constants.getDateFormatPattern());
	      row++;
	      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.from"), arial10format));
	      sheet.addCell(new Label(1, row, dateFormat.format(searchForm.getFromDate()), arial10format));
	    }

	    if (StringUtils.isNotBlank(searchForm.getTo())) {
	      DateFormat dateFormat = new SimpleDateFormat(Constants.getDateFormatPattern());
	      row++;
	      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.to"), arial10format));
	      sheet.addCell(new Label(1, row, dateFormat.format(searchForm.getToDate()), arial10format));
	    }

	    if (StringUtils.isNotBlank(searchForm.getWeek())) {
	      row++;
	      sheet.addCell(new Label(0, row, messages.getMessage(locale, "header.week"), arial10format));
	      sheet.addCell(new Label(1, row, searchForm.getWeek(), arial10format));
	    }

	    if (StringUtils.isNotBlank(searchForm.getMonth())) {
	      DateFormatSymbols dfs = new DateFormatSymbols(locale);

	      row++;
	      sheet.addCell(new Label(0, row, messages.getMessage(locale, "calendar.month"), arial10format));
	      sheet.addCell(new Label(1, row, dfs.getMonths()[Integer.parseInt(searchForm.getMonth())], arial10format));
	    }

	    if (StringUtils.isNotBlank(searchForm.getYear())) {
	      row++;
	      sheet.addCell(new Label(0, row, messages.getMessage(locale, "calendar.year"), arial10format));
	      sheet.addCell(new Label(1, row, searchForm.getYear(), arial10format));
	    }

	    if (StringUtils.isNotBlank(searchForm.getTimeCustomerId())) {
	      row++;
	      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.customer"), arial10format));
	      TimeCustomer customer = timeCustomerDao.findById(searchForm.getTimeCustomerId());
	      sheet.addCell(new Label(1, row, customer.getName(), arial10format));
	    }

	    if (StringUtils.isNotBlank(searchForm.getTimeProjectId())) {
	      row++;
	      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.project"), arial10format));
	      TimeProject project = timeProjectDao.findById(searchForm.getTimeProjectId());
	      sheet.addCell(new Label(1, row, project.getName(), arial10format));
	    }

	    if (StringUtils.isNotBlank(searchForm.getTimeTaskId())) {
	      row++;
	      sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.timeTask"), arial10format));
	      TimeTask task = timeTaskDao.findById(searchForm.getTimeTaskId());
	      sheet.addCell(new Label(1, row, task.getName(), arial10format));
	    }

	    if (isAdmin) {
	      if (StringUtils.isNotBlank(searchForm.getGroupId())) {
	        row++;
	        sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.group"), arial10format));
	        Group group = groupDao.findById(searchForm.getGroupId());
	        sheet.addCell(new Label(1, row, translateService.getText(group, locale), arial10format));
	      }

	      if (StringUtils.isNotBlank(searchForm.getUserId())) {
	        row++;
	        sheet.addCell(new Label(0, row, messages.getMessage(locale, "time.user"), arial10format));
	        User user = userDao.findById(searchForm.getUserId());
	        sheet.addCell(new Label(1, row, user.getName() + " " + user.getFirstName(), arial10format));
	      }
	    }
	    return row;
	  }

}
