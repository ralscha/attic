package ch.ralscha.wsdemo.je;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.sleepycat.je.Environment;
import com.sleepycat.persist.EntityStore;

@Named
public class Shutdown {

  @Inject
  private Environment environment;

  @Inject
  private EntityStore entityStore;

  @PreDestroy
  public void destroy() {
    entityStore.close();

    environment.cleanLog();
    environment.close();
  }
}
