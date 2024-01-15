
import java.awt.*;
import java.applet.*;

public class Slider extends Applet
{

    Label l;

    public void init()
    {
        setLayout(new BorderLayout());
        l = new Label("0", Label.CENTER);
        add("Center", l);
        add("South", new Scrollbar(Scrollbar.HORIZONTAL, 1, 0, 1, 100));
    }

    public boolean handleEvent(Event evt)
    {
        if (evt.target instanceof Scrollbar)
        {
            int v = ((Scrollbar)evt.target).getValue();
            l.setText(String.valueOf(v));
        }
        return super.handleEvent(evt);
    }

}
