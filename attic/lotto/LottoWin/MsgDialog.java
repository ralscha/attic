
import java.awt.*;
import java.awt.event.*;


public class MsgDialog extends Dialog
{

    public MsgDialog(Frame dw, String message)
	{
	    super(dw, "Information", true);
    	build(message);
	}

    public MsgDialog(Frame dw, String title, String message)
	{
	    super(dw, title, true);
    	build(message);
	}

	public MsgDialog(Frame dw, String title, String message, boolean mode)
	{
	    super(dw, title, mode);
	    build(message);
	}


    private void build(String message )
	{
    	setResizable(false);

        setLayout(new BorderLayout());
        Label label = new Label(message, Label.CENTER);
        add("Center", label);
        setSize(200,100);
	}

}
