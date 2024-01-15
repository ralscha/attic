
package ch.ess.pbroker.db.support;

import java.sql.SQLException;
import com.codestudio.util.*;

public abstract class IDKeyGenerator {
	abstract public int generate(String tableName, Object obj) throws SQLException, PoolPropsException ;
}