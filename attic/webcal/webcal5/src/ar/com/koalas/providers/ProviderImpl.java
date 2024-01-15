/*
 * Created on Jul 10, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ar.com.koalas.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * @author DZ156H
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class ProviderImpl implements Provider {
  private String name = null;
  private Collection<Object> fixedBeans = new ArrayList<Object>();
  private Map<String, String> params = new Hashtable<String, String>();

  private String keyName = null;
  private String description = null;

  private Collection<Filter> filters = new ArrayList<Filter>();

  public ProviderImpl() {
    //   noaction
  }

  public void setName(String string) {
    name = string;
  }

  public String getName() {
    return name;
  }

  public void addParam(String name1, String value) {
    this.params.put(name1, value);
  }

  public String getParam(String paramName) {
    return this.params.get(paramName);
  }

  public void addBean(Object bean) {
    this.fixedBeans.add(bean);
  }

  protected Collection<Object> getFixedBeans() {
    return this.fixedBeans;
  }

  public String getDescription() {
    return description;
  }

  public String getKeyName() {
    return keyName;
  }

  public void setDescription(String string) {
    description = string;
  }

  public void setKeyName(String string) {
    keyName = string;
  }

  public void addFilter(Filter filter) {
    this.filters.add(filter);
  }

  public boolean evaluateFilter(Object object, Object context) {
    Iterator<Filter> itFilters = this.filters.iterator();
    boolean retValue = true;
    while (itFilters.hasNext() && retValue) {
      Filter filter = itFilters.next();
      retValue = filter.filter(object, context);
    }

    return retValue;
  }
}