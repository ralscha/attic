
package ch.ess.addressbook.db.support;

import java.sql.SQLException;
import ch.ess.addressbook.db.*;

public class TableIDKeyGenerator extends IDKeyGenerator {

	public synchronized int generate(String tableName, Object obj) throws SQLException {	
		Maxids maxids = Db.getMaxidsTable().selectOne("tablename = '" + tableName + "'");
		if (maxids != null) {
			int nextid = maxids.getMaxid() + 1;
			maxids.setMaxid(nextid);
			Db.getMaxidsTable().update(maxids);		
			return nextid;	
		} else {
			return 0;
		}
	}
}