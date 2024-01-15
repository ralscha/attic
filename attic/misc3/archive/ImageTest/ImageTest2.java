import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


public class ImageTest2 extends Frame {

    public ImageTest2() {
        super("Scroller Example");

        add("Center", new RoundButton("Ich bin rund"));
        pack();

    }
   // No more handleEvent method needed to implement scrolling!

    public static void main(String args[]) {
        ImageTest2 test = new ImageTest2();
        test.show();
    }
}

/**
 * RoundButton - a class that produces a lightweight button.
 */
class RoundButton extends Component {

  String label;                      // The Button's text
  protected boolean pressed = false; // true if the button is detented.

  /**
   * Constructs a RoundButton with the specified label.
   * @param label the label of the button
   */
  public RoundButton(String label) {
      this.label = label;
      enableEvents(AWTEvent.MOUSE_EVENT_MASK);
  }

  /**
   * paints the RoundButton
   */
  public void paint(Graphics g) {
      int s = Math.min(getSize().width - 1, getSize().height - 1);

      // paint the interior of the button
      if(pressed) {
          g.setColor(getBackground().darker().darker());
      } else {
          g.setColor(getBackground());
      }
      g.fillArc(0, 0, s, s, 0, 360);

      // draw the perimeter of the button
      g.setColor(getBackground().darker().darker().darker());
      g.drawArc(0, 0, s, s, 0, 360);

      // draw the label centered in the button
      Font f = getFont();
      if(f != null) {
          FontMetrics fm = getFontMetrics(getFont());
          g.setColor(getForeground());
          g.drawString(label,
                       s/2 - fm.stringWidth(label)/2,
                       s/2 + fm.getMaxDescent());
      }
  }

  /**
   * The preferred size of the button.
   */
  public Dimension getPreferredSize() {
      Font f = getFont();
      if(f != null) {
          FontMetrics fm = getFontMetrics(getFont());
          int max = Math.max(fm.stringWidth(label) + 40, fm.getHeight() + 40);
          return new Dimension(max, max);
      } else {
          return new Dimension(100, 100);
      }
  }

  /**
   * The minimum size of the button.
   */
  public Dimension getMinimumSize() {
      return new Dimension(100, 100);
  }

   /**
    * Paints the button and distribute an action event to all listeners.
    */
   public void processMouseEvent(MouseEvent e) {
       Graphics g;
       switch(e.getID()) {
          case MouseEvent.MOUSE_PRESSED:
            // render myself inverted....
            pressed = true;

            // Repaint might flicker a bit. To avoid this, you can use
            // double buffering (see the Gauge example).
            repaint();
            break;
          case MouseEvent.MOUSE_RELEASED:
            // render myself normal again
            if(pressed == true) {
                pressed = false;
                // Repaint might flicker a bit. To avoid this, you can use
                // double buffering (see the Gauge example).
                repaint();
            }
            break;
          case MouseEvent.MOUSE_ENTERED:
            break;
          case MouseEvent.MOUSE_EXITED:
            if(pressed == true) {
                // Cancel! Don't send action event.
                pressed = false;

                // Repaint might flicker a bit. To avoid this, you can use
                // double buffering (see the DoubleBufferPanel example above).
                repaint();

                // Note: for a more complete button implementation,
                // you wouldn't want to cancel at this point, but
                // rather detect when the mouse re-entered, and
                // re-highlight the button. There are a few state
                // issues that that you need to handle, which we leave
                // this an an excercise for the reader (I always
                // wanted to say that!)
            }
            break;
       }
       super.processMouseEvent(e);
   }
}