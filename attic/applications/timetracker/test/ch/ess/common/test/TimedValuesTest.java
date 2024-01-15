package ch.ess.common.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import ch.ess.common.util.TimedValues;
import ch.ess.common.util.Value;
import ch.ess.common.util.Variant;


public class TimedValuesTest extends TestCase {
  public TimedValuesTest(String name) {
    super(name);
  }

  public void testNull() {

    Value testValue = new Value("sr", new Variant(10), 10);
    assertEquals("sr", testValue.getKey());
    assertEquals(10, testValue.getValue().getInt());
    TimedValues.setCleanupThreadTimeout(10); 

    List valuesList = TimedValues.getValues();
    assertEquals(0, valuesList.size());
    TimedValues.addValue("sr1", 700, 2); 
    TimedValues.addValue("sr1", 800, 15);
    TimedValues.addValue("sr1", 900, 0); 
    TimedValues.addValue("sr2", 2, 2); 
    TimedValues.addValue("sr3", 3, 15);
    TimedValues.addValue("sr4", 4, 0); 
    TimedValues.addValue("sr5", 5, 0); 
    valuesList = TimedValues.getValues();
    assertEquals(5, valuesList.size());

    Variant v1 = TimedValues.getValue("sr1");
    assertEquals(900, v1.getInt());

    Variant v2 = TimedValues.getValue("sr2");
    assertEquals(2, v2.getInt());

    Variant v3 = TimedValues.getValue("sr3");
    assertEquals(3, v3.getInt());

    Variant v4 = TimedValues.getValue("sr4");
    assertEquals(4, v4.getInt());

    Variant v5 = TimedValues.getValue("sr5");
    assertEquals(5, v5.getInt());
    TimedValues.removeValue("sr5");
    v5 = TimedValues.getValue("sr5");
    assertNull(v5);
    TimedValues.addValue("normal", 2, 1800);

    Variant normal = TimedValues.getValue("normal");
    assertEquals(2, normal.getInt());
    TimedValues.addValue("overwrite", 981, 1800);

    Variant over = TimedValues.getValue("overwrite");
    assertEquals(981, over.getInt());

    try {
      Thread.sleep(11000);
    } catch (InterruptedException ie) {
    // no action
    }
    valuesList = TimedValues.getValues();
    assertEquals(5, valuesList.size());
    v1 = TimedValues.getValue("sr1");
    assertEquals(900, v1.getInt());
    v2 = TimedValues.getValue("sr2");
    assertNull(v2);
    v3 = TimedValues.getValue("sr3");
    assertEquals(3, v3.getInt());
    v4 = TimedValues.getValue("sr4");
    assertEquals(4, v4.getInt());
    normal = TimedValues.getValue("normal");
    assertEquals(2, normal.getInt());
    TimedValues.addValue("normal", 3, 1800);
    TimedValues.addValue("sr4", 44, 0);
    v5 = TimedValues.getValue("sr5");
    assertNull(v5);
    TimedValues.addValue("sr6", 66, 2);
    TimedValues.addValue("sr7", 77, 20);
    over = TimedValues.getValue("overwrite");
    assertEquals(981, over.getInt());
    TimedValues.addValue("overwrite", 982, 2);
    over = TimedValues.getValue("overwrite");
    assertEquals(982, over.getInt());

    try {
      Thread.sleep(11000);
    } catch (InterruptedException ie) {
      //no action
    }
    valuesList = TimedValues.getValues();
    assertEquals(4, valuesList.size());
    v1 = TimedValues.getValue("sr1");
    assertEquals(900, v1.getInt());
    v2 = TimedValues.getValue("sr2");
    assertNull(v2);
    v3 = TimedValues.getValue("sr3");
    assertNull(v3);
    v4 = TimedValues.getValue("sr4");
    assertEquals(44, v4.getInt());
    v5 = TimedValues.getValue("sr5");
    assertNull(v5);

    Variant v6 = TimedValues.getValue("sr6");
    assertNull(v6);

    Variant v7 = TimedValues.getValue("sr7");
    assertEquals(77, v7.getInt());
    normal = TimedValues.getValue("normal");
    assertEquals(3, normal.getInt());
    over = TimedValues.getValue("overwrite");
    assertNull(over);

    Set expectedValues = new HashSet();
    expectedValues.add(new Integer(900));
    expectedValues.add(new Integer(44));
    expectedValues.add(new Integer(3));
    expectedValues.add(new Integer(77));

    Set expectedKeys = new HashSet();
    expectedKeys.add("sr1");
    expectedKeys.add("sr4");
    expectedKeys.add("sr7");
    expectedKeys.add("normal");
    valuesList = TimedValues.getValues();

    for (int i = 0; i < 4; i++) {

      Value v = (Value)valuesList.get(i);
      assertTrue(expectedKeys.contains(v.getKey()));
      assertTrue(expectedValues.contains(new Integer(v.getValue().getInt())));
    }
    
    Value val1 = new Value();
    Value val2 = new Value("test");
    
    assertNull(val1.getKey());
    assertNull(val1.getValue());
    
    assertEquals("test", val2.getKey());
    assertNull(val2.getValue());
    
  }
}