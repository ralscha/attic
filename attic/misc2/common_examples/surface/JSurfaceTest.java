package surface;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import common.ui.surface.*;

public class JSurfaceTest extends JPanel
{
  public JSurfaceTest()
  {
    setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    setLayout(new GridLayout(3, 3, 2, 2));
    add(makeFlowSurface(transparentRadio("Radio")));
    add(makeFlowSurface(transparentCheck("Check")));
    add(makeFlowSurface(transparentButton("Button")));
    add(newJTreeSurface());
    add(newJListSurface());
    add(newJTableSurface());
    add(makeFlowSurface(transparentField("Text Field")));
    add(makeSurface(transparentArea("Text Area")));
    add(makeFlowSurface(transparentSlider()));
  }
  
  protected JScrollPane transparentScrollPane(JComponent child)
  {
    JScrollPane scroll = new JScrollPane(child);
    scroll.getHorizontalScrollBar().setOpaque(false);
    scroll.getVerticalScrollBar().setOpaque(false);
    scroll.getViewport().setOpaque(false);
    scroll.setOpaque(false);
    scroll.setBorder(null);
    return scroll;
  }
  
  protected JRadioButton transparentRadio(String text)
  {
    JRadioButton button = new JRadioButton(text);
    button.setForeground(Color.lightGray);
    button.setOpaque(false);
    return button;
  }

  protected JCheckBox transparentCheck(String text)
  {
    JCheckBox button = new JCheckBox(text);
    button.setForeground(Color.lightGray);
    button.setOpaque(false);
    return button;
  }

  protected JButton transparentButton(String text)
  {
    JButton button = new JButton(text);
    button.setForeground(Color.lightGray);
    button.setOpaque(false);
    return button;
  }

  protected JList transparentList(Object[] items)
  {
    JList list = new JList(items);
    list.setOpaque(false);
    list.setCellRenderer(new TransparentRenderer());
    return list;
  }

  protected JTree transparentTree()
  {
    JTree tree = new JTree();
    tree.setOpaque(false);
    tree.setCellRenderer(new TransparentRenderer());
    return tree;
  }

  protected JTable transparentTable(TableModel model)
  {
    JTable table = new JTable(model);
    table.setOpaque(false);
    table.setDefaultRenderer(Object.class, new TransparentRenderer());
    return table;
  }

  protected JTextField transparentField(String text)
  {
    JTextField field = new JTextField(text);
    field.setBorder(BorderFactory.createLineBorder(Color.black));
    field.setForeground(Color.lightGray);
    field.setOpaque(false);
    return field;
  }

  protected JTextArea transparentArea(String text)
  {
    JTextArea area = new JTextArea(text);
    //area.setBorder(BorderFactory.createLineBorder(Color.black));
    area.setForeground(Color.lightGray);
    area.setOpaque(false);
    return area;
  }

  protected JSlider transparentSlider()
  {
    JSlider slider = new JSlider();
    slider.setForeground(Color.lightGray);
    slider.setOpaque(false);
    return slider;
  }

  protected JComponent newJListSurface()
  {
    JList list = transparentList(new String[]
      {"Claude", "Barb", "Jason", "Crystal", "Amber",
       "Claude", "Barb", "Jason", "Crystal", "Amber"});
    return makeSurface(list);
  }
  
  protected JComponent newJTreeSurface()
  {
    JTree tree = transparentTree();
    return makeSurface(tree);
  }
  
  protected JComponent newJTableSurface()
  {
    TableModel model = new DefaultTableModel(
      new Object[][] {{"1", "A", "One"},
        {"2", "B", "Two"}, {"3", "C", "Three"},
        {"1", "A", "One"}, {"2", "B", "Two"},
        {"3", "C", "Three"}, {"1", "A", "One"},
        {"2", "B", "Two"}, {"3", "C", "Three"}},
      new Object[] {"Num", "Alpha", "Name"});
    JTable table = transparentTable(model);
    return makeSurface(table);
  }
  
  protected JSurface makeSurface(JComponent child)
  {

    Image img = Toolkit.getDefaultToolkit().createImage(getClass().getResource("notebook.gif"));

    JSurface surface = new JSurface(
      img, new Insets(2, 2, 2, 2));
    surface.setLayout(new GridLayout());
    surface.add(transparentScrollPane(child));
    return surface;
  }
  
  protected JSurface makeFlowSurface(JComponent child)
  {
  Image img = Toolkit.getDefaultToolkit().createImage(getClass().getResource("notebook.gif"));

    JSurface surface = new JSurface(
      img, new Insets(2, 2, 2, 2));
    surface.setLayout(new FlowLayout());
    surface.add(transparentScrollPane(child));
    return surface;
  }
  
  public static void main(String[] args)
  {
    common.swing.PlafPanel.setNativeLookAndFeel(true);
    
  
    
    JFrame frame = new JFrame("JSurface Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JSurfaceTest());
    frame.setSize(400, 400);
    frame.show();
  }
}

