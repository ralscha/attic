package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.enums.TaskReminderBeforeEnum;

@Option(id = "taskReminderBeforeOptions")
public class TaskReminderBeforeOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    addTranslate(request, TaskReminderBeforeEnum.BEFORE_START.getKey(), TaskReminderBeforeEnum.BEFORE_START.getValue());
    addTranslate(request, TaskReminderBeforeEnum.BEFORE_DUE.getKey(), TaskReminderBeforeEnum.BEFORE_DUE.getValue());
  }

}