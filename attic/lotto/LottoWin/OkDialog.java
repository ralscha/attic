
import java.awt.*;
import java.awt.event.*;


public class OkDialog extends Dialog implements ActionListener
{

    public OkDialog(Frame dw, String message)
	{
	    super(dw, "Information", true);
    	build(message);
	}

    public OkDialog(Frame dw, String title, String message)
	{
	    super(dw, title, true);
    	build(message);
	}

    private void build(String message )
	{
    	setResizable(false);

	    GridBagLayout gb = new GridBagLayout();
    	setLayout( gb );
	    GridBagConstraints gbc = new GridBagConstraints();
    	gbc.insets = new Insets( 5, 5, 5, 5 );

    	Label label = new Label(message);
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
        gb.setConstraints(label, gbc);
    	add(label);
	    Button button = new Button("OK");
	    button.addActionListener(this);
    	gb.setConstraints(button, gbc);
	    add( button );
	    setSize(300,100);
	}


    public void actionPerformed(ActionEvent event)
    {
        setVisible(false);
    }


}
