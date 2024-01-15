package ch.ess.cal.resource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import ch.ess.cal.db.Resource;
import ch.ess.cal.db.ResourceGroup;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;

public class InitialDataLoadResource {

  public static void initialLoad() throws HibernateException {

    List l = HibernateSession.currentSession().find("from User as u where u.name =  ?", "admin", Hibernate.STRING);

    Set dep = new HashSet(l);

    ResourceGroup group = new ResourceGroup();
    group.setUsers(dep);
    group.setTranslations(getTranslationMap("Autos", "Cars"));
    group.persist();

    Resource resource = new Resource();
    resource.setTranslations(getTranslationMap("Auto 1", "Car 1"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Auto 2", "Car 2"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Auto 3", "Car 3"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Auto 4", "Car 4"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Auto 5", "Car 5"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Auto 6", "Car 6"));
    resource.setResourceGroup(group);
    resource.persist();

    l = HibernateSession.currentSession().find("from User as u where u.name =  ?", "user", Hibernate.STRING);
    dep = new HashSet(l);

    group = new ResourceGroup();
    group.setUsers(dep);
    group.setTranslations(getTranslationMap("Räume", "Rooms"));
    group.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Raum 1", "Room 1"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Raum 2", "Room 2"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Raum 3", "Room 3"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Raum 4", "Room 4"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Raum 5", "Room 5"));
    resource.setResourceGroup(group);
    resource.persist();

    l = HibernateSession.currentSession().find("from Department");

    dep = new HashSet();
    dep.add(l.get(0));

    group = new ResourceGroup();
    group.setDepartments(dep);
    group.setTranslations(getTranslationMap("Taxis", "Taxis"));    
    group.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Taxi 1", "Taxi 1"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Taxi 2", "Taxi 2"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Taxi 3", "Taxi 3"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Taxi 4", "Taxi 4"));
    resource.setResourceGroup(group);
    resource.persist();

    resource = new Resource();
    resource.setTranslations(getTranslationMap("Taxi 5", "Taxi 5"));
    resource.setResourceGroup(group);
    resource.persist();

  }

  private static Map getTranslationMap(String german, String english) {
    Map m = new HashMap();
    m.put(Constants.GERMAN_LOCALE, german);
    m.put(Constants.ENGLISH_LOCALE, english);
    return m;
  }
}
