package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.enums.TaskViewEnum;

@Option(id = "taskViewOptions")
public class TaskViewOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    addTranslate(request, TaskViewEnum.SIMPLE_LIST.getKey(), String.valueOf(TaskViewEnum.SIMPLE_LIST.getValue()));
    addTranslate(request, TaskViewEnum.DETAILED_LIST.getKey(), String.valueOf(TaskViewEnum.DETAILED_LIST.getValue()));
    addTranslate(request, TaskViewEnum.ACTIVE_TASKS.getKey(), String.valueOf(TaskViewEnum.ACTIVE_TASKS.getValue()));
    addTranslate(request, TaskViewEnum.NEXT_SEVEN_DAYS.getKey(), String.valueOf(TaskViewEnum.NEXT_SEVEN_DAYS.getValue()));
    addTranslate(request, TaskViewEnum.OVERDUE_TASKS.getKey(), String.valueOf(TaskViewEnum.OVERDUE_TASKS.getValue()));
  }

}
