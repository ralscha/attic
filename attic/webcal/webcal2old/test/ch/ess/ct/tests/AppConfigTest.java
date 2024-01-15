package ch.ess.ct.tests;

import ch.ess.cal.resource.*;

public class AppConfigTest extends DbTestCase {

  public AppConfigTest(String testName) {
    super(testName);
  }

  public void testAppConfig() {
    DatabaseChecker db = getDatabaseChecker();
    
    try {
      db.update("DELETE FROM calConfiguration");
          
      
      AppConfig.init();
      
      assertEquals("mail.ess.ch", AppConfig.getStringProperty(AppConfig.MAIL_SMTPHOST));
      assertEquals("sr@ess.ch", AppConfig.getStringProperty(AppConfig.MAIL_SENDER));
      assertEquals("sr@ess.ch", AppConfig.getStringProperty(AppConfig.ERROR_MAIL_RECEIVER));
      //assertEquals("c:/temp", AppConfig.getStringProperty(AppConfig.UPLOAD_PATH));
      //assertEquals(360, AppConfig.getIntegerProperty(AppConfig.PASSWORD_CHANGEDAYS, 0));
      assertEquals(7, AppConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, 0));
    
    
      AppConfig.storeProperty("boolean_true", true);
      AppConfig.storeProperty("boolean_false", false);      
      AppConfig.storeProperty("double", 1.001);
      AppConfig.storeProperty("int", 314);
      AppConfig.storeProperty("long", 108l);
      AppConfig.storeProperty("string", "ralph");
      AppConfig.storeProperty("obj", new TestObj());
      
      assertTrue(AppConfig.getBooleanProperty("boolean_true", false));
      assertFalse(AppConfig.getBooleanProperty("boolean_false", true));  
      
      assertTrue(AppConfig.getBooleanProperty("boolean_true").booleanValue());
      assertFalse(AppConfig.getBooleanProperty("boolean_false").booleanValue());  
      assertNull(AppConfig.getBooleanProperty("boolean_notexists"));
      
      assertEquals(1.001, AppConfig.getDoubleProperty("double", 0), 0.00001);
      assertEquals(1.001, AppConfig.getDoubleProperty("double").doubleValue(), 0.00001);
      assertNull(AppConfig.getDoubleProperty("double_notexists"));
      
      assertEquals(314, AppConfig.getIntegerProperty("int", 0));
      assertEquals(108l, AppConfig.getLongProperty("long", 0));

      assertEquals(314, AppConfig.getIntegerProperty("int").intValue());
      assertEquals(108l, AppConfig.getLongProperty("long").longValue());
      assertNull(AppConfig.getIntegerProperty("int_notexists"));
      assertNull(AppConfig.getLongProperty("long_notexists"));
      
      
      assertEquals("ralph", AppConfig.getStringProperty("string"));
      assertEquals("ralph", AppConfig.getStringProperty("string", null));


      assertTrue(AppConfig.getBooleanProperty("boolean_na", true));
      assertFalse(AppConfig.getBooleanProperty("boolean_na", false));  
      assertEquals(20, AppConfig.getDoubleProperty("double_na", 20), 0.00001);
      assertEquals(30, AppConfig.getIntegerProperty("int_na", 30));
      assertEquals(40, AppConfig.getLongProperty("long_na", 40));      
      assertEquals("schaer", AppConfig.getStringProperty("string_na", "schaer"));


      AppConfig.removeProperty("boolean_true");
      AppConfig.removeProperty("boolean_false");
      AppConfig.removeProperty("double");
      AppConfig.removeProperty("int");
      AppConfig.removeProperty("long");
      AppConfig.removeProperty("string");
      AppConfig.removeProperty("testobj");

      AppConfig.init();
            
      assertFalse(AppConfig.getBooleanProperty("boolean_true", false));
      assertTrue(AppConfig.getBooleanProperty("boolean_false", true));  
      assertEquals(0, AppConfig.getDoubleProperty("double", 0), 0.00001);
      assertEquals(0, AppConfig.getIntegerProperty("int", 0));
      assertEquals(0, AppConfig.getLongProperty("long", 0));
      assertNull(AppConfig.getStringProperty("string"));
      assertNull(AppConfig.getStringProperty("string", null));
      
      assertEquals("mail.ess.ch", AppConfig.getStringProperty(AppConfig.MAIL_SMTPHOST));
      assertEquals("sr@ess.ch", AppConfig.getStringProperty(AppConfig.MAIL_SENDER));
      assertEquals("sr@ess.ch", AppConfig.getStringProperty(AppConfig.ERROR_MAIL_RECEIVER));
      //assertEquals(360, AppConfig.getIntegerProperty(AppConfig.PASSWORD_CHANGEDAYS, 0));
      assertEquals(7, AppConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, 0));
      //assertEquals("c:/temp", AppConfig.getStringProperty(AppConfig.UPLOAD_PATH));

      AppConfig.storeProperty("string2", "value2");
      AppConfig.storeProperty("string3", "value3");
      assertEquals("value2", AppConfig.getStringProperty("string2"));
      assertEquals("value3", AppConfig.getStringProperty("string3"));
      AppConfig.removeProperty("string3");
      assertEquals("value2", AppConfig.getStringProperty("string2"));
      assertNull(AppConfig.getStringProperty("string3"));
      AppConfig.init();
      assertEquals("value2", AppConfig.getStringProperty("string2"));
      assertNull(AppConfig.getStringProperty("string3"));
      
    
    } catch (Exception e) {
      fail(e.toString());
    }  
  }
  

  class TestObj {
    public String toString() {      
      return "testObj";
    }

  }

}
