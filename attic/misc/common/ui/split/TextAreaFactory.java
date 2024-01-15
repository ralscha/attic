package common.ui.split;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class TextAreaFactory
  implements SplitViewFactory
{
  public TextAreaFactory() {}
  
  public JComponent createComponentView(Object obj)
  {
    if (obj == null) return new JTextArea();
    if (!(obj instanceof String))
      throw new IllegalArgumentException(
      "Object must of type String");
    return new JTextArea((String)obj);
  }
  
  public Object getModel(JComponent comp)
  {
    if (!(comp instanceof JTextArea))
      throw new IllegalArgumentException(
        "Component must of type JTextArea");
    JTextArea area = (JTextArea)comp;
    return area.getDocument();
  }
  
  public void setModel(JComponent comp, Object model)
  {
    if (!(comp instanceof JTextArea))
      throw new IllegalArgumentException(
        "Component must of type JTextArea");
    if (!(model instanceof Document))
      throw new IllegalArgumentException(
        "Model must be of type JTextArea");

    JTextArea area = (JTextArea)comp;
    Document doc = (Document)model;
    area.setDocument(doc);
  }
}
