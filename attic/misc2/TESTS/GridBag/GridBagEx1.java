import java.awt.*;
import java.util.*;

public class GridBagEx1 extends Frame {

    protected void makebutton(String name,
                              GridBagLayout gridbag,
                              GridBagConstraints c)
    {
        Button button = new Button(name);
        gridbag.setConstraints(button, c);
        add(button);
    }

    public GridBagEx1()
    {
        init();
        /*pack();*/
        show();
    }

    public void init()
    {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setFont(new Font("Helvetica", Font.PLAIN, 14));
        setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.8;
        c.weighty = 0.5;
        c.gridwidth = 1;
        makebutton("Button1", gridbag, c);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        makebutton("Button2", gridbag, c);

        c.weightx = 0.2;
        c.weighty = 0.5;
        c.gridwidth = 1;
        makebutton("Button3", gridbag, c);
        c.weightx = 0.0;
        c.weighty = 0.0;
    	c.gridwidth = GridBagConstraints.REMAINDER; //end row
        makebutton("Button4", gridbag, c);



        resize(300, 100);


    }

    public static void main(String args[])
    {
	    new GridBagEx1();
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
