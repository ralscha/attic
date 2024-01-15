import java.awt.*;
import java.awt.event.*;
import Acme.JPM.Encoders.GifEncoder;
import java.io.*;

public class ImageTest3 extends Frame
{
    IGCanvas igc;
    Image img;

    public ImageTest3()
    {
        super("TEST");
        addWindowListener(new Exit());
        setLayout(new BorderLayout());
        add(igc = new IGCanvas(), BorderLayout.CENTER);
        setSize(200,200);
        //setVisible(true);
        addNotify();
        img = igc.createImage();
        try
        {
            OutputStream os = new FileOutputStream("test.gif");
            GifEncoder ge = new GifEncoder(img, os);
            igc.drawsomething();
            img.getSource().startProduction(ge);
            os.close();
        }

        catch (IOException ioe)
        {
            System.out.println(ioe);
        }
        System.exit(0);
    }

    public static void main(String args[])
    {
        new ImageTest3();
    }

    class Exit extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            System.exit(0);
        }
    }
}
