package common.ui.surface;

import java.awt.*;
import java.awt.image.*;

public class SurfaceImage implements Surface
{
  protected BufferedImage image;
  protected int width, height;
  protected Insets insets;

  public SurfaceImage(Image image, Insets insets)
  {
    width = image.getWidth(null);
    height = image.getHeight(null);
    this.image = ImageUtil.toBufferedImage(image);
    this.insets = insets;
  }
    
  protected Image getSubimage(Rectangle rect)
  {
    return image.getSubimage(rect.x, rect.y,
      rect.width, rect.height);
  }
  
  public Insets getInsets()
  {
    return insets;
  }
  
  public Image getNorthWestImage()
  {
    return getSubimage(SurfaceUtil.
      getNorthWestArea(width, height, insets));
  }
  
  public Image getNorthEastImage()
  {
    return getSubimage(SurfaceUtil.
      getNorthEastArea(width, height, insets));
  }
  
  public Image getSouthWestImage()
  {
    return getSubimage(SurfaceUtil.
      getSouthWestArea(width, height, insets));
  }
  
  public Image getSouthEastImage()
  {
  {
    return getSubimage(SurfaceUtil.
      getSouthEastArea(width, height, insets));
  }
  }
  
  public Image getNorthImage()
  {
  {
    return getSubimage(SurfaceUtil.
      getNorthArea(width, height, insets));
  }
  }
  
  public Image getSouthImage()
  {
    return getSubimage(SurfaceUtil.
      getSouthArea(width, height, insets));
  }
  
  public Image getEastImage()
  {
    return getSubimage(SurfaceUtil.
      getEastArea(width, height, insets));
  }
  
  public Image getWestImage()
  {
    return getSubimage(SurfaceUtil.
      getWestArea(width, height, insets));
  }
  
  public Image getFillImage()
  {
    return getSubimage(SurfaceUtil.
      getFillArea(width, height, insets));
  }
}

