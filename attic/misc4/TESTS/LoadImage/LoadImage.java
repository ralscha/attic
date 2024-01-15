
import java.awt.*;
import java.awt.image.*;

public class LoadImage extends Frame
{

    public LoadImage()
    {
        ImageCanvas ic, ic2;
        Image img;
        MediaTracker tracker;
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        tracker = new MediaTracker(this);
        img = toolkit.getImage("wwf2.jpg");

        //wait until the image is in memory
        tracker.addImage(img, 0);
        try
        {
            tracker.waitForAll();
        }
        catch (InterruptedException e) { }

        ImageFilter f = new XORFilter();
        Image fim = createImage(new FilteredImageSource(img.getSource(), f));

        ic  = new ImageCanvas(fim);
        ic2 = new ImageCanvas(img);
        add("North", ic);
        add("South", ic2);
        resize(img.getWidth(this), img.getHeight(this)*3);
        show();

    }

    public boolean handleEvent(Event evt)
	{
		if (evt.id == Event.WINDOW_DESTROY && evt.target == this)
			System.exit(0);

		return super.handleEvent(evt);
	}

    public static void main(String args[])
    {
	    new LoadImage();
    }




}

class ImageCanvas extends Canvas
{
    Image img;

    public Dimension preferredSize()
    {
        return minimumSize();
    }

    public Dimension minimumSize()
    {
        return (new Dimension(img.getWidth(this), img.getHeight(this)));
    }

    ImageCanvas(Image img)
    {
        super();
        this.img = img;
    }

    public void paint(Graphics g)
    {
        g.drawImage(img, 0, 0, this);
    }
}