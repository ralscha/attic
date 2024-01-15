import java.awt.*;
import java.util.Vector;


public class WrapCanvas extends Canvas
{
    Image kImg = null;
    Graphics kG = null;
    Vector points = new Vector();

    public WrapCanvas()
    {
        super();
    }

    public synchronized void reshape(int x, int y, int width, int height)
    {
        super.reshape(x, y, width, height);
        if ((kImg == null) || (width != kImg.getWidth(null))
              || (height != kImg.getHeight(null)))
        {
            kImg = createImage(width, height);
            kG   = kImg.getGraphics();
            clear();
            kG.setColor(Color.black);
            kG.drawRect(0, 0, width-1, height-1);
        }

    }

    public void clear()
    {
        points.removeAllElements();
        kG.setColor(getBackground());
        kG.fillRect(0, 0, size().width, size().height);
        kG.setColor(Color.black);
        kG.drawRect(0, 0, size().width-1, size().height-1);
        repaint();
    }

    public void paint(Graphics g)
    {
        if (kImg != null)
        {
            g.drawImage(kImg, 0, 0, this);
        }
    }

    public boolean mouseDown(Event evt, int x, int y)
    {
        kG.setColor(Color.black);
        kG.fillOval(x-2,y-2,4,4);

        points.addElement(new Point(x,y));

        repaint();
        return true;
    }

    public void doWrap()
    {
        if (points.size() == 0) return;

        int i = wrap(points);
        int h = size().height;
        int w = size().width;

        for (int j = 0; j < i; j++)
            kG.drawLine(((Point)points.elementAt(j)).x,
                        ((Point)points.elementAt(j)).y,
                        ((Point)points.elementAt(j+1)).x,
                        ((Point)points.elementAt(j+1)).y);

        kG.drawLine(((Point)points.elementAt(i)).x,
                        ((Point)points.elementAt(i)).y,
                        ((Point)points.elementAt(0)).x,
                        ((Point)points.elementAt(0)).y);
        repaint();
    }

    int wrap(Vector p)
    {
        int i, min, M, N;
        Object help;
        float th, v;
        N = p.size();

        for (min = 0, i = 1; i < N; i++)
            if (((Point)p.elementAt(i)).y < ((Point)p.elementAt(min)).y)
                min = i;

        p.addElement(p.elementAt(min));
        th = (float)0.0;

        for (M = 0; M < N; M++)
        {
            help = p.elementAt(M);
            p.setElementAt(p.elementAt(min), M);
            p.setElementAt(help, min);

            min = N; v = th; th = (float)360.0;

            for(i = M+1; i <= N; i++)
                if (theta(p.elementAt(M), p.elementAt(i)) > v)
                    if (theta(p.elementAt(M), p.elementAt(i)) < th)
                    {
                        min = i;
                        th = theta(p.elementAt(M), p.elementAt(min));
                    }
            if (min == N) return M;
        }
        return M;
    }

    float theta(Object o1, Object o2)
    {
        Point p1 = (Point)o1;
        Point p2 = (Point)o2;
        int dx, dy, ax, ay;
        float t;
        dx = p2.x - p1.x; ax = Math.abs(dx);
        dy = p2.y - p1.y; ay = Math.abs(dy);
        t = (ax+ay == 0) ? 0 : (float)dy/(ax+ay);
        if (dx < 0)
            t = 2 - t;
        else if (dy < 0)
            t = 4+t;

        return t*(float)90.0;
    }

    public void update(Graphics g)
    {
        paint(g);
    }


}