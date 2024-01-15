import java.awt.*;
import java.lang.*;
import java.applet.Applet;
import netscape.javascript.JSObject;
import java.util.Date;

public class Cookie extends Applet
{
    JSObject win;

    public void init()
    {

        // Get a reference to the Navigator Window
        try
        {
            win = JSObject.getWindow(this);

            Date expdate = new Date();
            fixCookieDate(expdate);
            expdate.setTime(expdate.getTime()+(24 * 60 * 60 * 1000));

            Object msg[] = new Object[6];

            System.out.println("ccpath");
            msg[0] = new String("ccpath");
            msg[1] = new String("http://www.hidaho.com/colorcenter/");
            msg[2] = new Long(expdate.getTime());
            msg[3] = null;
            msg[4] = null;
            msg[5] = null;
            win.call("SetCookie", msg);

            System.out.println("ccname");
            msg[0] = new String("ccname");
            msg[1] = new String("hIdaho Design ColorCenter");
            msg[2] = new Long(expdate.getTime());
            win.call("SetCookie", msg);

            System.out.println("tempvar");
            msg[0] = new String("tempvar");
            msg[1] = new String("This is a temporary cookie.");
            msg[2] = null;
            win.call("SetCookie", msg);

            System.out.println("ubiquitous");
            msg[0] = new String("ubiquitous");
            msg[1] = new String("This cookie will work anywhere in this domain");
            msg[2] = null;
            msg[3] = new String("/");
            win.call("SetCookie", msg);

            System.out.println("paranoid");
            msg[0] = new String("paranoid");
            msg[1] = new String("This cookie requires secure communications");
            msg[2] = new Long(expdate.getTime());
            msg[3] = new String("/");
            msg[4] = null;
            msg[5] = new Boolean(true);
            win.call("SetCookie", msg);

            System.out.println("goner");
            msg[0] = new String("goner");
            msg[1] = new String("This cookie must die!");
            msg[2] = null;
            msg[3] = null;
            msg[4] = null;
            msg[5] = null;
            win.call("SetCookie", msg);

            /*System.out.println("Delete goner");
            msg[0] = new String("goner");
            msg[1] = null;
            win.call("DeleteCookie", msg);

            System.out.println("get ccpath");
            String res;
            msg[0] = new String("ccpath");
            msg[1] = null;
            res = (String)win.call("GetCookie", msg);
            System.out.println("ccpath = " + res);

            System.out.println("get ccname");
            msg[0] = new String("ccname");
            res = (String)win.call("GetCookie", msg);
            System.out.println("ccname = " + res);

            msg[0] = new String("tempvar");
            res = (String)win.call("GetCookie", msg);
            System.out.println("tempvar = " + res);
            */
        }
        catch (Exception e)
        {
            // Don't throw exception information away, print it.
            e.printStackTrace();
        }

    }


    public void fixCookieDate(Date date)
    {
        Date base = new Date(0);
        long skew = base.getTime();
        if (skew > 0)
            date.setTime(date.getTime() - skew);
    }
}