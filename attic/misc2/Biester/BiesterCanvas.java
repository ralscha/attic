import java.awt.*;


public class BiesterCanvas extends Canvas implements Runnable
{
    Image offImg = null;
    Graphics offG = null;
    Thread runner;
    Random r;

    public BiesterCanvas()
    {
        super();
        r = new Random();
    }

    public void init()
    {
        Dimension d = size();

    }

	public void run()
    {
        repaint();
    }


    public void start()
    {
        runner = new Thread(this);
        runner.setPriority(Thread.MIN_PRIORITY);

        runner.start();
    }

    public void stop()
    {
        if (runner != null)
        {
            runner.stop();
            runner = null;
        }
    }



    public synchronized void reshape(int x, int y, int width, int height)
    {
        super.reshape(x, y, width, height);
        if ((offImg == null) || (width != offImg.getWidth(null))
              || (height != offImg.getHeight(null)))
        {
            offImg = createImage(width, height);
            offG   = offImg.getGraphics();
        }

    }

    public void clear()
    {
        offG.setColor(getBackground());
        offG.fillRect(0, 0, size().width, size().height);
        repaint();
    }

    public void paint(Graphics g)
    {
        if (offImg != null)
        {
            g.drawImage(offImg, 0, 0, this);
        }
    }


    public void update(Graphics g)
    {
        paint(g);
    }


}