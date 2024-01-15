// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextTable.java

package COM.writeme.guyrice.awt;

import java.awt.*;
import java.awt.event.MouseEvent;

// Referenced classes of package COM.writeme.guyrice.awt:
//            StyleEvent, StyleListener, StyleManager

public class TextTable extends Component
    implements StyleListener
{

    public TextTable(String as[][])
    {
        this(as, true, false);
    }

    public TextTable(String as[][], boolean flag, boolean flag1)
    {
        firstRow = 1;
        firstCol = 1;
        style = StyleManager.global;
        scaleWidth = flag;
        scaleHeight = flag1;
        rows = as.length;
        cols = 0;
        for(int i = 0; i < rows; i++)
            if(as[i].length > cols)
                cols = as[i].length;

        cellText = new String[rows][cols][];
        for(int j = 0; j < rows; j++)
        {
            for(int k = 0; k < cols; k++)
                if(k < as[j].length && as[j][k] != null && as[j][k].length() > 0)
                {
                    cellText[j][k] = stringToArray(as[j][k]);
                    if(j == 0)
                        firstRow = 0;
                    if(k == 0)
                        firstCol = 0;
                }
                else
                {
                    cellText[j][k][0] = "";
                }

        }

        colWidth = new int[cols];
        rowHeight = new int[rows];
        x = new int[cols];
        y = new int[rows];
        w = new int[cols];
        h = new int[rows];
        enableEvents(16L);
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

    public void addNotify()
    {
        super.addNotify();
        FontMetrics fontmetrics = getFontMetrics(getFont());
        fontHeight = fontmetrics.getHeight();
        fontAscent = fontmetrics.getAscent();
        fontDescent = fontmetrics.getDescent();
        int ai[][] = new int[rows][cols];
        int ai1[][] = new int[rows][cols];
        for(int i = firstRow; i < rows; i++)
        {
            for(int j = firstCol; j < cols; j++)
            {
                for(int l = 0; l < cellText[i][j].length; l++)
                {
                    int j1 = fontmetrics.stringWidth(cellText[i][j][l]) + fontDescent * 2;
                    if(j1 > ai[i][j])
                        ai[i][j] = j1;
                }

                ai1[i][j] = cellText[i][j].length * fontHeight;
            }

        }

        int k = firstRow;
        int i1 = 2;
        for(; k < rows; k++)
        {
            rowHeight[k] = 0;
            for(int k1 = firstCol; k1 < cols; k1++)
                if(ai1[k][k1] > rowHeight[k])
                    rowHeight[k] = ai1[k][k1];

            y[k] = i1;
            h[k] = rowHeight[k];
            i1 += rowHeight[k] + 1;
        }

        int l1 = firstCol;
        int i2 = 2;
        for(; l1 < cols; l1++)
        {
            colWidth[l1] = 0;
            for(int j2 = firstRow; j2 < rows; j2++)
                if(ai[j2][l1] > colWidth[l1])
                    colWidth[l1] = ai[j2][l1];

            x[l1] = i2;
            w[l1] = colWidth[l1];
            i2 += colWidth[l1] + 1;
        }

        currentSize = preferredSize = new Dimension(x[cols - 1] + w[cols - 1] + 1, y[rows - 1] + h[rows - 1] + 1);
        style.addStyleListener(this);
    }

    public void removeNotify()
    {
        style.removeStyleListener(this);
        super.removeNotify();
    }

    public void styleChanged(StyleEvent styleevent)
    {
        if(styleevent.isGlobalChange())
        {
            adjustSize(getSize());
            repaint();
        }
    }

    public Dimension getPreferredSize()
    {
        return preferredSize;
    }

    public Dimension getMinimumSize()
    {
        return preferredSize;
    }

    public void paint(Graphics g)
    {
        Dimension dimension = getSize();
        if(!dimension.equals(currentSize))
            adjustSize(dimension);
        g.setClip(0, 0, dimension.width, dimension.height);
        g.setColor(getBackground());
        g.fillRect(0, 0, dimension.width, dimension.height);
        g.setColor(style.getOuterLight(true));
        g.drawLine(0, 0, dimension.width - 1, 0);
        g.drawLine(0, 0, 0, dimension.height - 1);
        g.setColor(style.getInnerLight(true));
        g.drawLine(1, 1, dimension.width - 2, 1);
        if(firstRow == 0)
            g.fillRect(1, 1, x[cols - 1] + w[cols - 1], h[0] + 2);
        g.drawLine(1, 1, 1, dimension.height - 2);
        if(firstCol == 0)
            g.fillRect(1, 1, w[0] + 2, y[rows - 1] + h[rows - 1]);
        g.setColor(style.getInnerShade(true));
        g.drawLine(dimension.width - 2, 1, dimension.width - 2, dimension.height - 2);
        g.drawLine(1, dimension.height - 2, dimension.width - 2, dimension.height - 2);
        g.setColor(style.getOuterShade(true));
        g.drawLine(dimension.width - 1, 0, dimension.width - 1, dimension.height - 1);
        g.drawLine(0, dimension.height - 1, dimension.width - 1, dimension.height - 1);
        for(int i = firstRow; i < rows; i++)
        {
            for(int j = firstCol; j < cols; j++)
                drawCell(g, i, j);

        }

    }

    private void drawCell(Graphics g, int i, int j)
    {
        if(i < firstRow || i >= rows || j < firstCol || j >= cols)
            return;
        g.setClip(new Rectangle(x[j], y[i], w[j], h[i]).intersection(dataRect));
        if(i == 0 || j == 0)
        {
            g.setColor(style.getBackground(false));
            g.fillRect(x[j], y[i], w[j], h[i]);
            g.setColor(style.getOutline() ? style.getInnerLight(false) : style.getOuterLight(false));
            g.drawLine(x[j], y[i], x[j] + w[j], y[i]);
            g.drawLine(x[j], y[i], x[j], y[i] + h[i]);
            g.setColor(style.getInnerShade(false));
            g.drawLine(x[j], (y[i] + h[i]) - 1, (x[j] + w[j]) - 1, (y[i] + h[i]) - 1);
            g.drawLine((x[j] + w[j]) - 1, y[i], (x[j] + w[j]) - 1, (y[i] + h[i]) - 1);
            g.setColor(style.getForeground(false));
        }
        else
        {
            boolean flag = i >= selTop && i <= selBottom && j >= selLeft && j <= selRight;
            g.setColor(flag ? Color.black : Color.white);
            g.fillRect(x[j], y[i], w[j], h[i]);
            g.setColor(flag ? Color.white : Color.black);
        }
        for(int k = 0; k < cellText[i][j].length; k++)
            g.drawString(cellText[i][j][k], x[j] + fontDescent, y[i] + k * fontHeight + fontAscent);

    }

    private void adjustSize(Dimension dimension)
    {
        int i = firstCol;
        int j = 2;
        for(; i < cols; i++)
        {
            x[i] = j;
            w[i] = colWidth[i];
            j += colWidth[i] + 1;
        }

        if(scaleWidth)
        {
            int k = dimension.width - 1 - x[1];
            int i1 = (x[cols - 1] + w[cols - 1] + 1) - x[1];
            for(int k1 = 2; k1 < cols; k1++)
            {
                x[k1] = ((x[k1] - x[1]) * k) / i1 + x[1];
                w[k1 - 1] = x[k1] - x[k1 - 1] - 1;
            }

            w[cols - 1] = dimension.width - 1 - x[cols - 1] - (style.getOutline() ? 1 : 2);
        }
        int l = firstRow;
        int j1 = 2;
        for(; l < rows; l++)
        {
            y[l] = j1;
            h[l] = rowHeight[l];
            j1 += rowHeight[l] + 1;
        }

        if(scaleHeight)
        {
            int l1 = dimension.height - 1 - y[1];
            int i2 = (y[rows - 1] + h[rows - 1] + 1) - y[1];
            for(int j2 = 2; j2 < rows; j2++)
            {
                y[j2] = ((y[j2] - y[1]) * l1) / i2 + y[1];
                h[j2 - 1] = y[j2] - y[j2 - 1] - 1;
            }

            h[rows - 1] = dimension.height - 1 - y[rows - 1] - (style.getOutline() ? 1 : 2);
        }
        currentSize = dimension;
        dataRect = new Rectangle(2, 2, dimension.width - 4, dimension.height - 4);
    }

    protected void processMouseEvent(MouseEvent mouseevent)
    {
        switch(mouseevent.getID())
        {
        default:
            break;

        case 500: 
            if(mouseevent.getClickCount() == 2)
            {
                Point point = mouseCell(mouseevent);
                if(point != null)
                {
                    if(firstCol == 0 && firstRow == 1)
                        point.y = 0;
                    else
                        point.x = 0;
                    setSelection(point.y, point.x);
                }
            }
            break;

        case 501: 
            Point point1 = mouseCell(mouseevent);
            if(point1 != null)
            {
                pressed = point1;
                setSelection(pressed.y, pressed.x);
                enableEvents(32L);
            }
            else
            {
                clearSelection();
            }
            break;

        case 502: 
            if(pressed == null)
                break;
            Point point2 = mouseCell(mouseevent);
            if(point2 != null)
                setSelection(pressed.y, pressed.x, point2.y, point2.x);
            disableEvents(32L);
            pressed = null;
            break;

        }
        super.processMouseEvent(mouseevent);
    }

    protected void processMouseMotionEvent(MouseEvent mouseevent)
    {
        if(mouseevent.getID() == 506)
        {
            Point point = mouseCell(mouseevent);
            if(point != null)
                setSelection(pressed.y, pressed.x, point.y, point.x);
        }
        super.processMouseMotionEvent(mouseevent);
    }

    private Point mouseCell(MouseEvent mouseevent)
    {
        int i = mouseevent.getX();
        int j = mouseevent.getY();
        int k = -1;
        int l = -1;
        for(int i1 = firstRow; i1 < rows; i1++)
        {
            if(j < y[i1] || j >= y[i1] + h[i1])
                continue;
            k = i1;
            break;
        }

        for(int j1 = firstCol; j1 < cols; j1++)
        {
            if(i < x[j1] || i >= x[j1] + w[j1])
                continue;
            l = j1;
            break;
        }

        if(k < 0 || l < 0)
            return null;
        else
            return new Point(l, k);
    }

    public void setSelection(int i, int j)
    {
        setSelection(i, j, i, j);
    }

    public void setSelection(int i, int j, int k, int l)
    {
        int i1 = selTop;
        int j1 = selLeft;
        int k1 = selBottom;
        int l1 = selRight;
        if(i == 0)
        {
            i = 1;
            k = rows - 1;
        }
        else
        if(k == 0)
            k = 1;
        if(j == 0)
        {
            j = 1;
            l = cols - 1;
        }
        else
        if(l == 0)
            l = 1;
        if(i < k)
        {
            selTop = i;
            selBottom = k;
        }
        else
        {
            selTop = k;
            selBottom = i;
        }
        if(j < l)
        {
            selLeft = j;
            selRight = l;
        }
        else
        {
            selLeft = l;
            selRight = j;
        }
        if(selTop != i1 || selLeft != j1 || selBottom != k1 || selRight != l1)
        {
            Graphics g = getGraphics();
            for(int i2 = i1; i2 <= k1; i2++)
            {
                for(int j2 = j1; j2 <= l1; j2++)
                    drawCell(g, i2, j2);

            }

            for(int k2 = selTop; k2 <= selBottom; k2++)
            {
                for(int l2 = selLeft; l2 <= selRight; l2++)
                    drawCell(g, k2, l2);

            }

        }
    }

    public int[] getSelection()
    {
        if(selTop < 1 || selLeft < 1)
            return null;
        if(selTop == selBottom && selLeft == selRight)
            return (new int[] {
                selTop, selLeft
            });
        else
            return (new int[] {
                selTop, selLeft, selBottom, selRight
            });
    }

    public void clearSelection()
    {
        setSelection(-1, -1, -2, -2);
    }


    private String cellText[][][];
    private boolean scaleWidth;
    private boolean scaleHeight;
    private int rows;
    private int cols;
    private int firstRow;
    private int firstCol;
    private int fontHeight;
    private int fontAscent;
    private int fontDescent;
    private int colWidth[];
    private int rowHeight[];
    private int x[];
    private int y[];
    private int w[];
    private int h[];
    private Dimension preferredSize;
    private Dimension currentSize;
    private Rectangle dataRect;
    private StyleManager style;
    private Point pressed;
    private int selTop;
    private int selLeft;
    private int selBottom;
    private int selRight;
}
