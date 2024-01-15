// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Text.java

package COM.writeme.guyrice.awt;

import java.awt.*;

public class Text extends Component
{

    public Text(String s)
    {
        this(s, 17, 1);
    }

    public Text(String s, int i)
    {
        this(s, i, 1);
    }

    public Text(String s, int i, int j)
    {
        text = stringToArray(s);
        width = new int[text.length];
        anchor = i;
        justify = j;
    }

    private String[] stringToArray(String s)
    {
        int i = 0;
        int j = 0;
        int k = -1;
        int i1;
        for(i1 = 1; (k = s.indexOf(10, k + 1)) >= 0; i1++);
        String as[] = new String[i1];
        while(--i1 > 0) 
        {
            int l = s.indexOf(10, j);
            as[i++] = s.substring(j, l);
            j = l + 1;
        }

        as[i] = s.substring(j);
        return as;
    }

    private void computeSize()
    {
        java.awt.Font font = getFont();
        if(font == null)
            return;
        FontMetrics fontmetrics = getFontMetrics(font);
        fontHeight = fontmetrics.getHeight();
        fontAscent = fontmetrics.getAscent();
        fontDescent = fontmetrics.getDescent();
        textWidth = 0;
        for(int i = 0; i < text.length; i++)
        {
            width[i] = fontmetrics.stringWidth(text[i]);
            if(width[i] > textWidth)
                textWidth = width[i];
        }

        textHeight = fontHeight * text.length;
    }

    public Dimension getMinimumSize()
    {
        if(fontHeight == 0)
            computeSize();
        return new Dimension(textWidth, textHeight);
    }

    public Dimension getPreferredSize()
    {
        if(fontHeight == 0)
            computeSize();
        return new Dimension(textWidth, textHeight);
    }

    public void paint(Graphics g)
    {
        g.setColor(getForeground());
        g.setFont(getFont());
        if(fontHeight == 0)
            computeSize();
        Dimension dimension = getSize();
        int i;
        switch(anchor & 0x3)
        {
        case 1: /* '\001' */
            i = 0;
            break;

        case 2: /* '\002' */
            i = dimension.width - textWidth;
            break;

        default:
            i = (dimension.width - textWidth) / 2;
            break;

        }
        int j;
        switch(anchor & 0x30)
        {
        case 16: /* '\020' */
            j = 0;
            break;

        case 32: /* ' ' */
            j = dimension.height - textHeight;
            break;

        default:
            j = (dimension.height - textHeight) / 2;
            break;

        }
        for(int l = 0; l < text.length; l++)
        {
            int k;
            switch(justify)
            {
            case 1: /* '\001' */
                k = 0;
                break;

            case 2: /* '\002' */
                k = textWidth - width[l];
                break;

            default:
                k = (textWidth - width[l]) / 2;
                break;

            }
            g.drawString(text[l], i + k, j + l * fontHeight + fontAscent);
        }

    }

    private String text[];
    private int width[];
    private int fontHeight;
    private int fontAscent;
    private int fontDescent;
    private int textWidth;
    private int textHeight;
    private int anchor;
    private int justify;
    public static final int CENTER = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    private static final int HORIZONTAL_MASK = 3;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;
    private static final int VERTICAL_MASK = 48;
    public static final int EAST = 2;
    public static final int NORTH = 16;
    public static final int NORTHEAST = 18;
    public static final int NORTHWEST = 17;
    public static final int SOUTH = 32;
    public static final int SOUTHEAST = 34;
    public static final int SOUTHWEST = 33;
    public static final int WEST = 1;
}
