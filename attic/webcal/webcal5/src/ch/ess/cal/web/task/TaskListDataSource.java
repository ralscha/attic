package ch.ess.cal.web.task;

import java.net.URL;
import java.util.Collection;

import net.sf.jasperreports.engine.JRField;
import ch.ess.base.service.JRPropertyDataSource;

public class TaskListDataSource extends JRPropertyDataSource {

  private URL completeImageURL;
  
  @SuppressWarnings("unchecked")
  public TaskListDataSource(final Collection collection, final URL completeImageURL) {
    super(collection);
    this.completeImageURL = completeImageURL;
  }


  @Override
  public Object getFieldValue(JRField field) {    
    if ("completeImage".equals(field.getName())) {
      try {
        Integer checkState = (Integer)getCurrentRowValue("checkState");
        
        if (checkState == 1) {
          return completeImageURL;
        } 
        return null;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      
    } 
    
    return super.getFieldValue(field);
  }

}
