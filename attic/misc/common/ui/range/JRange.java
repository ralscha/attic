package common.ui.range;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class JRange extends JPanel
  implements ChangeListener, KeyListener,
    MouseListener, MouseMotionListener,
    FocusListener
{
  public static final Cursor DEFAULT_CURSOR =
    Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
  public static final Cursor ADJUST_CURSOR =
    Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
  public static final Cursor DRAG_CURSOR =
    Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);

  protected int value, anchor;
  protected boolean drag = false;
  protected boolean left = false;
  protected boolean right = false;
  protected boolean editable = true;
  protected boolean hasFocus = false;
  
  protected BoundedRangeModel model;
  protected RangeRenderer renderer = new DefaultRangeRenderer();
  protected RangeEditor editor = new DefaultRangeEditor();
  protected CellRendererPane rendererPane = 
    new CellRendererPane();
  
  public JRange(int val, int len, int min, int max)
  {
    setBackground(Color.white);
    setPreferredSize(new Dimension(400, 16));
    setModel(new DefaultBoundedRangeModel(val, len, min, max));
    setEditable(true);
  }
  
  protected boolean isEditable()
  {
    return editable;
  }
  
  public void setEditable(boolean editable)
  {
    this.editable = editable;
    if (editable)
    {
      addFocusListener(this);
      addMouseMotionListener(this);
      addMouseListener(this);
      addKeyListener(this);
    }
    else
    {
      removeFocusListener(this);
      removeMouseMotionListener(this);
      removeMouseListener(this);
      removeKeyListener(this);
    }
  }
  
  public RangeRenderer getRenderer()
  {
    return renderer;
  }
  
  public void setRenderer(RangeRenderer renderer)
  {
    this.renderer = renderer;
  }
  
  public RangeEditor getEditor()
  {
    return editor;
  }
  
  public void setEditor(RangeEditor editor)
  {
    this.editor = editor;
  }
  
  public void setModel(BoundedRangeModel model)
  {
    if (this.model != null)
    {
      this.model.removeChangeListener(this);
    }
    this.model = model;
    model.addChangeListener(this);
  }
  
  public BoundedRangeModel getModel()
  {
    return model;
  }
  
  public void paintComponent(Graphics g)
  {
    Insets insets = getInsets();
    int x = insets.left;
    int y = insets.top;
    int w = getSize().width;
    int h = getSize().height;
    w -= (insets.left + insets.right);
    h -= (insets.top + insets.bottom);
    JComponent rendererComponent =
      renderer.getRangeRendererComponent(this, model, hasFocus);
    rendererPane.paintComponent(g, rendererComponent, this, x, y, w, h);
    if (editable)
    {
      JComponent editorComponent =
        editor.getRangeEditorComponent(this, model, hasFocus);
      rendererPane.paintComponent(g, editorComponent, this, x, y, w, h);
    }
  }

  public void stateChanged(ChangeEvent event)
  {
    repaint();
  }
  
  public boolean isFocusTraversable()
  {
    return editable;
  }
  
  public void focusGained(FocusEvent event)
  {
    hasFocus = true;
    repaint();
  }
  
  public void focusLost(FocusEvent event)
  {
    hasFocus = false;
    repaint();
  }
  
  public void keyTyped(KeyEvent event) {}
  public void keyReleased(KeyEvent event) {}
  public void keyPressed(KeyEvent event)
  {
    int code = event.getKeyCode();
    if (event.isControlDown())
    {
      int val = model.getValue();
      if (code == KeyEvent.VK_RIGHT)
      {
        BoundedRangeUtil.setValue(model, val + 1);
      }
      if (code == KeyEvent.VK_LEFT)
      {
        BoundedRangeUtil.setValue(model, val - 1);
      }
    }
    else
    {
      int len = model.getExtent();
      if (code == KeyEvent.VK_RIGHT)
      {
        BoundedRangeUtil.setExtent(model, len + 1);
      }
      if (code == KeyEvent.VK_LEFT)
      {
        BoundedRangeUtil.setExtent(model, len - 1);
      }
    }
  }
  
  public void mouseEntered(MouseEvent event) {}
  
  public void mouseExited(MouseEvent event) {}
  {
    setCursor(DEFAULT_CURSOR);
  }
  
  public void mousePressed(MouseEvent event)
  {
    requestFocus();
    Insets insets = getInsets();
    int w = getSize().width;
    w -= (insets.left + insets.right);
    int lft = BoundedRangeUtil.getScaledValuePos(model, w);
    int rgt = BoundedRangeUtil.getScaledExtentPos(model, w);
    int x = event.getX() - insets.left;
    int ww = (editor == null) ? 0 : editor.getWidth();
    if (x > lft - ww && x < lft + ww)
    {
      left = true;
    }
    if (x > rgt - ww && x < rgt + ww)
    {
      right = true;
    }
    if (x > lft + ww && x < rgt - ww)
    {
      drag = true;
      anchor = event.getX() - insets.left;
      value = model.getValue();
    }
  }
  
  public void mouseReleased(MouseEvent event)
  {
    drag = false;
    left = false;
    right = false;
    setCursor(DEFAULT_CURSOR);
  }
  
  public void mouseClicked(MouseEvent event) {}
  
  public void mouseMoved(MouseEvent event)
  {
    Insets insets = getInsets();
    int w = getSize().width;
    w -= (insets.left + insets.right);
    int lft = BoundedRangeUtil.getScaledValuePos(model, w);
    int rgt = BoundedRangeUtil.getScaledExtentPos(model, w);
    int x = event.getX() - insets.left;
    int ww = (editor == null) ? 0 : editor.getWidth();
    if ((x > lft - ww && x < lft + ww) ||
        (x > rgt - ww && x < rgt + ww))
    {
      setCursor(ADJUST_CURSOR);
    }
    else if (x > lft + ww && x < rgt - ww)
    {
      setCursor(DRAG_CURSOR);
    }
    else
    {
      setCursor(DEFAULT_CURSOR);
    }
  }
  
  public void mouseDragged(MouseEvent event)
  {
    Insets insets = getInsets();
    int w = getSize().width;
    w -= (insets.left + insets.right);
    int val = model.getValue();
    int len = model.getExtent();
    double range = BoundedRangeUtil.getRange(model);
    double unit = (double)w / range;
    int x = event.getX() - insets.left;
    int pos = (int)((double)x / unit);
    if (drag)
    {
      int diff = (int)((double)(x - anchor) / unit);
      BoundedRangeUtil.setValue(model, value + diff);
    }
    if (left)
    {
      BoundedRangeUtil.setValue(model, pos);
      pos = model.getValue(); // Clipped
      BoundedRangeUtil.setExtent(model, len + (val - pos));
    }
    if (right)
    {
      BoundedRangeUtil.setExtent(model, pos - val);
    }
  }
}

