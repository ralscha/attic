import java.awt.*;
import java.awt.event.*;

public class LRect extends Component
{
    private String id;
    private FontMetrics f;
    private final static int fontHeight = 8;
    private boolean cross;
    private LRectGroup lrg;

    public LRect(String id, LRectGroup lrg)
    {
        this.id = id;
        this.lrg = lrg;
        setForeground(Color.lightGray);
        setFont(new Font("Courier", Font.BOLD, fontHeight));
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        cross = false;
    }

    public void processMouseEvent(MouseEvent evt)
    {
        switch(evt.getID()) {
            case MouseEvent.MOUSE_RELEASED:
                if (cross) {
                    lrg.removeNumber(id);
                    cross = false;
                    repaint();
                } else {
                    if (lrg.addNumber(id)) {
                        cross = true;
                        repaint();
                    }
                }
                break;
      }
        super.processMouseEvent(evt);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0,0,getSize().width-1, getSize().height-1);

        f = g.getFontMetrics(g.getFont());
        int len = f.stringWidth(id);
        int xpos = (getSize().width-len)/2;
        int ypos = getSize().height-((getSize().height-fontHeight)/2);

        g.drawString(id, xpos, ypos);

        if (cross) {
            g.drawLine(0,0,getSize().width-1,getSize().height-1);
            g.drawLine(0,1,getSize().width-2,getSize().height-1);
            g.drawLine(getSize().width-1,0,0,getSize().height-1);
            g.drawLine(getSize().width-2,0,0,getSize().height-2);
        }

    }

    public Dimension getPreferredSize() {
        return new Dimension(18,18);
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
}