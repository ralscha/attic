package ch.ess.cal.web.event;

import java.io.InputStream;

import ch.ess.base.Util;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.cal.service.ImportIcalImpl;
import ch.ess.cal.service.ImportStatus;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;

@SpringAction
@StrutsAction(path = "/importIcal", form = ImportForm.class, scope = ActionScope.REQUEST, validate = false, input = "/importical.jsp", roles = "$event", forwards = @Forward(name = "success", path = "/importical.jsp"))
public class ImportAction extends FWAction {

  private UserDao userDao;
  private ImportIcalImpl importIcal;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setImportIcal(ImportIcalImpl importIcal) {
    this.importIcal = importIcal;
  }

  public void doExecute(ActionContext ctx) throws Exception {
    ImportForm importForm = (ImportForm)ctx.form();
    User user = Util.getUser(ctx.session(), userDao);
    try {
      if (importForm.getImportFile() != null && importForm.getImportFile().getFileSize() > 0) {
        InputStream is = importForm.getImportFile().getInputStream();
        try {
          ImportStatus status = importIcal.importIcal(user, is);
          ctx.addGlobalMessage("event.import.msg", status.getNoOfInserted(), status.getNoOfUpdated());
        } finally {
          if (is != null) {
            is.close();
          }
  
        }
      }
    } finally {
      if (importForm.getImportFile() != null) {
        importForm.getImportFile().destroy();
      }
    }    
    ctx.forwardByName("success");

  }
}