#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import junit.framework.TestCase;

import org.junit.Test;

public class DemoBeanTest extends TestCase {

	@Test
	public void testCounter() {
		DemoBean db = new DemoBean();
		assertEquals(0, db.getCounter());

		db.inc();
		assertEquals(1, db.getCounter());

		db.inc();
		assertEquals(2, db.getCounter());
	}

}
