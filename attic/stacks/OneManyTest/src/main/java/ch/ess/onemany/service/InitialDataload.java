package ch.ess.onemany.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.ess.onemany.entity.Many;
import ch.ess.onemany.entity.One;



@Service
public class InitialDataload {

  @Autowired
  private TestService testService;

  @PostConstruct
  public void init() {
    
    if (testService.findAllOnes().isEmpty()) {
      One one = new One();
      one.setName("one");
      Many many = new Many();
      many.setName("one_many");
      many.setOne(one);
      one.getMany().add(many);
      testService.merge(one);
      
      One two = new One();
      two.setName("two");
      testService.merge(two);
    }
  }

}

