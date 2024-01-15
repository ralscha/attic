
import junit.framework.*;

public class HelloWorldTest extends TestCase {

  public HelloWorldTest(String name) {
    super(name);
  }

  public void testSayHello() {
    HelloWorld world = new HelloWorld();
    assert(world!= null);
    assertEquals("Hello World", world.sayHello());
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(HelloWorldTest.class);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(HelloWorldTest.class);
    return suite;
  }
}