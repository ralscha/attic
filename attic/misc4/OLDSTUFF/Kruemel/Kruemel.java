
import java.awt.*;
import java.applet.*;

public class Kruemel extends Applet
{

    KruemelCanvas kc;

    public void init()
    {
        super.init();
        setLayout(new BorderLayout());

        kc = new KruemelCanvas();

        add("Center", kc);
        kc.start();

    }

    public String getAppletInfo()
    {
        return "Kruemel V1.0 by Ralph Schaer";
    }

    public String[][] getParameterInfo()
    {
        String[][] info = {{}};        
        return info;
    }

}
