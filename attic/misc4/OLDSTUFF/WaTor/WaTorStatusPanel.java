import java.awt.*;
import com.bdnm.awt.*;
import com.taligent.widget.BorderPanel;

public class WaTorStatusPanel extends BorderPanel
{
    Label chrononen;
    Label sharks;
    Label fishs;

    public WaTorStatusPanel(int f, int h)
    {
        super(BorderPanel.OUT);
        setBackground(Color.lightGray);
        setGap(2);

        setLayout(new BorderLayout());
        Panel p = new Panel();
        FractionalLayout fLay = new FractionalLayout();
        p.setLayout(fLay);

        Panel p1 = new Panel();
        Panel p2 = new Panel();
        p1.setLayout(new GridLayout(3, 1));
        p2.setLayout(new GridLayout(3, 1));

        p1.add(new Label("Fische"));
        p2.add(fishs = new Label(String.valueOf(f),Label.RIGHT));
        p1.add(new Label("Haie"));
        p2.add(sharks = new Label(String.valueOf(h),Label.RIGHT));
        p1.add(new Label("Chrononen"));
        p2.add(chrononen = new Label("0",Label.RIGHT));

        fLay.setConstraint(p1, new FrameConstraint(0.0,0, 0.0,0, 0.7,0, 1.0,0));
        p.add(p1);
        fLay.setConstraint(p2, new FrameConstraint(0.7,0, 0.0,0, 1.0,0, 1.0,0));
        p.add(p2);
        add("Center", p);

    }

    public void setValues(int fish, int shark, int chron)
    {
        chrononen.setText(String.valueOf(chron));
        fishs.setText(String.valueOf(fish));
        sharks.setText(String.valueOf(shark));
    }

}
