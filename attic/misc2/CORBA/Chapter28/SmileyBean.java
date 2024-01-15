import java.awt.*;
import java.beans.*;
import java.awt.event.*;

public class SmileyBean extends Component {
    private Color ourColor = Color.yellow;
    private boolean smile = true;    
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    
    public SmileyBean() {
        setSize(250,250);
    }
    
   
    public synchronized void toggleSmile() {
        setSmile(!isSmile());
    }
    
    public synchronized Color getColor() {
        return ourColor;
    }
    
    public void setColor(Color newColor) {
        ourColor = newColor;
        repaint();
    }
    
    public synchronized boolean isSmile() {
        return smile;
    }
    
    public void setSmile(boolean newSmile) {
        boolean oldSmile = smile;
        smile = newSmile;
        repaint();
        changes.firePropertyChange("smile", new Boolean(oldSmile),new Boolean(newSmile));
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(250,250);
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }
    
    public void paint(Graphics g) {
        int cx = getSize().width/2; // center x
        int cy = getSize().height/2; // center y
        double r = Math.min(cx,cy)*0.97; // find smallest radius
        int r007 = (int)(r*0.07); int r007x2 = r007*2; // precompute
        int r020 = (int)(r*0.20); int r020x2 = r020*2; // misc.
        int r030 = (int)(r*0.30); int r030x2 = r030*2; // radius
        int r060 = (int)(r*0.60); int r060x2 = r060*2; // values
        int r100 = (int)(r*1.00); int r100x2 = r100*2;

        g.setColor(ourColor);
        g.fillOval(cx-r100,cy-r100,r100x2,r100x2); // face background

        g.setColor(Color.black); // black
        g.drawOval(cx-r100,cy-r100,r100x2,r100x2); // outline

        if (smile) g.drawArc(cx-r060,cy-r060,r060x2,r060x2,200,140); //smile
        else g.drawArc(cx-r060,cy+r030,r060x2,r060x2,020,140); // frown

        g.fillOval(cx-r030-r007,cy-r030-r007,r007x2,r007x2); // left eye
        g.fillOval(cx+r030-r007,cy-r030-r007,r007x2,r007x2); // right eye
        g.fillOval(cx-r007,cy+r020-r007,r007x2,r007x2); // nose
    }
    
}