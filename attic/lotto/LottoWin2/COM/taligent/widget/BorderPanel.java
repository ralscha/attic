// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3)
// Source File Name:   BorderPanel.java

package COM.taligent.widget;

import java.awt.*;

public class BorderPanel extends Panel
{

    public BorderPanel()
    {
        style = 3;
        thickness = 10;
        gap = 4;
        color = DEFAULT_COLOR;
        text = null;
        font = DEFAULT_FONT;
        alignment = 0;
    }

    public BorderPanel(String s)
    {
        this();
        style = 3;
        text = s;
    }

    public BorderPanel(Color color1, int i)
    {
        this();
        style = 0;
        color = color1;
        thickness = i;
    }

    public BorderPanel(int i)
    {
        this();
        int j;
        switch(i)
        {
        case 0: /* '\0' */
            j = 4;
            break;

        case 1: /* '\001' */
            j = 2;
            break;

        case 2: /* '\002' */
            j = 2;
            break;

        case 3: /* '\003' */
            j = 10;
            break;

        case 4: /* '\004' */
            j = 10;
            break;

        default:
            j = 10;
            break;

        }
        style = i;
        thickness = j;
    }

    public BorderPanel(int i, int j)
    {
        this();
        style = i;
        thickness = j;
    }

    public Insets insets()
    {
        int i = 0;
        if((style == 3 || style == 4) && text != null && text.length() > 0)
            try
            {
                int j = getGraphics().getFontMetrics(font).getHeight();
                if(j > thickness)
                    i = j - thickness;
            }
            catch(Exception ex) {}
        int k = thickness + gap;
        return new Insets(k + i, k, k, k);
    }

    public BorderPanel setStyle(int i)
    {
        style = i;
        layout();
        repaint();
        return this;
    }

    public int getStyle()
    {
        return style;
    }

    public BorderPanel setThickness(int i)
    {
        if(i > 0)
        {
            thickness = i;
            layout();
            repaint();
        }
        return this;
    }

    public int getThickness()
    {
        return thickness;
    }

    public BorderPanel setGap(int i)
    {
        if(i > -1)
        {
            gap = i;
            layout();
            repaint();
        }
        return this;
    }

    public int getGap()
    {
        return gap;
    }

    public BorderPanel setColor(Color color1)
    {
        color = color1;
        if(style == 0 || style == 3 || style == 4)
            repaint();
        return this;
    }

    public Color getColor()
    {
        return color;
    }

    public BorderPanel setTextFont(Font font1)
    {
        if(font1 != null)
        {
            font = font1;
            if(style == 3 || style == 4)
            {
                layout();
                repaint();
            }
        }
        return this;
    }

    public Font getTextFont()
    {
        return font;
    }

    public BorderPanel setText(String s)
    {
        text = s;
        if(style == 3 || style == 4)
        {
            layout();
            repaint();
        }
        return this;
    }

    public String getText()
    {
        return text;
    }

    public BorderPanel setAlignment(int i)
    {
        alignment = i;
        if(style == 3 || style == 4)
        {
            layout();
            repaint();
        }
        return this;
    }

