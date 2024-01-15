package ch.ess.state.digester;

import java.util.*;

import org.apache.commons.lang.builder.*;

/**
 * @author rh
 * 20.02.2003
 */
public class DigesterEvent {
 
  private List transitionList;
  
  private String name;  
  
  
  public class Transition {
    private String from, to, guard;
    public Transition(String from, String to, String guard) {
      this.from = from;
      this.to = to;
      this.guard = guard;
    }
    public String getFrom() {
      return from;
    }

    public String getGuard() {
      return guard;
    }

    public String getTo() {
      return to;
    }

  }
  
  public DigesterEvent() {    
    this.transitionList = new ArrayList();
  }
    
  
    
  public void addTransition(String from, String to, String guard) {
    transitionList.add(new Transition(from, to, guard));
  }
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
      



  public List getTransitionList() {
    return transitionList;
  }

  public String getName() {
    return name;
  }

 
  public void setName(String name) {
    this.name = name;
  }

}
