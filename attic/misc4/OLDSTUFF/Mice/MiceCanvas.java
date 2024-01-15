import java.awt.*;


public class MiceCanvas extends Canvas implements Runnable
{
    Image miceImg = null;
    Graphics miceG = null;
    Thread runner;
    Point currentPos[] = new Point[8];
    Point newPos[]     = new Point[8];
    int sx, sy;
    Dimension d;

    public MiceCanvas()
    {
        super();
    }


    public void start(int sx, int sy)
    {
         this.sx = sx;
         this.sy = sy;

         runner = new Thread(this);
         runner.setPriority(Thread.MIN_PRIORITY);

         d = size();

         currentPos[0] = new Point(sx, 0);
         currentPos[4] = new Point(d.width-sx, 0);
         currentPos[3] = new Point(d.width-1, sy);
         currentPos[5] = new Point(d.width-1, d.height-sy);
         currentPos[2] = new Point(d.width-sx, d.height-1);
         currentPos[6] = new Point(sx, d.height-1);
         currentPos[1] = new Point(0, d.height-sy);
         currentPos[7] = new Point(0, sy);

         for (int i = 0; i < 8; i++)
            newPos[i] = new Point(0,0);


         miceImg = createImage(size().width, size().height);
         miceG   = miceImg.getGraphics();

         runner.start();
    }

    public void stop()
    {
        runner = null;
        miceImg = null;
        miceG   = null;
    }

    public synchronized void reshape(int x, int y, int width, int height)
    {
        super.reshape(x, y, width, height);
        if ((miceImg == null) || (width != miceImg.getWidth(null))
              || (height != miceImg.getHeight(null)))
        {
            stop();
            miceImg = createImage(width, height);
            miceG   = miceImg.getGraphics();
            runner = new Thread(this);
            start(sx, sy);
        }

    }
    public void paint(Graphics g)
    {
        if (miceImg != null)
        {
            g.drawImage(miceImg, 0, 0, this);
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }


    public void run()
    {
        int k = 0;

        miceG.setColor(Color.black);

        while((Thread.currentThread() == runner) && (k < 90))
        {
    		double h = 0.05;
	    	int n = 7;
	    	int mg;

		    miceG.drawLine(currentPos[n].x, currentPos[n].y,
	    		                currentPos[0].x, currentPos[0].y);
		    for (int m = 1; m <= n; m++)
    		{
	    		miceG.drawLine(currentPos[m-1].x,currentPos[m-1].y,
	    		                currentPos[m].x, currentPos[m].y);
		    }
			for (int m = 0; m <= n; m++)
    		{
    		    if (m == n)
    			    mg = 0;
    			else
    			    mg = m + 1;

	    	    newPos[m].x = currentPos[m].x + (int)(h * (currentPos[mg].x - currentPos[m].x));
		    	newPos[m].y = currentPos[m].y + (int)(h * (currentPos[mg].y - currentPos[m].y));
			}
    		for (int m = 0; m <= n; m++)
	    	{
		    	currentPos[m].x = newPos[m].x;
			   	currentPos[m].y = newPos[m].y;
    		}
	    	repaint();
	    	k++;
        }
    }





}