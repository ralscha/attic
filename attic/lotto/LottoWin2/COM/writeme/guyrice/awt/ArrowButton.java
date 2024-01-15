// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArrowButton.java

package COM.writeme.guyrice.awt;

import java.awt.*;

// Referenced classes of package COM.writeme.guyrice.awt:
//            BaseButton, StyleManager

public class ArrowButton extends BaseButton
{

    public ArrowButton(String s)
    {
        this(s, StyleManager.global);
    }

    public ArrowButton(String s, StyleManager stylemanager)
    {
        super(s, stylemanager);
        setLabel(s);
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String s)
    {
        label = s;
        if(s.equalsIgnoreCase("east"))
        {
            horizontal = true;
            reverse = false;
            return;
        }
        if(s.equalsIgnoreCase("west"))
        {
            horizontal = true;
            reverse = true;
            return;
        }
        if(s.equalsIgnoreCase("south"))
        {
            horizontal = false;
            reverse = false;
            return;
        }
        if(s.equalsIgnoreCase("north"))
        {
            horizontal = false;
            reverse = true;
            return;
        }
        else
        {
            throw new AWTError("ArrowButton direction invalid");
        }
    }

    public Dimension getPreferredSize()
    {
        FontMetrics fontmetrics = getFontMetrics(getFont());
        int i = Math.max(fontmetrics.getHeight() + (fontmetrics.getDescent() + 1) * 2, 15);
        return new Dimension(i, i);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(15, 15);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Dimension dimension = getSize();
        if(!dimension.equals(oldSize))
        {
            int i = (Math.min(dimension.width, dimension.height) / 2 - 4) / 3;
            if(reverse)
                i = -i;
            int j = i * 3;
            int ai[] = {
                -j, 0, 0, j, 0, 0, -j
            };
            int ai1[] = {
                -i, -i, -j, 0, j, i, i
            };
            arrow = horizontal ? new Polygon(ai, ai1, 7) : new Polygon(ai1, ai, 7);
            arrow.translate(dimension.width / 2, dimension.height / 2);
            oldSize = dimension;
        }
        g.setColor(style.getForeground(down()));
        g.drawPolygon(arrow);
        g.fillPolygon(arrow);
    }

    private String label;
    private boolean horizontal;
    private boolean reverse;
    private Dimension oldSize;
    private Polygon arrow;
}
