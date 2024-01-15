import java.awt.*;
import java.awt.event.*;

//public class ImageButton extends Canvas {
    //Lightweight
  public class ImageButton extends Component {
    private Image img;
    private String name;
    private int  width, height;
    private boolean MouseOn = false;
    private boolean MouseUnpressed = true;
    private Component _component;
    private ActionListener al = null;

  ImageButton(Image img, String Name) {
      this.img = img;
      this.name = Name;
      width   = img.getWidth(this) + 4;
      height  = img.getHeight(this) + 4;
//    setSize(width, height);  // JDK 1.1
//    resize(width, height);  // JDK 1.02

      //Damit wir auch die Mouse-Events erhalten
      enableEvents(AWTEvent.MOUSE_EVENT_MASK);
      enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
  }

  
    //statt setSize
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

   public void paint(Graphics g) {
       //g.setColor( Color.gray );
       if (MouseUnpressed)
           g.drawImage( img, 2, 2, null );
       else
           g.drawImage( img, 4, 4, null );
       if (MouseOn) {
           g.setColor( Color.lightGray );
           g.draw3DRect( 0, 0, width-1, height-1, MouseUnpressed );
           g.draw3DRect( 1, 1, width-3, height-3, MouseUnpressed );
       }
   }


    public void processMouseEvent(MouseEvent e) {
        switch(e.getID()) {
            case MouseEvent.MOUSE_PRESSED :
                _component = e.getComponent();
                MouseUnpressed = false;
                repaint();
                break;
            case MouseEvent.MOUSE_RELEASED :
                _component = null;
                
                if (MouseUnpressed == false) {
                    if (al != null) {
                        al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, name, e.getModifiers()));
                    }
                }
                MouseUnpressed = true;
                repaint();
                break;
                
            case MouseEvent.MOUSE_ENTERED :            
                MouseOn = true;
                repaint();
                break;
            case MouseEvent.MOUSE_EXITED :    
                MouseOn = false;
                repaint();
                break;
        }
        super.processMouseEvent(e);
    }
    
    public void processMouseMotionEvent(MouseEvent e) {
        switch(e.getID()) {
            case MouseEvent.MOUSE_DRAGGED :
                if ((_component == e.getComponent()) && (!contains(e.getX(), e.getY())) && (MouseOn == true)) {
                    MouseOn = false;
                    MouseUnpressed = true;
                    repaint();
                } 
                else if ((_component == e.getComponent()) && (contains(e.getX(), e.getY())) && (MouseOn == false)) {
                    MouseOn = true;
                    MouseUnpressed = false;
                    repaint();
                }
                
                break;                
        }        
        super.processMouseMotionEvent(e);    
    }
      
  public void addActionListener(ActionListener actionListener) {
    al = AWTEventMulticaster.add(al, actionListener);
  }
  
  public void removeActionListener(ActionListener actionListener) {
    al = AWTEventMulticaster.remove(al, actionListener);    
  }

}


