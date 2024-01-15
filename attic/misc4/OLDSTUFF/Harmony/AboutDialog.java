import java.awt.*;

class AboutDialog extends Dialog
{
    Button okButton;

    AboutDialog(Frame dw, String title)
    {
        super(dw, title, true);
        okButton = new Button("OK");
        add("North", new Label("Harmony 1.1", Label.CENTER));
        add("Center", new Label("(c) 1996 by Ralph Schaer, Reto Eric Koenig"));
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


