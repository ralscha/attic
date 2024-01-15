package ch.ess.addressbook.db;

import java.util.*;

public class CategoryCount {

  private Map categoryCountMap;
  
  public CategoryCount() {
    categoryCountMap = new HashMap();
  }
  
  
  public void updateCategoryCount(Contact contact) {
    
    dec(contact.getCategory());
    contact.setCategory(null);
    
    Iterator it = contact.getAttributes().entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry element = (Map.Entry)it.next();
      
      
      String value = (String)element.getValue();
      String key   = (String)element.getKey();
    
      if (value != null) {
        if (key.equals(AttributeEnum.LAST_NAME.getName())) {                  
          contact.setCategory(value.substring(0, 1).toLowerCase());
        }        
        contact.getAttributes().put(key, value);     
      } 
    }

              
    if (contact.getCategory() == null) {            
      String val = (String)contact.getAttributes().get(AttributeEnum.FIRST_NAME.getName());
      if (val != null) {
        contact.setCategory(val.substring(0, 1).toLowerCase());
      }
    }    
    
    if (contact.getCategory() == null) {
      String val = (String)contact.getAttributes().get(AttributeEnum.COMPANY_NAME.getName());
      if (val != null) {
        contact.setCategory(val.substring(0, 1).toLowerCase());
      }                        
    }
          
    if (contact.getCategory() == null) {
      contact.setCategory(" ");
    }    
    
    inc(contact.getCategory());  
  }  
  
  public void setCount(String category, int count) {
    if (category != null) {
      categoryCountMap.put(category, new Integer(count));
    }
  }
  
  public void inc(String category) {
    if (category != null) {
      Integer count = (Integer)categoryCountMap.get(category);
      if (count != null) {
        categoryCountMap.put(category, new Integer(count.intValue() + 1));
      } else {
        categoryCountMap.put(category, new Integer(1));
      }
    }
  }
  
  public void dec(String category) {
    if (category != null) {
      Integer count = (Integer)categoryCountMap.get(category);
      if (count != null) {
        int c = count.intValue();
        if (c > 0) {
          c = c - 1;
        } else {
          c = 0;
        }
        
        categoryCountMap.put(category, new Integer(c));
      }  
    }
  }
  
  public Map getCategoryCountMap() {
    return categoryCountMap;
  }
  
  public int getCount(String category) {
    Integer count = (Integer)categoryCountMap.get(category);
    if (count != null) {
      return count.intValue();
    } else {
      return 0;
    }
  }
}
