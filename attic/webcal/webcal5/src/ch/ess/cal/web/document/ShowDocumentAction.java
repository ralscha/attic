package ch.ess.cal.web.document;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.model.Document;
import ch.ess.base.web.AbstractDocumentAction;
import ch.ess.cal.dao.FileDao;
import ch.ess.cal.model.File;

@SpringAction
@StrutsAction(path = "/showDocument")
public class ShowDocumentAction extends AbstractDocumentAction {

  private FileDao fileDao;

  public void setFileDao(FileDao fileDao) {
    this.fileDao = fileDao;
  }

  @Override
  protected Document getContent(HttpServletRequest request) throws Exception {

    String id = request.getParameter("id");
    File file = fileDao.findById(id);
    String fileName = file.getName();
    if (!fileName.endsWith(file.getDocument().getFileExtension())) {
      fileName += "." + file.getDocument().getFileExtension();
    }
    file.getDocument().setFileName(fileName);
    return file.getDocument();

  }

}
