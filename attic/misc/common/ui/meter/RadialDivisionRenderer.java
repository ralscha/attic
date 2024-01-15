package common.ui.meter;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public interface RadialDivisionRenderer
{
  public JComponent getRadialDivisionRendererComponent(
    int minimumValue, int maximumValue,
    int normalValue, int dangerValue,
    Point centerPoint,
    int insideRadius, int outsideRadius,
    Hashtable labels, boolean insideLabels,
    int startAngle, int extentAngle);
}

