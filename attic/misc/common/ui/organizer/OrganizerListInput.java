package common.ui.organizer;

import java.awt.*;
import javax.swing.*;

public class OrganizerListInput
  implements OrganizerInput
{
  public Object[] getInputData(Component parent)
  {
    String text = JOptionPane.showInputDialog(parent,
      "Please enter your data:", "Organizer Input",
      JOptionPane.QUESTION_MESSAGE);
    return new Object[] {text};
  }
}

