package ch.ess.lbw.web.werk;

import ch.ess.base.PropertyCopier;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.lbw.model.Werk;

import com.cc.framework.adapter.struts.ActionContext;

@Role("$stammdaten")
public class WerkEditAction extends AbstractEditAction<Werk> {

  private PropertyCopier propertyCopier;
  
  public WerkEditAction() {
    propertyCopier = new PropertyCopier();
    propertyCopier.addExcludeProperty("version");
    propertyCopier.addExcludeProperty("id");
  }
  
  @Override
  public void modelToForm(ActionContext ctx, Werk model) throws Exception {
    WerkForm werkForm = (WerkForm)ctx.form();
    propertyCopier.copyProperties(model, werkForm);
    
  }

  @Override
  public void formToModel(ActionContext ctx, Werk model) throws Exception {
    WerkForm werkForm = (WerkForm)ctx.form();
    propertyCopier.copyProperties(werkForm, model);
  }
 
}
