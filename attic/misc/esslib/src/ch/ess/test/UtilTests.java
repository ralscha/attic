
package ch.ess.test;

import java.math.*;
import java.util.*;

import junit.framework.*;

import ch.ess.util.*;

public class UtilTests extends TestCase {

  public UtilTests(String name) {
    super(name);
  }
  
  public void testInPeriod() {
    Calendar von = new GregorianCalendar(2002, Calendar.JANUARY, 1);
    Calendar bis = new GregorianCalendar(2002, Calendar.DECEMBER, 31);
    
    Calendar test = new GregorianCalendar(2002, Calendar.JUNE, 23);
    assertTrue(Util.isInPeriod(test, von, bis));

    test = new GregorianCalendar(2002, Calendar.JANUARY, 1);
    assertTrue(Util.isInPeriod(test, von, bis));

    test = new GregorianCalendar(2002, Calendar.DECEMBER, 31);
    assertTrue(Util.isInPeriod(test, von, bis));
    
    test = new GregorianCalendar(2001, Calendar.DECEMBER, 31);
    assertFalse(Util.isInPeriod(test, von, bis));

    test = new GregorianCalendar(2003, Calendar.JANUARY, 1);
    assertFalse(Util.isInPeriod(test, von, bis));

    von = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    bis = new GregorianCalendar(2003, Calendar.DECEMBER, 31);
    
    test = new GregorianCalendar(2002, Calendar.JANUARY, 1);
    assertTrue(Util.isInPeriod(test, von, bis));

    test = new GregorianCalendar(2002, Calendar.DECEMBER, 31);
    assertTrue(Util.isInPeriod(test, von, bis));
    
    test = new GregorianCalendar(2001, Calendar.DECEMBER, 31);
    assertTrue(Util.isInPeriod(test, von, bis));

    test = new GregorianCalendar(2003, Calendar.JANUARY, 1);
    assertTrue(Util.isInPeriod(test, von, bis));
    

  }

  public void testBeforeOrEquals() {
    Calendar c1 = new GregorianCalendar();
    Calendar c2 = new GregorianCalendar();
    assertTrue(Util.beforeOrEquals(c1, c2));
    
    c1.set(Calendar.YEAR, 1970);
    assertTrue(Util.beforeOrEquals(c1, c2));

    c1 = new GregorianCalendar();
    c2.set(Calendar.YEAR, 1970);
    assertFalse(Util.beforeOrEquals(c1, c2));

    c1 = new GregorianCalendar(2002, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2002, Calendar.JANUARY, 1);    
    Util.clearTime(c1);
    Util.clearTime(c2);    
    assertTrue(Util.beforeOrEquals(c1, c2));

    c1 = new GregorianCalendar(2002, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2002, Calendar.JANUARY, 2);    
    Util.clearTime(c1);
    Util.clearTime(c2);    
    assertTrue(Util.beforeOrEquals(c1, c2));
    
  }
  
  public void testAfterOrEquals() {
    Calendar c1 = new GregorianCalendar();
    Calendar c2 = new GregorianCalendar();
    assertTrue(Util.afterOrEquals(c1, c2));

    c1.set(Calendar.YEAR, 1970);
    assertFalse(Util.afterOrEquals(c1, c2));

    c1 = new GregorianCalendar();
    c2.set(Calendar.YEAR, 1970);
    assertTrue(Util.afterOrEquals(c1, c2));

    c1 = new GregorianCalendar(2002, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2002, Calendar.JANUARY, 1);    
    Util.clearTime(c1);
    Util.clearTime(c2);    
    assertTrue(Util.afterOrEquals(c1, c2));
  
    c1 = new GregorianCalendar(2002, Calendar.JANUARY, 2);
    c2 = new GregorianCalendar(2002, Calendar.JANUARY, 1);    
    Util.clearTime(c1);
    Util.clearTime(c2);    
    assertTrue(Util.afterOrEquals(c1, c2));
  
  }

  public void testJustify() {
    assertEquals("test", Util.leftJustify("test", 4, ' '));
    assertEquals("test ", Util.leftJustify("test", 5, ' '));
    assertEquals("test  ", Util.leftJustify("test", 6, ' '));
    assertEquals("test   ", Util.leftJustify("test", 7, ' '));
    assertEquals("tes", Util.leftJustify("test", 3, ' '));
    assertEquals("te", Util.leftJustify("test", 2, ' '));        

    assertEquals("test", Util.leftJustify("test", 4, '*'));
    assertEquals("test*", Util.leftJustify("test", 5, '*'));
    assertEquals("test**", Util.leftJustify("test", 6, '*'));
    assertEquals("test***", Util.leftJustify("test", 7, '*'));
    assertEquals("tes", Util.leftJustify("test", 3, '*'));
    assertEquals("te", Util.leftJustify("test", 2, '*'));        
  }

  public void testBlank2Null() {
      
    TestObject to = new TestObject();
    Util.blank2nullBean(to);
    
    assertNotNull(to.getS1());
    assertEquals("20", to.getS1());
    assertNull(to.getS2());
    assertNull(to.getS3());
    assertEquals(10, to.getI());    
    
  }

