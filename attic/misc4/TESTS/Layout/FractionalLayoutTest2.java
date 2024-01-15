
import java.awt.*;
import java.applet.*;
import com.bdnm.awt.*;

public class FractionalLayoutTest2 extends Applet
{

    public void init()
    {
        super.init();

        Button b1 = new Button("50%/30%");
        Button b2 = new Button("20%/30%");
        Button b3 = new Button("30%/30%");
        Button b4 = new Button("30%/70%");
        Button b5 = new Button("70%/20%");
        Button b6 = new Button("20%/30%");
        Button b7 = new Button("20%/30%");
        Button b8 = new Button("70%/20%");


        FractionalLayout fLay = new FractionalLayout();
        setLayout(fLay);

        fLay.setConstraint(b1, new FrameConstraint(0.0,0, 0.0,0, 0.5,0, 0.3,0));
        add(b1);

        fLay.setConstraint(b2, new FrameConstraint(0.5,0, 0.0,0, 0.7,0, 0.3,0));
        add(b2);

        fLay.setConstraint(b3, new FrameConstraint(0.7,0, 0.0,0, 1.0,0, 0.3,0));
        add(b3);


        fLay.setConstraint(b4, new FrameConstraint(0.0,0, 0.3,0, 0.3,0, 1.0,0));
        add(b4);

        fLay.setConstraint(b5, new FrameConstraint(0.3,0 ,0.3,0, 1.0,0, 0.5,0));
        add(b5);

        fLay.setConstraint(b6, new FrameConstraint(0.3,0, 0.5,0, 0.5,0, 0.8,0));
        add(b6);

        fLay.setConstraint(b7, new FrameConstraint(0.8,0, 0.5,0, 1.0,0, 0.8,0));
        add(b7);

        fLay.setConstraint(b8, new FrameConstraint(0.3,0, 0.8,0, 1.0,0, 1.0,0));
        add(b8);

    }
}
