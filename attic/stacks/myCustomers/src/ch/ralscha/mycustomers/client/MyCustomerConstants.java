package ch.ralscha.mycustomers.client;

/**
 * Interface to represent the constants contained in resource bundle:
 * 	'D:/gwt/projekte/MyCustomers/src/ch/ralscha/mycustomers/client/MyCustomerConstants.properties'.
 */
public interface MyCustomerConstants extends com.google.gwt.i18n.client.Constants {
  
  /**
   * Translated "Delete".
   * 
   * @return translated "Delete"
   */
  @DefaultStringValue("Delete")
  @Key("delete")
  String delete();

  /**
   * Translated "New".
   * 
   * @return translated "New"
   */
  @DefaultStringValue("New")
  @Key("newCustomer")
  String newCustomer();

  /**
   * Translated "Save".
   * 
   * @return translated "Save"
   */
  @DefaultStringValue("Save")
  @Key("save")
  String save();
}
