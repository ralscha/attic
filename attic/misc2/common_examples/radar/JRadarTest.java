package radar;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import common.ui.radar.*;

public class JRadarTest extends JPanel
  implements RadarListener
{
  protected JLabel label;

  public JRadarTest()
  {
    setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    setLayout(new BorderLayout(8, 8));
    JRadar managed = new JRadar(managedModel());
    managed.addRadarListener(this);
    add(BorderLayout.CENTER, managed);
    JPanel panel = new JPanel(new GridLayout(2, 1));
    JRadar axes = new JRadar(axesModel());
    axes.addRadarListener(this);
    panel.add(axes);
    JRadar compass = new JRadar(compassModel());
    compass.addRadarListener(this);
    panel.add(compass);
    add(BorderLayout.EAST, panel);
    add(BorderLayout.SOUTH, label = new JLabel(" Status..."));
    label.setBorder(BorderFactory.createEtchedBorder());
  }

  protected RadarModel managedModel()
  {
    RadarModel model = new DefaultRadarModel();
    model.addRange("CPU",
      new DefaultBoundedRangeModel(10, 0, 0, 100));
    model.addRange("Input",
      new DefaultBoundedRangeModel(20, 0, 0, 100));
    model.addRange("Output",
      new DefaultBoundedRangeModel(30, 0, 0, 100));
    model.addRange("Read",
      new DefaultBoundedRangeModel(40, 0, 0, 100));
    model.addRange("Write",
      new DefaultBoundedRangeModel(50, 0, 0, 100));
    model.addRange("Queue",
      new DefaultBoundedRangeModel(60, 0, 0, 100));
    model.addRange("Memory",
      new DefaultBoundedRangeModel(70, 0, 0, 100));
    model.addRange("Network",
      new DefaultBoundedRangeModel(80, 0, 0, 100));
    model.addRange("Sockets",
      new DefaultBoundedRangeModel(90, 0, 0, 100));
    return model;
  }	
  
  protected RadarModel compassModel()
  {
    RadarModel model = new DefaultRadarModel();
    model.addRange("North",
      new DefaultBoundedRangeModel(65, 0, 0, 100));
    model.addRange("East",
      new DefaultBoundedRangeModel(35, 0, 0, 100));
    model.addRange("South",
      new DefaultBoundedRangeModel(50, 0, 0, 100));
    model.addRange("West",
      new DefaultBoundedRangeModel(65, 0, 0, 100));
    return model;
  }
  
  protected RadarModel axesModel()
  {
    RadarModel model = new DefaultRadarModel();
    model.addRange("X",
      new DefaultBoundedRangeModel(35, 0, 0, 100));
    model.addRange("Y",
      new DefaultBoundedRangeModel(50, 0, 0, 100));
    model.addRange("Z",
      new DefaultBoundedRangeModel(65, 0, 0, 100));
    return model;
  }

  public void radarChanged(RadarEvent event)
  {
    BoundedRangeModel range = event.getModel();
    label.setText(" " + event.getName() + " changed to " +
      range.getValue() + " (in a range between " +
      range.getMinimum() + " and " + range.getMaximum() + ")");
  }
  
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JRadar Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new GridLayout(1, 1));
    frame.getContentPane().add(new JRadarTest());
    frame.pack();
    frame.show();
  }
}

