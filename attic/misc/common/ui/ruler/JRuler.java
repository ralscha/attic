package common.ui.ruler;

import java.awt.Container;
import javax.swing.*;

public class JRuler extends JPanel
    implements RulerConstants
{

    public JRuler(RulerModel rulermodel)
    {
        model = rulermodel;
        setBorder(BorderFactory.createEtchedBorder());
        int i = rulermodel.getOrientation();
        setLayout(new RulerLayout(rulermodel));
        add(new RulerTicks(rulermodel));
        add(new RulerLabels(rulermodel));
    }

    public static JRuler getMetricInstance(int i, boolean flag, double d)
    {
        return new JRuler(new DefaultRulerModel(i, flag, 0, 0.0D, 0.0D, 0.0D, d, 1.0D, 30, 10, 2));
    }

    public static JRuler getEnglishInstance(int i, boolean flag, double d)
    {
        return new JRuler(new DefaultRulerModel(i, flag, 0, 0.0D, 0.0D, 0.0D, d, 1.0D, 72, 12, 4));
    }

    public static JRuler getVariableInstance(int i, boolean flag, int j, double d, double d1, double d2, double d3, double d4, int k, int l)
    {
        return new JRuler(new DefaultRulerModel(i, flag, j, d, d1, d2, d3, d4, -1, k, l));
    }

    public static JRuler getFixedInstance(int i, boolean flag, int j, double d, double d1, double d2, double d3, double d4, int k, int l, 
            int i1)
    {
        return new JRuler(new DefaultRulerModel(i, flag, j, d, d1, d2, d3, d4, k, l, i1));
    }

    public RulerModel getModel()
    {
        return model;
    }

    public void setModel(RulerModel rulermodel)
    {
        model = rulermodel;
    }

    protected RulerModel model;
}
