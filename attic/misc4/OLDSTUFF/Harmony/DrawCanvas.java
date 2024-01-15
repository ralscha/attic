import java.awt.*;
import java.util.*;

public class DrawCanvas extends Canvas
{
    double sinscroll[] = new double[Global.sb];
    double cosscroll[] = new double[Global.sb];
    Graphics offScrG = null;
    Image offScrImg;
    Dimension offScrDimension;
    int zoom;
    int mode;
    double start;
    double end;

    public DrawCanvas() {
        super();
        for (int i = 0; i < Global.sb; i++) {
            sinscroll[i] = 0.0;
            cosscroll[i] = 0.0;
        }
        zoom = 1;
        mode = 1;
        start = 0.0;
        end = Math.PI*2;
        show();
        repaint();
    }

    public void reset() {
        for (int i = 0; i < Global.sb; i++) {
            sinscroll[i] = 0.0;
            cosscroll[i] = 0.0;
        }
        repaint();
    }

    public void setSin(double scroll[]) {
        for (int i = 0; i < Global.sb; i++)
            sinscroll[i] = scroll[i];
    }

    public void setCos(double scroll[]) {
        for (int i = 0; i < Global.sb; i++)
            cosscroll[i] = scroll[i];
    }

    public void setMode(int mode) {
        this.mode = mode;
        repaint();
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
        repaint();
    }

    public void setRange(double start, double end) {
        this.start = start;
        this.end = end;
        repaint();
    }

    boolean isNewSize(Dimension d) {
        if ((offScrG == null) || (d.width != offScrDimension.width)
                           || (d.height != offScrDimension.height))
            return true;
        else
            return false;
    }

    void calculate(Graphics g, Dimension d) {
        double curve;
        int i, h, last;

        offScrG.setColor(getBackground());
        offScrG.fillRect(0, 0, d.width, d.height);
        offScrG.setColor(Color.black);

        offScrG.drawLine(10,10,10,d.height-10);
        offScrG.drawLine(5, d.height/2, d.width-5,d.height/2);

        double delta = (end - start) / (double)(d.width-5-10);

        last = d.height / 2;

        double zwi = start/delta;
        double halfheight = d.height / 2.0d;

        for (i = 10; i < d.width-5; i++) {
            curve = 0.0;
            for (int j = 0; j < Global.sb; j++) {
                if (sinscroll[j] > 0)
                    curve += Math.sin((i-10 + zwi) * delta * (j+1) ) * halfheight * sinscroll[j];

                if (cosscroll[j] > 0)
                    curve += Math.cos((i-10 + zwi) * delta * (j+1) ) * halfheight * cosscroll[j];
            }
            
            curve = curve / (double)zoom;
            curve  += halfheight;
            h = d.height - (int)Math.floor(curve+0.5d);
            
            offScrG.drawLine(i, last, i, h);
            last = h;
        }
    }

    void calculate2(Graphics g, Dimension d)
    {
        double curve;
        int i, h;
        int last[] = new int[Global.sb];

        offScrG.setColor(getBackground());
        offScrG.fillRect(0, 0, d.width, d.height);
        offScrG.setColor(Color.black);

        offScrG.drawLine(10,10,10,d.height-10);
        offScrG.drawLine(5, d.height/2, d.width-5,d.height/2);

        double delta = (end - start) / (d.width-5-10);

        for (int j = 0; j < Global.sb; j++)
            last[j] = d.height / 2;

        offScrG.setColor(Color.blue);

        double zwi = start/delta;
        double halfheight = d.height / 2.0d;

        for (i = 10; i < d.width-5; i++) {
            for (int j = 0; j < Global.sb; j++) {
                if (sinscroll[j] > 0) {
                    curve = Math.sin((i-10+zwi)*delta*(j+1))*halfheight*sinscroll[j];
                    curve = curve / zoom;
                    curve = curve + halfheight;
                    h = d.height-(int)Math.floor(curve+0.5);
                    offScrG.drawLine(i, last[j], i, h);
                    last[j] = h;
                }
            }
        }

        for (int j = 0; j < Global.sb; j++)
            last[j] = d.height/2;

        offScrG.setColor(Color.red);

        for (i = 10; i < d.width-5; i++)
        {
            for (int j = 0; j < Global.sb; j++)
            {
                if (cosscroll[j] > 0)
                {
                    curve = Math.cos((i-10+zwi)*delta*(j+1))*halfheight*cosscroll[j];
                    curve = curve / zoom;
                    curve = curve + halfheight;
                    h = d.height-(int)Math.floor(curve+0.5);
                    offScrG.drawLine(i, last[j], i, h);
                    last[j] = h;
                }
            }
        }

    }

    public void update(Graphics g)
    {
        Dimension d = size();
            if (isNewSize(d))
            {
                offScrDimension = d;
                offScrImg = createImage(d.width, d.height);
                offScrG = offScrImg.getGraphics();
            }
            if (mode == 1)
                calculate(g, d);
            else
                calculate2(g, d);

            offScrG.setColor(getBackground());
            offScrG.draw3DRect(0, 0, d.width-1, d.height-1, false);

            g.drawImage(offScrImg, 0, 0, this);
    }

    public void paint(Graphics g)
    {
        Dimension d = size();
        if (isNewSize(d))
        {
            offScrDimension = d;
            offScrImg = createImage(d.width, d.height);
            offScrG = offScrImg.getGraphics();
            if (mode == 1)
                calculate(g, d);
            else
                calculate2(g, d);

            offScrG.setColor(getBackground());
            offScrG.draw3DRect(0, 0, d.width-1, d.height-1, false);

            g.drawImage(offScrImg, 0, 0, this);
        }
        else
        {
            g.drawImage(offScrImg, 0, 0, this);
        }
    }

}

