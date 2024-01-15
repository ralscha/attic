package test3;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Part extends BaseObject {

  private String name;
  private Car car;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ManyToOne
  @JoinColumn(name = "carId", nullable = false)
  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }
  
  
  
}
