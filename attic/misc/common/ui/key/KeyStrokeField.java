package common.ui.key;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KeyStrokeField extends JTextField
  implements KeyListener
{
  protected KeyStroke keyStroke;
  
  public KeyStrokeField()
  {
    // No cut/paste inheritance.
    getInputMap().setParent(null);
    addKeyListener(this);
  }

  public static String formatKeyStroke(KeyStroke keyStroke)
  {
    if (keyStroke == null) return "";
    int keyCode = keyStroke.getKeyCode();
    int modifiers = keyStroke.getModifiers();
    if (keyCode == KeyEvent.VK_SHIFT) keyCode = 0;
    if (keyCode == KeyEvent.VK_CONTROL) keyCode = 0;
    if (keyCode == KeyEvent.VK_ALT) keyCode = 0;
    String key = keyCode == 0 ? "" : KeyEvent.getKeyText(keyCode);
    String mods = KeyEvent.getKeyModifiersText(modifiers);
    if (key.equalsIgnoreCase(mods)) mods = "";
    if (keyCode == 0) mods = "";
    if (mods.length() > 0) mods += "+";
    return mods + key;
  }
  
  public KeyStroke getKeyStroke()
  {
    return keyStroke;
  }
  
  public void setKeyStroke(KeyStroke keyStroke)
  {
    this.keyStroke = keyStroke;
    setText(formatKeyStroke(keyStroke));
  }

  public void keyTyped(KeyEvent event) {}

  public void keyPressed(KeyEvent event)
  {
    keyStroke = KeyStroke.getKeyStrokeForEvent(event);
    setText("");
  }
  
  public void keyReleased(KeyEvent event)
  {
    setText(formatKeyStroke(keyStroke));
  }
}

