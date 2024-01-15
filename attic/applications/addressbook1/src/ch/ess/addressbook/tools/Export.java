package ch.ess.addressbook.tools;

import java.io.*;

import org.apache.commons.lang.*;

import com.intrinsyc.cdo.*;
import com.intrinsyc.jeb.*;

public class Export {
/*
    private static final String CdoContact_SelectedAddress = "0x8022";
    private static final String CdoContact_BusinessAddress = "0x801B";
    private static final String CdoContact_BusinessAddressCity = "0x8046";
    private static final String CdoContact_BusinessAddressStreet = "0x8045";
    private static final String CdoContact_BusinessAddressState = "0x8047";
    private static final String CdoContact_BusinessAddressCountry = "0x8049";
    private static final String CdoContact_BusinessAddressPostalCode = "0x8048";
    private static final String CdoContact_BusinessAddressPostOfficeBox = "0x804A";
*/
    private static final String CdoPropSetID3 = "0420060000000000C000000000000046";
//    private static final String CdoPropSetID5 = "2903020000000000C000000000000046";
 
    public static void main(String[] args) {
      try {

        JebSession session = new JebSession();
        session.logon("TNET", "192.168.20.44", "sr", "trubel13", "Ralph Schär", "192.168.20.44");
 
        session.getDefaultContactFolder();

        Session s = session.getSession(); //switch over to CDO objects 
        InfoStores inStores = new InfoStoresProxy(s.getInfoStores());

        InfoStore publicStore = new InfoStoreProxy(inStores.getItem("Public Folders"));
        Folder publicFolder = new FolderProxy(publicStore.getRootFolder());
        Folders folders = new FoldersProxy(publicFolder.getFolders());

            
        Folder t = new FolderProxy(folders.getLast());
            
        Folders allpub = new FoldersProxy(t.getFolders());
        t = new FolderProxy(allpub.getItem("addresses"));
          
        Messages msgs = new MessagesProxy(t.getMessages());

        File f = new File("c:/temp/contacts.xml");
        if (f.exists()) {
          f.delete();
        }

        PrintWriter pw = new PrintWriter(new FileWriter("c:/temp/contacts.xml"));

        pw.println("<contacts>");
        Object next;
        while ((next = msgs.getNext()) != null) { 
        
          pw.println("  <contact>");       
          Message msg = new MessageProxy(next);
          JebContact contact = new JebContact(session, msg);

          if (notEmpty(contact.getFirstName())) {
            pw.println("    <attribut name=\"firstName\">"+StringUtils.replace(contact.getFirstName(), "&", "&amp;")+"</attribut>");  
          }
          if (notEmpty(contact.getMiddleName())) {
            pw.println("    <attribut name=\"middleName\">"+StringUtils.replace(contact.getMiddleName(), "&", "&amp;")+"</attribut>");
          }
          if (notEmpty(contact.getLastName())) {
            pw.println("    <attribut name=\"lastName\">"+StringUtils.replace(contact.getLastName(), "&", "&amp;")+"</attribut>");          
          }
          if (notEmpty(contact.getEmail())) {
            pw.println("    <attribut name=\"email\">"+StringUtils.replace(contact.getEmail(), "&", "&amp;")+"</attribut>");          
          }
          if (notEmpty(contact.getBusinessNumber())) {
            pw.println("    <attribut name=\"businessNumber\">"+StringUtils.replace(contact.getBusinessNumber(), "&", "&amp;")+"</attribut>");
          }
          if (notEmpty(contact.getHomeNumber())) {
            pw.println("    <attribut name=\"homeNumber\">"+StringUtils.replace(contact.getHomeNumber(), "&", "&amp;")+"</attribut>");      
          }
            
       
        
          String businessAddressCity = getValue(CdoPropTags.CdoPR_BUSINESS_ADDRESS_CITY, msg);
          if (notEmpty(businessAddressCity)) {
            pw.println("    <attribut name=\"addressCity\">"+businessAddressCity+"</attribut>");      
          }           
             
          String businessAddressStreet = getValue(CdoPropTags.CdoPR_BUSINESS_ADDRESS_STREET, msg);
          if (notEmpty(businessAddressStreet)) {
            pw.println("    <attribut name=\"addressStreet\">"+businessAddressStreet+"</attribut>");      
          } 
                
          String businessAddressState = getValue(CdoPropTags.CdoPR_BUSINESS_ADDRESS_STATE_OR_PROVINCE, msg);
          if (notEmpty(businessAddressState)) {
            pw.println("    <attribut name=\"addressState\">"+businessAddressState+"</attribut>");      
          }     
            
          String businessAddressCountry = getValue(CdoPropTags.CdoPR_BUSINESS_ADDRESS_COUNTRY, msg);
          if (notEmpty(businessAddressCountry)) {
            pw.println("    <attribut name=\"addressCountry\">"+businessAddressCountry+"</attribut>");      
          }    
                
          String businessAddressPostalCode = getValue(CdoPropTags.CdoPR_BUSINESS_ADDRESS_POSTAL_CODE, msg);
          if (notEmpty(businessAddressPostalCode)) {
            pw.println("    <attribut name=\"addressPostalCode\">"+businessAddressPostalCode+"</attribut>");      
          }    
                
          String businessAddressPostOfficeBox = getValue(CdoPropTags.CdoPR_BUSINESS_ADDRESS_POST_OFFICE_BOX, msg);
          if (notEmpty(businessAddressPostOfficeBox)) {
            pw.println("    <attribut name=\"addressPOBox\">"+businessAddressPostOfficeBox+"</attribut>");      
          }    
           
          String fax = getValue(CdoPropTags.CdoPR_BUSINESS_FAX_NUMBER, msg);
          if (notEmpty(fax)) {
            pw.println("    <attribut name=\"fax\">"+fax+"</attribut>");      
          } 

          String homepage = getValue(CdoPropTags.CdoPR_BUSINESS_HOME_PAGE, msg);
          if (notEmpty(homepage)) {
            pw.println("    <attribut name=\"homepage\">"+homepage+"</attribut>");      
          }   
        
          String comment = getValue(CdoPropTags.CdoPR_COMMENT, msg);
          if (notEmpty(comment)) {
            pw.println("    <attribut name=\"comment\">"+comment+"</attribut>");      
          }  
        
          /*
          String cell = getValue(CdoPropTags.CdoPR_CELLULAR_TELEPHONE_NUMBER, msg);
          if (notEmpty(cell)) {
            pw.println("    <attribut name=\"cellularNumber\">"+cell+"</attribut>");      
          }   
          */
        
          String value = getValue(CdoPropTags.CdoPR_COMPANY_MAIN_PHONE_NUMBER, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"companyMainPhoneNumber\">"+value+"</attribut>");      
          }                                        

          value = getValue(CdoPropTags.CdoPR_COMPANY_NAME, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"companyName\">"+value+"</attribut>");      
          }                                        

          value = getValue(CdoPropTags.CdoPR_DEPARTMENT_NAME, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"departmentName\">"+value+"</attribut>");      
          }   
        
