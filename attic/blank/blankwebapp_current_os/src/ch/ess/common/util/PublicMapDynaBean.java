package ch.ess.common.util;

import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaClass;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.3 $ $Date: 2004/05/22 12:24:31 $
 */
public class PublicMapDynaBean extends BasicDynaBean {

  public PublicMapDynaBean(DynaClass dynaClass) {
    super(dynaClass);
  }

  public Map getMap() {
    return values;
  }
}