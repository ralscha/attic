import java.awt.*;
import java.awt.event.*;

public class ImageButton102 extends Canvas {
    private Image img;
    private String name;
    private int  width, height;
    private boolean MouseOn = false;
    private boolean MouseUnpressed = true;
    private ActionListener al = null;

  public ImageButton102(Image img, String Name) {
      this.img = img;
      this.name = Name;
      width   = img.getWidth(this) + 4;
      height  = img.getHeight(this) + 4;
//    setSize(width, height);  // JDK 1.1
      resize(width, height);  // JDK 1.02
  }

   public void paint( Graphics g ) {
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


  public boolean mouseDown(Event evt, int x, int y) {
      MouseUnpressed = false;
      repaint();
      return true;
  }
  
  public boolean mouseUp(Event evt, int x, int y) {
      MouseUnpressed = true;
      repaint();
      if (al != null) {
        al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, this.name));
      }
      
      //Event newEvt = new Event(this,Event.ACTION_EVENT,this.name);
      //deliverEvent(newEvt);
      return true;
  }

  public boolean mouseEnter(Event evt, int x, int y) {
      MouseOn = true;
      repaint();
      return true;
  }

  public boolean mouseExit(Event evt, int x, int y) {
      MouseOn = false;
      repaint();
      return true;
  }
  
  
  public void addActionListener(ActionListener actionListener) {
    al = AWTEventMulticaster.add(al, actionListener);
  }
  
  public void removeActionListener(ActionListener actionListener) {
    al = AWTEventMulticaster.remove(al, actionListener);    
  }

}
