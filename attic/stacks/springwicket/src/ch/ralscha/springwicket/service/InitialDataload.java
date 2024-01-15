package ch.ralscha.springwicket.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

@Service
public class InitialDataload implements ApplicationListener {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private LokationenService lokationenService;

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof ContextRefreshedEvent) {
      String[] configLocations = ((XmlWebApplicationContext)event.getSource()).getConfigLocations();
      if (configLocations != null && configLocations[0].indexOf("applicationContext.xml") != -1) {
        init();
      }
    }
  }

  private void init() {

    Query query = entityManager.createQuery("select count(*) from Lokationen");
    if ((Long)query.getSingleResult() == 0) {

      lokationenService.insert("Aquarello", 1, 1, "47");
      lokationenService.insert("Feinkost Käfer", 2, 2, "52");
      lokationenService.insert("Maredo", 3, 3, "210");
      lokationenService.insert("Maredo", 4, 3, "190");
      lokationenService.insert("Maredo", 5, 3, "143");

    }

  }

}
