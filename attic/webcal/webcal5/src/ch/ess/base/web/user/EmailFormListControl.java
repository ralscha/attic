package ch.ess.base.web.user;

import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.base.model.User;
import ch.ess.base.web.AbstractFormListControl;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.model.Email;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlRequestContext;

public class EmailFormListControl extends AbstractFormListControl<User, Email> {

  @Override
  public void onDelete(ControlRequestContext ctx, String key) throws Exception {
    super.onDelete(ctx, key);
    
    //check if there is one default email
    SimpleListDataModel dataModel = (SimpleListDataModel)getDataModel();
    if (dataModel.size() > 0 ) {
      boolean hasDefault = false;
      for (int i = 0; i < dataModel.size() && !hasDefault; i++) {
        DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
        Boolean def = (Boolean)dynaBean.get("default");
        if (def != null && def) {
          hasDefault = true;
        }
      }
    
      //no default, set first row to default
      if (!hasDefault) {
        DynaBean dynaBean = (DynaBean)dataModel.getElementAt(0);
        dynaBean.set("default", Boolean.TRUE);
      }
    
    }    
  }

  @Override
  protected Email newModelItem(DynaBean dynaBean, User user, Map<String, Object> parameters) {
    Boolean def = (Boolean)dynaBean.get("default");
    String emailStr = (String)dynaBean.get("email");

    Email e = new Email();
    e.setDefaultEmail(def);
    e.setEmail(emailStr);
    e.setUser(user);
    return e;
  }

  @Override
  public void updateModelItem(DynaBean dynaBean, Email email, Map<String, Object> parameters) {
    Boolean def = (Boolean)dynaBean.get("default");
    email.setDefaultEmail(def);
  }

  @Override
  public void newRowItem(ActionContext ctx, DynaBean dynaBean, Email email) {
    dynaBean.set("email", email.getEmail());
    dynaBean.set("default", email.isDefaultEmail());
  }

  public void add(FormActionContext ctx, String email) {
    SimpleListDataModel dataModel = (SimpleListDataModel)getDataModel();

    int max = getMaxIndex(dataModel);

    DynaBean dynaBean = new LazyDynaBean("emailList");
    dynaBean.set("id", String.valueOf(max + 1));
    dynaBean.set("email", email);

    if (dataModel.size() == 0) {
      dynaBean.set("default", Boolean.TRUE);
    } else {
      dynaBean.set("default", Boolean.FALSE);
    }

    dataModel.add(dynaBean);

  }

  @Override
  public void onCheck(ControlRequestContext ctx, String key, SelectMode mode, boolean checked) throws Exception {

    //unchecked requests werden nicht verarbeitet
    //bei einem checked alle anderen auf unchecked gesetzt

    if (checked) {
      SimpleListDataModel dataModel = (SimpleListDataModel)getDataModel();

      for (int i = 0; i < dataModel.size(); i++) {
        DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
        if (key.equals(dataModel.getUniqueKey(i))) {
          dynaBean.set("default", Boolean.TRUE);
        } else {
          dynaBean.set("default", Boolean.FALSE);
        }
      }
    }

  }

}
