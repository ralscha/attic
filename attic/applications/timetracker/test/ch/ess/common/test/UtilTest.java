package ch.ess.common.test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;
import ch.ess.common.util.Util;

public class UtilTest extends TestCase {

  public UtilTest(String arg0) {
    super(arg0);
  }

  public void testGetLocale() {
    
    Locale l = Util.getLocale(null);
    assertEquals(l.toString(), Locale.getDefault().toString());
    
    l = Util.getLocale("en");
    assertEquals("", l.getCountry());
    assertEquals("", l.getVariant());
    assertEquals("en", l.getLanguage());
    
    l = Util.getLocale("it_IT");
    assertEquals("IT", l.getCountry());
    assertEquals("", l.getVariant());
    assertEquals("it", l.getLanguage());
    
    l = Util.getLocale("fr_FR_t");
    assertEquals("FR", l.getCountry());
    assertEquals("t", l.getVariant());
    assertEquals("fr", l.getLanguage());
    
  }

  public void testTruncateNicely() {    
    assertNull(Util.truncateNicely(null, 1, 2, null, false));
    assertEquals("rälph", Util.truncateNicely("rälph", 20, 20, "...", false));
    assertEquals("r&auml;lph", Util.truncateNicely("rälph", 20, 20, "...", true));
    
    assertEquals("a löng text", Util.truncateNicely("<test>a löng text</test>", 20, 20, "...", false));
    assertEquals("a&nbsp;l&ouml;ng&nbsp;text", Util.truncateNicely("<test>a löng text</test>", 20, 20, "...", true));
    
    assertEquals("a ...", Util.truncateNicely("<test>a long text</test>", 2, 2, "...", false));
    assertEquals("a l...", Util.truncateNicely("<test>a long text</test>", 2, 3, "...", false));
    assertEquals("a...", Util.truncateNicely("<test>a long text</test>", 1, 5, "...", false));
    
    assertEquals("a long...", Util.truncateNicely("<test>a long text</test>", 5, 8, "...", false));
    assertEquals("a longmore", Util.truncateNicely("<test>a long text</test>", 5, 8, "more", false));
    assertEquals("a long", Util.truncateNicely("<test>a long text</test>", 5, 8, "", false));
    assertEquals("a long", Util.truncateNicely("<test>a long text</test>", 5, 8, null, false));
    
    assertEquals("simple", Util.truncateNicely("simple", 20, 20, "...", false));
    assertEquals("12345678901234567890", Util.truncateNicely("12345678901234567890", 20, 20, "...", false));
    assertEquals("1234567890...", Util.truncateNicely("12345678901234567890", 10, 10, "...", false));
    
    assertEquals("1234567890...", Util.truncateNicely("1234567890 1234567890", 10, 20, "...", false));
    assertEquals("12 34 56 78 90 12 34...", Util.truncateNicely("12 34 56 78 90 12 34 56 78 90", 15, 20, "...", false));
    assertEquals("12 34 56 78 90 12 34...", Util.truncateNicely("12 34 56 78 90 12 34 56 78 90", 16, 20, "...", false));
    assertEquals("12 34 56 78 90 12 34...", Util.truncateNicely("12 34 56 78 90 12 34 56 78 90", 17, 20, "...", false));

    assertEquals("12 34 56 78 90 12 34...", Util.truncateNicely("12 34 56 78 90 12 34 56 78 90", 20, 15, "...", false));
    assertEquals("12 34 56 78 90 12 34...", Util.truncateNicely("12 34 56 78 90 12 34 56 78 90", 20, 16, "...", false));
    assertEquals("12 34 56 78 90 12 34...", Util.truncateNicely("12 34 56 78 90 12 34 56 78 90", 20, 17, "...", false));
    
    
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

  public void testRemoveXml() {
    assertNull(Util.removeXml(null));
    assertEquals("", Util.removeXml(""));
    assertEquals(" ", Util.removeXml(" "));
    assertEquals("ralph", Util.removeXml("ralph"));
    assertEquals("", Util.removeXml("<ralph>"));
    assertEquals("", Util.removeXml("<ralph></ralph>"));
    assertEquals("test", Util.removeXml("<ralph>test</ralph>"));
    assertEquals("", Util.removeXml("<ralph"));    
    assertEquals("1020", Util.removeXml("10>20"));
    assertEquals("cell", Util.removeXml("<tr><td>cell</td></tr>"));
  }

  public void testReplaceHTMLEntities() {
    assertNull(Util.replaceHTMLEntities(null));
    
    assertEquals("", Util.replaceHTMLEntities(""));
    assertEquals("ralph", Util.replaceHTMLEntities("ralph"));
    
    assertEquals("G & B", Util.replaceHTMLEntities("G &amp; B"));
    assertEquals("\"this\"", Util.replaceHTMLEntities("&quot;this&quot;"));
    assertEquals("<10", Util.replaceHTMLEntities("&lt;10"));
    assertEquals(">11", Util.replaceHTMLEntities("&gt;11"));
    
    assertEquals("<\"A & B\">", Util.replaceHTMLEntities("&lt;&quot;A &amp; B&quot;&gt;"));
    
  }

  public void testReplaceToHTMLEntities() {
    assertNull(Util.replaceToHTMLEntities(null));
    
    assertEquals("", Util.replaceToHTMLEntities(""));
    assertEquals("ralph", Util.replaceToHTMLEntities("ralph"));
        
    assertEquals("&quot;this&quot;", Util.replaceToHTMLEntities("\"this\""));
    assertEquals("&lt;10", Util.replaceToHTMLEntities("<10"));
    assertEquals("&gt;11", Util.replaceToHTMLEntities(">11"));
    assertEquals("&nbsp;&nbsp;", Util.replaceToHTMLEntities("  "));
    assertEquals("&lt;&quot;A&nbsp;+&nbsp;B&quot;&gt;", Util.replaceToHTMLEntities("<\"A + B\">"));    
  }

  public void testReplaceHTMLUml() {
    assertNull(Util.replaceHTMLUml(null));
    
    assertEquals("", Util.replaceHTMLUml(""));
    assertEquals(" ", Util.replaceHTMLUml("&nbsp;"));
    assertEquals("ralph", Util.replaceHTMLUml("ralph"));
    assertEquals("gären", Util.replaceHTMLUml("g&auml;ren"));
    assertEquals("führen", Util.replaceHTMLUml("f&uuml;hren"));
    assertEquals("Löcher", Util.replaceHTMLUml("L&ouml;cher"));
    assertEquals("Öffentlich", Util.replaceHTMLUml("&Ouml;ffentlich"));
    assertEquals("Übelkeit", Util.replaceHTMLUml("&Uuml;belkeit"));
    assertEquals("Ärgernis", Util.replaceHTMLUml("&Auml;rgernis"));
    
    assertEquals("ä ö ü ÄÖÜ", Util.replaceHTMLUml("&auml;&nbsp;&ouml;&nbsp;&uuml;&nbsp;&Auml;&Ouml;&Uuml;"));
  }

  public void testRemoveNonDigits() {
    assertNull(Util.removeNonDigits(null));
    assertEquals("", Util.removeNonDigits(""));
    assertEquals("", Util.removeNonDigits("ABC"));
    assertEquals("9", Util.removeNonDigits("9"));
    assertEquals("9.1", Util.removeNonDigits("9.1"));
    assertEquals("9.1", Util.removeNonDigits("9.1ab"));
    assertEquals("9.1", Util.removeNonDigits("a9b.c1d"));
    assertEquals("100", Util.removeNonDigits("USD 100"));
  }

  public void testToUsAscii() {
    assertNull(Util.toUsAscii(null));
    
    assertEquals("", Util.toUsAscii(""));
    assertEquals("ralph", Util.toUsAscii("ralph"));
    
    assertEquals(" ", Util.toUsAscii(" "));    
    assertEquals("gaeren", Util.toUsAscii("gären"));
    assertEquals("fuehren", Util.toUsAscii("führen"));
    assertEquals("Loecher", Util.toUsAscii("Löcher"));
    assertEquals("Oeffentlich", Util.toUsAscii("Öffentlich"));
    assertEquals("Uebelkeit", Util.toUsAscii("Übelkeit"));
    assertEquals("Aergernis", Util.toUsAscii("Ärgernis"));    
    assertEquals("eeEEnormal", Util.toUsAscii("éèÉÈnormal"));
    assertEquals("aA", Util.toUsAscii("àÀ"));
    assertEquals("coaueinss", Util.toUsAscii("çôâûêïñß"));
  }

  public void testReplaceToHtmlUml() {
    assertNull(Util.replaceToHtmlUml(null));
    assertEquals("", Util.replaceToHtmlUml(""));    
    assertEquals("ralph", Util.replaceToHtmlUml("ralph"));
    assertEquals("&amp;", Util.replaceToHtmlUml("&"));
    assertEquals("g&auml;ren", Util.replaceToHtmlUml("gären"));
    assertEquals("f&uuml;hren", Util.replaceToHtmlUml("führen"));
    assertEquals("L&ouml;cher", Util.replaceToHtmlUml("Löcher"));
    assertEquals("&Ouml;ffentlich", Util.replaceToHtmlUml("Öffentlich"));
    assertEquals("&Uuml;belkeit", Util.replaceToHtmlUml("Übelkeit"));
    assertEquals("&Auml;rgernis", Util.replaceToHtmlUml("Ärgernis"));
    
    assertEquals("&auml;&ouml;&uuml; &amp; &Auml;&Ouml;&Uuml;", Util.replaceToHtmlUml("äöü & ÄÖÜ"));
  }

  
  public void testContainsString() {
    
    Map testMap = new HashMap();
    testMap.put("one", "eins");
    testMap.put("two", "two");
    testMap.put("three", "three");
    
    testMap.put("One", "One");
    
    assertTrue(Util.containsString(null, null));
    assertTrue(Util.containsString(testMap, null));
    assertTrue(Util.containsString(testMap, ""));
    assertTrue(Util.containsString(testMap, "   "));
    
    assertTrue(Util.containsString(testMap, "eins"));
    assertTrue(Util.containsString(testMap, "ein"));
    assertTrue(Util.containsString(testMap, "ei"));
    assertTrue(Util.containsString(testMap, "e"));
    
    assertTrue(Util.containsString(testMap, "EINS"));
    assertTrue(Util.containsString(testMap, "eIn"));
    assertTrue(Util.containsString(testMap, "eI"));
    assertTrue(Util.containsString(testMap, "E"));
    
    assertTrue(Util.containsString(testMap, "two"));
    assertTrue(Util.containsString(testMap, "three"));
    assertTrue(Util.containsString(testMap, "One"));
    
    assertFalse(Util.containsString(testMap, "four"));
    assertFalse(Util.containsString(testMap, "Four"));
    assertFalse(Util.containsString(testMap, "einsa"));
    assertFalse(Util.containsString(testMap, "twox"));
    
  }
  
  
  /*
   *     public static boolean containsString(Map translations, String searchText) { 
331 0     if (GenericValidator.isTimeTrackerOrNull(searchText)) { 
332 0       return true; 
333       } 
334        
335 0     searchText = searchText.toLowerCase(); 
336        
337 0     for (Iterator it = translations.values().iterator(); it.hasNext();) { 
338 0       String element = (String)it.next(); 
339 0       if (element.toLowerCase().indexOf(searchText) != -1) { 
340 0         return true; 
341         } 
342       } 
343         
344 0     return false; 
345     } 

   * @author sr
   */
}
