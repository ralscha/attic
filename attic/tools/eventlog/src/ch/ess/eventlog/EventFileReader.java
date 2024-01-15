package ch.ess.eventlog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EventFileReader {

  private static Log LOG = LogFactory.getLog(EventFileReader.class);
  
  private List fileList;
  private Set excludeSources;
  private List excludeDescriptionPatterns;

  public void setExcludeSources(Set excludeSources) {
    this.excludeSources = excludeSources;
  }

  public void setExcludeDescriptionPatterns(List excludeDescriptionPatterns) {
    this.excludeDescriptionPatterns = excludeDescriptionPatterns;
  }

  public void setFileList(List fileList) {
    this.fileList = fileList;
  }

  private boolean excludeEvent(String source, String description) {
    if ((excludeSources != null) && (!excludeSources.isEmpty())) {
      if (source != null) {        
        if (excludeSources.contains(source)) {
          return true;
        }
      }
    }
    
    if ((excludeDescriptionPatterns != null) && (!excludeDescriptionPatterns.isEmpty())) {
      if (description != null) {
        for (Iterator it = excludeDescriptionPatterns.iterator(); it.hasNext();) {          
          String pattern = (String)it.next();
          if (description.matches(pattern)) {
            return true;
          }         
        }
      }
    }
    
    return false;
  }
  
  
  public List read() throws IOException {

    if (fileList == null) {
      return null;
    }
    
    List eventList = new ArrayList();

    Pattern pattern = Pattern.compile("(\\d{1,2}/\\d{1,2}/\\d{4}),(\\d{2}:\\d{2}:\\d{2}),(\\d{1,2}),([^,]*),(.*)");

    for (Iterator it = fileList.iterator(); it.hasNext();) {
      String fileName = (String)it.next();
      LOG.info("reading file: " + fileName);

      BufferedReader br = new BufferedReader(new FileReader(fileName));
      try {
        String line;
        while ((line = br.readLine()) != null) {
  
          Matcher matcher = pattern.matcher(line);
          if (matcher.matches()) {
  
            String typ = matcher.group(3);
            if ("1".equals(typ) || "2".equals(typ)) {
  
              String source = matcher.group(4);
              String description = matcher.group(5);
              
              if (!excludeEvent(source, description)) {
                Event event = new Event();
                
                if ("1".equals(typ)) {
                  event.setTyp("ERROR");
                } else {
                  event.setTyp("WARNING");
                }
                
                event.setDate(matcher.group(1));
                event.setTime(matcher.group(2));
                event.setSource(source);
                event.setDescription(description);
                eventList.add(event);
              }
            }
          } else {
            LOG.info("not match: " + line);
          }
        }
      } finally {
        br.close();
      }

    }

    return eventList;
  }

}
