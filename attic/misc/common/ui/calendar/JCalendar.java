package common.ui.calendar;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class JCalendar extends JPanel implements ActionListener {
	protected Vector listeners = new Vector();
	protected ListSelectionModel selector;
	protected CalendarRenderer headRenderer;
	protected CalendarRenderer cellRenderer;
	protected CalendarGroup group = new CalendarGroup();
	protected JButton westArrow, eastArrow;

	public JCalendar() {
		this(Calendar.getInstance(), 1, 1, new DefaultListSelectionModel(),
     		new DefaultCalendarRenderer(true), new DefaultCalendarRenderer(false));
	}

	public JCalendar(Calendar date) {
		this(date, 1, 1, new DefaultListSelectionModel(),
     		new DefaultCalendarRenderer(true), new DefaultCalendarRenderer(false));
	}

	public JCalendar(int w, int h) {
		this(Calendar.getInstance(), w, h, new DefaultListSelectionModel(),
     		new DefaultCalendarRenderer(true), new DefaultCalendarRenderer(false));
	}

	public JCalendar(Calendar date, int w, int h, ListSelectionModel selector,
                 	CalendarRenderer headRenderer, CalendarRenderer cellRenderer) {
		this.selector = selector;
		this.headRenderer = headRenderer;
		this.cellRenderer = cellRenderer;
		group.setParent(this);
		setLayout(new GridLayout(h, w, 3, 3));
		for (int i = 0; i < (w * h); i++) {
			boolean isWest = (i == 0);
			boolean isEast = (i == (w - 1));
			int startMonth = date.get(Calendar.MONTH);
			Calendar clone = (Calendar) date.clone();
			clone.set(Calendar.MONTH, startMonth + i);
			CalendarView view = new CalendarView(clone, isWest, isEast, selector, headRenderer,
                                     			cellRenderer, group);
			view.month.addActionListener(this);
			view.month.setActive(false);
			add(view);
			if (isWest) {
				westArrow = view.getWestArrow();
				westArrow.addActionListener(this);
			}
			if (isEast) {
				eastArrow = view.getEastArrow();
				eastArrow.addActionListener(this);
			}
		}
		group.setActiveMonth(0);
	}

	public void setDate(Calendar date) {
		group.getActiveMonth().setDate(date);
	}

	public Calendar getDate() {
		return group.getActiveMonth().getDate();
	}

	public void setSelectionModel(ListSelectionModel selector) {
		this.selector = selector;
	}

	public ListSelectionModel getSelectionModel() {
		return selector;
	}

	public void setHeadRenderer(CalendarRenderer renderer) {
		headRenderer = renderer;
	}

	public CalendarRenderer getHeadRenderer() {
		return headRenderer;
	}

	public void setCellRenderer(CalendarRenderer renderer) {
		cellRenderer = renderer;
	}

	public CalendarRenderer getCellRenderer() {
		return cellRenderer;
	}

	public void setGroup(CalendarGroup group) {
		this.group = group;
	}

	public CalendarGroup getGroup() {
		return group;
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source instanceof CalendarMonth) {
			String command = event.getActionCommand();
			if (command.equals(CalendarMonth.ACTION_ENTER) ||
    				command.equals(CalendarMonth.ACTION_CLICK))
				fireActionEvent(command);
		}
	}

	public void addActionListener(ActionListener listener) {
		listeners.addElement(listener);
	}

	public void removeActionListener(ActionListener listener) {
		listeners.removeElement(listener);
	}

	public void fireActionEvent(String command) {
		ActionListener listener;
		Vector list = (Vector) listeners.clone();
		ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command);
		for (int i = 0; i < list.size(); i++) {
			listener = ((ActionListener) list.elementAt(i));
			listener.actionPerformed(event);
		}
	}
}