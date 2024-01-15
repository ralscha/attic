import java.awt.*;
import java.util.*;

public class StockGraph extends Canvas
{
    // variables needed
    public int top;
    public int bottom;
    public int left;
    public int right;
    int titleHeight;
    int labelWidth;
    FontMetrics fm;
    int padding = 4;
    String title;
    int min=0;
    int max=0;
    Vector items;
    double xinc;

    int increment;
    int position;

    public StockGraph(String title, double min, double max)
    {
        this.title = title;
        this.min = calcMin(min);
        this.max = calcMax(max);
        items = new Vector();
    }

    public void setMinMax(double min, double max)
    {
        if (this.min == 0)
            this.min = calcMin(min);
        else
            this.min = Math.min(calcMin(min),this.min);

        if (this.max == 50)
            this.max = calcMax(max);
        else
            this.max = Math.max(calcMax(max),this.max);

        repaint();
    }

    int calcMin(double minc)
    {
        int r = (int)(minc / 50.0);
        return (r * 50);
    }

    int calcMax(double maxc)
    {
        int r = (int)(maxc / 50.0);
        return ((r * 50)+50);

    }

    public void paint(Graphics g)
    {
        increment = 1;
        //position = left;
        Item first = (Item)items.firstElement();
        position = left+((int)(first.minutes*xinc));
        int newPos;

        g.setColor(Color.black);
        // draw the title
        fm = getFontMetrics(getFont());
//        g.drawString(title, (getSize().width - fm.stringWidth(title))/2, top);
        // draw the max and min values
        g.drawString(new Integer(min).toString(), padding, bottom);
        g.drawString(new Integer(max).toString(), padding, top+titleHeight-(2*fm.getDescent()));
        g.drawString("10:00", left, bottom+titleHeight);
        g.drawString("16:30", right-fm.stringWidth("16:30"), bottom+titleHeight);
        // draw the vertical and horizontal lines
        g.drawLine(left, top, left, bottom);
        g.drawLine(left, bottom, right, bottom);


        for (int i = 0; i < items.size() - 1; i++)
        {
            Item thisItem = (Item)items.elementAt(i);
            int thisAdjustedValue = bottom - (int)(((thisItem.value - min)*(bottom - top))/(max - min));
            Item nextItem = (Item)items.elementAt(i+1);
            int nextAdjustedValue = bottom - (int)(((nextItem.value - min)*(bottom - top))/(max - min));
            newPos = (int)(nextItem.minutes*xinc) + left;

            g.drawLine(position, thisAdjustedValue,newPos, nextAdjustedValue);
            position = newPos;
        }
    }

    public void setBounds(int x, int y, int w, int h)
    {
        super.setBounds(x,y,w,h);
        fm = getFontMetrics(getFont());
        titleHeight = fm.getHeight();
        labelWidth = Math.max(fm.stringWidth(new Integer(min).toString()),
                     fm.stringWidth(new Integer(max).toString())) + 2;
        top = padding + titleHeight;
        bottom = getSize().height - padding - titleHeight;
        left = padding + labelWidth;
        right = getSize().width - padding;

        xinc = (right-left)/390.0;
    }

    public Dimension getPreferredSize()
    {
        return(new Dimension(600,150));
    }


    public void addItem(int minutes, double value)
    {
        items.addElement(new Item(minutes,value));
    }

    class Item
    {
        int minutes;
        double value;

        Item(int minutes, double value)
        {
            this.minutes = minutes;
            this.value = value;
        }
    }

}
