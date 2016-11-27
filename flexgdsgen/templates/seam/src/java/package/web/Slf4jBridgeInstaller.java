package @packageProject@.web;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Slf4jBridgeInstaller implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent event) {
    Logger rootLogger = LogManager.getLogManager().getLogger("");
    Handler[] handlers = rootLogger.getHandlers();
    for (int i = 0; i < handlers.length; i++) {
      rootLogger.removeHandler(handlers[i]);
    }

    SLF4JBridgeHandler.install();

  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    SLF4JBridgeHandler.uninstall();
  }

}
