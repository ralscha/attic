package ch.ess.common.util;

import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaClass;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public class PublicMapDynaBean extends BasicDynaBean {

  public PublicMapDynaBean(DynaClass dynaClass) {
    super(dynaClass);
  }

  public Map getMap() {
    return values;
  }
}