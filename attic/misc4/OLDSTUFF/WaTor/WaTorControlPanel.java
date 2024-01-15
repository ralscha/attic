import java.awt.*;

public class WaTorControlPanel extends Panel
{
    private WaTorCanvas wtc;
    private Button pref;
    private Button restart;
    WaTorPrefDialog wtpd;

    public WaTorControlPanel(WaTorCanvas wtc)
    {
        super();
        this.wtc = wtc;

        setBackground(Color.lightGray);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(pref = new Button("Einstellungen"));
        add(restart = new Button("Restart"));
    }

    public boolean action(Event evt, Object arg)
    {
        if (evt.target == pref)
        {
            wtpd = new WaTorPrefDialog(wtc, wtc.gx, wtc.gy, wtc.fbrut, wtc.hbrut, wtc.afischinit, wtc.ahaiinit, wtc.fasten);
            wtpd.show();
            wtpd.setfirstFocus();

            return true;

        }
        else if (evt.target == restart)
        {
            wtc.stop();
            wtc.start();
            return true;
        }
        return false;
    }



}
