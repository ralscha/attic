package common.ui.imagemap;

import java.awt.*;
import javax.swing.*;

public interface ImageMap
{
  public void setDefaultAction(Action action);
  public void setShapeAction(Shape shape, Action action);
  public void removeShape(Shape shape);
  public Action getAction(int x, int y);
  public Shape getShape(int x, int y);
  public Shape[] getShapeList();
}

