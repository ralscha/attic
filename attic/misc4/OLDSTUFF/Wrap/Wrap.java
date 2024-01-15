
import java.awt.*;
import java.util.*;
import java.applet.*;


public class Wrap extends Applet
{

    WrapCanvas kc;
    Button wrap;
    Button clear;
    /*Button exit;*/


    public void init()
    {
        super.init();
        setLayout(new BorderLayout(5,5));
        kc = new WrapCanvas();
        add("Center",kc);

        Panel p = new Panel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        wrap = new Button("Wrap");
        clear = new Button("Clear");
        /*exit  = new Button("Exit");*/
        p.add(wrap);
        p.add(clear);
        /*p.add(exit);*/

        add("South", p);
    }


    public boolean action(Event evt, Object arg)
    {
        if (evt.target == wrap)
        {
            kc.doWrap();
            return true;
        }
        else if (evt.target == clear)
        {
            kc.clear();
            return true;
        }
        /*
        else if (evt.target == exit)
        {
            System.exit(0);
            return true;
        }
        */
        return false;
    }

    public String getAppletInfo()
    {
        return "Wrap V1.0 by Ralph Schaer";
    }

    public String[][] getParameterInfo()
    {
        String[][] info = {{}};                          ;
        return info;
    }


}
