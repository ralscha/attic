package ch.ess.cal.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import ch.ess.cal.db.Resource;
import ch.ess.cal.db.ResourceGroup;




public class InitialDataLoadResource {
  
  public static void load(Session sess) throws HibernateException {
    List l = sess.find("from User as u where u.name = 'admin'");
    Set dep = new HashSet(l);


    ResourceGroup group = new ResourceGroup();
    group.setUsers(dep);
    group.setDescription("Cars");
    group.setName("Cars");
    sess.save(group);
            
    Resource resource = new Resource();
    resource.setName("Car 1");  
    resource.setResourceGroup(group);
    sess.save(resource);    
  
    
    
        
    resource = new Resource();
    resource.setName("Car 2");    
    resource.setResourceGroup(group);
    sess.save(resource);    

    

        
    resource = new Resource();
    resource.setName("Car 3");    
    resource.setResourceGroup(group);
    sess.save(resource);    

    

        
    resource = new Resource();
    resource.setName("Car 4");    
    resource.setResourceGroup(group);
    sess.save(resource);
    

        
    resource = new Resource();
    resource.setName("Car 5");    
    resource.setResourceGroup(group);
    sess.save(resource);
    

        
    resource = new Resource();
    resource.setName("Car 6");    
    resource.setResourceGroup(group);
    sess.save(resource);
    
    
        
    
 
    l = sess.find("from User as u where u.name = 'user'");
    dep = new HashSet(l);
 
    group = new ResourceGroup();
    group.setUsers(dep);
    group.setDescription("Rooms");
    group.setName("Rooms");
    sess.save(group);
    
    
        
    resource = new Resource();
    resource.setName("Room 1");    
    resource.setResourceGroup(group);
    sess.save(resource);
    
    
        
    resource = new Resource();
    resource.setName("Room 2");    
    resource.setResourceGroup(group);
    sess.save(resource);
    

        
    resource = new Resource();
    resource.setName("Room 3");    
    resource.setResourceGroup(group);
    sess.save(resource);
    

        
    resource = new Resource();
    resource.setName("Room 4");    
    resource.setResourceGroup(group);
    sess.save(resource);
    

        
    resource = new Resource();
    resource.setName("Room 5");    
    resource.setResourceGroup(group);
    sess.save(resource);
    
    
        

    l = sess.find("from Department as dep where dep.name = 'Office 1'");
    dep = new HashSet(l);

    group = new ResourceGroup();
    group.setDepartments(dep);
    group.setDescription("Taxis");
    group.setName("Taxis");
    sess.save(group);
    
    
        
    resource = new Resource();
    resource.setName("Taxi 1"); 
    resource.setResourceGroup(group);   
    sess.save(resource);
        
    
        
    resource = new Resource();
    resource.setName("Taxi 2");
    resource.setResourceGroup(group);
    sess.save(resource);
    

        
    resource = new Resource();
    resource.setName("Taxi 3");
    resource.setResourceGroup(group);
    sess.save(resource);
    

        
    resource = new Resource();
    resource.setName("Taxi 4");
    resource.setResourceGroup(group);
    sess.save(resource);
    

        
    resource = new Resource();
    resource.setName("Taxi 5");
    resource.setResourceGroup(group);
    sess.save(resource);
    
    
  }

}