          value = getValue(CdoPropTags.CdoPR_BIRTHDAY, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"birthday\">"+value+"</attribut>");      
          }   
        
          value = getValue(CdoPropTags.CdoPR_HOBBIES, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"hobbies\">"+value+"</attribut>");      
          }    
        
          value = getValue(CdoPropTags.CdoPR_HOME2_TELEPHONE_NUMBER, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"homeNumber2\">"+value+"</attribut>");      
          }                        

          value = getValue(CdoPropTags.CdoPR_HOME_ADDRESS_CITY, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"homeCity\">"+value+"</attribut>");      
          }  

          value = getValue(CdoPropTags.CdoPR_HOME_ADDRESS_COUNTRY, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"homeCountry\">"+value+"</attribut>");      
          } 
        
          value = getValue(CdoPropTags.CdoPR_HOME_ADDRESS_POST_OFFICE_BOX, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"homePOBox\">"+value+"</attribut>");      
          }           
          
          value = getValue(CdoPropTags.CdoPR_HOME_ADDRESS_POSTAL_CODE, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"homePostalCode\">"+value+"</attribut>");      
          }  

          value = getValue(CdoPropTags.CdoPR_HOME_ADDRESS_STATE_OR_PROVINCE, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"homeState\">"+value+"</attribut>");      
          }  

          value = getValue(CdoPropTags.CdoPR_HOME_ADDRESS_STREET, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"homeStreet\">"+value+"</attribut>");      
          }   
        
          value = getValue(CdoPropTags.CdoPR_MOBILE_TELEPHONE_NUMBER, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"mobileNumber\">"+value+"</attribut>");      
          }   
        
          value = getValue(CdoPropTags.CdoPR_NICKNAME, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"nickName\">"+value+"</attribut>");      
          }   
        
          value = getValue(CdoPropTags.CdoPR_OFFICE2_TELEPHONE_NUMBER, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"officeNumber2\">"+value+"</attribut>");      
          } 
        
          value = getValue(CdoPropTags.CdoPR_OFFICE_LOCATION, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"officeLocation\">"+value+"</attribut>");      
          }                                                                             

          /*
          value = getValue(CdoPropTags.CdoPR_OFFICE_TELEPHONE_NUMBER, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"officeNumber\">"+value+"</attribut>");      
          }  
          */
        
          value = getValue(CdoPropTags.CdoPR_OTHER_ADDRESS_CITY, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"otherCity\">"+value+"</attribut>");      
          }                                                                                    

          value = getValue(CdoPropTags.CdoPR_OTHER_ADDRESS_COUNTRY, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"otherCountry\">"+value+"</attribut>");      
          }    
    
          value = getValue(CdoPropTags.CdoPR_OTHER_ADDRESS_POST_OFFICE_BOX, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"otherPOBox\">"+value+"</attribut>");      
          }     
    
          value = getValue(CdoPropTags.CdoPR_OTHER_ADDRESS_POSTAL_CODE, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"otherPostalCode\">"+value+"</attribut>");      
          }  
        
          value = getValue(CdoPropTags.CdoPR_OTHER_ADDRESS_STATE_OR_PROVINCE, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"otherState\">"+value+"</attribut>");      
          }     
        
          value = getValue(CdoPropTags.CdoPR_OTHER_ADDRESS_STREET, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"otherStreet\">"+value+"</attribut>");      
          } 
        
          value = getValue(CdoPropTags.CdoPR_OTHER_TELEPHONE_NUMBER, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"otherNumber\">"+value+"</attribut>");      
          }                        

          value = getValue(CdoPropTags.CdoPR_TITLE, msg);
          if (notEmpty(value)) {
            pw.println("    <attribut name=\"title\">"+value+"</attribut>");      
          } 
        
                                

               
          pw.println("  </contact>");     
        }
        pw.println("</contacts>");      
        pw.close();
      
        session.logoff();
      } catch (Exception e) {
        e.printStackTrace();        
      }
    }
  
    public static boolean notEmpty(String str) {
      if (str != null) {
        return (!str.trim().equals(""));
      }    
      return false;
    }


    private static String getValue(int cdoPR_id, Message msg) {
      String returnValue = null;

      try {
        Fields fs = new FieldsProxy(msg.getFields());
        Field fd = new FieldProxy(fs.getItem(new Integer(cdoPR_id), CdoPropSetID3)); 
      
        returnValue = (String)fd.getValue();
        returnValue = StringUtils.replace(returnValue, "&", "&amp;");

          
      } catch (Exception e) {
        //e.printStackTrace();
      }

      return returnValue;  
    } 


  }



