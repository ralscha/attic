/*
 * @(#)Common_examples.java 1.0 01/12/16
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_1\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */
package myprojects.common_examples;

import java.awt.*;
import java.awt.event.*;

class Common_examples extends Frame {
	
	public Common_examples() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}

	public static void main(String args[]) {
		System.out.println("Starting Common_examples...");
		Common_examples mainFrame = new Common_examples();
		mainFrame.setSize(400, 400);
		mainFrame.setTitle("Common_examples");
		mainFrame.setVisible(true);
	}
}
