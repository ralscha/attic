package ch.ess.cal.persistence;

import java.util.List;
import java.util.Locale;

import ch.ess.cal.model.Category;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:31 $
 */
public interface CategoryDao extends Dao<Category> {

  List<Category> find(Locale locale, String categoryName);

  int getSize(String id);

  void updateTurnOffUnknownFlag(final String id);
}
