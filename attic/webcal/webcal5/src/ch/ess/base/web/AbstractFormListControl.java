package ch.ess.base.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Util;
import ch.ess.base.model.BaseObject;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlRequestContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;

public abstract class AbstractFormListControl<P extends BaseObject, T extends BaseObject> extends SimpleListControl {

  
  @Override
  public void onDelete(final ControlRequestContext ctx, final String key) throws Exception {
    SimpleListDataModel dataModel = (SimpleListDataModel)ctx.control().getDataModel();
  
    for (int i = 0; i < dataModel.size(); i++) {
      if (key.equals(dataModel.getUniqueKey(i))) {
  
        dataModel.remove(i);
        return;
      }
    }
  }

  protected Locale getLocale(final HttpServletRequest request) {
    return Util.getLocale(request);
  }

  protected MessageResources getResources(final HttpServletRequest request) {
    return Util.getMessages(request);
  }
  
  protected String translate(final HttpServletRequest request, final String key) {
    return Util.translate(request, key);
  }

  public void populateEmptyList() {
    SimpleListDataModel dataModel = new SimpleListDataModel();
    setDataModel(dataModel);
  }

  protected int getMaxIndex(final ListDataModel dataModel) {
    int max = 0;
    for (int i = 0; i < dataModel.size(); i++) {
      String unique = dataModel.getUniqueKey(i);
      max = Math.max(max, Integer.parseInt(unique));
    }
    return max;
  }

  public void formToModel(final P parentObject, final Set<T> children) {
    formToModel(parentObject, children, null);
  }
  
  public void formToModel(final P parentObject, final Set<T> children, final Map<String, Object> parameters) {        
    SimpleListDataModel dataModel = (SimpleListDataModel)getDataModel();
    
    Map<Integer, T> idMap = new HashMap<Integer, T>();
  
    for (T reminder : children) {
      idMap.put(reminder.getId(), reminder);
    }
  
    for (int i = 0; i < dataModel.size(); i++) {
      DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
  
      Integer id = (Integer)dynaBean.get("dbId");
  
      if (id != null) {
        updateModelItem(dynaBean, idMap.get(id), parameters);
        idMap.remove(id);        
      } else {
        T newReminder = newModelItem(dynaBean, parentObject, parameters);
        children.add(newReminder);
      }
    }
  
    for (T reminder : idMap.values()) {      
      children.remove(reminder);      
    }
  }
  
  protected void updateModelItem(final DynaBean dynaBean, final T child, final Map<String, Object> parameters) {
    // default action, do nothing    
  }

  abstract protected T newModelItem(final DynaBean dynaBean, final P parentObject, final Map<String, Object> parameters) ;

  public void populateList(ActionContext ctx, Set<T> children) {
    SimpleListDataModel dataModel = new SimpleListDataModel();
  
    int ix = 0;
  
    for (T child : children) {
      DynaBean dynaBean = new LazyDynaBean();
      dynaBean.set("id", String.valueOf(ix++));  
      dynaBean.set("dbId", child.getId());
      newRowItem(ctx, dynaBean, child);
      
      dataModel.add(dynaBean);
    }
  
    setDataModel(dataModel);
  
  }

  abstract public void newRowItem(final ActionContext ctx, final DynaBean dynaBean, final T child);
  
}
