package ch.ess.cal.web.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.LazyDynaClass;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import ch.ess.cal.Constants;
import ch.ess.cal.web.AbstractListAction;
import ch.ess.cal.web.DynaListDataModel;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/04/04 11:31:12 $ 
 * 
 * @struts.action path="/listJob" roles="$job"
 * @struts.action-forward name="success" path="/joblist.jsp" 
 * 
 * @spring.bean name="/listJob" lazy-init="true"
 * @spring.property name="attribute" value="jobs"
 */
public class JobListAction extends AbstractListAction {

  private static LazyDynaClass lazyDynaClass = new LazyDynaClass();
  static {
    lazyDynaClass.add("description", String.class);
    lazyDynaClass.add("nextFireTime", String.class);
    lazyDynaClass.add("startTime", String.class);
    lazyDynaClass.add("endTime", String.class);
    lazyDynaClass.add("previousFireTime", String.class);
  }

  private Scheduler scheduler;

  /**    
   * @spring.property ref="scheduler"
   */
  public void setScheduler(final Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    getResources(ctx.request());

    DynaListDataModel dataModel = new DynaListDataModel();

    DateFormat dateFormat = new SimpleDateFormat(Constants.getDateTimeFormatPattern() + ":ss");

    String[] triggerGroups = scheduler.getTriggerGroupNames();

    for (String groupName : triggerGroups) {
      String[] triggerNames = scheduler.getTriggerNames(groupName);
      for (String triggerName : triggerNames) {

        Trigger trigger = scheduler.getTrigger(triggerName, groupName);

        DynaBean dynaBean = new LazyDynaBean(lazyDynaClass);
        dynaBean.set("id", groupName + triggerName);

        dynaBean.set("group", trigger.getJobGroup());
        dynaBean.set("name", trigger.getJobName());
        dynaBean.set("description", trigger.getDescription());

        if (trigger.getNextFireTime() != null) {
          dynaBean.set("nextFireTime", dateFormat.format(trigger.getNextFireTime()));
        }

        if (trigger.getStartTime() != null) {
          dynaBean.set("startTime", dateFormat.format(trigger.getStartTime()));
        }

        if (trigger.getEndTime() != null) {
          dynaBean.set("endTime", dateFormat.format(trigger.getEndTime()));
        }

        if (trigger.getPreviousFireTime() != null) {
          dynaBean.set("previousFireTime", dateFormat.format(trigger.getPreviousFireTime()));
        }

        dataModel.add(dynaBean);

      }
    }

    dataModel.sort("group", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    return false;
  }
}
