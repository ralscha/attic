/*
 * Created on 30/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ar.com.koalas.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

/**
 * @author namorrortu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Map2DynaBeanFactory implements BeanFactory {

  @SuppressWarnings("unchecked")
  public Object create(Object oldRow) {
    Map map = (Map)oldRow;
    try {
      DynaBean db = new BasicDynaClass(BasicDynaClass.class.getName(), BasicDynaBean.class, this.getProperties(map))
          .newInstance();
      this.populate(db, map);
      return db;
    } catch (IllegalAccessException e) {
      // XXX Auto-generated catch block
      e.printStackTrace();
    } catch (InstantiationException e) {
      // XXX Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }

  @SuppressWarnings("unchecked")
  private void populate(DynaBean db, Map map) {
    Iterator it = map.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry current = (Entry)it.next();
      db.set((String)current.getKey(), current.getValue());
    }
  }

  @SuppressWarnings("unchecked")
  private DynaProperty[] getProperties(Map map) {
    Collection<DynaProperty> dynaProperties = new ArrayList<DynaProperty>();
    Iterator properties = map.entrySet().iterator();
    while (properties.hasNext()) {
      Map.Entry current = (Entry)properties.next();
      dynaProperties.add(this.getProperty(current.getKey(), current.getValue()));
    }

    return dynaProperties.toArray(new DynaProperty[0]);
  }

  private DynaProperty getProperty(Object key, Object value) {
    return new DynaProperty(key.toString(), value.getClass());
  }
}
