import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author sr
 */
public class Test {

  public static void main(String[] args) {

    ApplicationContext ap = new ClassPathXmlApplicationContext(new String[]{"/spring-datasource-local.xml",
        "/spring.xml", "/spring-mail.xml", "/spring-hibernate.xml", "/spring-init.xml"});

    SessionFactory sf = (SessionFactory)ap.getBean("sessionFactory");

    Session session = SessionFactoryUtils.getSession(sf, true);
    session.setFlushMode(FlushMode.NEVER);

    TransactionSynchronizationManager.bindResource(sf, new SessionHolder(session));

    //    UserDao userDao = (UserDao)ap.getBean("userDao");
    //    User user = (User)userDao.get("1");

    session.createQuery("delete from Configuration").executeUpdate();

    //user.setFirstName("test");
    TransactionSynchronizationManager.unbindResource(sf);
    SessionFactoryUtils.releaseSession(session, sf);

    //    ApplicationContext ap = new ClassPathXmlApplicationContext("/spring-data.xml");
    //    PermissionData permissions = (PermissionData)ap.getBean("permissionData");
    //
    //    Set set = permissions.getPermissions();
    //    for (Iterator it = set.iterator(); it.hasNext();) {
    //      System.out.println(it.next());
    //
    //    }

    //    try {
    //      List testList = new ArrayList();
    //      
    //      DynaBean dynaBean = resultClass.newInstance();
    //      dynaBean.set("id", new Integer(1));
    //      dynaBean.set("userName", "sr");
    //      dynaBean.set("name", "Schär");
    //      dynaBean.set("firstName", "Ralph");
    //      
    //      testList.add(dynaBean);
    //      
    //      dynaBean = resultClass.newInstance();
    //      dynaBean.set("id", new Integer(2));
    //      dynaBean.set("userName", "aa");
    //      dynaBean.set("name", "Arnold");
    //      dynaBean.set("firstName", "Achim");
    //      
    //      testList.add(dynaBean);
    //      
    //      SortOrder direction = SortOrder.ASCENDING;
    //      
    //      if (direction.equals(SortOrder.ASCENDING)) {
    //        Collections.sort(testList, new DynaBeanComparator("name"));
    //      } else {
    //        Collections.sort(testList, new DynaBeanComparator("name", new
    // ReverseComparator()));
    //      }
    //      
    //      
    //      
    //      
    //      for (Iterator it = testList.iterator(); it.hasNext();) {
    //        DynaBean db = (DynaBean)it.next();
    //        System.out.println(db.get("id"));
    //        System.out.println(db.get("name"));
    //      }
    //      
    //      
    //      
    //    } catch (IllegalAccessException e) {
    //      e.printStackTrace();
    //    } catch (InstantiationException e) {
    //      e.printStackTrace();
    //    }

  }
}