  public void testDaysInMonth() {

    Calendar c1 = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    Calendar c2 = new GregorianCalendar(2001, Calendar.DECEMBER, 31);

    assertEquals(31, Util.daysInMonth(c1, c2, 2001, Calendar.JANUARY));
    assertEquals(28, Util.daysInMonth(c1, c2, 2001, Calendar.FEBRUARY));
    assertEquals(31, Util.daysInMonth(c1, c2, 2001, Calendar.MARCH));
    assertEquals(30, Util.daysInMonth(c1, c2, 2001, Calendar.APRIL));
    assertEquals(31, Util.daysInMonth(c1, c2, 2001, Calendar.MAY));
    assertEquals(30, Util.daysInMonth(c1, c2, 2001, Calendar.JUNE));
    assertEquals(31, Util.daysInMonth(c1, c2, 2001, Calendar.JULY));
    assertEquals(31, Util.daysInMonth(c1, c2, 2001, Calendar.AUGUST));
    assertEquals(30, Util.daysInMonth(c1, c2, 2001, Calendar.SEPTEMBER));
    assertEquals(31, Util.daysInMonth(c1, c2, 2001, Calendar.OCTOBER));
    assertEquals(30, Util.daysInMonth(c1, c2, 2001, Calendar.NOVEMBER));
    assertEquals(31, Util.daysInMonth(c1, c2, 2001, Calendar.DECEMBER));

    c1 = new GregorianCalendar(2001, Calendar.JANUARY, 10);
    c2 = new GregorianCalendar(2001, Calendar.DECEMBER, 31);

    assertEquals(22, Util.daysInMonth(c1, c2, 2001, Calendar.JANUARY));

    c1 = new GregorianCalendar(2000, Calendar.JANUARY, 10);
    c2 = new GregorianCalendar(2001, Calendar.JANUARY, 20);

    assertEquals(20, Util.daysInMonth(c1, c2, 2001, Calendar.JANUARY));

    c1 = new GregorianCalendar(2001, Calendar.JANUARY, 10);
    c2 = new GregorianCalendar(2001, Calendar.JANUARY, 20);

    assertEquals(11, Util.daysInMonth(c1, c2, 2001, Calendar.JANUARY));
  }

  public void testNotEmpty() {
    assertEquals(false, Util.notEmpty(null));
    assertEquals(true, Util.notEmpty("Ralph"));
    assertEquals(false, Util.notEmpty(""));
    assertEquals(false, Util.notEmpty("     "));    
    assertEquals(false, Util.notEmpty(" "));    
    assertEquals(true, Util.notEmpty("Ralph "));        
    assertEquals(true, Util.notEmpty(" Ralph"));            
    assertEquals(true, Util.notEmpty(" Ralph "));                
    assertEquals(true, Util.notEmpty("                      Ralph          "));                    
  }

  public void testIsInYear() {

    Calendar c1 = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    Calendar c2 = new GregorianCalendar(2001, Calendar.JANUARY, 28);

    assertEquals(true, Util.isInYear(c1, c2, 2001));
    assertEquals(false, Util.isInYear(c1, c2, 2000));
    assertEquals(false, Util.isInYear(c1, c2, 2002));

    c1 = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2001, Calendar.DECEMBER, 31);

    assertEquals(true, Util.isInYear(c1, c2, 2001));
    assertEquals(false, Util.isInYear(c1, c2, 2000));
    assertEquals(false, Util.isInYear(c1, c2, 2002));

    c1 = new GregorianCalendar(2001, Calendar.JUNE, 23);
    c2 = new GregorianCalendar(2001, Calendar.JUNE, 27);

    assertEquals(true, Util.isInYear(c1, c2, 2001));
    assertEquals(false, Util.isInYear(c1, c2, 2000));
    assertEquals(false, Util.isInYear(c1, c2, 2002));

    c1 = new GregorianCalendar(2000, Calendar.JUNE, 1);
    c2 = new GregorianCalendar(2001, Calendar.MAY, 31);

    assertEquals(true, Util.isInYear(c1, c2, 2001));
    assertEquals(true, Util.isInYear(c1, c2, 2000));
    assertEquals(false, Util.isInYear(c1, c2, 2002));

    c1 = new GregorianCalendar(2001, Calendar.JUNE, 1);
    c2 = new GregorianCalendar(2002, Calendar.MAY, 31);

    assertEquals(true, Util.isInYear(c1, c2, 2001));
    assertEquals(false, Util.isInYear(c1, c2, 2000));
    assertEquals(true, Util.isInYear(c1, c2, 2002));

    c1 = new GregorianCalendar(2000, Calendar.JUNE, 1);
    c2 = new GregorianCalendar(2002, Calendar.MAY, 31);

