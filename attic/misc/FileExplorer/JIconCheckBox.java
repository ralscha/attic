

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class JIconCheckBox extends JPanel implements MouseListener, ActionListener {

	private JTriStateCheckBox checkBox;
	private JLabel label;
	private JPopupMenu popup; 
	private JMenuItem selectMenuItem, deselectMenuItem;
	
	private static final String SELECT_CMD = "select";
	private static final String DESELECT_CMD = "deselect";
	
	public JIconCheckBox() {
		this(null, null);
	}

	public JIconCheckBox(Icon icon, String text) {
		setLayout(new BorderLayout(0, 0));
		checkBox = new JTriStateCheckBox();
		label = new JLabel(text, icon, SwingConstants.LEFT);
		label.addMouseListener(this);

		add(checkBox, BorderLayout.WEST);
		add(label, BorderLayout.EAST);

		popup = new JPopupMenu();
		selectMenuItem = new JMenuItem("select");
		selectMenuItem.setActionCommand(SELECT_CMD);
		selectMenuItem.addActionListener(this);
		popup.add(selectMenuItem);
		deselectMenuItem = new JMenuItem("deselect");
		deselectMenuItem.setActionCommand(DESELECT_CMD);
		deselectMenuItem.addActionListener(this);
		popup.add(deselectMenuItem);

		//addMouseListener(new PopupListener());
		
		//setSize(getPreferredSize());
	}

	public void setBackground(Color color) {
		if (label != null)
			label.setBackground(color);
		if (checkBox != null)
			checkBox.setBackground(color);
		super.setBackground(color);
	}


	public void setIcon(Icon icon) {
		label.setIcon(icon);
	}

	public void setText(String text) {
		label.setText(text);
	}

	public void setSelected(int state) {
		checkBox.setSelected(state);
	}

	public void setSelected(boolean selected) {
		checkBox.setSelected(selected);
	}

	public int getState() {
		return checkBox.getState();
	}

	public void actionPerformed(ActionEvent ae) {
		String cmd = ae.getActionCommand();
		if (cmd.equals(SELECT_CMD)) {
			checkBox.setSelected(JTriStateCheckBox.FULLY_SELECTED);
		} else {
			checkBox.setSelected(JTriStateCheckBox.DESELECTED);
		}
		
	}

	public void mouseClicked(MouseEvent me) {
		//System.out.println(me);
	}

	public void mouseEntered(MouseEvent me) { }
	public void mouseExited(MouseEvent me) { }
	public void mouseReleased(MouseEvent me) { }
	public void mousePressed(MouseEvent me) { }


	public Dimension getPreferredSize() {
		Dimension d1 = checkBox.getPreferredSize();
		Dimension d2 = label.getPreferredSize();
		return new Dimension(d1.width + d2.width, Math.max(d1.height, d2.height));
	}

	public Dimension getMinimumSize() {
		Dimension d1 = checkBox.getMinimumSize();
		Dimension d2 = label.getMinimumSize();
		return new Dimension(d1.width + d2.width, Math.max(d1.height, d2.height));
	}

	class PopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				
				if (checkBox.isPartiallySelected() || 
					 checkBox.isFullySelected()) {
					selectMenuItem.setEnabled(false);
					deselectMenuItem.setEnabled(true);
				} else {
					selectMenuItem.setEnabled(true);
					deselectMenuItem.setEnabled(false);					
				}
				
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}


}