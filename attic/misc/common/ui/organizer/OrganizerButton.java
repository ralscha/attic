package common.ui.organizer;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class OrganizerButton extends JButton
{
  protected String command;

  public OrganizerButton(String name)
  {
    setMargin(new Insets(1, 1, 1, 1));
	 
    String iconFile = "icons/" + name + "Icon.gif";
    URL iconFileURL = getClass().getResource(iconFile);
	 
	 if (iconFileURL != null)
    {
      setIcon(new ImageIcon(iconFileURL));
      setPreferredSize(new Dimension(22, 22));
      setMaximumSize(new Dimension(22, 22));
    }
    else setText(name);
    command = name;
  }
  
  public boolean isDefaultButton()
  {
    return false;
  }
  
  public String getCommand()
  {
    return command;
  }
}


