package gtf.tests;

/**
 * This type was created in VisualAge.
 */

import junit.framework.*;
import gtf.common.*;

public class AccountFormatTest extends TestCase {
	private String okInt1Acct;
	private String okInt2Acct;

	private String nokInt1Acct;
	private String nokInt2Acct;

	private String okExt1Acct;
	private String okExt2Acct;

	private String nokExt1Acct;
	private String nokExt2Acct;
	private String nokExt3Acct;
	private String nokExt4Acct;

	private String totalWrong;




	/**
	 * AccountFormatTest constructor comment.
	 * @param arg1 java.lang.String
	 */
	public AccountFormatTest(String arg1) {
		super(arg1);
	}
	/**
	  * Starts the application.
	  * @param args an array of command-line arguments
	  */
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	/**
	  * This method was created in VisualAge.
	  */
	protected void setUp() {
		okInt1Acct = "0835054181771000";
		okInt2Acct = "0835054181771001";

		nokInt1Acct = "08350541817A1000";
		nokInt2Acct = "083505418171";

		okExt1Acct = "0835-0541817-71-000";
		okExt2Acct = "0835-0541817-71-001";

		nokExt1Acct = "0835-541817-1";
		nokExt2Acct = "0835-541817-7-000";
		nokExt3Acct = "0835-541817 -1";
		nokExt4Acct = "0835-541 817-1";

		totalWrong = "IBBB-541817-71";
	}
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new AccountFormatTest("test_format"));
		suite.addTest(new AccountFormatTest("test_isAccount"));
		suite.addTest(new AccountFormatTest("test_Account"));

		return suite;
	}
	/**
	  * This method was created in VisualAge.
	  */
	public void test_Account() {

		Account acct = AccountFormat.makeAccount(okInt1Acct);
		Account acct2 = AccountFormat.makeAccount(okExt1Acct);

		Account acct1 = new Account();
		acct1.setIbbb("0835");
		acct1.setStamm("0541817");
		acct1.setP("71");
		acct1.setLast("000");


		assert(acct.equals(acct1));
		assertEquals(acct.getExternString(), okExt1Acct);
		assertEquals(acct.getInternString(), okInt1Acct);

		assert(acct2.equals(acct1));
		assertEquals(acct2.getExternString(), okExt1Acct);
		assertEquals(acct2.getInternString(), okInt1Acct);


		acct = AccountFormat.makeAccount(okInt2Acct);
		acct2 = AccountFormat.makeAccount(okExt2Acct);

		acct1 = new Account();
		acct1.setIbbb("0835");
		acct1.setStamm("0541817");
		acct1.setP("71");
		acct1.setLast("001");

		assert(acct.equals(acct1));
		assertEquals(acct.getExternString(), okExt2Acct);
		assertEquals(acct.getInternString(), okInt2Acct);

		assert(acct2.equals(acct1));
		assertEquals(acct2.getExternString(), okExt2Acct);
		assertEquals(acct2.getInternString(), okInt2Acct);


		assert(AccountFormat.makeAccount(totalWrong) == null);
		assert(AccountFormat.makeAccount(nokExt1Acct) == null);
		assert(AccountFormat.makeAccount(nokExt2Acct) == null);
		assert(AccountFormat.makeAccount(nokExt3Acct) == null);
		assert(AccountFormat.makeAccount(nokExt4Acct) == null);

	}
	/**
	  * This method was created in VisualAge.
	  */
	public void test_format() {

		assert(AccountFormat.format(okInt1Acct, 2) == null);
		assert(AccountFormat.format(totalWrong, AccountFormat.INTERN) == null);
		assert(AccountFormat.format(totalWrong, AccountFormat.EXTERN) == null);
		assert(AccountFormat.format(nokInt1Acct, AccountFormat.EXTERN) == null);
		assert(AccountFormat.format(nokExt1Acct, AccountFormat.INTERN) == null);

		assertEquals(AccountFormat.format(okInt1Acct, AccountFormat.EXTERN), okExt1Acct);
		assertEquals(AccountFormat.format(okInt2Acct, AccountFormat.EXTERN), okExt2Acct);

		assertEquals(AccountFormat.format(okExt1Acct, AccountFormat.INTERN), okInt1Acct);
		assertEquals(AccountFormat.format(okExt2Acct, AccountFormat.INTERN), okInt2Acct);

		assert(AccountFormat.format(okInt1Acct, AccountFormat.INTERN) == null);
		assert(AccountFormat.format(okInt2Acct, AccountFormat.INTERN) == null);

		assert(AccountFormat.format(okExt1Acct, AccountFormat.EXTERN) == null);
		assert(AccountFormat.format(okExt2Acct, AccountFormat.EXTERN) == null);


	}
	/**
	  * This method was created in VisualAge.
	  */
	public void test_isAccount() {
		assertEquals(AccountFormat.isAccount(totalWrong), AccountFormat.WRONG);
		assertEquals(AccountFormat.isAccount(nokInt1Acct), AccountFormat.WRONG);
		assertEquals(AccountFormat.isAccount(nokInt2Acct), AccountFormat.WRONG);
		assertEquals(AccountFormat.isAccount(nokExt1Acct), AccountFormat.WRONG);
		assertEquals(AccountFormat.isAccount(nokExt2Acct), AccountFormat.WRONG);
		assertEquals(AccountFormat.isAccount(nokExt3Acct), AccountFormat.WRONG);
		assertEquals(AccountFormat.isAccount(nokExt4Acct), AccountFormat.WRONG);


		assertEquals(AccountFormat.isAccount(okInt1Acct), AccountFormat.INTERN);
		assertEquals(AccountFormat.isAccount(okInt2Acct), AccountFormat.INTERN);

		assertEquals(AccountFormat.isAccount(okExt1Acct), AccountFormat.EXTERN);
		assertEquals(AccountFormat.isAccount(okExt2Acct), AccountFormat.EXTERN);

	}
}