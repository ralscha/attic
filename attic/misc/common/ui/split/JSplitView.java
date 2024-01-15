package common.ui.split;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class JSplitView extends JPanel
{
  public JSplitView(String text)
  {
    this(new TextAreaFactory(), text);
  }
  
  public JSplitView(SplitViewFactory factory, Object obj)
  {
    setLayout(new GridLayout());
    JComponent nw = factory.createComponentView(obj);
    JComponent ne = factory.createComponentView(null);
    JComponent sw = factory.createComponentView(null);
    JComponent se = factory.createComponentView(null);
    
    Object model = factory.getModel(nw);
    factory.setModel(ne, model);
    factory.setModel(sw, model);
    factory.setModel(se, model);
    
    SplitPanel panel = new SplitPanel();
    panel.add(SplitViewLayout.NW, new JScrollPane(nw));
    panel.add(SplitViewLayout.NE, new JScrollPane(ne));
    panel.add(SplitViewLayout.SW, new JScrollPane(sw));
    panel.add(SplitViewLayout.SE, new JScrollPane(se));
    add(panel);
  }
}

