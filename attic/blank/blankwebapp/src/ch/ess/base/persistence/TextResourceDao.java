package ch.ess.base.persistence;

import ch.ess.base.model.TextResource;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
 */
public interface TextResourceDao extends Dao<TextResource> {

  TextResource find(String name);

  String getText(String name, String locale);

}