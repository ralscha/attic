import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LRectPanel extends Panel
{
    LRectGroup lrg;

    public LRectPanel(int min, int max) {
	    super();
	    lrg = new LRectGroup(min, max);
        setLayout(new GridLayout(8,6,5,5));
        for (int i = 0; i < 45; i++)
            add(new LRect(String.valueOf(i+1),lrg));
    }
/*
    public Dimension getPreferredSize()
    {
        return (new Dimension(140,170));
    }
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
*/
    public boolean isOK() {
        return(lrg.isOK());
    }

    public Vector getNumbers() {
        return(lrg.getNumbers());
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0,0,getSize().width-1, getSize().height-1);
        super.paint(g);
    }

}

