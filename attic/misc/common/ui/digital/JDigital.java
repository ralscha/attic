package common.ui.digital;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class JDigital extends JPanel
  implements ChangeListener
{
  public static Insets DEFAULT_OUTLINE =
    new Insets(1, 1, 1, 1);
  public static Dimension DEFAULT_CELLSIZE =
    new Dimension(8, 8);
  
  protected CellRendererPane cellRendererPane =
    new CellRendererPane();
  protected DigitalRenderer renderer;
  protected DigitalModel model;
  protected Dimension cellSize;
  protected Insets outline;

  public JDigital()
  {
    this(PatternImage.DIGIT[0],
      new LEDRenderer(LEDRenderer.SQUARE),
      DEFAULT_OUTLINE);
  }
  
  public JDigital(DigitalModel model)
  {
    this(model,
      new LEDRenderer(LEDRenderer.SQUARE),
      DEFAULT_OUTLINE);
  }
  
  public JDigital(DigitalModel model,
    DigitalRenderer renderer)
  {
    this(model, renderer, DEFAULT_OUTLINE);
  }
  
  public JDigital(DigitalModel model,
    DigitalRenderer renderer, Insets outline)
  {
    setModel(model);
    setRenderer(renderer);
    setOutline(outline);
    setBackground(Color.black);
    setForeground(Color.green);
    setCellSize(DEFAULT_CELLSIZE);
    setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
  }
  
  public DigitalModel getModel()
  {
    return model;
  }
  
  public void setModel(DigitalModel model)
  {
    if (model != null)
    {
      model.removeChangeListener(this);
    }
    model.addChangeListener(this);
    this.model = model;
    updatePreferredSize();
  }
  
  public DigitalRenderer getRenderer()
  {
    return renderer;
  }
  
  public void setRenderer(DigitalRenderer renderer)
  {
    this.renderer = renderer;
  }
  
  public Insets getOutline()
  {
    return outline;
  }
  
  public void setOutline(Insets outline)
  {
    this.outline = outline;
  }
  
  public void setBorder(Border border)
  {
    super.setBorder(border);
    updatePreferredSize();
  }
  
  public Dimension getCellSize()
  {
    return cellSize;
  }
  
  public void setCellSize(Dimension cellSize)
  {
    this.cellSize = cellSize;
    updatePreferredSize();
  }
  
  protected void updatePreferredSize()
  {
    Insets insets = getInsets();
    if (model != null && outline != null)
    {
      int horz = outline.left + outline.right;
      int vert = outline.top + outline.bottom;
      int w = (horz + model.getWidth()) *
         cellSize.width + 
        (insets.left + insets.right);
      int h = (vert + model.getHeight()) *
         cellSize.height +
        (insets.top + insets.bottom);
      setPreferredSize(new Dimension(w, h));
    }
  }

  public void paintComponent(Graphics gc)
  {
    int w = getSize().width;
    int h = getSize().height;
    Graphics2D g = (Graphics2D)gc;
    g.setColor(getBackground());
    g.fillRect(0, 0, w, h);
    Insets insets = getInsets();
    w -= (insets.left + insets.right);
    h -= (insets.top + insets.bottom);
    int horz = outline.left + outline.right;
    int vert = outline.top + outline.bottom;
    int width = model.getWidth() + horz;
    int height = model.getHeight() + vert;
    int ww = w / width;
    int hh = h / height;
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        int xx = x * ww + insets.left;
        int yy = y * hh + insets.top;
        
        int color = 0;
        if (x >= outline.left && x < width - outline.right &&
            y >= outline.top && y < height - outline.bottom)
        {
          color = model.getRGB(
            x - outline.left, y - outline.top);
        }
        JComponent rendererComponent =
          renderer.getDigitalRendererComponent(this, color);
        cellRendererPane.paintComponent(
          g, rendererComponent, this, xx, yy, ww, hh);
      }
    }
  }

  public void stateChanged(ChangeEvent event)
  {
    repaint();
  }
}

