import java.awt.*;
import java.awt.image.*;


public class KruemelCanvas extends Canvas implements Runnable
{
    Thread runner;

    int zustaende = 15;
    Image newImg;
    int neu[];
    int alt[];
    IndexColorModel cm;

    public KruemelCanvas()
    {
        super();
        show();
    }


    public void start()
    {
         if (runner == null)
         {
            runner = new Thread(this);
            runner.setPriority(Thread.MIN_PRIORITY);
            runner.start();
         }
    }

    public void stop() {
        runner = null;
    }

    public void paint(Graphics g)
    {
        if (newImg != null)
        {
            g.drawImage(newImg, 0, 0, this);
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }


    public synchronized void reshape(int x, int y, int width, int height)
    {
        super.reshape(x, y, width, height);
        if ((newImg == null) || (width != newImg.getWidth(null))
              || (height != newImg.getHeight(null)))
        {
            stop();
            newImg = createImage(width, height);
            start();
        }

    }


    public void run()
    {
        int w = size().width;
        int h = size().height;

        int x, y, i, j;
        neu = new int[w * h];
        alt = new int[w * h];

        byte red[] = new byte[16];
        byte green[] = new byte[16];
        byte blue[] = new byte[16];
        int aix = w;
        int bix = h;
        int x1, y1;

        for (i = 0; i < 16; i++) {
            red[i]   = (byte)(i * 20 % 256);
            green[i] = (byte)(i * 30 % 256);
            blue[i]  = (byte)(i * 50 % 256);
        }
        cm = new IndexColorModel(4, 16, red, green, blue);

        int ix;
        for (x = 0; x < w; x++) {
            for (y = 0; y < h; y++) {
                ix = y * w + x;
                neu[ix] = (int)(Math.random() * (zustaende+1));
                alt[ix] = neu[ix];
            }
        }

        MemoryImageSource source = new MemoryImageSource(w, h, cm, neu, 0, w);
        source.setAnimated(true);
    	
        newImg = createImage(source);
        repaint();

        while((Thread.currentThread() == runner)) {
		
            for(i = 0; i < aix; i++) {
		        for (j = 0; j < bix; j++) {
		        	x = i - 1;
        			if (x < 0) x = aix - 1;

        			y = j - 1;
		        	if (y < 0) y = bix-1;

        			x1 = (i + 1) % aix;
     	        	y1 = (j + 1) % bix;


        			if (alt[j*w+x] == (alt[j*w+i] + 1) % zustaende)
  	   		            neu[j*w+i] = alt[j*w+x];
	            	else if (alt[y*w+i] == (alt[j*w+i] + 1) % zustaende)
        				neu[j*w+i] = alt[y*w+i];
		        	else if (alt[j*w+x1] == (alt[j*w+i] + 1) % zustaende)
	             		neu[j*w+i] = alt[j*w+x1];
        			else if (alt[y1*w+i] == (alt[j*w+i] + 1) % zustaende)
             			neu[j*w+i] = alt[y1*w+i];
          		}
      		}

      		for (i = 0; i < aix; i++)
      		    for (j = 0; j < bix; j++)
      		        alt[j*w+i] = neu[j*w+i];

            source.newPixels();
            
            try {
		        runner.sleep(10);
    		} catch( InterruptedException e ) {
	    	    return;
		    }

//	        newImg = createImage(source);
//      		newImg = createImage(new MemoryImageSource(w, h, cm, neu, 0, w));
//      		repaint();
      	}

    }

    public boolean mouseDown(Event e, int x, int y)
    {
       if (runner == null)
       {
           start();
       }
       else
       {
           stop();
       }
       return false;
    }


}