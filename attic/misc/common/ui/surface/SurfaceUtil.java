package common.ui.surface;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class SurfaceUtil
{
  public static Rectangle getNorthWestArea(
    int width, int height, Insets insets)
  {
    return new Rectangle(0, 0,
      insets.left, insets.top);
  }
  
  public static Rectangle getNorthEastArea(
    int width, int height, Insets insets)
  {
    return new Rectangle(
      width - insets.right, 0,
      insets.right, insets.top);
  }
  
  public static Rectangle getSouthWestArea(
    int width, int height, Insets insets)
  {
    return new Rectangle(
      0, height - insets.bottom,
      insets.left, insets.bottom);
  }
  
  public static Rectangle getSouthEastArea(
    int width, int height, Insets insets)
  {
    return new Rectangle(
      width - insets.right, 
      height - insets.bottom,
      insets.right, insets.bottom);
  }
  
  public static Rectangle getNorthArea(
    int width, int height, Insets insets)
  {
    return new Rectangle(
      insets.left, 0,
      width - insets.left - insets.right,
      insets.top);
  }
  
  public static Rectangle getSouthArea(
    int width, int height, Insets insets)
  {
    return new Rectangle(
      insets.left,
      height - insets.bottom,
      width - insets.left - insets.right,
      insets.bottom);
  }
  
  public static Rectangle getEastArea(
    int width, int height, Insets insets)
  {
    return new Rectangle(
      width - insets.right,
      insets.top, insets.right,
      height - insets.top - insets.bottom);
  }
  
  public static Rectangle getWestArea(
    int width, int height, Insets insets)
  {
    return new Rectangle(
      0, insets.top, insets.left,
      height - insets.top - insets.bottom);
  }
  
  public static Rectangle getFillArea(
    int width, int height, Insets insets)
  {
    return new Rectangle(
      insets.left, insets.top,
      width - insets.left - insets.right,
      height - insets.top - insets.bottom);
  }
}

