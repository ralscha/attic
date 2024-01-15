package common.ui.link;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class JLink extends JPanel
  implements ChangeListener
{
  protected CellRendererPane rendererPane =
    new CellRendererPane();
  protected ConnectionRenderer connectionRenderer =
    new DefaultConnectionRenderer();
  protected PageRenderer pageRenderer =
    new DefaultPageRenderer();
  protected PageModel model =
    new DefaultPageModel();

  public JLink()
  {
    setForeground(Color.black);
    setBackground(Color.white);
  }
  
  public PageModel getModel()
  {
    return model;
  }
  
  public void setModel(PageModel model)
  {
    if (this.model != null)
    {
      this.model.removeChangeListener(this);
    }
    this.model = model;
    model.addChangeListener(this);
  }
  
  public void stateChanged(ChangeEvent event)
  {
    repaint();
  }
  
  public void paintComponent(Graphics g)
  {
    int w = getSize().width;
    int h = getSize().height;
    g.setColor(getBackground());
    g.fillRect(0, 0, w, h);
    g.setColor(getForeground());
    int left = w / 6;
    int right = w - left;
    
    int unit = 0;
    int x1 = (w / 2);
    int y1 = (h / 2);
    
    // Inbound pages
    int in = model.getInboundLinkCount();
    unit = h / (in + 1);
    for (int i = 0; i < in; i++)
    {
      int x2 = left;
      int y2 = (i * unit) + unit;
      JComponent connection = connectionRenderer.
        getConnnectionRendererComponent(
          this, x1, y1, x2, y2);
      rendererPane.paintComponent(g,
        connection, this, 0, 0, w, h);
    }
    for (int i = 0; i < in; i++)
    {
      int x2 = left;
      int y2 = (i * unit) + unit;
      JComponent page = pageRenderer.
        getPageRendererComponent(this,
          model.getInboundLink(i), x2, y2);
      rendererPane.paintComponent(g,
        page, this, 0, 0, w, h);
    }
    
    // Outbound pages
    int out = model.getOutboundLinkCount();
    unit = h / (out + 1);
    for (int i = 0; i < out; i++)
    {
      int x2 = right;
      int y2 = (i * unit) + unit;
      JComponent connection = connectionRenderer.
        getConnnectionRendererComponent(
          this, x1, y1, x2, y2);
      rendererPane.paintComponent(g,
        connection, this, 0, 0, w, h);
    }
    for (int i = 0; i < out; i++)
    {
      int x2 = right;
      int y2 = (i * unit) + unit;
      JComponent page = pageRenderer.
        getPageRendererComponent(this, 
          model.getOutboundLink(i), x2, y2);
      rendererPane.paintComponent(g,
        page, this, 0, 0, w, h);
    }
    
    // Center page
    JComponent center = pageRenderer.
      getPageRendererComponent(
        this, model.getThisPage(), x1, y1);
    rendererPane.paintComponent(g,
      center, this, 0, 0, w, h);
  }
}

