package common.ui.preview;

import java.awt.*;
import java.awt.print.*;
import java.awt.event.*;
import javax.swing.*;

public class PreviewToolbar extends JToolBar implements ActionListener {
	protected JPopupMenu popup;
	protected PreviewSelector selector;
	protected PreviewButton full, one, multi, setup, print;
	protected JComboBox percent;
	protected PrinterJob job;
	protected PageFormat format;
	protected PreviewPanel panel;
	protected double scale = 1.0;

	public PreviewToolbar(PrinterJob job, PreviewPanel panel) {
		this.job = job;
		this.panel = panel;
		format = job.defaultPage();
		setLayout(new FlowLayout(FlowLayout.LEFT));

		percent = new JComboBox();
		percent.setEditable(true);
		percent.addItem("200%");
		percent.addItem("150%");
		percent.addItem("100%");
		percent.addItem("75%");
		percent.addItem("50%");
		percent.addItem("25%");
		percent.addItem("20%");
		percent.addItem("15%");
		percent.addItem("10%");
		percent.addItem("Page Width");
		percent.addItem("Whole Page");
		percent.addItem("Two Pages");
		percent.setSelectedIndex(8);
		percent.setMaximumRowCount(12);
		percent.addActionListener(this);

		add(full = new PreviewButton("FullSize", this));
		add(one = new PreviewButton("OnePage", this));
		add(multi = new PreviewButton("MultiPage", this));
		add(percent);
		add(setup = new PreviewButton("PageSetup", this));
		add(print = new PreviewButton("Print", this));

		popup = new JPopupMenu();
		popup.add(selector = new PreviewSelector(6, 4));
		selector.addActionListener(this);
		setFloatable(false);
	}

	public void actionPerformed(ActionEvent event) {
		ExtendedInsets insets = new ExtendedInsets(panel.getInsets());
		double width = insets.adjustWidth(panel.getSize().width);
		double height = insets.adjustHeight(panel.getSize().height);
		Object source = event.getSource();
		if (source == percent) {
			String item = (String) percent.getSelectedItem();
			if (item.equals("Page Width")) {
				source = full;
			}
			if (item.equals("Whole Page")) {
				source = one;
			}
			if (item.equals("Two Pages")) {
				panel.setGrid(new Dimension(2, 1));
				panel.render(format);
			}
			if (item.endsWith("%")) {
				scale = (double) Integer.parseInt(item.substring(0, item.length() - 1)) / 100.0;
				panel.setScale(scale);
				panel.render(format);
			}
		}
		if (source == full) {
			double w = format.getWidth();
			scale = width / w;
			panel.setScale(scale);
			panel.render(format);
		}
		if (source == one) {
			panel.setGrid(new Dimension(1, 1));
			panel.render(format);
		}
		if (source == multi) {
			popup.show(multi, 0, multi.getSize().height);
		}
		if (source == setup) {
			format = job.pageDialog(format);
			panel.setScale(scale);
			panel.render(format);
		}
		if (source == print) {
			job.printDialog();
		}
		if (source == selector) {
			Dimension grid = selector.getGridSize();
			panel.setGrid(grid);
			panel.render(format);
		}
	}
}

