import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ch.ess.cal.dao.ContactDao;
import ch.ess.cal.model.Contact;
import ch.ess.cal.model.ContactAttribute;

public class MigrateAB {

  public static void main(String[] args) {

    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");

      ApplicationContext ap = new ClassPathXmlApplicationContext(new String[]{"/spring-datasource-local.xml",
          "/spring.xml", "/spring-mail.xml", "/spring-hibernate.xml", "/spring-init.xml"});

      SessionFactory sf = (SessionFactory)ap.getBean("sessionFactory");

      Session session = SessionFactoryUtils.getSession(sf, true);
      session.setFlushMode(FlushMode.MANUAL);

      TransactionSynchronizationManager.bindResource(sf, new SessionHolder(session));

      ContactDao contactDao = (ContactDao)ap.getBean("contactDao");

      Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.20.197:1433/ab", "sa", "papa8gei");

      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select id,category from abContact");
      while (rs.next()) {

        Long cid = rs.getLong(1);
        String category = rs.getString(2);

        Contact contact = new Contact();
        contact.setCategory(category);

        Map<String, ContactAttribute> ca = new HashMap<String, ContactAttribute>();

        Statement stmtre = con.createStatement();
        ResultSet rs2 = stmtre.executeQuery("select value,field from abContactAttribute where contactId =  " + cid);
        while (rs2.next()) {
          String value = rs2.getString(1);
          String field = rs2.getString(2);

          ContactAttribute c = new ContactAttribute();
          c.setContact(contact);
          c.setField(field);
          c.setValue(value);
          ca.put(field, c);
          
        }
        rs2.close();
        stmtre.close();

        contact.setContactAttributes(ca);
        contactDao.save(contact);
      }

      rs.close();
      stmt.close();
      con.close();

      TransactionSynchronizationManager.unbindResource(sf);
      SessionFactoryUtils.releaseSession(session, sf);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
