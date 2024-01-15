// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageCanvas.java

package COM.writeme.guyrice.awt;

import java.awt.*;
import java.net.URL;

// Referenced classes of package COM.writeme.guyrice.awt:
//            StyleEvent, StyleListener, StyleManager

public class ImageCanvas extends Canvas
    implements StyleListener
{

    public ImageCanvas(Image image1, boolean flag)
    {
        image = image1;
        sink = flag;
        constructor();
    }

    public ImageCanvas(String s, boolean flag)
    {
        image = getToolkit().getImage(s);
        sink = flag;
        constructor();
    }

    public ImageCanvas(URL url, boolean flag)
    {
        image = getToolkit().getImage(url);
        sink = flag;
        constructor();
    }

    private void constructor()
    {
        style = StyleManager.global;
        MediaTracker mediatracker = new MediaTracker(this);
        mediatracker.addImage(image, 1);
        try
        {
            mediatracker.waitForAll();
        }
        catch(InterruptedException ex) {}
        imageSize = new Dimension(image.getWidth(this), image.getHeight(this));
        if(sink)
        {
            prefSize = new Dimension(imageSize.width + 4, imageSize.height + 4);
            return;
        }
        else
        {
            prefSize = imageSize;
            return;
        }
    }

    public Image getImage()
    {
        return image;
    }

    public Dimension getMinimumSize()
    {
        return prefSize;
    }

    public Dimension getPreferredSize()
    {
        return prefSize;
    }

    public void paint(Graphics g)
    {
        Dimension dimension = getSize();
        int i;
        int j;
        int k;
        int l;
        if(dimension.width >= prefSize.width && dimension.height >= prefSize.height)
        {
            g.drawImage(image, i = (dimension.width - (k = imageSize.width)) / 2, j = (dimension.height - (l = imageSize.height)) / 2, this);
        }
        else
        {
            int i1 = dimension.width - (sink ? 4 : 0);
            int k1 = dimension.height - (sink ? 4 : 0);
            k = i1;
            l = (imageSize.height * i1) / imageSize.width;
            if(l > k1)
            {
                k = (imageSize.width * k1) / imageSize.height;
                l = k1;
            }
            g.drawImage(image, i = (dimension.width - k) / 2, j = (dimension.height - l) / 2, k, l, this);
        }
        if(sink)
        {
            int j1 = j - 2;
            int l1 = i - 2;
            int i2 = i + k + 1;
            int j2 = j + l + 1;
            g.setColor(style.getOuterLight(true));
            g.drawLine(l1, j1, i2, j1);
            g.drawLine(l1, j1, l1, j2);
            g.setColor(style.getInnerLight(true));
            g.drawLine(l1 + 1, j1 + 1, i2 - 1, j1 + 1);
            g.drawLine(l1 + 1, j1 + 1, l1 + 1, j2 - 1);
            g.setColor(style.getInnerShade(true));
            g.drawLine(l1 + 1, j2 - 1, i2 - 1, j2 - 1);
            g.drawLine(i2 - 1, j1 + 1, i2 - 1, j2 - 1);
            g.setColor(style.getOuterShade(true));
            g.drawLine(l1, j2, i2, j2);
            g.drawLine(i2, j1, i2, j2);
        }
    }

    public void addNotify()
    {
        super.addNotify();
        style.addStyleListener(this);
    }

    public void removeNotify()
    {
        image.flush();
        style.removeStyleListener(this);
        super.removeNotify();
    }

    public void styleChanged(StyleEvent styleevent)
    {
        if(styleevent.isGlobalChange())
            repaint();
    }

    private Image image;
    private Dimension imageSize;
    private Dimension prefSize;
    private StyleManager style;
    private boolean sink;
}
