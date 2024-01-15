package ch.ess.cal.web.holiday;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

import ch.ess.cal.Constants;
import ch.ess.cal.model.Holiday;
import ch.ess.cal.persistence.HolidayDao;
import ch.ess.cal.service.impl.HolidayRegistry;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractListAction;
import ch.ess.cal.web.CheckableLazyDynaBean;
import ch.ess.cal.web.DynaListDataModel;
import ch.ess.cal.web.MapForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/17 06:04:25 $ 
 * 
 * @struts.action path="/listHoliday" roles="$holiday" name="mapForm" input="/holidaylist.jsp" scope="session" validate="false"
 * @struts.action-forward name="success" path="/holidaylist.jsp" 
 * @struts.action-forward name="edit" path="/editHoliday.do?id={0}" redirect="true"
 * @struts.action-forward name="create" path="/editHoliday.do" redirect="true"
 * 
 * @spring.bean name="/listHoliday" lazy-init="true" 
 * @spring.property name="attribute" value="holidays"
 */
public class HolidayListAction extends AbstractListAction {

  private HolidayDao holidayDao;
  private TranslationService translationService;
  private HolidayRegistry holidayRegistry;

  /**    
   * @spring.property reflocal="holidayDao"
   */
  public void setHolidayDao(final HolidayDao holidayDao) {
    this.holidayDao = holidayDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  /**
   * @spring.property ref="holidayRegistry"
   */
  public void setHolidayRegistry(HolidayRegistry holidayRegistry) {
    this.holidayRegistry = holidayRegistry;
  }

  public void holidays_onCheckAll(ControlActionContext ctx, SelectMode mode, boolean checked) throws Exception {
    List<Holiday> holidays = holidayDao.list();

    for (Holiday holiday : holidays) {
      holiday.setActive(Boolean.valueOf(checked));
      holidayDao.saveOrUpdate(holiday);
    }
    holidayRegistry.init();
    onRefresh(ctx);
  }

  public void holidays_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked) throws Exception {
    Holiday holiday = holidayDao.get(key);
    holiday.setActive(Boolean.valueOf(checked));
    holidayDao.saveOrUpdate(holiday);
    holidayRegistry.init();
    onRefresh(ctx);
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    DynaListDataModel dataModel = new DynaListDataModel();

    dataModel.addColumnFormatter("dayOfMonth", Constants.SIMPLE_FORMAT);
    dataModel.addColumnFormatter("fromYear", Constants.SIMPLE_FORMAT);
    dataModel.addColumnFormatter("toYear", Constants.SIMPLE_FORMAT);

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
      holidays = holidayDao.list();
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

      dynaBean.set("active", holiday.getActive());

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
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    Holiday holiday = holidayDao.get(key);
    if (holiday.getBuiltin() == null) {
      holidayDao.delete(key);
      return true;
    }
    return false;
  }
}
