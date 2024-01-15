package common.ui.layout;

import java.awt.Insets;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * A layout manager for a container that lays out grids with
 * percentage or pixel assignments for column widths.
 *
 * @version 1.0
 * @author Claude Duguay
 */

public class ScalingLayout extends AbstractLayout implements LayoutManager2, Serializable {
	protected Hashtable table = new Hashtable();
	protected int rows = 1;
	protected int cols = 1;

	/**
	 * Creates a grid layout with a default of one column per component,
	 * in a single row.
	 */
	public ScalingLayout() {
		super();
	}

	/**
	  * Creates a grid layout with the specified rows and columns.
	  * @param rows the rows; zero means 'any number.'
	  * @param cols the columns; zero means 'any number.'  Only one of 'rows'
	  * and 'cols' can be zero, not both.
	  * @exception IllegalArgumentException If the rows and columns are invalid.
	  */
	public ScalingLayout(int rows, int cols) {
		super();
		setRows(rows);
		setCols(cols);
	}

	/**
	  * Returns the number of rows in this layout.
	  */
	public int getRows() {
		return rows;
	}

	/**
	  * Sets the number of rows in this layout.
	  * @param rows number of rows in this layout
	  */
	public void setRows(int rows) {
		if (rows == 0) {
			throw new IllegalArgumentException("rows cannot set to zero");
		}
		this.rows = rows;
	}

	/**
	  * Returns the number of columns in this layout.
	  */
	public int getCols() {
		return cols;
	}

	/**
	  * Sets the number of cols in this layout.
	  * @param cols number of cols in this layout
	  */
	public void setCols(int cols) {
		if (cols == 0) {
			throw new IllegalArgumentException("cols cannot set to zero");
		}
		this.cols = cols;
	}

	/**
	  * Add the specified component to the layout.
	  * @param comp the component to be removed
	  */
	public void addLayoutComponent(Component comp, Object rect) {
		if (!(rect instanceof Rectangle)) {
			throw new IllegalArgumentException("constraint object must be a Rectangle");
		}
		table.put(comp, rect);
	}

	/**
	  * Removes the specified component from the layout.
	  * @param comp the component to be removed
	  */
	public void removeLayoutComponent(Component comp) {
		table.remove(comp);
	}

	/**
	  * Returns the preferred dimensions needed to layout the
	  * components contained in the specified panel.
	  * @param parent the component which needs to be laid out
	  * @see #minimumLayoutSize
	  */
	public Dimension preferredLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();
		int nrows = rows;
		int ncols = cols;

		Dimension dim;
		Component comp;
		int count = 0;
		float xUnit = 0;
		float yUnit = 0;
		float unit;

		int w = 0;
		int h = 0;
		for (int i = 0; i < ncomponents; i++) {
			comp = parent.getComponent(count);
			dim = comp.getPreferredSize();
			Rectangle rect = (Rectangle) table.get(comp);
			unit = (float) dim.width / (float) rect.width;
			if (unit > xUnit)
				xUnit = unit;
			unit = (float) dim.height / (float) rect.height;
			if (unit > yUnit)
				yUnit = unit;
			count++;
		}

		int height = (int)(yUnit * nrows);
		int width = (int)(xUnit * ncols);

		return new Dimension(insets.left + insets.right + width,
                     		insets.top + insets.bottom + height);
	}

	/**
	  * Returns the minimum dimensions needed to layout the
	  * components contained in the specified panel.
	  * @param parent the component which needs to be laid out
	  * @see #preferredLayoutSize
	  */
	public Dimension minimumLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();
		int nrows = rows;
		int ncols = cols;

		Dimension dim;
		Component comp;
		int count = 0;
		float xUnit = 0;
		float yUnit = 0;
		float unit;

		int w = 0;
		int h = 0;
		for (int i = 0; i < ncomponents; i++) {
			comp = parent.getComponent(count);
			dim = comp.getMinimumSize();
			Rectangle rect = (Rectangle) table.get(comp);
			unit = (float) dim.width / (float) rect.width;
			if (unit > xUnit)
				xUnit = unit;
			unit = (float) dim.height / (float) rect.height;
			if (unit > yUnit)
				yUnit = unit;
			count++;
		}

		int height = (int)(yUnit * nrows);
		int width = (int)(xUnit * ncols);

		return new Dimension(insets.left + insets.right + width,
                     		insets.top + insets.bottom + height);
	}

	/**
	  * Lays out the container in the specified panel.
	  * @param parent the specified component being laid out
	  * @see Container
	  */
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();
		int nrows = rows;
		int ncols = cols;

		if (ncomponents == 0)
			return;

		int w = parent.getSize().width - (insets.left + insets.right);
		int h = parent.getSize().height - (insets.top + insets.bottom);
		float ww = (float)(w - (ncols - 1) * hgap) / (float) ncols;
		float hh = (float)(h - (nrows - 1) * vgap) / (float) nrows;
		int left = insets.left;
		int top = insets.top;
		Component comp;
		Rectangle rect;
		int x, y, width, height;
		for (int i = 0; i < ncomponents; i++) {
			comp = parent.getComponent(i);
			rect = (Rectangle) table.get(comp);
			x = (int)(ww * (float) rect.x) + left;
			y = (int)(hh * (float) rect.y) + top;
			// Calculate by substraction to avoid rounding errors
			width = (int)(ww * (float)(rect.x + rect.width)) - x;
			height = (int)(hh * (float)(rect.y + rect.height)) - y;
			comp.setBounds(new Rectangle(x, y, width, height));
		}
	}

}