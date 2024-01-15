import java.awt.*;
import com.bdnm.awt.*;

class WaTorPrefDialog extends Frame
{
    Button restartButton;
    Button closeButton;
    TextField gxtf, gytf, fbruttf, hbruttf, afischtf, ahaitf, fastentf;
    int gx, gy, fbrut, hbrut, afisch, ahai, fasten;
    ComponentList cl = new ComponentList(7);
    WaTorCanvas wtc;

    WaTorPrefDialog(WaTorCanvas wtc, int gx, int gy, int fbrut, int hbrut, int afisch, int ahai, int fasten)
    {
        super("WaTor Preference");
        this.wtc = wtc;
        this.gx = gx;
        this.gy = gy;
        this.fbrut = fbrut;
        this.hbrut = hbrut;
        this.afisch = afisch;
        this.ahai = ahai;
        this.fasten = fasten;

        setBackground(Color.lightGray);

        setLayout(new BorderLayout());
        Panel p = new Panel();
        FractionalLayout fLay = new FractionalLayout();
        p.setLayout(fLay);

        Panel p1 = new Panel();
        Panel p2 = new Panel();
        p1.setLayout(new GridLayout(7, 1));
        p2.setLayout(new GridLayout(7, 1));

        gxtf = new TextField(String.valueOf(gx), 4);
        gytf = new TextField(String.valueOf(gy), 4);
        fbruttf = new TextField(String.valueOf(fbrut), 4);
        hbruttf = new TextField(String.valueOf(hbrut), 4);
        afischtf = new TextField(String.valueOf(afisch), 4);
        ahaitf = new TextField(String.valueOf(ahai), 4);
        fastentf = new TextField(String.valueOf(fasten), 4);

        cl.addComponent(gxtf);
        cl.addComponent(gytf);
        cl.addComponent(afischtf);
        cl.addComponent(ahaitf);
        cl.addComponent(fbruttf);
        cl.addComponent(hbruttf);
        cl.addComponent(fastentf);


        Panel pa[] = new Panel[7];
        int i;
        for (i = 0; i < 7; i++)
        {
            pa[i] = new Panel();
            pa[i].setLayout(new FlowLayout());
        }

        pa[0].add(gxtf);
        pa[1].add(gytf);
        pa[2].add(afischtf);
        pa[3].add(ahaitf);
        pa[4].add(fbruttf);
        pa[5].add(hbruttf);
        pa[6].add(fastentf);

        for (i = 0; i < 7; i++)
            p2.add(pa[i]);

        p1.add(new Label("Gitterpunkte horizontal"));
        p1.add(new Label("Gitterpunkte vertikal"));
        p1.add(new Label("Fische zu Beginn"));
        p1.add(new Label("Haie zu Beginn"));

        Panel p11 = new Panel();
        p11.setLayout(new GridLayout(2,1,0,-5));
        p11.add(new Label("Chrononen die ein Fisch existieren"));
        p11.add(new Label("muss bis er einen Nachkommen hat"));
        p1.add(p11);

        Panel p12 = new Panel();
        p12.setLayout(new GridLayout(2,1,0,-5));
        p12.add(new Label("Chrononen die ein Hai existieren"));
        p12.add(new Label("muss bis er einen Nachkommen hat"));
        p1.add(p12);

        Panel p13 = new Panel();
        p13.setLayout(new GridLayout(2,1,0,-5));
        p13.add(new Label("Chrononen die ein Hai ohne"));
        p13.add(new Label("Nahrung auskommt"));
        p1.add(p13);


        fLay.setConstraint(p1, new FrameConstraint(0.0,0, 0.0,0, 0.7,0, 1.0,0));
        p.add(p1);
        fLay.setConstraint(p2, new FrameConstraint(0.7,0, 0.0,0, 1.0,0, 1.0,0));
        p.add(p2);
        add("Center", p);

        Panel p3 = new Panel();
        p3.setLayout(new FlowLayout(FlowLayout.CENTER));
        restartButton = new Button("Restart");
        closeButton = new Button("Close");
        p3.add(restartButton);
        p3.add(closeButton);

        add("South", p3);
        resize(320, 150);
        pack();

    }

    public boolean action(Event event, Object arg)
    {
        if (event.target == restartButton)
        {

            wtc.stop();

            try { gx = Integer.parseInt(gxtf.getText()); }
            catch (NumberFormatException nfe) {}

            try { gy = Integer.parseInt(gytf.getText()); }
            catch (NumberFormatException nfe) {}

            try { fbrut = Integer.parseInt(fbruttf.getText()); }
            catch (NumberFormatException nfe) {}

            try { hbrut = Integer.parseInt(hbruttf.getText()); }
            catch (NumberFormatException nfe) {}

            try { afisch = Integer.parseInt(afischtf.getText()); }
            catch (NumberFormatException nfe) {}

            try { ahai = Integer.parseInt(ahaitf.getText()); }
            catch (NumberFormatException nfe) {}

            try { fasten = Integer.parseInt(fastentf.getText()); }
            catch (NumberFormatException nfe) {}

            wtc.gx = gx;
            wtc.gy = gy;
            wtc.fbrut = fbrut;
            wtc.hbrut = hbrut;
            wtc.afischinit = afisch;
            wtc.ahaiinit = ahai;
            wtc.fasten = fasten;

            wtc.start();

            return true;
        }
        else if (event.target == closeButton)
        {
            dispose();
            return true;
        }
        return false;
    }


    public void setfirstFocus()
    {
        gxtf.requestFocus();
        gxtf.selectAll();
    }


    public boolean handleEvent(Event evt)
	{
   		Component help;

		if (evt.id == Event.WINDOW_DESTROY && evt.target == this)
		{
			dispose();
		}

        else if (evt.key == 9 && evt.id == Event.KEY_PRESS)
        {
            if (evt.shiftDown())
                help = cl.prevComponent(evt.target);
            else
                help = cl.nextComponent(evt.target);

            help.requestFocus();
            if (help instanceof TextField)
                ((TextField)help).selectAll();
        }


		return super.handleEvent(evt);
	}


    /*public Insets insets()
    {
        return new Insets(30, 30, 30, 30);
    }*/
}


