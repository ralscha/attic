import java.awt.*;


public class WaTorChartCanvas extends Canvas
{
    Image img = null;
    Graphics gs = null;
    int max;
    int lastx, lastyS, lastyF;
    float dy;

    public WaTorChartCanvas(int max, int fish, int shark)
    {
        super();
        this.max = max;
        lastx = 0;
        lastyS = shark;
        lastyF = fish;
    }

    public void initWTCC()
    {
        if ((size().width > 0) && (size().height > 0))
        {
            img = createImage(size().width, size().height);
            gs  = img.getGraphics();
            clearImage();
        }

        dy = (float)size().height / (float)max;
        lastyS = size().height-(int)(dy * lastyS);
        lastyF = size().height-(int)(dy * lastyF);
    }

    public synchronized void reshape(int x, int y, int width, int height)
    {
        super.reshape(x, y, width, height);
        if ((img == null) || (width != img.getWidth(null))
              || (height != img.getHeight(null)))
        {
            if ((width > 0) && (height > 0))
            {
                img = createImage(width, height);
                gs  = img.getGraphics();
                clearImage();
            }
            dy = (float)height / (float)max;
        }

    }


    public void paint(Graphics g)
    {
        if (img != null)
        {
            g.drawImage(img, 0, 0, this);
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    void clearImage()
    {
        gs.setColor(Color.lightGray);
        gs.fillRect(0, 0, size().width, size().height);
        gs.setColor(Color.black);
    }

    void restart(int max, int fish, int shark)
    {
        clearImage();
        this.max = max;
        lastx = 0;
        lastyS = shark;
        lastyF = fish;
        dy = (float)size().height / (float)max;
        lastyS = size().height-(int)(dy * lastyS);
        lastyF = size().height-(int)(dy * lastyF);
    }

    void drawPoints(int fish, int shark)
    {
        int newx = ++lastx;
        int h = size().height;
        int newyS = h-(int)(dy * shark);
        int newyF = h-(int)(dy * fish);
        if (newyF == h) newyF--;
        if (newyS == h) newyS--;

        if (newx >= size().width)
        {
            newx = 1;
            lastx = 0;
            clearImage();
        }

        gs.setColor(Color.white);
        gs.drawLine(lastx, lastyS, newx, newyS);

        gs.setColor(Color.blue);
        gs.drawLine(lastx, lastyF, newx, newyF);

        lastx = newx;
        lastyS = newyS;
        lastyF = newyF;
        repaint();
    }

}