    public int getAlignment()
    {
        return alignment;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Dimension dimension = size();
        int i = dimension.width;
        int j = dimension.height;
        Color color1 = getBackground().brighter().brighter().brighter();
        Color color2 = getBackground().darker().darker().darker();
        int j3;
        switch(style)
        {
        case 1: /* '\001' */
        case 2: /* '\002' */
            Color color3 = null;
            Color color4 = null;
            if(style == 1)
            {
                color3 = color1;
                color4 = color2;
            }
            else
            {
                color3 = color2;
                color4 = color1;
            }
            g.setColor(color3);
            for(int k = 0; k < thickness; k++)
            {
                g.drawLine(k, k, i - k - 2, k);
                g.drawLine(k, k + 1, k, j - k - 1);
            }

            g.setColor(color4);
            for(int l = 0; l < thickness; l++)
            {
                g.drawLine(l + 1, j - l - 1, i - l - 1, j - l - 1);
                g.drawLine(i - l - 1, l, i - l - 1, j - l - 2);
            }

            return;

        case 3: /* '\003' */
        case 4: /* '\004' */
            int i1 = 0;
            int j1 = 0;
            Font font1 = g.getFont();
            g.setFont(font);
            FontMetrics fontmetrics = g.getFontMetrics();
            int k1 = fontmetrics.getAscent();
            if(style == 3)
                i1 = 1;
            else
                j1 = 1;
            int l1 = 0;
            if(text != null && text.length() > 0 && k1 > thickness)
                l1 = (k1 - thickness) / 2;
            int i2 = thickness / 2;
            int j2 = thickness / 2 + l1;
            int k2 = i - thickness - 1;
            int l2 = j - thickness - 1 - l1;
            g.setColor(color1);
            g.drawRect(i2 + i1, j2 + i1, k2, l2);
            g.setColor(color2);
            g.drawRect(i2 + j1, j2 + j1, k2, l2);
            if(text != null && text.length() > 0)
            {
                int i3 = fontmetrics.getHeight();
                int k3 = fontmetrics.stringWidth(text);
                int l3 = i - 2 * (thickness + 5);
                if(k3 > l3)
                    k3 = l3;
                int i4;
                switch(alignment)
                {
                case 1: /* '\001' */
                    i4 = (i - k3) / 2;
                    break;

                case 2: /* '\002' */
                    i4 = i - k3 - thickness - 5;
                    break;

                case 0: /* '\0' */
                default:
                    i4 = thickness + 5;
                    break;

                }
                g.clearRect(i4 - 5, 0, k3 + 10, i3);
                g.clipRect(i4, 0, k3, i3);
                g.setColor(color);
                g.drawString(text, i4, k1);
                g.clipRect(0, 0, i, j);
            }
            g.setFont(font1);
            return;

        case 0: /* '\0' */
        default:
            g.setColor(color);
            j3 = 0;
            break;

        }
        for(; j3 < thickness; j3++)
            g.drawRect(j3, j3, i - 2 * j3 - 1, j - 2 * j3 - 1);

    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer("BorderPanel[");
        stringbuffer.append("style=");
        switch(style)
        {
        case 0: /* '\0' */
            stringbuffer.append("SOLID");
            break;

        case 1: /* '\001' */
            stringbuffer.append("RAISED");
            break;

        case 2: /* '\002' */
            stringbuffer.append("LOWERED");
            break;

        case 3: /* '\003' */
            stringbuffer.append("IN");
            break;

        case 4: /* '\004' */
            stringbuffer.append("OUT");
            break;

        default:
            stringbuffer.append("unknown");
            break;

        }
        stringbuffer.append(",");
        stringbuffer.append("thickness=");
        stringbuffer.append(thickness);
        stringbuffer.append(",");
        stringbuffer.append("gap=");
        stringbuffer.append(gap);
        stringbuffer.append(",");
        stringbuffer.append(color);
        stringbuffer.append(",");
        stringbuffer.append(font);
        stringbuffer.append(",");
        stringbuffer.append("text=");
        stringbuffer.append(text);
        stringbuffer.append(",");
        stringbuffer.append("alignment=");
        switch(alignment)
        {
        case 0: /* '\0' */
            stringbuffer.append("LEFT");
            break;

        case 1: /* '\001' */
            stringbuffer.append("CENTER");
            break;

        case 2: /* '\002' */
            stringbuffer.append("RIGHT");
            break;

        default:
            stringbuffer.append("unknown");
            break;

        }
        stringbuffer.append("]");
        return stringbuffer.toString();
    }

    public static final int SOLID = 0;
    public static final int RAISED = 1;
    public static final int LOWERED = 2;
    public static final int IN = 3;
    public static final int OUT = 4;
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int DEFAULT_STYLE = 3;
    public static final int DEFAULT_THICKNESS = 10;
    public static final int DEFAULT_SOLID_THICKNESS = 4;
    public static final int DEFAULT_RAISED_THICKNESS = 2;
    public static final int DEFAULT_LOWERED_THICKNESS = 2;
    public static final int DEFAULT_IN_THICKNESS = 10;
    public static final int DEFAULT_OUT_THICKNESS = 10;
    public static final int DEFAULT_GAP = 4;
    public static final Color DEFAULT_COLOR = Color.black;
    public static final Font DEFAULT_FONT = new Font("TimesRoman", 0, 14);
    public static final int DEFAULT_ALIGNMENT = 0;
    private int style;
    private int thickness;
    private int gap;
    private Color color;
    private Font font;
    private String text;
    private int alignment;

}
