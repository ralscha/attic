package ch.ess.test.dao;

import org.springframework.stereotype.Component;
import ch.ess.test.hibernate.TestObject;

@Component
public class TestObjectDao extends AbstractDao<TestObject> {

  public TestObjectDao() {
    super(TestObject.class);

  }

}
