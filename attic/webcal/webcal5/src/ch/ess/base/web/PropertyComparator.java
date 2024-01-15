package ch.ess.base.web;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class PropertyComparator implements Comparator<Object>, Serializable {

  private String property1;
  private String property2;
  private String property3;
  private String property4;
  private String property5;
  private Comparator<Object> comparator;

  @SuppressWarnings("unchecked")
  public PropertyComparator(final String property1) {
    this(property1, ComparableComparator.getInstance());
  }

  @SuppressWarnings("unchecked")
  public PropertyComparator(final String property1, final String property2) {
    this(property1, property2, ComparableComparator.getInstance());
  }
  
  @SuppressWarnings("unchecked")
public PropertyComparator(final String property1, final String property2, final String property3, final String property4, final String property5) {
	  this(property1, property2, property3,property4, property5, ComparableComparator.getInstance());
  }

  public PropertyComparator(final String property1, final Comparator<Object> comparator) {
    this(property1, null, comparator);
  }

  public PropertyComparator(final String property1, final String property2, final Comparator<Object> comparator) {
    setProperty1(property1);
    setProperty2(property2);
    this.comparator = comparator;
  }

  public PropertyComparator(final String property1, final String property2, final String property3, final String property4, final String property5, final Comparator<Object> comparator) {
    setProperty1(property1);
    setProperty2(property2);
    setProperty3(property3);
    setProperty4(property4);
    setProperty5(property5);
    this.comparator = comparator;
  }

  public final void setProperty1(final String property1) {
    this.property1 = property1;
  }

  public String getProperty1() {
    return property1;
  }

  public final void setProperty2(final String property2) {
    this.property2 = property2;
  }

  public String getProperty2() {
    return property2;
  }

  public final void setProperty3(final String property3) {
    this.property3 = property3;
  }

  public String getProperty3() {
    return property3;
  }

  public final void setProperty4(final String property4) {
    this.property4 = property4;
  }

  public String getProperty4() {
    return property4;
  }

  public final void setProperty5(final String property5) {
    this.property5 = property5;
  }

  public String getProperty5() {
    return property5;
  }

  public Comparator<Object> getComparator() {
    return comparator;
  }

  
  @Override
  public int compare(final Object o1, final Object o2) {

	  try {
		  //Variablen anlegen
		  int comp;
		  
		  //Property1
		  comp = compareProperty(o1, o2, property1);
      
		  if (comp == 0) {
			  //Property2
			  comp = compareProperty(o1, o2, property2);
			  			  
			  if (comp == 0){
				  //Property3
				  comp = compareProperty(o1, o2, property3);
				  
				  if(comp == 0) {
					  //Property4
					  comp = compareProperty(o1, o2, property4);
					  
					  if(comp == 0) {
						  //Property5
						  comp = compareProperty(o1, o2, property5);
					  }
				  }
			  }
		  }
		  return comp;
		  
	  } catch (Exception e) {
		  throw new RuntimeException(e);
	  }
  	}
  
  private int compareProperty(final Object o1, final Object o2, final String property){
	  //Variablen anlegen
	  Object value1;
	  Object value2;
	  int ret = 0;
	  
	  try{
		  if(property != null) {
			  value1 = PropertyUtils.getProperty(o1, property);
			  value2 = PropertyUtils.getProperty(o2, property);
	  
			  if( value1 == null && value2 != null){
				  ret = 1;
			  }
			  else if ( value2 == null && value1 != null) {
				  ret = -1;
			  }
			  else if (value1 instanceof String) {        
				  ret = comparator.compare(StringUtils.lowerCase((String)value1), StringUtils.lowerCase((String)value2));
			  } else {
				  ret = comparator.compare(value1, value2);
			  }
		  }
		  return ret;
		  
	  } catch (Exception e) {
		  throw new RuntimeException(e);
	  }
  }
}