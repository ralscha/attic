package ch.ess.cal.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import ch.ess.cal.Constants;
import ch.ess.cal.db.Category;
import ch.ess.cal.db.Department;
import ch.ess.cal.db.Email;
import ch.ess.cal.db.Permission;
import ch.ess.cal.db.User;
import ch.ess.cal.db.UserGroup;
import ch.ess.cal.util.PasswordDigest;

public class InitialDataLoad {
  
  public static void load() throws HibernateException {
    
    Session sess = null;
    Transaction tx = null;
  
    try {  
      sess = HibernateManager.open();
        
      tx = sess.beginTransaction(); 
            
      List resultList = sess.find("from Permission as perm");
      if (resultList.isEmpty()) {

        Permission permission = new Permission();
        permission.setName("admin");
        sess.save(permission);

        Department department1 = new Department();
        department1.setName("Office 1");
        sess.save(department1);                                 

        Department department2 = new Department();
        department2.setName("Office 2");
        sess.save(department2);                                 

        Department department3 = new Department();
        department3.setName("Office 3");
        sess.save(department3);                                 

        Department department4 = new Department();
        department4.setName("Office 4");
        sess.save(department4); 
                      
        UserGroup group = new UserGroup();
        group.setName("Admin");        
        Set permissionSet = new HashSet();
        permissionSet.add(permission);
        group.setPermissions(permissionSet);
        sess.save(group);
                        
        
        
                           
        User newUser = new User();
        Set departments = new HashSet();
        departments.add(department1);
        newUser.setDepartments(departments);
        
        departments = new HashSet();
        departments.add(department2);
        newUser.setAccessDepartments(departments);

        newUser.setUserGroup(group);
        newUser.setUserName("admin");
        newUser.setFirstName("admin");
        newUser.setName("admin");
        newUser.setLocale(Constants.LOCALE_EN);
        newUser.setPasswordHash(PasswordDigest.getDigestString("admin"));
        sess.save(newUser);

        Email email = new Email();
        email.setUser(newUser);
        email.setDefaultEmail(false); 
        email.setEmail("ralphschaer@yahoo.com");
        email.setSequence(1);
        sess.save(email);

        email = new Email();
        email.setUser(newUser);
        email.setDefaultEmail(true);
        email.setEmail("schaer@ess.ch");
        email.setSequence(0);
        sess.save(email);
        
        //normal user
        newUser = new User();
        departments = new HashSet();
        departments.add(department2);
        departments.add(department4);

        newUser.setDepartments(departments);

        departments = new HashSet();
        departments.add(department1);
        departments.add(department3);
        newUser.setAccessDepartments(departments);

        newUser.setUserGroup(null);
        newUser.setUserName("user");
        newUser.setFirstName("user");
        newUser.setName("user");
                
        newUser.setLocale(Constants.LOCALE_EN);
        newUser.setPasswordHash(PasswordDigest.getDigestString("user"));
        sess.save(newUser);    
        
        email = new Email();
        email.setUser(newUser);
        email.setDefaultEmail(false); 
        email.setEmail("ralphschaer@yahoo.com");
        email.setSequence(1);
        sess.save(email);

        email = new Email();
        email.setUser(newUser);
        email.setDefaultEmail(true);
        email.setEmail("schaer@ess.ch");
        email.setSequence(0);
        sess.save(email);
        
        
        //categories
        Category newCat = new Category();
        newCat.setName("Business");
        newCat.setDescription(null);
        newCat.setColour("FF9900");
        newCat.setBwColour("000000");
        newCat.setIcalKey("BUSINESS");
        newCat.setUnknown(false);        
        sess.save(newCat);
        
        newCat = new Category();
        newCat.setName("Appointment");
        newCat.setDescription(null);
        newCat.setColour("0000FF");
        newCat.setBwColour("101010");
        newCat.setIcalKey("APPOINTMENT");
        newCat.setUnknown(false);                
        sess.save(newCat);        

        newCat = new Category();
        newCat.setName("Education");
        newCat.setDescription(null);
        newCat.setColour("FFFF00");
        newCat.setBwColour("202020");
        newCat.setIcalKey("EDUCATION");
        newCat.setUnknown(false);                
        sess.save(newCat);        

        newCat = new Category();
        newCat.setName("Holiday");
        newCat.setDescription(null);
        newCat.setColour("FF0000");
        newCat.setBwColour("303030");
        newCat.setIcalKey("HOLIDAY");
        newCat.setUnknown(false);         
        sess.save(newCat); 

        newCat = new Category();
        newCat.setName("Meeting");
        newCat.setDescription(null);
        newCat.setColour("FF00FF");
        newCat.setBwColour("404040");
        newCat.setIcalKey("MEETING");
        newCat.setUnknown(false); 
        sess.save(newCat);         

        newCat = new Category();
        newCat.setName("Miscellaneous");
        newCat.setDescription(null);
        newCat.setColour("FF00FF");
        newCat.setBwColour("505050");
        newCat.setIcalKey("MISCELLANEOUS");
        newCat.setUnknown(true);                 
        sess.save(newCat);         
            

        newCat = new Category();
        newCat.setName("Personal");
        newCat.setDescription(null);
        newCat.setColour("FF00FF");
        newCat.setBwColour("606060");
        newCat.setIcalKey("PERSONAL");
        newCat.setUnknown(false);                 
        sess.save(newCat);         

        newCat = new Category();
        newCat.setName("Phone Call");
        newCat.setDescription(null);
        newCat.setColour("FF00FF");
        newCat.setBwColour("707070");
        newCat.setIcalKey("PHONE CALL");
        newCat.setUnknown(false);                 
        sess.save(newCat);         
        
        newCat = new Category();
        newCat.setName("Sick Day");
        newCat.setDescription(null);
        newCat.setColour("FF00FF");
        newCat.setBwColour("808080");
        newCat.setIcalKey("SICK DAY");
        newCat.setUnknown(false);                 
        sess.save(newCat);         
        

        newCat = new Category();
        newCat.setName("Special Occassion");
        newCat.setDescription(null);
        newCat.setColour("FF00FF");
        newCat.setBwColour("909090");
        newCat.setIcalKey("SPECIAL OCCASSION");
        newCat.setUnknown(false);                 
        sess.save(newCat);         
        
        newCat = new Category();
        newCat.setName("Travel");
        newCat.setDescription(null);
        newCat.setColour("FF00FF");
        newCat.setBwColour("A0A0A0");
        newCat.setIcalKey("TRAVEL");
        newCat.setUnknown(false);                 
        sess.save(newCat);       
        
        newCat = new Category();
        newCat.setName("Vacation");
        newCat.setDescription(null);
        newCat.setColour("FF00FF");
        newCat.setBwColour("B0B0B0");
        newCat.setIcalKey("VACATION");
        newCat.setUnknown(false);                 
        sess.save(newCat);         
          
        

        InitialDataLoadHoliday.load(sess);            
        InitialDataLoadResource.load(sess);            

      }
            
      tx.commit();
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
      throw e;
    } finally {
      HibernateManager.finallyHandling(sess);
    }  

  
  }

}
