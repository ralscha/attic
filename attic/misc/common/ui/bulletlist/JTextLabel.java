package common.ui.bulletlist;

import javax.swing.*;

public class JTextLabel extends JTextArea
{
  protected void initBackgroundColor()
  {
    String propertyName = "Label.background";
    if (UIManager.get(propertyName) != null)
    {
      setBackground(UIManager.getColor(propertyName));
    }
  }
  
  protected void initForegroundColor()
  {
    String propertyName = "Label.foreground";
    if (UIManager.get(propertyName) != null)
    {
      setForeground(UIManager.getColor(propertyName));
    }
  }
  
  protected void initLabelFont()
  {
    String propertyName = "Label.font";
    if (UIManager.get(propertyName) != null)
    {
      setFont(UIManager.getFont(propertyName));
    }
  }
  
  public JTextLabel(String text)
  {
    super(text);
    setBorder(null);
    setWrapStyleWord(true);
    setEditable(false);
    setLineWrap(true);
    initBackgroundColor();
    initForegroundColor();
    initLabelFont();
  }

  public boolean isFocusTraversable()
  {
    return false;
  }
}


