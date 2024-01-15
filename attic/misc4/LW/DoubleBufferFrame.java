import java.awt.*;

public class DoubleBufferFrame extends Frame
{
    private Image offscreen;

    public Dimension getPreferredSize()
    {
        return new Dimension(100,100);
    }

    public void paint(Graphics g)
    {
        if (offscreen == null)
        {
            offscreen = createImage(getSize().width, getSize().height);
        }
        Graphics og = offscreen.getGraphics();
        og.setClip(g.getClip());
        og.clearRect(0,0,getSize().width,getSize().height);
        super.paint(og);
        g.drawImage(offscreen,0,0,null);
        og.dispose();
    }

    public void invalidate()
    {
        super.invalidate();
        offscreen = null;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

}