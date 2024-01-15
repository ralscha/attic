package common.swing;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class PlafPanel extends JFrame {
	private UIManager.LookAndFeelInfo[] plafInfoArray;
	private Component testGui;
	private PlafPanel plafPanel = this;

	public PlafPanel(Component testGui) {

		this.testGui = testGui;
		plafInfoArray = UIManager.getInstalledLookAndFeels();

		for (int i = 0; i < plafInfoArray.length; i++) {
			String className = plafInfoArray[i].getClassName();
			String label = className.substring(className.lastIndexOf(".") + 1);
			JButton button = new JButton(label);
			button.setActionCommand(className);
			button.addActionListener(new ButtonActionListener());
			getContentPane().add(button);
		}

		getContentPane().setLayout(new FlowLayout());
		setTitle("Plaf Panel");
		setBounds(30, 300, 350, 120);
		setVisible(true);
	}

	public static void main(String[] args) {
		new PlafPanel(new JExitFrame());
	}

	class ButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				UIManager.setLookAndFeel(((JButton) e.getSource()).getActionCommand());
				SwingUtilities.updateComponentTreeUI(plafPanel);
				SwingUtilities.updateComponentTreeUI(testGui);
			} catch (Exception ex) {
				System.out.println(ex);
			}

		}
	}


	public static void setNativeLookAndFeel(boolean nativeLAF) {
		try {
			String plaf;
			if (nativeLAF) {
				plaf = UIManager.getSystemLookAndFeelClassName();
			} else {
				plaf = UIManager.getCrossPlatformLookAndFeelClassName();
			}
			UIManager.setLookAndFeel(plaf);
		} catch (Exception e) {
			System.out.println("Error loading Look and Feel :"+e);
		}
	}

}