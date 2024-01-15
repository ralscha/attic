import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;

public class MigrateTT {

  public static void main(String[] args) {

    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");

      ApplicationContext ap = new ClassPathXmlApplicationContext(new String[]{"/spring-datasource-local.xml",
          "/spring.xml", "/spring-mail.xml", "/spring-hibernate.xml", "/spring-init.xml", "/spring-last.xml"});

      SessionFactory sf = (SessionFactory)ap.getBean("sessionFactory");

      Session session = SessionFactoryUtils.getSession(sf, true);
      session.setFlushMode(FlushMode.AUTO);

      TransactionSynchronizationManager.bindResource(sf, new SessionHolder(session));

      TimeTaskDao timeTaskDao = (TimeTaskDao)ap.getBean("timeTaskDao");
      TimeProjectDao timeProjectDao = (TimeProjectDao)ap.getBean("timeProjectDao");
      TimeCustomerDao timeCustomerDao = (TimeCustomerDao)ap.getBean("timeCustomerDao");
      TimeDao timeDao = (TimeDao)ap.getBean("timeDao");
      UserDao userDao = (UserDao)ap.getBean("userDao");

      Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.20.197:1433/timetracker;instance=SQL2005;cachemetadata=true", "sa",
          "papa8gei");

      Map<Integer, TimeCustomer> customerMap = new HashMap<Integer, TimeCustomer>();
      Map<Integer, TimeProject> projectMap = new HashMap<Integer, TimeProject>();
      Map<Integer, TimeTask> taskMap = new HashMap<Integer, TimeTask>();
      Map<Integer, String> userMap = new HashMap<Integer, String>();
      
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select id,user_name from tt_user");
      while (rs.next()) {

        int id = rs.getInt(1);
        String loginid = rs.getString(2);
        
        userMap.put(id, loginid);
      }

      rs.close();
            
      rs = stmt.executeQuery("select id,name, description from tt_customer");
      while (rs.next()) {

        int id = rs.getInt(1);
        String name = rs.getString(2);
        String description = rs.getString(3);

        TimeCustomer customer = new TimeCustomer();
        customer.setActive(true);
        customer.setName(name);
        customer.setDescription(StringUtils.isNotBlank(description) ? description.trim() : null);
        timeCustomerDao.save(customer);

        customerMap.put(id, customer);
      }

      rs.close();
      
      
      rs = stmt.executeQuery("select id,name, description, customer_id from tt_project");
      while (rs.next()) {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String description = rs.getString(3);
        int customerId = rs.getInt(4);
        
        TimeProject project = new TimeProject();
        project.setActive(true);
        project.setName(name);
        project.setDescription(StringUtils.isNotBlank(description) ? description.trim() : null);
        project.setTimeCustomer(customerMap.get(customerId));
        timeProjectDao.save(project);
        projectMap.put(id, project);        
      }
      rs.close();
      
      rs = stmt.executeQuery("select id,name, description, project_id from tt_task");
      while (rs.next()) {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String description = rs.getString(3);
        int projectId = rs.getInt(4);
        
        TimeTask task = new TimeTask();
        task.setActive(true);
        task.setName(name);
        task.setDescription(StringUtils.isNotBlank(description) ? description.trim() : null);
        task.setTimeProject(projectMap.get(projectId));
        timeTaskDao.save(task);
        taskMap.put(id, task);
                
      }      
      rs.close();
        

      rs = stmt.executeQuery("select description, comment, task_time_date, start_hour, start_min, end_hour, end_min, "+
          "work_in_hour, hour_rate, cost, task_id, user_id from tt_task_time");
      while (rs.next()) {
        String description = rs.getString(1);
        String comment = rs.getString(2);
        Date timeDate = rs.getDate(3);
        
        int startHouri = rs.getInt(4);
        Integer startHour = null;
        if (!rs.wasNull()) {
          startHour = startHouri;
        }
        
        int startMini = rs.getInt(5);
        Integer startMin = null;
        if (!rs.wasNull()) {
          startMin = startMini;
        }
        
        int endHouri = rs.getInt(6);
        Integer endHour = null;
        if (!rs.wasNull()) {
          endHour = endHouri;
        }
        
        int endMini = rs.getInt(7);
        Integer endMin = null;
        if (!rs.wasNull()) {
          endMin = endMini;
        }
        
        BigDecimal workInHour = rs.getBigDecimal(8);
        BigDecimal hourRate = rs.getBigDecimal(9);
        BigDecimal cost = rs.getBigDecimal(10);
        int taskId = rs.getInt(11);
        int userId = rs.getInt(12);
        String userName = userMap.get(userId);
        User user = userDao.findByName(userName);
        if (user == null) {
          System.out.println(userId);
        }
        
        Time time = new Time();
        time.setUser(user);
        time.setActivity(description);
        time.setTaskTimeDate(timeDate);
        time.setComment(comment);
        time.setStartHour(startHour);
        time.setStartMin(startMin);
        time.setEndHour(endHour);
        time.setEndMin(endMin);
        time.setWorkInHour(workInHour);
        time.setHourRate(hourRate);
        time.setCost(cost);
        time.setTimeTask(taskMap.get(taskId));
        timeDao.save(time);

                
      }      
      rs.close();
      
      
      
      
      stmt.close();
      con.close();
      
      System.out.println("ENDE");

      TransactionSynchronizationManager.unbindResource(sf);
      SessionFactoryUtils.releaseSession(session, sf);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
