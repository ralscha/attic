package test3;

import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateTest {

  public static void main(String[] args) {

    try {
      

      Configuration conf = new AnnotationConfiguration().configure();
      SessionFactory sf = conf.buildSessionFactory();
      
//      Properties dbProps = new Properties();
//      dbProps.load(HibernateTest.class.getResourceAsStream("/hibernate.cfg.xml"));
//
//      Configuration conf = new Configuration();
//      conf.setProperties(dbProps);
      

      Session session = sf.openSession();
      
      Transaction tx = session.beginTransaction();
      
      Car car = new Car();
      car.setAktiv(Boolean.TRUE);
      car.setDate(new Date());
      car.setName("test");
      car.setAbzahlung(Time.MINUTE);
      session.save(car);
      
      System.out.println(car.getId());
      
      tx.commit();
      session.close();
      
      
      
//      SchemaUpdate su = new SchemaUpdate(conf);
//      su.execute(true, true);
      
//      AnnotationConfiguration ac = new AnnotationConfiguration();
//      ac.setProperties(dbProps);            
//      ac.addAnnotatedClass(Field.class);
//      ac.addAnnotatedClass(FieldTextres.class);
//      ac.addAnnotatedClass(Language.class);
//      ac.addAnnotatedClass(TextResource.class);
//      
//      SessionFactory sf = ac.buildSessionFactory();
      
      //Session session = sf.openSession();
      
//      SchemaUpdate upd = new SchemaUpdate(conf);
//      upd.execute(true, true);

//      Transaction tx = session.beginTransaction();
//
//            
////      Car car = new Car();
////      car.setAktiv(Boolean.TRUE);
////      car.setDate(new Date());
////      car.setName("test");
////      car.setAbzahlung(Time.MINUTE);
////      session.save(car);
////      
////      System.out.println(car.getId());
//      
//      Car car = (Car)session.load(Car.class, 1);
//      car.setName("test2");
//      
//      
////      Criteria c = session.createCriteria(Car.class);
////      List<Car> l = c.list();
////      for (Car car : l) {
////        System.out.println(car.getName());
////        int size = car.getParts().size();
////        if (size > 0) {          
////          System.out.println(size);
////          Part p = car.getParts().iterator().next();
////        }
////      }
//      
////      Criteria c = session.createCriteria(Car.class);
////      c.add(Restrictions.eq("name", "test");
////      List l = c.list();
//      
//      
//      
//      tx.commit();
//      session.close();

    } catch (MappingException e) {
      e.printStackTrace();
    } catch (HibernateException e) {
      e.printStackTrace();
    } 

  }
}
