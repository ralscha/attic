package ch.ess.task.web.task;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.taglib.TagUtils;

import ch.ess.common.web.ListDecorator;

public class TaskDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Task");
  }

  public String getDelete() throws MalformedURLException {
    return getDeleteString("Task", "name");
  }

  public String getCompletePic() throws MalformedURLException {
    
    Integer complete = getIntegerProperty("complete");

    Map params = new HashMap();
    params.put("w", "50");
    params.put("h", "10");  
    params.put("c", complete.toString());
    
       
    String picURL =
       TagUtils.getInstance().computeURL(getPageContext(), null, null, "/completePic.donogz", null, params, null, true);
    
    return "<img src=\""+picURL+"\" alt=\"\" width=\"50\" height=\"10\" border=\"0\">";
    
  }
}

