package common.ui.bulletlist;

import java.awt.*;
import javax.swing.*;

public abstract class BasicListEntry extends JPanel
  implements SwingConstants
{
  protected int position, width;
  protected JComponent bullet, label, content;
  protected JPanel westPanel, eastPanel;

  public BasicListEntry() {}

  public BasicListEntry(
    JComponent bullet, JComponent label, JComponent content,
    int position, int width)
  {	
    initListEntry(bullet, label, content, position, width);
  }
  
  protected static JComponent northPanel(JComponent component)
  {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(BorderLayout.NORTH, component);
    return panel;
  }
	
  protected void initListEntry(
    JComponent bullet, JComponent label, JComponent content,
    int position, int width)
  {	
    this.bullet = bullet;
    this.label = label;
    this.content = content;
    this.position = position;
    this.width = width;
  
    setLayout(new BorderLayout());
    add(BorderLayout.WEST, westPanel =
      new JPanel(new BorderLayout()));
    add(BorderLayout.CENTER, eastPanel =
      new JPanel(new BorderLayout(4, 4)));
    
    bullet.setPreferredSize(new Dimension(width, 
      bullet.getPreferredSize().height));

    setBulletComponent(bullet);
    setLabelComponent(label);
    setContentComponent(content);
  }

  public void setComponentEnabled(boolean state)
  {
    setComponentEnabled(content, state);
  }
  
  public void setComponentEnabled(Component component, boolean state)
  {
    if (component == null) return;
    component.setEnabled(state);
    if (component instanceof Container)
    {
      Container container = (Container)component;
      int count = container.getComponentCount();
      for (int i = 0; i < count; i++)
      {
        setComponentEnabled(container.getComponent(i), state);
      }
    }
  }

  public void setBulletComponent(JComponent bullet)
  {
    bullet.setPreferredSize(new Dimension(
      width, bullet.getPreferredSize().height));
    westPanel.add(BorderLayout.NORTH, bullet);
    revalidate();
  }

  public void setLabelComponent(JComponent label)
  {
    eastPanel.add(position == JBulletList.CONTENT_BELOW ?
      BorderLayout.NORTH : BorderLayout.WEST, label);
    revalidate();
  }

  public void setContentComponent(JComponent content)
  {
    if (content == null) return;
    eastPanel.add(BorderLayout.CENTER, content);
    revalidate();
  }
}

