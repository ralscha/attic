package ch.ess.base.web;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.utx.PdfWorkbook;
import ch.ess.utx.UtxCell;
import ch.ess.utx.UtxCellStyle;
import ch.ess.utx.UtxFont;
import ch.ess.utx.UtxRow;
import ch.ess.utx.UtxSheet;
import ch.ess.utx.UtxWorkbook;
import ch.ess.utx.XlsWorkbook;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.AlignmentType;
import com.cc.framework.ui.control.ControlActionContext;

@SpringAction
public abstract class AbstractListBaseAction extends FWAction implements CrumbInfo {

  public void onCrumbClick(final ControlActionContext ctx, final String key) throws Exception {
    doExecute(ctx);
  }

  protected String getListAttributeName() {
    return "listControl";
  }

  public void onEdit(final ControlActionContext ctx, final String key) {
    ctx.forwardByName("edit", key);
  }

  public void onCopy(final ControlActionContext ctx, final String key) {
    ctx.forwardByName("copy", key);
  }

  public void onDelete(final ControlActionContext ctx, final String key) throws Exception {
    deleteObject(ctx, key);
    ctx.addGlobalMessage("common.deleteOK");
  
    onRefresh(ctx);
  }

  abstract void onRefresh(final ControlActionContext ctx) throws Exception;
  
  public abstract void deleteObject(final ControlActionContext ctx, final String key) throws Exception;

  public void onSubmit(final FormActionContext ctx) throws Exception {
    doExecute(ctx);
  }

  public void search_onClick(final FormActionContext ctx) throws Exception {
    doExecute(ctx);
  }

  public void clearSearch_onClick(final FormActionContext ctx) throws Exception {
    MapForm form = (MapForm) ctx.form();
    form.getValueMap().clear();
    ctx.session().removeAttribute(getListAttributeName());
    ctx.forwardToInput();
  }  
  
  public void onCreate(final ControlActionContext ctx) {
    ctx.forwardByName("create");
  }

  public void onPrintList(final ControlActionContext ctx) throws Exception {
    doExport(ctx, new PdfWorkbook());
    ctx.forwardToResponse();
  }

  public void onExportList(ControlActionContext ctx) throws Exception {
    doExport(ctx, new XlsWorkbook());
    ctx.forwardToResponse();
  }

  
  protected abstract SimpleListDataModel getExportDataModel(final ControlActionContext ctx);
  
  public void doExport(final ControlActionContext ctx, UtxWorkbook workbook) throws Exception {
  
    SimpleListDataModel dataModel = getExportDataModel(ctx);
    List<ExportPropertyDescription> exportPropertiesList = dataModel.getExportPropertiesList();
  
    OutputStream out = ctx.response().getOutputStream();
  
    Util.setExportHeader(ctx.response(), workbook.getMimeType(), dataModel.getExportFileName() + "."
        + workbook.getFileExtension());
  
    UtxSheet sheet = workbook.createSheet();
    
    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());
  
    UtxFont font = workbook.createFont();
    UtxFont titleFont = workbook.createFont();
  
    font.setColor(HSSFColor.BLACK.index);
    font.setFontName("Helvetica");
    font.setFontHeightInPoints((short)10);
    font.setBoldweight(UtxFont.BOLDWEIGHT_NORMAL);
  
    titleFont.setColor(HSSFColor.BLACK.index);
    titleFont.setFontName("Helvetica");
    titleFont.setFontHeightInPoints((short)10);
    titleFont.setBoldweight(UtxFont.BOLDWEIGHT_BOLD);
  
    UtxCellStyle normalStyle = workbook.createCellStyle();
    normalStyle.setFont(font);
  
    UtxCellStyle normalRightStyle = workbook.createCellStyle();
    normalRightStyle.setFont(font);
    normalRightStyle.setAlignment(UtxCellStyle.ALIGN_RIGHT);
  
    UtxCellStyle normalDateStyle = workbook.createCellStyle();
    normalDateStyle.setDataFormat(DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("de")));
    normalDateStyle.setFont(font);
    normalDateStyle.setAlignment(UtxCellStyle.ALIGN_LEFT);
  
    UtxCellStyle normalNumberStyle = workbook.createCellStyle();
    normalNumberStyle.setDataFormat(new DecimalFormat("#,##0.00"));
    normalNumberStyle.setFont(font);
    normalNumberStyle.setAlignment(UtxCellStyle.ALIGN_RIGHT);
  
    UtxCellStyle titleStyle = workbook.createCellStyle();
    titleStyle.setFont(titleFont);
  
    //Title Row
    UtxRow row = sheet.createRow(0);
    int col = 0;
  
    for (ExportPropertyDescription propertyDescr : exportPropertiesList) {
  
      UtxCell cell = row.createCell((short)col);
      String msg = messages.getMessage(locale, propertyDescr.getTitle());
      if (msg != null) {
        cell.setCellValue(msg);
      } else {
        cell.setCellValue(propertyDescr.getTitle());
      }
      cell.setCellStyle(titleStyle);
      sheet.setColumnWidth((short)col, (short)propertyDescr.getColWidth());
      col++;
    }
  
    DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());
  
    int size = dataModel.size();
    for (int i = 0; i < size; i++) {
      row = sheet.createRow(i + 1);
      Object rowObject = dataModel.getElementAt(i);
  
      col = 0;
      for (ExportPropertyDescription propertyDescr : exportPropertiesList) {
  
        UtxCell cell = row.createCell((short)col);
        Object originalValue = PropertyUtils.getProperty(rowObject, propertyDescr.getPropertyName());
        
        Object value = null;
        if ((originalValue != null) && (propertyDescr.getConverter() != null)) {
          value = propertyDescr.getConverter().getAsString(null, originalValue);  
        } else {
          value = originalValue;
        }
  
        if (value != null) {
          boolean styleSet = false;
          if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal)value).doubleValue());
            cell.setCellStyle(normalNumberStyle);
            styleSet = true;
          } else if (value instanceof Number) {
            cell.setCellValue(((Number)value).doubleValue());
          } else if (value instanceof Date) {
            cell.setCellValue(df.format((Date)value));
            cell.setCellStyle(normalDateStyle);
            styleSet = true;
          } else if (value instanceof Calendar) {
            cell.setCellValue(df.format(((Calendar)value).getTime()));
            cell.setCellStyle(normalDateStyle);
            styleSet = true;
          } else if (value instanceof Boolean) {
            cell.setCellValue(((Boolean)value).booleanValue());
          } else if (value instanceof String) {
            value = StringUtils.replace((String)value, "<br>", "\n");
            cell.setCellValue((String)value);
          } else {
            cell.setCellValue(value.toString());
          }
  
          if (!styleSet) {
            if (propertyDescr.getAlignment().equals(AlignmentType.RIGHT)) {
              cell.setCellStyle(normalRightStyle);
            } else {
              cell.setCellStyle(normalStyle);
            }
          }
        }
        col++;
      }
    }
  
    workbook.write(out);
  
    out.close();
    ctx.forwardToResponse();
  }

  public String getTitle(final String id, final ActionContext ctx) {
    return null;
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
