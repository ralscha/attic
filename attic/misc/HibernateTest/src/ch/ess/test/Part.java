package ch.ess.test;

// Generated 13-Jul-2006 12:08:02 by Hibernate Tools 3.1.0.beta5

/**
 * Part generated by hbm2java
 */
public class Part implements java.io.Serializable {

  // Fields    

  private int id;
  private int version;
  private Car car;
  private String name;

  // Constructors

  /** default constructor */
  public Part() {
  }

  /** minimal constructor */
  public Part(int id, Car car) {
    this.id = id;
    this.car = car;
  }

  /** full constructor */
  public Part(int id, Car car, String name) {
    this.id = id;
    this.car = car;
    this.name = name;
  }

  // Property accessors
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getVersion() {
    return this.version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Car getCar() {
    return this.car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
