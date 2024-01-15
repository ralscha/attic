
package ch.ess.addressbook.db.support;

import java.sql.SQLException;

public abstract class IDKeyGenerator {
	abstract public int generate(String tableName, Object obj) throws SQLException ;
}