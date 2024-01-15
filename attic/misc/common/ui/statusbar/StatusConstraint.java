package common.ui.statusbar;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class StatusConstraint implements StatusArea, StatusConstants {
	protected boolean relative;
	protected float width;

	public StatusConstraint() {
		this(FIXED, 1);
	}

	public StatusConstraint(int width) {
		this(RELATIVE, width);
	}

	public StatusConstraint(boolean relative) {
		this(relative, 1);
	}

	public StatusConstraint(boolean relative, float width) {
		this.relative = relative;
		this.width = width;
	}

	public boolean isRelativeWidth() {
		return relative;
	}

	public float getRequiredWidth(Component component) {
		if (relative || width != 1)
			return width;
		return component.getPreferredSize().width;
	}
}

