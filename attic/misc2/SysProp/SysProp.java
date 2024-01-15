
import java.awt.*;
import java.applet.*;

public class SysProp extends Applet
{

    static final int NUMPROPS = 9;

    String props[] = new String[NUMPROPS];
    String descr[] = {"Java class version number: ", "Java vendor-specific string: ",
                      "Java vendor URL: ", "Java version number: ",
                      "Operating system architecture: ", "Operating system name: ",
                      "Operating system version: ", "Browser: ", "Browser version: "};

    Font f1, f2;
	FontMetrics fm;
	int height;
    int maxlen;


    int maxlength()
    {
        int m = 0;

        for (int i = 0; i < NUMPROPS; i++)
        {
            if (m < fm.stringWidth(descr[i]))
                m = fm.stringWidth(descr[i]);
        }

        return m+10;
    }

    public void init()
    {
        f1 = new Font("Helvetica", Font.PLAIN, 16);
        f2 = new Font("Helvetica", Font.BOLD, 16);
        fm = getFontMetrics(f1);
	    height = fm.getHeight();
	    maxlen = maxlength();

		props[0] = System.getProperty("java.class.version");
        props[1] = System.getProperty("java.vendor");
        props[2] = System.getProperty("java.vendor.url");
        props[3] = System.getProperty("java.version");
        props[4] = System.getProperty("os.arch");
        props[5] = System.getProperty("os.name");
        props[6] = System.getProperty("os.version");
        props[7] = System.getProperty("browser");
        props[8] = System.getProperty("browser.version");
    }

    public void paint(Graphics g)
    {
        int xpos, ypos;
        xpos = 10;
        ypos = 20;

        g.setColor(getBackground());
        g.fillRect(0, 0, size().width, size().height);
        g.setColor(Color.black);
        for (int i = 0; i < NUMPROPS; i++)
        {
            g.setFont(f1);
            g.drawString(descr[i], xpos, ypos);
            xpos += maxlen;
            g.setFont(f2);
            g.drawString(props[i], xpos, ypos);
            xpos = 10;
            ypos += height;
        }
    }


}