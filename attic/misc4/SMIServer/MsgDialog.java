import java.awt.*;
import java.awt.event.*;


class MsgDialog extends Dialog implements ActionListener
{
    private TextField field;
    private Button okButton;

    MsgDialog(Frame dw, String title, String msg)
    {
        super(dw, title, true);
        setLayout(new BorderLayout());
        add("Center", new Label(msg, Label.CENTER));
        okButton = new Button("OK");
        okButton.addActionListener(this);
        add("South", okButton);
        setSize(300, 100);
    }


    public void actionPerformed(ActionEvent event)
    {
        setVisible(false);
    }

}