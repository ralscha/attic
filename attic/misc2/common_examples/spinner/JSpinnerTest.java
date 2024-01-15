package spinner;


import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import common.ui.spinner.*;
import common.ui.layout.*;

public class JSpinnerTest extends JPanel implements ActionListener {
	protected JComboBox localeChoice;
	protected JSpinnerField date, time, currency, percent, number, skip, list, color;
	protected String[] localeNames = { "U.S.", "English", "French", "German" };
	protected Locale[] localeTypes = { Locale.US, Locale.ENGLISH, Locale.FRENCH,
                                   	Locale.GERMAN };

	public JSpinnerTest() {
		setLayout(new FieldLayout(4, 4));
		add(new JLabel(" Locale: "));
		add(localeChoice = new JComboBox(localeNames));
		localeChoice.addActionListener(this);
		add(new JLabel(" JSpinnerField (1-10/1): "));
		add(number = new JSpinnerField(1, 1, 1, 10, true));
		add(new JLabel(" JSpinnerField (0-8/2): "));
		add(skip = new JSpinnerField(0, 2, 0, 8, true));
		add(new JLabel(" JSpinnerDate (today): "));
		add(date = new JSpinnerDate());
		add(new JLabel(" JSpinnerTime (now): "));
		add(time = new JSpinnerTime());
		add(new JLabel(" JSpinnerPercent: "));
		add(percent = new JSpinnerPercent());
		add(new JLabel(" JSpinnerCurrency: "));
		add(currency = new JSpinnerCurrency());
		add(new JLabel(" JSpinnerList (a,b,c,d,e): "));
		add(list = new JSpinnerList(new String[]{ "alpha", "beta", "gamma", "delta", "epsilon"},
                            		0, true));
		add(new JLabel(" JSpinnerColor (r,g,b): "));
		add(color =
      		new JSpinnerColor(new Color[]{ Color.red, Color.green, Color.blue}, 0, true));
	}

	public void actionPerformed(ActionEvent event) {
		Locale locale = localeTypes[localeChoice.getSelectedIndex()];
		date.setLocale(locale);
		time.setLocale(locale);
		percent.setLocale(locale);
		currency.setLocale(locale);
		number.setLocale(locale);
		skip.setLocale(locale);
		list.setLocale(locale);
		color.setLocale(locale);
		repaint();
	}

	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);
		JFrame frame = new JFrame("JSpinner* Test");
		frame.getContentPane().add(new JSpinnerTest());
		frame.setBounds(100, 100, 250, 250);
		frame.setDefaultCloseOperation(3);
		frame.show();
	}
}