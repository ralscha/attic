/*
 * Created on Jul 10, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ar.com.koalas.providers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author DZ156H
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FixedProvider extends ProviderImpl {
  private Map<String, String> localIndex;

  @SuppressWarnings("unchecked")
  public Collection getCollection() {
    return this.getFixedBeans();
  }

  public Object getDescription(Object id) throws ProviderException {
    try {
      this.reIndex();
      return this.localIndex.get(id);
    } catch (Exception e) {
      throw new ProviderException(e);
    }
  }

  @SuppressWarnings("unchecked")
  private void reIndex() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    // Ya fue inicializado.
    if (this.localIndex != null) {
      return;
    }

    this.localIndex = new Hashtable<String, String>();
    Iterator itBeans = this.getFixedBeans().iterator();
    while (itBeans.hasNext()) {
      Object bean = itBeans.next();
      this.localIndex.put(BeanUtils.getProperty(bean, this.getKeyName()), BeanUtils.getProperty(bean, this
          .getDescription()));
    }
  }
}
