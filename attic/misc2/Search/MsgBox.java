import java.awt.*;

class MsgBox extends Dialog
{
    Button okButton;

    MsgBox(Frame dw, String title, String msg)
    {
        super(dw, title, true);
        okButton = new Button("OK");
        add("North", new Label(title));
        add("Center", new Label(msg));
        add("South", okButton);
        resize(300, 150);
        pack();

    }

    public boolean action(Event event, Object arg)
    {
        if (event.target == okButton)
        {
            hide();
            return true;
        }
        return false;
    }


    public Insets insets()
    {
        return new Insets(30, 30, 30, 30);
    }
}

