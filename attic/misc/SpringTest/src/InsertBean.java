import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public class InsertBean extends HibernateDaoSupport implements InsertBeanInterface {
  
  @Transactional
  public void insertTest() {
  
    TestBean tb = new TestBean();
    tb.setName("1");
    
    getHibernateTemplate().save(tb);
  }
  
  
  public void insertTest2() {
    
      TestBean tb = new TestBean();
      tb.setName("11");
      
      getHibernateTemplate().save(tb);
    }
}
