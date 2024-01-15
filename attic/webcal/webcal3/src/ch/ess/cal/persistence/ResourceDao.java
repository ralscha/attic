package ch.ess.cal.persistence;

import java.util.List;
import java.util.Locale;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/12/23 16:26:11 $
 */
public interface ResourceDao extends Dao {

  List find(Locale locale, String resourceName);
}