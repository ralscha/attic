package ch.ess.cal.db;

import ch.ess.common.db.Persistent;

/** A business entity class representing a DepartmentConfiguration
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calDepartmentConfiguration"
  */
public class DepartmentConfiguration extends Persistent {

  private String name;
  private String propValue;
  private Department department;


  /** 
  * @hibernate.property length="100" not-null="true"
  */
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /** 
  * @hibernate.property length="255"
  */
  public String getPropValue() {
    return this.propValue;
  }

  public void setPropValue(String propValue) {
    this.propValue = propValue;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.cal.db.Department"
  * @hibernate.column name="departmentId" not-null="true" index="FK_DepartmentConfig_Department"  
  */
  public Department getDepartment() {
    return this.department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

}
