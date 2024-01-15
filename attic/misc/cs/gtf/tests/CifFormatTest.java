package gtf.tests;

/**
 * This type was created in VisualAge.
 */

import junit.framework.*;
import gtf.common.*;

public class CifFormatTest extends TestCase {
	private String okInt1Cif;
	private String okInt2Cif;

	private String nokInt1Cif;
	private String nokInt2Cif;

	private String okExt1Cif;
	private String okExt2Cif;

	private String nokExt1Cif;
	private String nokExt2Cif;
	private String nokExt3Cif;
	private String nokExt4Cif;


	private String totalWrong;




	/**
	 * AccountFormatTest constructor comment.
	 * @param arg1 java.lang.String
	 */
	public CifFormatTest(String arg1) {
		super(arg1);
	}
	/**
	  * Starts the application.
	  * @param args an array of command-line arguments
	  */
	public static void main(java.lang.String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	/**
	  * This method was created in VisualAge.
	  */
	protected void setUp() {
		okInt1Cif = "083505418177";
		okInt2Cif = "083500018177";

		nokInt1Cif = "08350541817A";
		nokInt2Cif = "083505418177000";

		okExt1Cif = "0835-0541817-7";
		okExt2Cif = "0835-0001817-7";

		nokExt1Cif = "0835541817-1";
		nokExt2Cif = "0835-541817-72-000";
		nokExt3Cif = "0835-531817 -7";
		nokExt4Cif = "0835-541 817-7";
		totalWrong = "IBBB-541817-71";
	}
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new CifFormatTest("test_format"));
		suite.addTest(new CifFormatTest("test_isCif"));
		suite.addTest(new CifFormatTest("test_Cif"));
		return suite;
	}
	public void test_Cif() {

		Cif cif = CifFormat.makeCif(okInt1Cif);
		Cif cif2 = CifFormat.makeCif(okExt1Cif);

		Cif cif1 = new Cif();
		cif1.setIbbb("0835");
		cif1.setStamm("0541817");
		cif1.setP("7");

		assert(cif.equals(cif1));
		assertEquals(cif.getExternString(), okExt1Cif);
		assertEquals(cif.getInternString(), okInt1Cif);

		assert(cif2.equals(cif1));
		assertEquals(cif2.getExternString(), okExt1Cif);
		assertEquals(cif2.getInternString(), okInt1Cif);


		cif = CifFormat.makeCif(okInt2Cif);
		cif2 = CifFormat.makeCif(okExt2Cif);

		cif1 = new Cif();
		cif1.setIbbb("0835");
		cif1.setStamm("0001817");
		cif1.setP("7");

		assert(cif.equals(cif1));
		assertEquals(cif.getExternString(), okExt2Cif);
		assertEquals(cif.getInternString(), okInt2Cif);

		assert(cif2.equals(cif1));
		assertEquals(cif2.getExternString(), okExt2Cif);
		assertEquals(cif2.getInternString(), okInt2Cif);


		assert(CifFormat.makeCif(totalWrong) == null);
		assert(CifFormat.makeCif(nokExt1Cif) == null);
		assert(CifFormat.makeCif(nokExt2Cif) == null);
		assert(CifFormat.makeCif(nokExt3Cif) == null);
		assert(CifFormat.makeCif(nokExt4Cif) == null);

	}
	/**
	  * This method was created in VisualAge.
	  */
	public void test_format() {

		assert(CifFormat.format(okInt1Cif, 2) == null);
		assert(CifFormat.format(totalWrong, CifFormat.INTERN) == null);
		assert(CifFormat.format(totalWrong, CifFormat.EXTERN) == null);
		assert(CifFormat.format(nokInt1Cif, CifFormat.EXTERN) == null);
		assert(CifFormat.format(nokExt1Cif, CifFormat.INTERN) == null);

		assertEquals(CifFormat.format(okInt1Cif, CifFormat.EXTERN), okExt1Cif);
		assertEquals(CifFormat.format(okInt2Cif, CifFormat.EXTERN), okExt2Cif);

		assertEquals(CifFormat.format(okExt1Cif, CifFormat.INTERN), okInt1Cif);
		assertEquals(CifFormat.format(okExt2Cif, CifFormat.INTERN), okInt2Cif);

		assert(CifFormat.format(okInt1Cif, CifFormat.INTERN) == null);
		assert(CifFormat.format(okInt2Cif, CifFormat.INTERN) == null);

		assert(CifFormat.format(okExt1Cif, CifFormat.EXTERN) == null);
		assert(CifFormat.format(okExt2Cif, CifFormat.EXTERN) == null);


	}
	/**
	  * This method was created in VisualAge.
	  */
	public void test_isCif() {
		assertEquals(CifFormat.isCif(totalWrong), CifFormat.WRONG);
		assertEquals(CifFormat.isCif(nokInt1Cif), CifFormat.WRONG);
		assertEquals(CifFormat.isCif(nokInt2Cif), CifFormat.WRONG);
		assertEquals(CifFormat.isCif(nokExt1Cif), CifFormat.WRONG);
		assertEquals(CifFormat.isCif(nokExt2Cif), CifFormat.WRONG);
		assertEquals(CifFormat.isCif(nokExt3Cif), CifFormat.WRONG);
		assertEquals(CifFormat.isCif(nokExt4Cif), CifFormat.WRONG);


		assertEquals(CifFormat.isCif(okInt1Cif), CifFormat.INTERN);
		assertEquals(CifFormat.isCif(okInt2Cif), CifFormat.INTERN);

		assertEquals(CifFormat.isCif(okExt1Cif), CifFormat.EXTERN);
		assertEquals(CifFormat.isCif(okExt2Cif), CifFormat.EXTERN);

	}
}