package common.ui.surface;

import java.awt.*;
import javax.swing.*;

public interface Surface
{
  public Insets getInsets();
  public Image getNorthWestImage();
  public Image getNorthEastImage();
  public Image getSouthWestImage();
  public Image getSouthEastImage();
  public Image getNorthImage();
  public Image getSouthImage();
  public Image getEastImage();
  public Image getWestImage();
  public Image getFillImage();
}

