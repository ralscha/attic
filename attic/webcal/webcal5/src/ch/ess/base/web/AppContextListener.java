package ch.ess.base.web;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.LogFactory;

import com.cc.framework.Globals;
import com.cc.framework.ui.control.ControlActionDef;
import com.cc.framework.ui.model.ImageModel;
import com.cc.framework.ui.model.imp.ImageModelImp;
import com.cc.framework.ui.painter.PainterFactory;
import com.cc.framework.ui.painter.html.HtmlPainterFactory;

public class AppContextListener implements ServletContextListener {

  public void contextInitialized(final ServletContextEvent event) {

    final ServletContext servletContext = event.getServletContext();

    ControlActionDef.registerActionDefinition(WebConstants.ACTION_COPY);
    
    //Def
    String resourceDir = "fw/def/";
    PainterFactory.registerApplicationPainter(servletContext, MyPainterFactory.instance());    
    MyPainterFactory.instance().getResources().registerImage("ico.copy", createImage(resourceDir, "icons/copy.gif", 16, 16));
    MyPainterFactory.instance().getResources().registerImage("ico.upload", createImage(resourceDir, "icons/upload.gif", 16, 16));
        
    //Def2    
    //String resourceDir = "fw/def2/";
    //PainterFactory.registerApplicationPainter(servletContext, Def2PainterFactory.instance());
    //Def2PainterFactory.instance().getResources().registerImage("ico.copy", createImage(resourceDir, "icons/copy.gif", 16, 16));
    
    
    PainterFactory.registerApplicationPainter(servletContext, HtmlPainterFactory.instance());
    
    servletContext.setAttribute(Globals.LOCALENAME_KEY, "true");
  }

  public void contextDestroyed(final ServletContextEvent event) {
    final ServletContext servletContext = event.getServletContext();

    //release PainterFactories

    //Def
    PainterFactory.unregisterApplicationPainter(servletContext, MyPainterFactory.instance());
        
    //Def2 
    //PainterFactory.unregisterApplicationPainter(servletContext, Def2PainterFactory.instance());
       
    
    PainterFactory.unregisterApplicationPainter(servletContext, HtmlPainterFactory.instance());

    //release JDBC Drivers
    try {
      for (Enumeration<Driver> e = DriverManager.getDrivers(); e.hasMoreElements();) {
        Driver driver = e.nextElement();
        if (driver.getClass().getClassLoader() == getClass().getClassLoader()) {
          DriverManager.deregisterDriver(driver);
        }
      }
    } catch (Throwable throwable) {
      servletContext.log("Failled to cleanup");
      throwable.printStackTrace();
    }

    //release Logging
    LogFactory.release(Thread.currentThread().getContextClassLoader());

  }

  protected ImageModel createImage(String resourceDir, String fileName, int width, int height) {
    StringBuffer stringbuffer = (new StringBuffer()).append(resourceDir).append("image/").append(fileName);
    return new ImageModelImp(stringbuffer.toString(), width, height);
  }
}
