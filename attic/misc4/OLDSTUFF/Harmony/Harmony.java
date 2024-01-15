import java.awt.*;
import com.bdnm.awt.*;

public class Harmony extends Frame
{
    private boolean inAnApplet = true;

    DrawCanvas draw;
    ScrollbarPanel sinp, cosp;
    ControlPanel controlpanel;
    Panel allPanel;

    public Harmony()
    {
        super();

        allPanel = new Panel();
        setLayout(new BorderLayout());

        FractionalLayout fLay = new FractionalLayout();
        allPanel.setLayout(fLay);

        draw = new DrawCanvas();
        sinp = new SinScrollbarPanel("Sinus", draw);
        cosp = new CosScrollbarPanel("Cosinus", draw);
        controlpanel = new ControlPanel(draw, sinp, cosp, this);

        fLay.setConstraint(draw, new FrameConstraint(0.0,5, 0.00,5, 0.8,-5, 0.8,-5));
        allPanel.add(draw);

        fLay.setConstraint(controlpanel, new FrameConstraint(0.0,5, 0.8,0, 0.8,-5, 1.0,0));
        allPanel.add(controlpanel);

        fLay.setConstraint(sinp, new FrameConstraint(0.8,0, 0.00,5, 1.0,-5, 0.5,-3));
        allPanel.add(sinp);

        fLay.setConstraint(cosp, new FrameConstraint(0.8,0, 0.5,3, 1.0,-5, 1.0,-5));
        allPanel.add(cosp);

        add("Center", allPanel);
    }


    public boolean handleEvent(Event evt)
    {
        if (evt.id == Event.WINDOW_DESTROY)
        {
            if (inAnApplet)
            {
                dispose();
                return true;
            }
            else
            {
                System.exit(0);
            }

        }
        return super.handleEvent(evt);
    }


    public static void main(String args[])
    {
        Harmony window = new Harmony();
        window.inAnApplet = false;
        window.setTitle("Harmony V1.1");
        window.resize(700, 400);
        window.show();
    }

}


