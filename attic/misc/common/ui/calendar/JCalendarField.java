
package common.ui.calendar;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

public class JCalendarField extends JPanel implements ActionListener {
	protected DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG);

	protected BasicArrowButton calendarButton;
	protected JCalendar calendar;
	protected JPopupMenu popup;
	protected JTextField field;
	protected Calendar date;

	public JCalendarField() {
		this(Calendar.getInstance());
	}

	public JCalendarField(Calendar date) {
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, field = new JTextField());
		field.setEditable(false);
		setBorder(field.getBorder());
		field.setBorder(null);

		calendarButton = new BasicArrowButton(BasicArrowButton.SOUTH);
		calendarButton.addActionListener(this);
		add(BorderLayout.EAST, calendarButton);

		calendar = new JCalendar(date, 1, 1, new DefaultListSelectionModel(),
                         		new SimpleCalendarRenderer(true), new SimpleCalendarRenderer(false));
		calendar.addActionListener(this);
		popup = new JPopupMenu();
		popup.add(calendar);
		setField(date);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == calendarButton) {
			field.requestFocus();
			getRootPane().setDefaultButton(null);
			if (!popup.isVisible()) {
				Dimension dim = calendarButton.getSize();
				calendar.setDate(date);
				popup.show(calendarButton, dim.width - popup.getPreferredSize().width,
           				dim.height);
			} else {
				popup.setVisible(false);
				field.requestFocus();
			}
		}
		if (event.getSource() == calendar) {
			setField(calendar.getDate());
			popup.setVisible(false);
			field.requestFocus();
		}
	}

	protected void setField(Calendar date) {
		this.date = date;
		field.setText(formatter.format(date.getTime()));
	}

	public void setDate(Calendar date) {
		calendar.setDate(date);
	}

	public Calendar getDate() {
		return calendar.getDate();
	}

	public void setCalendar(JCalendar calendar) {
		this.calendar = calendar;
	}

	public JCalendar getCalendar() {
		return calendar;
	}

	public void setDateFormat(DateFormat formatter) {
		this.formatter = formatter;
	}
}