    assertEquals(true, Util.isInYear(c1, c2, 2001));
    assertEquals(true, Util.isInYear(c1, c2, 2000));
    assertEquals(true, Util.isInYear(c1, c2, 2002));
  }

  public void testSuppressNull() {
    assertEquals("", Util.suppressNull(null));
    assertEquals("no", Util.suppressNull("no"));
  }

  public void testRoundOne() {

    BigDecimal bd = new BigDecimal(100);
    BigDecimal result = Util.roundOne(bd);

    assertTrue(bd.compareTo(result) == 0);

    bd = new BigDecimal("100.001");
    result = Util.roundOne(bd);

    BigDecimal expected = new BigDecimal("100.00");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("100.01");
    result = Util.roundOne(bd);
    expected = new BigDecimal("100.01");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("100.09");
    result = Util.roundOne(bd);
    expected = new BigDecimal("100.09");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("100.099");
    result = Util.roundOne(bd);
    expected = new BigDecimal("100.10");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("100.055");
    result = Util.roundOne(bd);
    expected = new BigDecimal("100.06");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("100.054");
    result = Util.roundOne(bd);
    expected = new BigDecimal("100.05");

    assertTrue(expected.compareTo(result) == 0);
  }

  public void testRoundFive() {

    BigDecimal bd = new BigDecimal(100);
    BigDecimal result = Util.roundFive(bd);

    assertTrue(bd.compareTo(result) == 0);

    bd = new BigDecimal("100.001");
    result = Util.roundFive(bd);

    BigDecimal expected = new BigDecimal("100.00");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("100.01");
    result = Util.roundFive(bd);
    expected = new BigDecimal("100.00");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("100.21");
    result = Util.roundFive(bd);
    expected = new BigDecimal("100.20");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("100.054");
    result = Util.roundFive(bd);
    expected = new BigDecimal("100.05");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("0.23");
    result = Util.roundFive(bd);
    expected = new BigDecimal("0.20");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("1977.25");
    result = Util.roundFive(bd);
    expected = new BigDecimal("1977.25");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("1977.26");
    result = Util.roundFive(bd);
    expected = new BigDecimal("1977.25");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("1977.27");
    result = Util.roundFive(bd);
    expected = new BigDecimal("1977.25");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("1977.28");
    result = Util.roundFive(bd);
    expected = new BigDecimal("1977.25");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("1977.29");
    result = Util.roundFive(bd);
    expected = new BigDecimal("1977.25");

    assertTrue(expected.compareTo(result) == 0);

    bd = new BigDecimal("1977.30");
    result = Util.roundFive(bd);
    expected = new BigDecimal("1977.30");

    assertTrue(expected.compareTo(result) == 0);
  }

  public void testHexDigit() {

    assertEquals("00", Util.hexDigit((byte)0));
    assertEquals("01", Util.hexDigit((byte)1));
    assertEquals("02", Util.hexDigit((byte)2));
    assertEquals("04", Util.hexDigit((byte)4));
    assertEquals("63", Util.hexDigit((byte)99));
    assertEquals("80", Util.hexDigit((byte)128));
    assertEquals("ab", Util.hexDigit((byte)171));
    assertEquals("cd", Util.hexDigit((byte)205));
    assertEquals("ef", Util.hexDigit((byte)239));
    assertEquals("ff", Util.hexDigit((byte)255));
    assertEquals("00", Util.hexDigit((byte)256));
  }

  public void testToHex() {

    assertEquals("00", Util.toHex(new byte[]{ (byte)0 }));
    assertEquals("ABCDEF", Util.toHex(new byte[]{ (byte)171, (byte)205, (byte)239 }));
    assertEquals("010204", Util.toHex(new byte[]{ (byte)1, (byte)2, (byte)4 }));
    assertEquals("63", Util.toHex(new byte[]{ (byte)99 }));
    assertEquals("", Util.toHex(new byte[]{}));
  }

  public void testGetSprache() {

    assertEquals("enCA", Util.getSprache(Locale.CANADA));
    assertEquals("en", Util.getSprache(Locale.ENGLISH));
    assertEquals("de", Util.getSprache(Locale.GERMAN));
    assertEquals("de", Util.getSprache(new Locale("de", "")));
    assertEquals("deCH", Util.getSprache(new Locale("de", "CH")));
    assertEquals("frCHab", Util.getSprache(new Locale("fr", "CH", "ab")));
  }

  public void testToUsAscii() {

    assertNull(Util.toUsAscii(null));
    assertEquals("Raeuber", Util.toUsAscii("Räuber"));
    assertEquals("Koenig", Util.toUsAscii("König"));
    assertEquals("Kuebel", Util.toUsAscii("Kübel"));
    assertEquals("AeUeOe", Util.toUsAscii("ÄÜÖ"));
    assertEquals("dass", Util.toUsAscii("daß"));
    assertEquals("ein", Util.toUsAscii("êïñ"));
    assertEquals("ohneirgendwaszuaendern", Util.toUsAscii("ohneirgendwaszuaendern"));
  }

  public void testReplaceHTMLUml() {

    assertNull(Util.replaceHTMLUml(null));
    assertEquals("über", Util.replaceHTMLUml("&uuml;ber"));
    assertEquals("Gärtner", Util.replaceHTMLUml("G&auml;rtner"));
    assertEquals("Lösung", Util.replaceHTMLUml("L&ouml;sung"));
    assertEquals("ÜÄÖ", Util.replaceHTMLUml("&Uuml;&Auml;&Ouml;"));
    assertEquals("Ü Ä Ö", Util.replaceHTMLUml("&Uuml;&nbsp;&Auml;&nbsp;&Ouml;"));
  }

  public void testReplaceHTMLEntities() {

    assertNull(Util.replaceHTMLEntities(null));
    assertEquals("über", Util.replaceHTMLEntities("&uuml;ber"));
    assertEquals("Gärtner", Util.replaceHTMLEntities("G&auml;rtner"));
    assertEquals("Lösung", Util.replaceHTMLEntities("L&ouml;sung"));
    assertEquals("ÜÄÖ", Util.replaceHTMLEntities("&Uuml;&Auml;&Ouml;"));
    assertEquals("Ü Ä Ö", Util.replaceHTMLEntities("&Uuml;&nbsp;&Auml;&nbsp;&Ouml;"));
    
    assertEquals("\"", Util.replaceHTMLEntities("&quot;"));
    assertEquals("<", Util.replaceHTMLEntities("&lt;"));
    assertEquals(">", Util.replaceHTMLEntities("&gt;"));
    assertEquals("&", Util.replaceHTMLEntities("&amp;"));
    
    assertEquals("&Ü Ä Ö&", Util.replaceHTMLEntities("&amp;&Uuml;&nbsp;&Auml;&nbsp;&Ouml;&amp;"));
    assertEquals("<Lösung>", Util.replaceHTMLEntities("&lt;L&ouml;sung&gt;"));
    assertEquals("\"Gärtner\"", Util.replaceHTMLEntities("&quot;G&auml;rtner&quot;"));
  }
  
  public void testReplaceToHTMLEntities() {

    assertNull(Util.replaceToHTMLEntities(null));
    assertEquals("&uuml;ber", Util.replaceToHTMLEntities("über"));
    assertEquals("G&auml;rtner", Util.replaceToHTMLEntities("Gärtner"));
    assertEquals("L&ouml;sung", Util.replaceToHTMLEntities("Lösung"));
    assertEquals("&Uuml;&Auml;&Ouml;", Util.replaceToHTMLEntities("ÜÄÖ"));
    assertEquals("&Uuml;&nbsp;&Auml;&nbsp;&Ouml;", Util.replaceToHTMLEntities("Ü Ä Ö"));
    
    assertEquals("&quot;", Util.replaceToHTMLEntities("\""));
    assertEquals("&lt;", Util.replaceToHTMLEntities("<"));
    assertEquals("&gt;", Util.replaceToHTMLEntities(">"));
    assertEquals("&lt;&gt;", Util.replaceToHTMLEntities("<>"));
    assertEquals("&amp;", Util.replaceToHTMLEntities("&"));
    
    assertEquals("simple", Util.replaceToHTMLEntities("simple"));
    assertEquals("&nbsp;&uuml;&auml;&ouml;&Uuml;&Auml;&Ouml;&amp;&quot;&lt;&gt;", Util.replaceToHTMLEntities(" üäöÜÄÖ&\"<>"));
    
    assertEquals("&nbsp;&nbsp;&nbsp;&nbsp;", Util.replaceToHTMLEntities("    "));
    
    
    assertEquals("&amp;&Uuml;&nbsp;&Auml;&nbsp;&Ouml;&amp;", Util.replaceToHTMLEntities("&Ü Ä Ö&"));
    assertEquals("&lt;L&ouml;sung&gt;", Util.replaceToHTMLEntities("<Lösung>"));
    assertEquals("&quot;G&auml;rtner&quot;", Util.replaceToHTMLEntities("\"Gärtner\""));
  }  
  
  public void testReplaceBlank2HTMLBlank() {
    assertNull(Util.replaceBlank2HTMLBlank(null));
    assertEquals("one&nbsp;two&nbsp;three&nbsp;four&nbsp;", Util.replaceBlank2HTMLBlank("one two three four "));    
  }

  public void testRemoveApostroph() {

    assertNull(Util.removeApostroph(null));
    assertEquals("ohneApostroph", Util.removeApostroph("ohneApostroph"));
    assertEquals("ganzviele", Util.removeApostroph("'g'a'n''zvi'e'l'e'"));
    assertEquals("OReilly", Util.removeApostroph("O'Reilly"));
    assertEquals("12820.45", Util.removeApostroph("12'820.45"));
    assertEquals("1112820.45", Util.removeApostroph("1'112'820.45"));
  }

  public void testRemoveLinefeed() {

    assertNull(Util.removeLineFeed(null));
    assertEquals("ohne", Util.removeLineFeed("ohne"));
    assertEquals("ohne\n", Util.removeLineFeed("ohne\n"));
    assertEquals("mit", Util.removeLineFeed("mit\r"));
    assertEquals("mit\n", Util.removeLineFeed("mit\r\n"));
  }

  public void testDoubleApostroph() {

    assertNull(Util.doubleApostrophe(null));
    assertEquals("ohneApostroph", Util.doubleApostrophe("ohneApostroph"));
    assertEquals("''g''a''n''''zvi''e''l''e''", Util.doubleApostrophe("'g'a'n''zvi'e'l'e'"));
    assertEquals("O''Reilly", Util.doubleApostrophe("O'Reilly"));
  }

  public void testIsValidEmail() {

    assertEquals(false, Util.isValidEmail(null));
    assertEquals(false, Util.isValidEmail("aber.hallo"));
    assertEquals(false, Util.isValidEmail("so@nicht"));
    assertEquals(false, Util.isValidEmail("ganz@normal.zulang"));
    assertEquals(true, Util.isValidEmail("ganz@normal.ch"));
    assertEquals(true, Util.isValidEmail("ein-bischen9@normal.ch"));
    assertEquals(true, Util.isValidEmail("ganz.spez-ial@test.test.ch"));
    assertEquals(true, Util.isValidEmail("ganz.spez-ial@test.test.ch;ein-bischen9@normal.ch;ganz@normal.ch"));
    assertEquals(false, Util.isValidEmail("ganz.spez-ial@test.test.ch;ein-bischen9@normal.ch;ganz@normal.ch;"));
  }

  public void testIsCurrency() {

    assertEquals(false, Util.isCurrency("abc"));
    assertEquals(false, Util.isCurrency("a1c"));
    assertEquals(true, Util.isCurrency("10"));
    assertEquals(true, Util.isCurrency("10.05"));
    assertEquals(false, Util.isCurrency("10,05"));
  }

  public void testContainsDigits() {

    assertEquals(false, Util.containsDigits("abc"));
    assertEquals(true, Util.containsDigits("a1c"));
    assertEquals(true, Util.containsDigits("10"));
    assertEquals(true, Util.containsDigits("10.05"));
    assertEquals(true, Util.containsDigits("10,05"));
  }

  public void testIsDigits() {

    assertEquals(false, Util.isDigits("abc"));
    assertEquals(false, Util.isDigits("a1c"));
    assertEquals(true, Util.isDigits("10"));
    assertEquals(false, Util.isDigits("10.05"));
  }

  public void testCut() {

    assertNull(Util.cut(null, 20));
    assertEquals("kein cut", Util.cut("kein cut", 8));
    assertEquals("mit ...", Util.cut("mit  cut", 7));
    assertEquals("m...", Util.cut("mit  cut", 2));
    assertEquals("sehr lange", Util.cut("sehr lange", 15));
    assertEquals("sehr...", Util.cut("sehr lange", 7));
  }

  public void testDayDiff() {

    Calendar c1 = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    Calendar c2 = new GregorianCalendar(2001, Calendar.JANUARY, 1);

    assertEquals(1, Util.dayDiff(c1, c2));

    c1 = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2001, Calendar.JANUARY, 2);

    assertEquals(2, Util.dayDiff(c1, c2));

    c1 = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2001, Calendar.DECEMBER, 31);

    assertEquals(365, Util.dayDiff(c1, c2));

    c1 = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2002, Calendar.JANUARY, 1);

    assertEquals(366, Util.dayDiff(c1, c2));

    c1 = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2002, Calendar.JANUARY, 1);

    assertEquals(366, Util.dayDiff(c1, c2));

    c1 = new GregorianCalendar(2001, Calendar.JANUARY, 2);
    c2 = new GregorianCalendar(2002, Calendar.JANUARY, 1);

    assertEquals(365, Util.dayDiff(c1, c2));

    c1 = new GregorianCalendar(2001, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2001, Calendar.JUNE, 30);

    assertEquals(181, Util.dayDiff(c1, c2));

    c1 = new GregorianCalendar(2000, Calendar.JANUARY, 1);
    c2 = new GregorianCalendar(2000, Calendar.JUNE, 30);

    assertEquals(182, Util.dayDiff(c1, c2));

    c2 = new GregorianCalendar(2000, Calendar.JANUARY, 1);
    c1 = new GregorianCalendar(2000, Calendar.JUNE, 30);

    assertEquals(182, Util.dayDiff(c1, c2));
  }

  public void testClearTime() {

    GregorianCalendar c1 = new GregorianCalendar();
    GregorianCalendar expected = new GregorianCalendar();

    Util.clearTime(c1);
    assertEquals(expected.get(Calendar.ERA), c1.get(Calendar.ERA));
    assertEquals(expected.get(Calendar.YEAR), c1.get(Calendar.YEAR));
    assertEquals(expected.get(Calendar.MONTH), c1.get(Calendar.MONTH));
    assertEquals(expected.get(Calendar.WEEK_OF_YEAR), c1.get(Calendar.WEEK_OF_YEAR));
    assertEquals(expected.get(Calendar.WEEK_OF_MONTH), c1.get(Calendar.WEEK_OF_MONTH));
    assertEquals(expected.get(Calendar.DATE), c1.get(Calendar.DATE));
    assertEquals(expected.get(Calendar.DAY_OF_MONTH), c1.get(Calendar.DAY_OF_MONTH));
    assertEquals(expected.get(Calendar.DAY_OF_YEAR), c1.get(Calendar.DAY_OF_YEAR));
    assertEquals(expected.get(Calendar.DAY_OF_WEEK), c1.get(Calendar.DAY_OF_WEEK));
    assertEquals(expected.get(Calendar.DAY_OF_WEEK_IN_MONTH), c1.get(Calendar.DAY_OF_WEEK_IN_MONTH));
    assertEquals(Calendar.AM, c1.get(Calendar.AM_PM));
    assertEquals(expected.get(Calendar.ZONE_OFFSET), c1.get(Calendar.ZONE_OFFSET));
    //assertEquals(expected.get(Calendar.DST_OFFSET), c1.get(Calendar.DST_OFFSET));
    assertEquals(0, c1.get(Calendar.HOUR));
    assertEquals(0, c1.get(Calendar.HOUR_OF_DAY));
    assertEquals(0, c1.get(Calendar.MINUTE));
    assertEquals(0, c1.get(Calendar.SECOND));
    assertEquals(0, c1.get(Calendar.MILLISECOND));

    c1 = new GregorianCalendar();

    Date result = Util.clearTime(c1.getTime());
    GregorianCalendar r = new GregorianCalendar();

    r.setTime(result);
    assertEquals(r.get(Calendar.ERA), c1.get(Calendar.ERA));
    assertEquals(r.get(Calendar.YEAR), c1.get(Calendar.YEAR));
    assertEquals(r.get(Calendar.MONTH), c1.get(Calendar.MONTH));
    assertEquals(r.get(Calendar.WEEK_OF_YEAR), c1.get(Calendar.WEEK_OF_YEAR));
    assertEquals(r.get(Calendar.WEEK_OF_MONTH), c1.get(Calendar.WEEK_OF_MONTH));
    assertEquals(r.get(Calendar.DATE), c1.get(Calendar.DATE));
    assertEquals(r.get(Calendar.DAY_OF_MONTH), c1.get(Calendar.DAY_OF_MONTH));
    assertEquals(r.get(Calendar.DAY_OF_YEAR), c1.get(Calendar.DAY_OF_YEAR));
    assertEquals(r.get(Calendar.DAY_OF_WEEK), c1.get(Calendar.DAY_OF_WEEK));
    assertEquals(r.get(Calendar.DAY_OF_WEEK_IN_MONTH), c1.get(Calendar.DAY_OF_WEEK_IN_MONTH));
    assertEquals(Calendar.AM, r.get(Calendar.AM_PM));
    assertEquals(r.get(Calendar.ZONE_OFFSET), c1.get(Calendar.ZONE_OFFSET));
    //assertEquals(r.get(Calendar.DST_OFFSET), c1.get(Calendar.DST_OFFSET));
    assertEquals(0, r.get(Calendar.HOUR));
    assertEquals(0, r.get(Calendar.HOUR_OF_DAY));
    assertEquals(0, r.get(Calendar.MINUTE));
    assertEquals(0, r.get(Calendar.SECOND));
    assertEquals(0, r.get(Calendar.MILLISECOND));
  }

  public void testAddYear() {

    GregorianCalendar c1 = new GregorianCalendar();
    GregorianCalendar expected = new GregorianCalendar(c1.get(Calendar.YEAR) + 2, Calendar.JANUARY, 1, 0, 0, 0);
    Date result = Util.addYear(c1.getTime(), 2);

    c1.setTime(result);
    assertEquals(expected.get(Calendar.ERA), c1.get(Calendar.ERA));
    assertEquals(expected.get(Calendar.YEAR), c1.get(Calendar.YEAR));
    assertEquals(expected.get(Calendar.MONTH), c1.get(Calendar.MONTH));
    assertEquals(expected.get(Calendar.WEEK_OF_YEAR), c1.get(Calendar.WEEK_OF_YEAR));
    assertEquals(expected.get(Calendar.WEEK_OF_MONTH), c1.get(Calendar.WEEK_OF_MONTH));
    assertEquals(expected.get(Calendar.DATE), c1.get(Calendar.DATE));
    assertEquals(expected.get(Calendar.DAY_OF_MONTH), c1.get(Calendar.DAY_OF_MONTH));
    assertEquals(expected.get(Calendar.DAY_OF_YEAR), c1.get(Calendar.DAY_OF_YEAR));
    assertEquals(expected.get(Calendar.DAY_OF_WEEK), c1.get(Calendar.DAY_OF_WEEK));
    assertEquals(expected.get(Calendar.DAY_OF_WEEK_IN_MONTH), c1.get(Calendar.DAY_OF_WEEK_IN_MONTH));
    assertEquals(expected.get(Calendar.AM_PM), c1.get(Calendar.AM_PM));
    assertEquals(expected.get(Calendar.ZONE_OFFSET), c1.get(Calendar.ZONE_OFFSET));
    assertEquals(expected.get(Calendar.DST_OFFSET), c1.get(Calendar.DST_OFFSET));
    assertEquals(0, c1.get(Calendar.HOUR));
    assertEquals(0, c1.get(Calendar.HOUR_OF_DAY));
    assertEquals(0, c1.get(Calendar.MINUTE));
    assertEquals(0, c1.get(Calendar.SECOND));
    assertEquals(0, c1.get(Calendar.MILLISECOND));

    c1 = new GregorianCalendar();
    expected = new GregorianCalendar(c1.get(Calendar.YEAR) - 1, Calendar.JANUARY, 1, 0, 0, 0);
    result = Util.addYear(c1.getTime(), -1);

    c1.setTime(result);
    assertEquals(expected.get(Calendar.ERA), c1.get(Calendar.ERA));
    assertEquals(expected.get(Calendar.YEAR), c1.get(Calendar.YEAR));
    assertEquals(expected.get(Calendar.MONTH), c1.get(Calendar.MONTH));
    assertEquals(expected.get(Calendar.WEEK_OF_YEAR), c1.get(Calendar.WEEK_OF_YEAR));
    assertEquals(expected.get(Calendar.WEEK_OF_MONTH), c1.get(Calendar.WEEK_OF_MONTH));
    assertEquals(expected.get(Calendar.DATE), c1.get(Calendar.DATE));
    assertEquals(expected.get(Calendar.DAY_OF_MONTH), c1.get(Calendar.DAY_OF_MONTH));
    assertEquals(expected.get(Calendar.DAY_OF_YEAR), c1.get(Calendar.DAY_OF_YEAR));
    assertEquals(expected.get(Calendar.DAY_OF_WEEK), c1.get(Calendar.DAY_OF_WEEK));
    assertEquals(expected.get(Calendar.DAY_OF_WEEK_IN_MONTH), c1.get(Calendar.DAY_OF_WEEK_IN_MONTH));
    assertEquals(expected.get(Calendar.AM_PM), c1.get(Calendar.AM_PM));
    assertEquals(expected.get(Calendar.ZONE_OFFSET), c1.get(Calendar.ZONE_OFFSET));
    assertEquals(expected.get(Calendar.DST_OFFSET), c1.get(Calendar.DST_OFFSET));
    assertEquals(0, c1.get(Calendar.HOUR));
    assertEquals(0, c1.get(Calendar.HOUR_OF_DAY));
    assertEquals(0, c1.get(Calendar.MINUTE));
    assertEquals(0, c1.get(Calendar.SECOND));
    assertEquals(0, c1.get(Calendar.MILLISECOND));
  }

  public void testToHMString() {

    for (int i = 0; i < 100; i++) {
      String minutes = "";

      if (i < 10) {
        minutes = "0";
      }

      minutes += String.valueOf(i);

      String result = Util.toHMString(new BigDecimal("10." + minutes));
      String reminstr = result.substring(result.indexOf(":") + 1);

      if (reminstr.startsWith("0")) {
        reminstr = reminstr.substring(1);
      }

      int remin = Integer.parseInt(reminstr);
      int rehr = Integer.parseInt(result.substring(0, result.indexOf(":")));
      BigDecimal expected = new BigDecimal(i);

      expected = expected.multiply(new BigDecimal(60));
      expected = expected.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);

      assertEquals(expected.intValue(), remin);
      assertEquals(10, rehr);
    }
  }



  public void testToHM() {

    for (int i = 0; i < 100; i++) {
      String minutes = "";

      if (i < 10) {
        minutes = "0";
      }

      minutes += String.valueOf(i);

      BigDecimal result = Util.toHM(new BigDecimal("5." + minutes));
      BigDecimal expected = new BigDecimal(i);

      expected = expected.multiply(new BigDecimal(60));
      expected = expected.divide(new BigDecimal(100), 3, BigDecimal.ROUND_HALF_UP);
      expected = expected.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
      expected = expected.add(new BigDecimal(5));

      assertEquals(expected, result);
    }
  }

  public void testInWorten() {

    assertEquals("eins", Util.inWorten(new BigDecimal(1)));
    assertEquals("zweidrei", Util.inWorten(new BigDecimal(23)));
    assertEquals("vierfünfsechs", Util.inWorten(new BigDecimal(456)));
    assertEquals("sieben", Util.inWorten(new BigDecimal(7)));
    assertEquals("achtneun", Util.inWorten(new BigDecimal(89)));
    assertEquals("einskommaneun", Util.inWorten(new BigDecimal("1.9")));
    assertEquals("achtkommaeins", Util.inWorten(new BigDecimal("8.1")));
  }

  public void testFilterUmlaute() {

    assertNull(Util.filterUmlaute(null));
    assertEquals("nothing", Util.filterUmlaute("nothing"));
    assertEquals("G&auml;rtner", Util.filterUmlaute("Gärtner"));
    assertEquals("G&uuml;rbe", Util.filterUmlaute("Gürbe"));
    assertEquals("L&ouml;sung", Util.filterUmlaute("Lösung"));
    assertEquals("&Auml;rger", Util.filterUmlaute("Ärger"));
    assertEquals("&Ouml;se", Util.filterUmlaute("Öse"));
    assertEquals("&Uuml;bel", Util.filterUmlaute("Übel"));
    assertEquals("H&uuml;b&auml;r", Util.filterUmlaute("Hübär"));
  }

  public void testFilterNewline() {

    assertNull(Util.filterNewline(null));
    assertEquals("ohne", Util.filterNewline("ohne"));
    assertEquals("ohne<br>\n", Util.filterNewline("ohne\n"));
    assertEquals("mit\r", Util.filterNewline("mit\r"));
    assertEquals("mit\r<br>\n", Util.filterNewline("mit\r\n"));
    assertEquals("<br>\nohne<br>\n<br>\nzwei<br>\nund<br>\nmehr", Util.filterNewline("\nohne\n\nzwei\nund\nmehr"));
  }


  public void testRemoveXML() {
    assertNull(Util.removeXml(null));
    assertEquals("ohne", Util.removeXml("ohne"));
    assertEquals("mit", Util.removeXml("<ohne>mit"));
    assertEquals("mittest", Util.removeXml("<ohne>mit<ohne>test</ohne>"));
    
  }
  
  public void testTruncateNicely() {
    assertNull(Util.truncateNicely(null, -1, -1, null, false));
    
    assertEquals("simple", Util.truncateNicely("simple", 20, 20, "...", false));
    assertEquals("12345678901234567890", Util.truncateNicely("12345678901234567890", 20, 20, "...", false));
    assertEquals("1234567890...", Util.truncateNicely("12345678901234567890", 10, 10, "...", false));
    
    assertEquals("1234567890...", Util.truncateNicely("1234567890 1234567890", 10, 20, "...", false));
    assertEquals("12 34 56 78 90 12 34...", Util.truncateNicely("12 34 56 78 90 12 34 56 78 90", 15, 20, "...", false));
    assertEquals("12 34 56 78 90 12 34...", Util.truncateNicely("12 34 56 78 90 12 34 56 78 90", 16, 20, "...", false));
    assertEquals("12 34 56 78 90 12 34...", Util.truncateNicely("12 34 56 78 90 12 34 56 78 90", 17, 20, "...", false));
    
    assertEquals("test...", Util.truncateNicely("test test", 4, 8, "...", false));
        
    assertEquals("Fischer&nbsp;Partner", Util.truncateNicely("Fischer&nbsp;Partner", 7, 20, "...", true));
    assertEquals("Fischer&nbsp;Partner", Util.truncateNicely("Fischer&nbsp;Partner", 7, 15, "...", true));
    assertEquals("Fischer...", Util.truncateNicely("Fischer&nbsp;Partner", 7, 14, "...", true));
    assertEquals("Fischer&nbsp;P...", Util.truncateNicely("Fischer&nbsp;Partner", 9, 9, "...", true));
    assertEquals("Fischer&nbsp;Pa...", Util.truncateNicely("Fischer&nbsp;Partner", 10, 10, "...", true));
    assertEquals("Fischer&nbsp;Par...", Util.truncateNicely("Fischer&nbsp;Partner", 11, 11, "...", true));
    assertEquals("Fischer&nbsp;Part...", Util.truncateNicely("Fischer&nbsp;Partner", 12, 12, "...", true));
    assertEquals("Fischer&nbsp;Partn...", Util.truncateNicely("Fischer&nbsp;Partner", 13, 13, "...", true));
    assertEquals("Fischer&nbsp;Partne...", Util.truncateNicely("Fischer&nbsp;Partner", 14, 14, "...", true));
    
    assertEquals("H&auml;nselmann", Util.truncateNicely("H&auml;nselmann", 20, 20, "...", true));
    assertEquals("H&auml;...", Util.truncateNicely("H&auml;nselmann", 2, 2, "...", true));
    assertEquals("&nbsp;&uuml;&auml;&ouml;&Uuml;&Auml;&Ouml;&amp;&quot;&lt;&gt;", Util.truncateNicely("&nbsp;&uuml;&auml;&ouml;&Uuml;&Auml;&Ouml;&amp;&quot;&lt;&gt;", 20, 20, "...", true));
    assertEquals("&nbsp;&uuml;&auml;&ouml;&Uuml;...", Util.truncateNicely("&nbsp;&uuml;&auml;&ouml;&Uuml;&Auml;&Ouml;&amp;&quot;&lt;&gt;", 5, 5, "...", true));
    assertEquals("&nbsp...", Util.truncateNicely("&nbsp;&uuml;&auml;&ouml;&Uuml;&Auml;&Ouml;&amp;&quot;&lt;&gt;", 5, 5, "...", false));
    
  }
}
