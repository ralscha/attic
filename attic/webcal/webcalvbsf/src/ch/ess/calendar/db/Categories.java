package ch.ess.calendar.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class Categories.
 */
public class Categories {

    /* attributes */
    private Long categoryid;
    private String description;
    private String color;
    private boolean limited;
    
    /** Creates a new Categories */    
    public Categories() {
    }
    
    
    /** Accessor for attribute categoryid */
    public Long getCategoryid() {
        return categoryid;
    }
    
    /** Accessor for attribute description */
    public String getDescription() {
        return description;
    }
    
    /** Accessor for attribute color */
    public String getColor() {
        return color;
    }
    
    /** Mutator for attribute categoryid */
    public void setCategoryid(Long newCategoryid) {
        categoryid = newCategoryid;
    }
    
    /** Mutator for attribute description */
    public void setDescription(String newDescription) {
        description = newDescription;
    
      if ( (description != null) && (description.toLowerCase().startsWith("privat")) ) {
        limited = true;
      }
    }
        
    /** Mutator for attribute color */
    public void setColor(String newColor) {
        color = newColor;
    }
    
    public boolean isLimited() {
      return limited;
    }
  
    public void setLimited(boolean limited) {
      this.limited = limited;
    }    
    
    
    
}
