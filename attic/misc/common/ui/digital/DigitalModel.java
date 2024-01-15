package common.ui.digital;

import javax.swing.event.*;

public interface DigitalModel
{
  public int getWidth();
  public int getHeight();
  public int getRGB(int x, int y);
  public void setRGB(int x, int y, int rgb);
  public void addChangeListener(ChangeListener listener);
  public void removeChangeListener(ChangeListener listener);
}

