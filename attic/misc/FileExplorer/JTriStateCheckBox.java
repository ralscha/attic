

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JTriStateCheckBox extends JCheckBox implements ItemListener {

	public final static int DESELECTED = 0;
	public final static int PARTIALLY_SELECTED = 1;
	public final static int FULLY_SELECTED = 2;

	private static Icon partIcon = null;
	private int state;

	public JTriStateCheckBox() {
		super();
		addItemListener(this);
	}

	private void setPartiallyIcon(LookAndFeel laf) {
		if (laf.getID().equals("Windows"))
			partIcon = new WindowsPartiallyCheckBoxIcon();
		else
			partIcon = new PartiallySelectedCheckBoxIcon();	
		
	}
	
	public void updateUI() {
		super.updateUI();		
		setPartiallyIcon(UIManager.getLookAndFeel());
    }
	
	
	public JTriStateCheckBox(String text, int state) {
		super(text);

		addItemListener(this);

		if (state == PARTIALLY_SELECTED) {
			setSelected(true);
			setIcon(partIcon);
			this.state = PARTIALLY_SELECTED;
		} else if (state == FULLY_SELECTED) {
			setSelected(true);
			this.state = FULLY_SELECTED;
		} else {
			this.state = DESELECTED;
		}

	}

	public JTriStateCheckBox(String text) {
		this(text, DESELECTED);
	}

	public void setSelected(int state) {
		if (state == DESELECTED) {
			setIcon(null);
			setSelected(false);
			this.state = DESELECTED;
		} else if (state == PARTIALLY_SELECTED) {
			setSelected(true);
			setIcon(partIcon);
			this.state = PARTIALLY_SELECTED;
		} else if (state == FULLY_SELECTED) {
			setIcon(null);
			setSelected(true);
			this.state = FULLY_SELECTED;
		}
	}

	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			setIcon(null);
			state = FULLY_SELECTED;
		} else if (event.getStateChange() == ItemEvent.DESELECTED) {
			setIcon(null);	
			state = DESELECTED;
		}
		
	}

	//fully selected or not selected
	public void setSelected(boolean selected) {
		setIcon(null);
		super.setSelected(selected);

		if (selected)
			this.state = FULLY_SELECTED;
		else
			this.state = DESELECTED;
	}

	//true only if fully selected
	public boolean isSelected() {
		return isFullySelected();
	}

	public boolean isFullySelected() {
		return (this.state == FULLY_SELECTED);
	}

	public boolean isPartiallySelected() {
		return (this.state == PARTIALLY_SELECTED);
	}

	public int getState() {
		return state;
	}

	private static class PartiallySelectedCheckBoxIcon extends javax.swing.plaf.metal.MetalCheckBoxIcon {
		public PartiallySelectedCheckBoxIcon() {}

		protected void drawCheck(Component c, Graphics g, int x, int y) {
			g.setColor(Color.white);
			super.drawCheck (c, g, x, y);
		}
	}

	private static class WindowsPartiallyCheckBoxIcon implements Icon {
		final static int csize = 13;
		public void paintIcon(Component c, Graphics g, int x, int y) {
			AbstractButton b = (AbstractButton) c;
			ButtonModel model = b.getModel();

			// outer bevel
			g.setColor(UIManager.getColor("CheckBox.background"));
			g.fill3DRect(x, y, csize, csize, false);

			// inner bevel
			g.setColor(UIManager.getColor("CheckBox.shadow"));
			g.fill3DRect(x + 1, y + 1, csize - 2, csize - 2, false);

			// inside box
			if ((model.isPressed() && model.isArmed()) || !model.isEnabled()) {
				g.setColor(UIManager.getColor("CheckBox.background"));
			} else {
				g.setColor(UIManager.getColor("CheckBox.highlight"));
			}
			g.fillRect(x + 2, y + 2, csize - 4, csize - 4);

			if (model.isEnabled()) {
				g.setColor(UIManager.getColor("CheckBox.darkShadow"));
			} else {
				g.setColor(UIManager.getColor("CheckBox.shadow"));
			}

			// paint check
			g.setColor(Color.lightGray);
			if (model.isSelected()) {
				g.drawLine(x + 9, y + 3, x + 9, y + 3);
				g.drawLine(x + 8, y + 4, x + 9, y + 4);
				g.drawLine(x + 7, y + 5, x + 9, y + 5);
				g.drawLine(x + 6, y + 6, x + 8, y + 6);
				g.drawLine(x + 3, y + 7, x + 7, y + 7);
				g.drawLine(x + 4, y + 8, x + 6, y + 8);
				g.drawLine(x + 5, y + 9, x + 5, y + 9);
				g.drawLine(x + 3, y + 5, x + 3, y + 5);
				g.drawLine(x + 3, y + 6, x + 4, y + 6);
			}
		}
		public int getIconWidth() {
			return csize;
		}

		public int getIconHeight() {
			return csize;
		}

	}
}