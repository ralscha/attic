package ch.ess.state;

import org.apache.commons.lang.exception.*;

public class StateMachineException extends NestableException {


  public StateMachineException(Throwable root) {
    super(root);
  }
  
  
  public StateMachineException(String string, Throwable root) {
    super(string, root);
  }
  
  public StateMachineException(String s) {
    super(s);
  }

}
