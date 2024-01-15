package ch.ess.cal.web.holiday;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.CheckableLazyDynaBean;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.dao.HolidayDao;
import ch.ess.cal.model.Holiday;
import ch.ess.cal.service.HolidayRegistry;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

@Role("$admin")
public class HolidayListAction extends AbstractListAction {

  private HolidayDao holidayDao;
  private TranslationService translationService;
  private HolidayRegistry holidayRegistry;

  public void setHolidayDao(final HolidayDao holidayDao) {
    this.holidayDao = holidayDao;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  public void setHolidayRegistry(HolidayRegistry holidayRegistry) {
    this.holidayRegistry = holidayRegistry;
  }

  public void listControl_onCheckAll(ControlActionContext ctx, SelectMode mode, boolean checked) throws Exception {
    List<Holiday> holidays = holidayDao.findAll();

    for (Holiday holiday : holidays) {
      holiday.setActive(Boolean.valueOf(checked));
      holidayDao.save(holiday);
    }
    holidayRegistry.init();
    onRefresh(ctx);
  }

  public void listControl_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked) throws Exception {
    Holiday holiday = holidayDao.findById(key);
    holiday.setActive(Boolean.valueOf(checked));
    holidayDao.save(holiday);
    holidayRegistry.init();
    onRefresh(ctx);
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();
//
//    dataModel.addColumnFormatter("dayOfMonth", Constants.SIMPLE_FORMAT);
//    dataModel.addColumnFormatter("fromYear", Constants.SIMPLE_FORMAT);
//    dataModel.addColumnFormatter("toYear", Constants.SIMPLE_FORMAT);

    Locale locale = getLocale(ctx.request());
    DateFormatSymbols symbols = new DateFormatSymbols(locale);

    MapForm searchForm = (MapForm)ctx.form();

    String monthNo = null;
    if (searchForm != null) {
      monthNo = searchForm.getStringValue("monthNo");
    }

    List<Holiday> holidays = null;
    if (monthNo != null) {
      Integer monthNoInt = new Integer(monthNo);
      holidays = holidayDao.find(monthNoInt);
    } else {
      holidays = holidayDao.findAll();
    }

    String[] weekdays = symbols.getWeekdays();
    String[] months = symbols.getMonths();

    for (Holiday holiday : holidays) {

      CheckableLazyDynaBean dynaBean = new CheckableLazyDynaBean("holidayList");

      dynaBean.set("id", holiday.getId().toString());

      dynaBean.set("name", translationService.getText(holiday, locale));

      if (holiday.getMonthNo() != null) {
        dynaBean.set("monthNo", months[holiday.getMonthNo()]);
      }

      if (holiday.getDayOfMonth() != null) {
        dynaBean.set("dayOfMonth", holiday.getDayOfMonth());
      }

      if (holiday.getDayOfWeek() != null) {
        dynaBean.set("dayOfWeek", weekdays[holiday.getDayOfWeek()]);
      }

      if (holiday.getFromYear() != null) {
        dynaBean.set("fromYear", holiday.getFromYear());
      }

      if (holiday.getToYear() != null) {
        dynaBean.set("toYear", holiday.getToYear());
      }

      if (holiday.getAfterDay() != null) {
        dynaBean.set("after", holiday.getAfterDay());
      } else {
        dynaBean.set("after", Boolean.FALSE);
      }

      //dynaBean.set("active", holiday.getActive());
      dynaBean.set("deletable", holidayDao.canDelete(holiday));
      
      if (holiday.getActive()) {
        dynaBean.setCheckState(1);
      } else {
        dynaBean.setCheckState(0);
      }
      dataModel.add(dynaBean);

    }

    dataModel.sort("monthNo", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    Holiday holiday = holidayDao.findById(key);
    if (holiday.getBuiltin() == null) {
      holidayDao.delete(key);
    }
  }
  
  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      Holiday holiday = holidayDao.findById(id);
      if (holiday != null) {
        Locale locale = getLocale(ctx.request());
        return translationService.getText(holiday, locale);
      }
    }
    return null;
  } 
}
