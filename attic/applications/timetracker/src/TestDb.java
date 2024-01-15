import java.util.Calendar;
import java.util.GregorianCalendar;


public class TestDb {
  
  public static void main(String[] args) {
    
    Calendar c = new GregorianCalendar(2004, Calendar.JANUARY, 1);
    c.set(Calendar.WEEK_OF_YEAR, 2);
    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    
    //int weekday = c.get(Calendar.DAY_OF_WEEK);
    System.out.println(c.getTime());
    
    
    /*
    BasicConfigurator.configure();
    Transaction tx = null;

    
    try {
      
      HibernateFactoryManager.initXML("/hibernatelocal.cfg.xml");
      System.out.println("HIER");
      Session session = HibernateSession.currentSession();
      tx = session.beginTransaction();
      
      List l = session.find("select c.name, p.name, t.name, sum(tt.workInHour), sum(tt.cost) from Customer c join c.projects p join p.tasks t join t.taskTimes tt group by t.name, p.name, c.name");      

      for (Iterator it = l.iterator(); it.hasNext(); ) {
        Object[] o = (Object[])it.next();
        System.out.println(o[0]);
        System.out.println(o[1]);
        System.out.println(o[2]);
        System.out.println(o[3]);
        System.out.println(o[4]);
        
      }
      
      
      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    */
  }

}
