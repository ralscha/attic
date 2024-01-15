package ch.ess.cal.web.task;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.model.Document;
import ch.ess.base.web.AbstractDocumentAction;
import ch.ess.cal.dao.TaskDao;
import ch.ess.cal.model.Attachment;

@SpringAction
@StrutsAction(path = "/taskDocument")
public class TaskDocumentAction extends AbstractDocumentAction {

  private TaskDao taskDao;

  public void setTaskDao(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  protected Document getContent(HttpServletRequest request) throws Exception {

    String id = request.getParameter("id");
    Attachment attachment = taskDao.findAttachmentById(id);
    return attachment.getDocument();

  }

}
