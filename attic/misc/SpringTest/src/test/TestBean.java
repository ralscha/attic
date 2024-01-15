package test;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(autowire=Autowire.BY_NAME)
public class TestBean {

  private String name = "R";
  private int age = 34;

  public TestBean() {
    //no default action 
  }
  
  public TestBean(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
