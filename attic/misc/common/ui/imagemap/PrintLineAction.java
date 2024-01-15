package common.ui.imagemap;

import javax.swing.*;
import java.awt.event.*;

public class PrintLineAction
  extends AbstractAction
{
  protected String text;

  public PrintLineAction(String text)
  {
    this.text = text;
  }
  
  public void actionPerformed(ActionEvent event)
  {
    System.out.println(text);
  }
  
  public String toString()
  {
    return text;
  }
}

