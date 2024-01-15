package ch.ess.addressbook.db;

import java.util.*;

import org.apache.commons.lang.enum.*;

 public final class AttributeEnum extends Enum {
  
   public static final AttributeEnum TITLE = new AttributeEnum("title");
   public static final AttributeEnum FIRST_NAME = new AttributeEnum("firstName");
   public static final AttributeEnum MIDDLE_NAME = new AttributeEnum("middleName");
   public static final AttributeEnum LAST_NAME = new AttributeEnum("lastName");
   public static final AttributeEnum NICK_NAME = new AttributeEnum("nickName");
   public static final AttributeEnum ADDRESS_STREET = new AttributeEnum("addressStreet");
   public static final AttributeEnum ADDRESS_POBOX = new AttributeEnum("addressPOBox");
   public static final AttributeEnum ADDRESS_STATE = new AttributeEnum("addressState");
   public static final AttributeEnum ADDRESS_POSTAL_CODE = new AttributeEnum("addressPostalCode");
   public static final AttributeEnum ADDRESS_CITY = new AttributeEnum("addressCity");
   public static final AttributeEnum ADDRESS_COUNTRY = new AttributeEnum("addressCountry");
   
   
   public static final AttributeEnum EMAIL = new AttributeEnum("email");
   public static final AttributeEnum HOMEPAGE = new AttributeEnum("homepage");


   //BUSINESS   
   public static final AttributeEnum BUSINESS_NUMBER = new AttributeEnum("businessNumber");
   public static final AttributeEnum OFFICE_NUMBER_2 = new AttributeEnum("officeNumber2");   
   public static final AttributeEnum OFFICE_LOCATION = new AttributeEnum("officeLocation");

   public static final AttributeEnum COMPANY_NAME = new AttributeEnum("companyName");
   public static final AttributeEnum DEPARTMENT_NAME = new AttributeEnum("departmentName");
   public static final AttributeEnum COMPANY_MAIN_PHONE_NUMBER = new AttributeEnum("companyMainPhoneNumber");

   
   public static final AttributeEnum FAX = new AttributeEnum("fax");   
   public static final AttributeEnum MOBILE_NUMBER = new AttributeEnum("mobileNumber");
   
  

   //HOME
   public static final AttributeEnum HOME_NUMBER = new AttributeEnum("homeNumber");
   public static final AttributeEnum HOME_NUMBER_2 = new AttributeEnum("homeNumber2");
   
   public static final AttributeEnum HOME_STREET = new AttributeEnum("homeStreet");
   public static final AttributeEnum HOME_POBOX = new AttributeEnum("homePOBox");
   public static final AttributeEnum HOME_STATE = new AttributeEnum("homeState");
   public static final AttributeEnum HOME_POSTAL_CODE = new AttributeEnum("homePostalCode");
   public static final AttributeEnum HOME_CITY = new AttributeEnum("homeCity");
   public static final AttributeEnum HOME_COUNTRY = new AttributeEnum("homeCountry");
   
   
   
   //OTHER
   public static final AttributeEnum OTHER_NUMBER = new AttributeEnum("otherNumber");

   public static final AttributeEnum OTHER_STREET = new AttributeEnum("otherStreet");
   public static final AttributeEnum OTHER_POBOX = new AttributeEnum("otherPOBox");
   public static final AttributeEnum OTHER_STATE = new AttributeEnum("otherState");   
   public static final AttributeEnum OTHER_POSTAL_CODE = new AttributeEnum("otherPostalCode");
   public static final AttributeEnum OTHER_CITY = new AttributeEnum("otherCity");
   public static final AttributeEnum OTHER_COUNTRY = new AttributeEnum("otherCountry");

   public static final AttributeEnum COMMENT = new AttributeEnum("comment");
   public static final AttributeEnum BIRTHDAY = new AttributeEnum("birthday");
   public static final AttributeEnum HOBBIES = new AttributeEnum("hobbies");



   //PICTURE
   public static final AttributeEnum WIDTH = new AttributeEnum("width");
   public static final AttributeEnum HEIGHT = new AttributeEnum("height");
   public static final AttributeEnum ORIGINAL_WIDTH = new AttributeEnum("originalWidth");
   public static final AttributeEnum ORIGINAL_HEIGHT = new AttributeEnum("originalHeight");
   
   public static final AttributeEnum CONTENT_TYPE = new AttributeEnum("contentType");
   
   private AttributeEnum(String attributeName) {
     super(attributeName);
   }
 
   public static AttributeEnum getEnum(String attributName) {
     return (AttributeEnum)getEnum(AttributeEnum.class, attributName);
   }
 
   public static Map getEnumMap() {
     return getEnumMap(AttributeEnum.class);
   }
 
   public static List getEnumList() {
     return getEnumList(AttributeEnum.class);     
   }
 
   public static Iterator iterator() {
     return iterator(AttributeEnum.class);
   }
 }
