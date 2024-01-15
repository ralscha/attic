package ch.ess.common.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import ch.ess.common.util.Variant;


public class VariantTest extends TestCase {
  public VariantTest(String name) {
    super(name);
  }

  public void testNull() {

    Variant var = new Variant();
    assertNull(var.getObject());
    assertNull(var.getType());
  }

  public void testString() {

    String test = "Ralph";
    Variant var = new Variant(test);
    test = "Schaer";
    assertEquals("Ralph", var.getString());
    assertEquals(var.getType(), String.class);
  }

  public void testDouble() {

    Double test = new Double(100.4);
    Variant var = new Variant(test);
    test = new Double(101.5);
    assertEquals(100.4, ((Double)var.getObject()).doubleValue(), 0);
    assertEquals(var.getType(), Double.class);

    double t = 100.5;
    var = new Variant(t);
    t = 101.5;
    assertEquals(100.5, var.getDouble(), 0);
    assertEquals(var.getType(), Double.TYPE);
  }

  public void testFloat() {

    Float test = new Float(100.4f);
    Variant var = new Variant(test);
    test = new Float(101.5f);
    assertEquals(100.4f, ((Float)var.getObject()).floatValue(), 0);
    assertEquals(var.getType(), Float.class);

    float t = 100.5f;
    var = new Variant(t);
    t = 101.5f;
    assertEquals(100.5f, var.getFloat(), 0);
    assertEquals(var.getType(), Float.TYPE);
  }

  public void testInteger() {

    Integer test = new Integer(100);
    Variant var = new Variant(test);
    test = new Integer(99);
    assertEquals(100, ((Integer)var.getObject()).intValue(), 0);
    assertEquals(var.getType(), Integer.class);

    int t = 100;
    var = new Variant(t);
    t = 99;
    assertEquals(100, var.getInt());
    assertEquals(var.getType(), Integer.TYPE);
  }

  public void testLong() {

    Long test = new Long(100l);
    Variant var = new Variant(test);
    test = new Long(99l);
    assertEquals(100l, ((Long)var.getObject()).longValue(), 0);
    assertEquals(var.getType(), Long.class);

    long t = 100l;
    var = new Variant(t);
    t = 99l;
    assertEquals(100l, var.getLong());
    assertEquals(var.getType(), Long.TYPE);
  }

  public void testDate() {

    Calendar cal = new GregorianCalendar(2001, Calendar.OCTOBER, 11);
    Date test = new Date(cal.getTime().getTime());
    Variant var = new Variant(test);
    test = new Date();

    Date test2 = new Date(cal.getTime().getTime());
    assertEquals(test2, var.getDate());
    assertEquals(var.getType(), Date.class);
    var = new Variant(100);
    assertNull(var.getDate());
  }

  public void testByte() {

    Byte test = new Byte((byte)10);
    Variant var = new Variant(test);
    test = new Byte((byte)11);
    assertEquals((byte)10, ((Byte)var.getObject()).byteValue());
    assertEquals(var.getType(), Byte.class);

    byte t = (byte)10;
    var = new Variant(t);
    t = (byte)11;
    assertEquals((byte)10, var.getByte());
    assertEquals(var.getType(), Byte.TYPE);
  }

  public void testChar() {

    Character test = new Character('a');
    Variant var = new Variant(test);
    test = new Character('b');
    assertEquals('a', ((Character)var.getObject()).charValue());
    assertEquals(var.getType(), Character.class);

    char t = 'c';
    var = new Variant(t);
    t = 'd';
    assertEquals('c', var.getChar());
    assertEquals(var.getType(), Character.TYPE);
  }

  public void testBoolean() {

    Boolean test = Boolean.TRUE;
    Variant var = new Variant(test);
    test = Boolean.FALSE;
    assertEquals(true, ((Boolean)var.getObject()).booleanValue());
    assertEquals(var.getType(), Boolean.class);

    boolean t = true;
    var = new Variant(t);
    t = false;
    assertEquals(true, var.getBoolean());
    assertEquals(var.getType(), Boolean.TYPE);
  }
}