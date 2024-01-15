package common.ui.borders;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class GrooveBorder extends GroupBorder implements Border, BorderConstants {
	public GrooveBorder() {
		this(RAISED, 1, 5);
	}

	public GrooveBorder(int width) {
		this(RAISED, 1, width);
	}

	public GrooveBorder(int height, int width) {
		this(RAISED, height, width);
	}

	public GrooveBorder(int type, int height, int width) {
		super();
		addBorder( new ThreeDBorder((type == RAISED) ? ThreeDBorder.RAISED :
                            		ThreeDBorder.LOWERED, height));
		addBorder(new EmptyBorder(width, width, width, width));
		addBorder( new ThreeDBorder((type == RAISED) ? ThreeDBorder.LOWERED :
                            		ThreeDBorder.RAISED, height));
	}

}
