
import java.awt.*;
import java.util.*;


public class Biester extends Frame
{
    BiesterCanvas bc;

    public static void main(String arg[])
    {
        new Biester();
    }

    public Biester()
    {
        super("Biester V1.0");
        setLayout(new BorderLayout(5,5));
        bc = new BiesterCanvas();
        add("Center",bc);

        resize(500,400);
        show();
    }
 

    public boolean handleEvent(Event evt)
	{
		if (evt.id == Event.WINDOW_DESTROY && evt.target == this)
		{
			System.exit(0);
		}

		return super.handleEvent(evt);
	}



}
