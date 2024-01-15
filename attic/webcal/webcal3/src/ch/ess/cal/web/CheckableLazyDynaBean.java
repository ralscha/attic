package ch.ess.cal.web;

import org.apache.commons.beanutils.LazyDynaBean;

import com.cc.framework.ui.model.Checkable;

/**
 * @author sr
 */
public class CheckableLazyDynaBean extends LazyDynaBean implements Checkable {

  private int checkState;

  public CheckableLazyDynaBean(String name) {
    super(name);
  }

  public int getCheckState() {
    return checkState;
  }

  public void setCheckState(final int state) {
    checkState = state;
  }

}
