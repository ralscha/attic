import java.awt.*;
import java.awt.image.*;

public class IGCanvas extends Canvas
{
    Image offImg;
    Graphics offG;

    public IGCanvas()
    {
        super();
    }

    public Image createImage()
    {
        offImg = createImage(200, 200);
        offG   = offImg.getGraphics();
        return offImg;
    }

    public void drawsomething()
    {
        if (offG == null)
        {
            System.out.println("OFFG ist null");
            return;
        }
        offG.fillRect(10,10,40,40);
        offG.setColor(Color.blue);
        offG.fillOval(50,50,30,30);
        repaint();
    }

    public void paint(Graphics g)
    {
        if (offImg != null)
        {
            g.drawImage(offImg, 0, 0, this);
        }

    }

    void clearImage()
    {
        if (offImg != null)
        {
            offG.setColor(getBackground());
            offG.fillRect(0, 0, size().width, size().height);
            offG.setColor(Color.black);
        }
    }




}