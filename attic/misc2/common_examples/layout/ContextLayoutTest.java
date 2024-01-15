package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

import common.ui.layout.*;

public class ContextLayoutTest extends JPanel implements ActionListener {
	JButton button1, button2, button3, button4, button5, button6;

	public ContextLayoutTest() {
		setLayout(new ContextLayout(5, 5));

		button1 = new JButton("Blue");
		button2 = new JButton("Green");
		button3 = new JButton("Red");
		button4 = new JButton("White");
		button5 = new JButton("Orange");
		button6 = new JButton("Black");

		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);

		JPanel center1 = new JPanel();
		center1.setBackground(Color.blue);
		//center1.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		JPanel center2 = new JPanel();
		center2.setBackground(Color.green);
		JPanel center3 = new JPanel();
		center3.setBackground(Color.red);
		JPanel center4 = new JPanel();
		center4.setBackground(Color.white);
		JPanel center5 = new JPanel();
		center5.setBackground(Color.orange);
		JPanel center6 = new JPanel();
		center6.setBackground(Color.black);

		add(button1, center1);
		add(button2, center2);
		add(button3, center3);
		add(button4, center4);
		add(button5, center5);
		add(button6, center6);

		((ContextLayout) getLayout()).setIndex(this, 1);
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == button1)
			((ContextLayout) getLayout()).setIndex(this, 1);
		if (source == button2)
			((ContextLayout) getLayout()).setIndex(this, 2);
		if (source == button3)
			((ContextLayout) getLayout()).setIndex(this, 3);
		if (source == button4)
			((ContextLayout) getLayout()).setIndex(this, 4);
		if (source == button5)
			((ContextLayout) getLayout()).setIndex(this, 5);
		if (source == button6)
			((ContextLayout) getLayout()).setIndex(this, 6);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Table Panel");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new ContextLayoutTest());
		frame.setBounds(0, 0, 100, 400);
		frame.setDefaultCloseOperation(3);
		//frame.pack();
		frame.show();
	}

}