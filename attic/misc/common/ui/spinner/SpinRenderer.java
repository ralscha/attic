package common.ui.spinner;

import java.text.*;
import java.awt.*;

public interface SpinRenderer {
	public Component getSpinCellRendererComponent(JSpinnerField spin, Object value,
        	boolean hasFocus, Format formatter, int selectedFieldID);
}