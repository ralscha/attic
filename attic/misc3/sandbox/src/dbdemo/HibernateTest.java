package dbdemo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;

public class HibernateTest {

  public static void main(String[] args) {
    
    try {

      Properties dbProps = new Properties();
      dbProps.load(HibernateTest.class.getResourceAsStream("/hibernate.properties"));


      SessionFactory sf = new Configuration().addClass(Child.class).addClass(Parent.class)
      .setProperties(dbProps).buildSessionFactory();      
      
      
      // Then build a session to the database      
      Session session = sf.openSession();
  
      Transaction tx = session.beginTransaction();
      
      /*
      Set s = new HashSet();
      Parent p = new Parent();
      Child c = new Child();
      c.setParent(p);
      c.setName("one");
      c.setDescr("an one");     
      s.add(c); 
      

      c = new Child();
      c.setParent(p);
      c.setName("two");
      c.setDescr("a two");
      s.add(c);      
      p.setChild(s);
      
      Map m = new HashMap();
      m.put("one", "an one");
      m.put("two", "a two");
      p.setChildMap(m);
      
      session.save(p);
      */
      //session.flush();
      
      
      Criteria crit = session.createCriteria(Parent.class);            
      crit.createCriteria("child").add(net.sf.hibernate.expression.Expression.like("name", "one"));
      
      List l = crit.list();
      Iterator it = l.iterator();
      while (it.hasNext()) {
        Parent par = (Parent)it.next();
        System.out.println(par.getId());        
      }
      
      
      l = session.find("from Parent as p where p.childMap.descr like 'test'");
      it = l.iterator();
      while (it.hasNext()) {
        Parent par = (Parent)it.next();
        System.out.println(par.getId());        
      }
      
      
        
      
/*      
      User newUser = new User();
      newUser.setUserID("joe_cool9");
      newUser.setUserName("Joseph Cool");
      newUser.setPassword("abc123");
      newUser.setEmailAddress("joe@cool.com");
      newUser.setLastLogon(new java.util.Date());

      // And the Hibernate call which stores it
      session.save(newUser);

*/

/*
      User newUser2 = (User) session.load(User.class, "joe_cool2");
      System.out.println(newUser2.getUserID());
      
      session.delete(newUser2);
  */    
      
      /*
      newUser2 = (User) session.load(User.class, "joe_cool");
      System.out.println(newUser2);
      */
      /*
      OrderTotal newOrder = new OrderTotal();
      newOrder.setDescription("one");
      newOrder.setTotal(new BigDecimal("12.55"));
      
      OrderPos pos = new OrderPos();
      pos.setDescription("pos1");
      pos.setNo(new BigDecimal("1"));
      pos.setPrice(new BigDecimal("10.05"));

      pos.setOrder(newOrder);
      
      
      Set posItems = new HashSet();
      posItems.add(pos);
      newOrder.setOrderPos(posItems);
     
      
      session.save(newOrder);
    
    */
      /*
      pos.setOrderTotal(newOrder);
      session.save(pos);

      
      newOrder = new OrderTotal();
      newOrder.setDescription("two");
      newOrder.setTotal(new BigDecimal("99.95"));
      session.save(newOrder);
      */
  /*  
      List myOrders = session.find("from orders in class dbdemo.OrderTotal");
      //System.out.println(myOrders.size());


      for (Iterator i = myOrders.iterator(); i.hasNext() ; ) {

        OrderTotal orders = (OrderTotal)i.next();        
        
        Set posSet = orders.getOrderPos();
        
        if (posSet != null) {
          Iterator it = posSet.iterator();
          while(it.hasNext()) {
            OrderPos opos = (OrderPos)it.next();
            System.out.println(opos.getDescription());
          }
        }
        
        
       
      }

*/ 
/*
      } else {
        System.out.println("Didn't find any matching users..");

      }
      
    
  */    
           
      
      
      tx.commit();
      
      // close our session and release resources
      
      //session.flush();
      //session.connection().commit();
      
      session.close();
      
      
      
    } catch (MappingException e) {
      e.printStackTrace();
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    
  }
}
