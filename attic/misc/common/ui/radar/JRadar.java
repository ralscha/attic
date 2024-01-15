package common.ui.radar;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class JRadar extends JPanel
  implements RadarListener
{
  protected RadarModel model;

  public JRadar(RadarModel model)
  {
    setModel(model);
    int count = model.getCount();
    setLayout(new CircleLayout());
    for (int i = 0; i < count; i++)
    {
      add(new JLabel(model.getLabel(i)));
    }
    add(BorderLayout.CENTER, new RadarEdit(this, count));
  }
  
  public void setModel(RadarModel model)
  {
    if (this.model != null)
      this.model.removeRadarListener(this);
    this.model = model;
    model.addRadarListener(this);
  }
  
  public RadarModel getModel()
  {
    return model;
  }
  
  public void radarChanged(RadarEvent event)
  {
    repaint();
  }

  public void addRadarListener(RadarListener listener)
  {
    model.addRadarListener(listener);
  }

  public void removeRadarListener(RadarListener listener)
  {
    model.removeRadarListener(listener);
  }
}

