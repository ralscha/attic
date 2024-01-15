package ch.ess.cal.persistence;

import java.util.List;

import ch.ess.cal.model.Holiday;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public interface HolidayDao extends Dao<Holiday> {

  List<Holiday> find(Integer monthNo);

  List<Holiday> findActiveHolidays();

  Holiday findWithBuiltin(String builtin);

}
