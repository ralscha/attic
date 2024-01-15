import java.awt.*;

public class MyCanvas extends Canvas
{

    Font f, font;
    String s;
    Dimension old = new Dimension();
    FontMetrics fontMetrics;
    boolean textFits;

    public MyCanvas()
    {
        super();
        s = "1";
    }

    public MyCanvas(String s)
    {
        super();
        this.s = s;

    }


    public void paint(Graphics g)
    {
        Dimension d = size();
        if ((d.width != old.width) || (d.height != old.height))
        {
            old.width = d.width;
            old.height = d. height;

            f = new Font("Helvetica", Font.PLAIN, d.height/2);
            setFont(f);

        }

        g.setColor(getBackground());
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.black);

        g.drawString(s, 10, size().height-10);
    }
}