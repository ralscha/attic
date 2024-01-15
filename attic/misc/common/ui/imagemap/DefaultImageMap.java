package common.ui.imagemap;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class DefaultImageMap
  implements ImageMap
{
  protected Action defaultAction;
  protected HashMap map = new HashMap();

  public void setDefaultAction(Action action)
  {
    defaultAction = action;
  }
  
  public void setShapeAction(Shape shape, Action action)
  {
    map.put(shape, action);
  }
  
  public void removeShape(Shape shape)
  {
    map.remove(shape);
  }
  
  public Action getAction(int x, int y)
  {
    Iterator iterator = map.keySet().iterator();
    while(iterator.hasNext())
    {
      Shape shape = (Shape)iterator.next();
      if (shape.contains(x, y))
        return (Action)map.get(shape);
    }
    return defaultAction;
  }

  public Shape getShape(int x, int y)
  {
    Iterator iterator = map.keySet().iterator();
    while(iterator.hasNext())
    {
      Shape shape = (Shape)iterator.next();
      if (shape.contains(x, y))
        return shape;
    }
    return null;
  }
  
  public Shape[] getShapeList()
  {
    Shape[] list = new Shape[map.size()];
    Iterator iterator = map.keySet().iterator();
    int index = 0;
    while(iterator.hasNext())
    {
      list[index] = (Shape)iterator.next();
      index++;
    }
    return list;
  }
}

