
import java.awt.*;
import java.applet.*;
import com.bdnm.awt.*;

public class Layout extends Frame
{

    NumberCanvas b3;

    public Layout()
    {
        super();

        b3 = new NumberCanvas("20");

        setLayout(new BorderLayout());

        add("Center", b3);

    }

    public boolean handleEvent(Event evt)
    {
        if (evt.id == Event.WINDOW_DESTROY)
            System.exit(0);

        return super.handleEvent(evt);
    }

    public boolean mouseDown(Event evt, int x, int y)
    {
        b3.change(String.valueOf(x));
        return true;
    }

    public static void main(String args[])
    {
        Layout window = new Layout();
        window.resize(200, 100);
        window.show();

        for (int i = 0; i < 100; i++)
        {
            System.out.println((int)(Math.random() * 15));
        }
    }

}
