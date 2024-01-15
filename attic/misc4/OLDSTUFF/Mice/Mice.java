
import java.awt.*;
import java.util.*;


public class Mice extends Frame
{

    MiceCanvas mc;
    Button start, setx, sety, rand, print;
    Panel p;
    Label tx, ty;
    int sx, sy;
    Dimension d;
    boolean setXMode, setYMode;

    public static void main(String arg[])
    {
        new Mice().start();
    }

    public Mice()
    {
        super("Mice V1.0");
        setLayout(new BorderLayout(5,5));

        p = new Panel();

        p.setLayout(new FlowLayout());

        setx = new Button("Set X");
        sety = new Button("Set Y");
        rand = new Button("Random");
        start  = new Button("Start");
        print = new Button("Print");

        tx = new Label("000", Label.LEFT);
        ty = new Label("000", Label.LEFT);
        p.add(setx);
        p.add(tx);
        p.add(sety);
        p.add(ty);
        p.add(start);
        p.add(rand);
        p.add(print);

        mc = new MiceCanvas();
        add("Center", mc);
        add("South" , p);

        setXMode = false;
        setYMode = false;
        resize(500,400);
        show();
    }

    public void start()
    {
        d = mc.size();
        sx = d.width / 3;
        sy = d.height / 3;
        setLabel();
        mc.start(sx, sy);
    }

    public boolean mouseUp(Event evt, int x, int y)
    {
        if (setXMode)
        {
            setXMode = false;
            setx.enable();
        }
        else if (setYMode)
        {
            setYMode = false;
            sety.enable();
        }

        return false;
    }

    public boolean mouseMove(Event evt, int x, int y)
    {
        if (setXMode)
        {
            sx = x;
            tx.setText(String.valueOf(sx));
        }
        else if (setYMode)
        {
            d = mc.size();
            if (y <= d.height-1)
                sy = y;
            else
                sy = d.height-1;

            ty.setText(String.valueOf(sy));
        }

        return false;
    }

    public boolean action(Event evt, Object arg)
    {
        if (evt.target == start)
        {
            mc.stop();
            mc.start(sx, sy);
            return true;
        }
        else if (evt.target == print)
        {
            PrintJob pjob = getToolkit().getPrintJob(this, "Printing Test", (Properties)null);
            if (pjob != null)
            {
                Graphics pg = pjob.getGraphics();
                if (pg != null)
                {
                    mc.print(pg);
                    pg.dispose();
                }
                pjob.end();
            }
        }
        else if (evt.target == rand)
        {
            d = mc.size();
            sx = (int)(Math.random() * d.height);
            sy = (int)(Math.random() * d.height);
            setLabel();
            mc.stop();
            mc.start(sx, sy);
            return true;
        }
        else if (evt.target == setx)
        {
            setXMode = true;
            setx.disable();
            return false;
        }
        else if (evt.target == sety)
        {
            setYMode = true;
            sety.disable();
            return false;
        }

        return false;
    }

    public Insets insets()
    {
        return new Insets(5, 5, 5, 5);
    }


    private void setLabel()
    {
        tx.setText(String.valueOf(sx));
        ty.setText(String.valueOf(sy));
    }

    public boolean handleEvent(Event evt)
	{
		if (evt.id == Event.WINDOW_DESTROY && evt.target == this)
		{
			System.exit(0);
		}

		return super.handleEvent(evt);
	}



}
