import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import ch.ess.issue.entity.User;

public class Test {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    System.out.println(UUID.randomUUID().toString().length());
    //System.out.println(DigestUtils.shaHex("admin2323232323").length());

    Configuration conf = new AnnotationConfiguration().configure();
    SessionFactory sf = conf.buildSessionFactory();

    Session session = sf.openSession();

    Transaction tx = session.beginTransaction();

    
    List<User> users = session.createCriteria(User.class).list();
    
    for (User user : users) {
      System.out.println(user);
    }
    
    
    tx.commit();
    session.close();

  }

}
