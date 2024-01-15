import java.awt.*;

public class NumberCanvas extends Canvas
{

    Font f, font;
    String s;
    Dimension old = new Dimension();
    FontMetrics fontMetrics;
    boolean textFits;


    public NumberCanvas(String s)
    {
        super();
        this.s = s;
    }

    public void change(String s)
    {
        this.s = s;
        repaint();
    }


    public void paint(Graphics g)
    {
        Dimension d = size();
        if ((d.width != old.width) || (d.height != old.height))
        {
            old.width = d.width;
            old.height = d. height;

            f = new Font("Helvetica", Font.PLAIN, (int)(d.height/1.5));
            setFont(f);

        }

        g.setColor(getBackground());
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.black);

        g.drawString(s, 10, size().height-10);
    }
}