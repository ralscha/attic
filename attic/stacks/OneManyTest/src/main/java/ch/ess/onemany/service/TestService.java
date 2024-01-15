package ch.ess.onemany.service;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.onemany.entity.Many;
import ch.ess.onemany.entity.One;

@Service
public class TestService {

  @Autowired
  private SessionFactory sessionFactory;

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<One> findAllOnes() {
    Criteria crit = getSession().createCriteria(One.class);
    List<One> ones = crit.list();
    
    for (One one : ones) {
      if (!one.getMany().isEmpty()) {
        for (Many many : one.getMany()) {
          System.out.println(many);
        }
      }
    }
    
    return ones;
  }
  
  @Transactional
  public void merge(final One one) {
    System.out.println(one.getMany().getClass());
    getSession().merge(one);
  }
  

}
