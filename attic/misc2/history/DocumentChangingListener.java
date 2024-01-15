

import javax.swing.*;
import javax.swing.text.*;

/**
Interface for an observer to receive notifications of changes to a text document before change.
*/
public interface DocumentChangingListener {

  /**
    insertUpdate is going to occur

    @param offset of change
    @param string to change
    @param attributeset 

    @return string that you desire to change

  */
  public String gointToUpdate(int offs, java.lang.String str, AttributeSet a);

} 