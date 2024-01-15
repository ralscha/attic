package ch.ess.common.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    
    var.set("true");
    assertEquals("true", var.getString());
    assertTrue(var.getBoolean());
    
    var.set("1");
    assertEquals((byte)1, var.getByte());
    assertEquals((short)1, var.getShort());
    assertEquals(1, var.getInt());
    assertEquals(1l, var.getLong());
    assertEquals(1f, var.getFloat(), 0);
    assertEquals(1.0, var.getDouble(), 0);
    assertEquals('1', var.getChar());
    assertNull(var.getDate());
    
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
    
    var.set(new Double(1100));
    assertEquals(1100, ((Double)var.getObject()).doubleValue(), 0);
    assertEquals(1100, var.getDouble(), 0);
    
    var.set(1200.0);
    assertEquals(1200.0, var.getDouble(), 0); 
    
    var.set((Double)null);
    assertNull(var.getType());    
    assertNull(var.getObject());
    
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
    
    var.set(new Float(1100));
    assertEquals(1100, ((Float)var.getObject()).floatValue(), 0);
    assertEquals(1100, var.getFloat(), 0);
    
    var.set(1200f);
    assertEquals(1200, var.getFloat(), 0);  
    
    var.set((Float)null);
    assertNull(var.getType());    
    assertNull(var.getObject());
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
    
    var.set(new Integer(1100));
    assertEquals(1100, ((Integer)var.getObject()).intValue(), 0);
    assertEquals(1100, var.getInt());
    
    var.set(1200);
    assertEquals(1200, var.getInt());    
    
    assertEquals("1200", var.getString());
    
    var.set((Integer)null);
    assertNull(var.getType());    
    assertNull(var.getObject());
  }
  
  public void testShort() {

    Short test = new Short((short)100);
    Variant var = new Variant(test);
    test = new Short((short)99);
    assertEquals(100, ((Short)var.getObject()).shortValue(), 0);
    assertEquals(var.getType(), Short.class);

    short t = 100;
    var = new Variant(t);
    t = 99;
    assertEquals(100, var.getShort());
    assertEquals(var.getType(), Short.TYPE);
    
    var.set(new Short((short)11));
    assertEquals(11, ((Short)var.getObject()).shortValue(), 0);
    assertEquals(11, var.getShort());
    
    var.set((short)12);
    assertEquals(12, var.getShort());  
    
    assertEquals("12", var.getString());
    
    var.set((Short)null);
    assertNull(var.getType());    
    assertNull(var.getObject());
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
    
    
    var.set(new Long(1100));
    assertEquals(1100, ((Long)var.getObject()).longValue(), 0);
    assertEquals(1100, var.getLong());
    
    var.set(1200l);
    assertEquals(1200l, var.getLong());
    
    assertEquals("1200", var.getString());
    
    var.set((Long)null);
    assertNull(var.getType());    
    assertNull(var.getObject());
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
    
    cal = new GregorianCalendar(2003, Calendar.OCTOBER, 11);
    var.set(cal.getTime());
    assertEquals(cal.getTime(), var.getDate());
    
    var.set((Date)null);
    assertNull(var.getType());
    assertNull(var.getDate());
    assertNull(var.getObject());
    
    
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
    
    
    var.set(new Byte((byte)12));
    assertEquals((byte)12, ((Byte)var.getObject()).byteValue(), 0);
    assertEquals((byte)12, var.getByte());
    
    var.set((byte)13);
    assertEquals((byte)13, var.getByte());
    
    var.set((Byte)null);
    assertNull(var.getType());    
    assertNull(var.getObject());
    
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
    
    var.set('z');
    assertEquals('z', var.getChar());
    
    var.set(new Character('x'));
    assertEquals('x', ((Character)var.getObject()).charValue());
    assertEquals('x', var.getChar());
    
    var.set((Character)null);
    assertNull(var.getType());    
    assertNull(var.getObject());
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
    
    var.set(Boolean.FALSE);
    assertEquals(false, ((Boolean)var.getObject()).booleanValue());
    assertFalse(var.getBoolean());
    var.set(true);
    assertEquals(true, ((Boolean)var.getObject()).booleanValue());
    assertTrue(var.getBoolean());
    
    var.set((Boolean)null);
    assertNull(var.getType());    
    assertNull(var.getObject());
  }
  
  public void testCopyConstructor() {
    Boolean test = Boolean.TRUE;
    Variant var = new Variant(test);
    
    Variant var2 = new Variant(var);
    assertTrue(var2.toString().equals(var.toString()));
    assertTrue(var2.getBoolean() == var.getBoolean());
  }
  
  public void testObject() {
    List testList = new ArrayList();
    testList.add("one");
    Variant var = new Variant(testList);
    
    List l = (List)var.getObject();
    assertEquals(l.size(), 1);
    
    var.set((Object)null);
    assertTrue(var.isNull());
    
    l = (List)var.getObject();
    assertNull(l);
    
  }
}