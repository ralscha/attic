package ch.ess.cal.persistence;

import ch.ess.cal.model.TextResource;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public interface TextResourceDao extends Dao<TextResource> {

  TextResource find(String name);

  String getText(String name, String locale);

}
