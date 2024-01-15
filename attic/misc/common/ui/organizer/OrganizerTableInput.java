package common.ui.organizer;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class OrganizerTableInput
  implements OrganizerInput
{
  public Object[] getInputData(Component parent)
  {
    //boolean result = JOptionPane.showConfirmDialog(
    //	parent, "Insert a Table Row?") == JOptionPane.YES_OPTION;
    //return result ? new Object[] {new Vector()} : null;
    return new Object[] {new Vector()};
  }
}

