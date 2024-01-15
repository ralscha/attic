package ch.ess.cal.persistence;

import java.util.List;
import java.util.Locale;

/**
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/04/04 11:31:12 $
 */
public interface GroupDao extends Dao {

  List find(Locale locale, String groupName);
}