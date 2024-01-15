package layout;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import common.ui.layout.*;

public class Test extends JFrame
{
    public Test(GridLayout layout, JComponent[] component) {
        super(layout.getClass().getName());
        JPanel panel = new JPanel(layout);
        //--- code needed to add the components
        // less than using a GridBagLayout
        for (int i = 0; i < component.length; i ++) {
        	panel.add(component[i]);
        }
        //---
        panel.setBorder(new EtchedBorder());
        setContentPane(panel);
        pack();
        show();
    }


    public Test(int columns, Insets insets, JComponent[] component) {
        super(GridBagLayout.class.getName());
        GridBagLayout layout = new GridBagLayout();
        JPanel panel = new JPanel(layout);
        //--- code needed to add the components
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = insets;
        constraints.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < component.length; i ++) {
        	constraints.gridx = i % columns;
	        constraints.gridy = i / columns;
	        layout.setConstraints(component[i], constraints);
        	panel.add(component[i]);
        }
        //---
        panel.setBorder(new EtchedBorder());
        setContentPane(panel);
        pack();
        show();
    }

	// Generate n components of random sizes
	public static JComponent[] generateComponents(int n) {
		Random r = new Random(0);
		JComponent[] c = new JComponent[n];
		int m = n;
		while (m > 0) {
			int i = r.nextInt(n);
			if (c[i] == null) {
				c[i] = new JLabel("Component " + i, null, SwingConstants.CENTER);
				int w = 5 * (2 + r.nextInt(20));
				int h = 5 * (2 + r.nextInt(20));
				c[i].setPreferredSize(new Dimension(w, h));
				c[i].setBorder(new EtchedBorder());
				m --;
			}
		}
		return c;
	}

	// Generate the components for the "change-password" panel
	public static JComponent[] createPanelComponets() {
		JComponent[] c = new JComponent[6];
		c[0] = new JLabel("Login");
		c[1] = new JTextField("", 20);
		c[2] = new JLabel("Password");
		c[3] = new JPasswordField("", 20);
		c[4] = new JLabel("Re-enter Password");
		c[5] = new JPasswordField("", 20);
		return c;
	}

    public static void main(String[] args) {
        new Test(new GridLayout(4, 4, 4, 2), generateComponents(14));
        new Test(new GridLayout2(4, 4, 4, 2), generateComponents(14));
        new Test(4, new Insets(1, 2, 1, 2), generateComponents(14));

        new Test(new GridLayout(3, 2, 2, 2), createPanelComponets());
        new Test(new GridLayout2(3, 2, 2, 2), createPanelComponets());
        new Test(2, new Insets(1, 1, 1, 1), createPanelComponets());
    }
}

