package common.ui.desktop;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

public class JDesktopFrame
  extends JInternalFrame
  implements DesktopConstants
{
  public JDesktopFrame(String title)
  {
    this(title, false, false, false, false,
      false, false);
  }
  
  public JDesktopFrame(String title, boolean resizable)
  {
    this(title, resizable, false, false, false,
      false, false);
  }
  
  public JDesktopFrame(String title,
    boolean resizable, boolean closable)
  {
    this(title, resizable, closable, false, false, 
      false, false);
  }
  
  public JDesktopFrame(String title,
    boolean resizable,
    boolean closable,
    boolean maximizable)
  {
    this(title, resizable, closable, maximizable, false, 
      false, false);
  }
  
  public JDesktopFrame(String title,
    boolean resizable,
    boolean closable,
    boolean maximizable,
    boolean iconifiable)
  {
    this(title, resizable, closable, maximizable, 
      iconifiable, false, false);
  }
  
  public JDesktopFrame(String title,
    boolean resizable,
    boolean closable,
    boolean maximizable,
    boolean iconifiable,
    boolean constrained)
  {
    this(title, resizable, closable, 
      maximizable, iconifiable,
      constrained, false);
  }
  
  public JDesktopFrame(String title,
    boolean resizable,
    boolean closable,
    boolean maximizable,
    boolean iconifiable,
    boolean constrained,
    boolean rollable)
  {
    super(title, resizable, closable, maximizable, iconifiable);
    setConstrained(constrained);
    setRollable(rollable);
  }
  
  protected void setBooleanProperty(String name, boolean value)
  {
    putClientProperty(name, value ? Boolean.TRUE : Boolean.FALSE);
  }
  
  protected boolean getBooleanProperty(String name)
  {
    Object obj = getClientProperty(name);
    if (obj == null) return false;
    if (obj instanceof Boolean)
      return ((Boolean)obj).booleanValue();
    else return false;
  }
  
  public void setConstrained(boolean constrained)
  {
    setBooleanProperty(CONSTRAINED, constrained);
  }
  
  public boolean isConstrained()
  {
    return getBooleanProperty(CONSTRAINED);
  }

  public void setRollable(boolean rollable)
  {
    setBooleanProperty(ROLLABLE, rollable);
  }
  
  public boolean isRollable()
  {
    return getBooleanProperty(ROLLABLE);
  }
}

