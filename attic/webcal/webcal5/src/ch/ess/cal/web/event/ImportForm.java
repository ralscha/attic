package ch.ess.cal.web.event;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ImportForm extends ActionForm {
  private FormFile importFile;

  public FormFile getImportFile() {
    return importFile;
  }

  public void setImportFile(FormFile importFile) {
    this.importFile = importFile;
  }
}
