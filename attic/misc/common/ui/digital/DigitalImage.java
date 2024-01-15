package common.ui.digital;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.event.*;

public class DigitalImage
  extends BufferedImage
  implements DigitalModel
{
  protected ArrayList listeners = new ArrayList();
  
  public DigitalImage(Image image)
  {
    super(
      image.getWidth(null),
      image.getHeight(null),
      TYPE_INT_ARGB);
    Graphics g = getGraphics();
    g.drawImage(image, 0, 0, null);
  }
  
  public DigitalImage(
    int w, int h, int type)
  {
    super(w, h, type);
  }
    
  public void setRGB(int x, int y, int rgb)
  {
    super.setRGB(x, y, rgb);
    fireChangeEvent();
  }
  
  public void addChangeListener(ChangeListener listener)
  {
    listeners.add(listener);
  }
  
  public void removeChangeListener(ChangeListener listener)
  {
    listeners.remove(listener);
  }
  
  protected void fireChangeEvent()
  {
    ArrayList list = (ArrayList)listeners.clone();
    ChangeEvent event = new ChangeEvent(this);
    for (int i = 0; i < list.size(); i++)
    {
      ChangeListener listener = (ChangeListener)list.get(i);
      listener.stateChanged(event);
    }
  }
}

