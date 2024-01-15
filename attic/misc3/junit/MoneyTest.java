
import junit.framework.*;

public class MoneyTest extends TestCase {

  private Money m12CHF;
  private Money m14CHF;

  public MoneyTest(String name) {
    super(name);
  }

  protected void setUp() {
    m12CHF = new Money(12, "CHF");
    m14CHF = new Money(14, "CHF");
  }

  public void testEquals() {
    assert(!m12CHF.equals(null));
    assertEquals(m12CHF, m12CHF);
    assertEquals(m12CHF, new Money(12, "CHF"));
    assert(!m12CHF.equals(m14CHF));
  }

  public void testSimpleAdd() {
    Money expected = new Money(26, "CHF");
    Money result = m12CHF.add(m14CHF);
    assert(expected.equals(result));
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(MoneyTest.class);
    return suite;
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
