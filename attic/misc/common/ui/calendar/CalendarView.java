package common.ui.calendar;


import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

public class CalendarView extends JPanel implements ActionListener {
	protected BasicArrowButton westArrow, eastArrow;
	protected CalendarTitle title;
	protected CalendarMonth month;
	protected CalendarGroup group;

	public CalendarView(Calendar date, boolean west, boolean east,
                    	ListSelectionModel selector, CalendarRenderer headRenderer,
                    	CalendarRenderer cellRenderer, CalendarGroup group) {
		this.group = group;
		setLayout(new BorderLayout());
		setBorder(new ThinBorder(ThinBorder.LOWERED));

		JPanel nav = new JPanel();
		nav.setLayout(new BorderLayout());
		nav.add(BorderLayout.CENTER, title = new CalendarTitle(formatLabel(date)));
		if (west) {
			nav.add(BorderLayout.WEST, westArrow = new ArrowButton(BasicArrowButton. WEST));
			westArrow.addActionListener(this);
		}
		if (east) {
			nav.add(BorderLayout.EAST, eastArrow = new ArrowButton(BasicArrowButton. EAST));
			eastArrow.addActionListener(this);
		}

		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		header.add(BorderLayout.NORTH, nav);
		header.add(BorderLayout.SOUTH, new CalendarHeader(headRenderer));

		add(BorderLayout.NORTH, header);
		add(BorderLayout.CENTER,
    		month = new CalendarMonth(date, selector, cellRenderer, group));
		month.addActionListener(this);
	}

	private String formatLabel(Calendar calendar) {
		String year = " " + calendar.get(Calendar.YEAR);
		switch (calendar.get(Calendar.MONTH)) {
			case 0:
				return "January" + year;
			case 1:
				return "February" + year;
			case 2:
				return "March" + year;
			case 3:
				return "April" + year;
			case 4:
				return "May" + year;
			case 5:
				return "June" + year;
			case 6:
				return "July" + year;
			case 7:
				return "August" + year;
			case 8:
				return "September" + year;
			case 9:
				return "October" + year;
			case 10:
				return "November" + year;
			default:
				return "December" + year;
		}
	}

	public BasicArrowButton getWestArrow() {
		return westArrow;
	}

	public BasicArrowButton getEastArrow() {
		return eastArrow;
	}

	public void nextMonth() {
		group.nextMonth(false);
		Calendar date = month.getDate();
		title.setText(formatLabel(date));
		repaint();
	}

	public void prevMonth() {
		group.prevMonth(false);
		Calendar date = month.getDate();
		title.setText(formatLabel(date));
		repaint();
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == month) {
			Calendar date = month.getDate();
			title.setText(formatLabel(date));
			title.repaint();
		}
		if (source == westArrow) {
			group.prevMonth(true);
		}
		if (source == eastArrow) {
			group.nextMonth(true);
		}
	}
}