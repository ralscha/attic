package ch.ess.base.web;

import com.cc.framework.ui.painter.def.DefResourceMap;

/**
 * @author sr
 */
public class MyResourceMap extends DefResourceMap {

  @Override
  protected void doRegisterImages() {
    super.doRegisterImages();    
    registerImage("ico.edit", createImage("icons/edit.gif", 16, 15));
    registerImage("ico.delete", createImage("icons/delete.gif", 16, 15));
    registerImage("ico.add", createImage("icons/add.gif", 16, 15));

  }

}
