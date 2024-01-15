/*
 * Created on Jul 8, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ar.com.koalas.providers;

import java.util.Collection;

/**
 * @author DZ156H
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Provider {
  @SuppressWarnings("unchecked")
  public Collection getCollection() throws ProviderException;

  public Object getDescription(Object id) throws ProviderException;

  public String getName();

  public String getParam(String name);

  public void addParam(String name, String value);

  public void addBean(Object bean);

  public void addFilter(Filter filter);

  public boolean evaluateFilter(Object object, Object Context);

  public String getKeyName();

  public String getDescription();
}
