package test3;

import java.io.IOException;
import java.util.Date;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


public class TestServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    try {
      System.out.println("HIER");
      
      Configuration conf = new AnnotationConfiguration().configure();
      
      
      SessionFactory sf = conf.buildSessionFactory();
      
      UserTransaction tx = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
      
      
      tx.begin();
      
      Session session = sf.getCurrentSession();
      
      Car car = new Car();
      car.setAktiv(Boolean.TRUE);
      car.setDate(new Date());
      car.setName("test");
      car.setAbzahlung(Time.MINUTE);
      session.save(car);
      
      System.out.println(car.getId());
      
      tx.commit();
            
      System.out.println("COMMIT");
      //session.close();
    } catch (Exception e) {
      throw new ServletException(e);
    } 
    
    
    
  }

}
