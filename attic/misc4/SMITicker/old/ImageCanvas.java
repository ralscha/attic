import java.awt.*;

class ImageCanvas extends Canvas
{
    Image img;

    ImageCanvas(Image img)
    {
        super();
        this.img = img;
    }

    public Dimension getPreferredSize()
    {
        return getMinimumSize();
    }

    public Dimension getMinimumSize()
    {
        return (new Dimension(img.getWidth(this), img.getHeight(this)));
    }


    public void paint(Graphics g)
    {
        g.drawImage(img, 0, 0, this);
    }
}