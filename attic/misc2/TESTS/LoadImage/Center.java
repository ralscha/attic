
import java.awt.*;

public class Center extends Frame
{

    int frameHeight = 200;
    int frameWidth  = 200;

    public Center()
    {
        int x, y;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenD = toolkit.getScreenSize();
        System.out.println(screenD);
        x = (screenD.width - frameWidth) / 2;
        y = (screenD.height - frameHeight) / 2;
        addNotify();
        reshape(x, y, frameWidth, frameHeight);
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
	    new Center();
    }




}

