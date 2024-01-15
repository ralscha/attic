package ch.ess.base.web;

import com.cc.framework.common.Singleton;
import com.cc.framework.ui.painter.PainterFactory;
import com.cc.framework.ui.painter.ResourceMap;
import com.cc.framework.ui.painter.def.DefPainterFactory;

/**
 * @author sr
 */
public class MyPainterFactory extends DefPainterFactory implements Singleton {

  private static MyPainterFactory instance = new MyPainterFactory();

  public static PainterFactory instance() {
    return instance;
  }

  @Override
  protected ResourceMap createResourceMap() {
    return new MyResourceMap();
  }

}