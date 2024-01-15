package common.ui.scroller;


import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class JTabPane extends JPanel
  implements ChangeListener
{
  protected JTabBar tab;
  protected TabLayout layout;
  protected SingleSelectionModel model =
    new DefaultSingleSelectionModel();

  public JTabPane()
  {
    tab = new JTabBar();
    setLayout(layout = new TabLayout());
    model.addChangeListener(this);
    tab.setModel(model);
  }
  
  public JTabBar getTabBar()
  {
    return tab;
  }
  
  public Component add(Component component)
  {
    add(component, component.toString());
    return component;
  }
  
  public void addTab(String title, Component component)
  {
    add(component, title);
    tab.addTab(title);
  }
  
  public void stateChanged(ChangeEvent event)
  {
    int selected = model.getSelectedIndex();
    int count = getComponentCount();
    if (selected <= count)
      layout.show(this, selected);
  }
}

