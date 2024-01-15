package common.ui.layout;

import java.awt.Insets;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.Serializable;

/**
 * A layout manager for a container that lays out grids with
 * percentage or pixel assignments for column widths.
 *
 * @version 1.0
 * @author Claude Duguay
 */

public class ProportionLayout extends AbstractLayout implements LayoutManager, Serializable {
	int[] iRows = {100};
	int[] iCols = {100};
	float[] fRows = {1.0f};
	float[] fCols = {1.0f};

	/**
	 * Creates a grid layout with a default of one column per component,
	 * in a single row.
	 */
	public ProportionLayout() {
		super();
	}

	/**
	  * Creates a proportion layout with the specified row and column
	  * count. This constructor creates evenly distributed rows and
	  * columns, which behaves like the GridLayout manager.
	  * @param rows The number of rows
	  * @param cols The number of columns
	  */
	public ProportionLayout(int rows, int cols) {
		super();
		setRows(rows);
		setCols(cols);
	}

	/**
	  * Creates a proportion layout with the specified row and column
	  * count. This constructor creates evenly distributed rows and
	  * columns, which behaves like the GridLayout manager.
	  * @param rows The number of rows
	  * @param cols The number of columns
	  */
	public ProportionLayout(int[] rows, int cols) {
		super();
		setRows(rows);
		setCols(cols);
	}

	/**
	  * Creates a proportion layout with the specified row and column
	  * count. This constructor creates evenly distributed rows and
	  * columns, which behaves like the GridLayout manager.
	  * @param rows The number of rows
	  * @param cols The number of columns
	  */
	public ProportionLayout(int rows, int[] cols) {
		super();
		setRows(rows);
		setCols(cols);
	}

	/**
	  * Creates a proportion layout with the specified rows and columns.
	  * @param rows the rows
	  * @param cols the columns
	  */
	public ProportionLayout(int[] rows, int[] cols) {
		super();
		setRows(rows);
		setCols(cols);
	}

	/**
	  * Creates a grid layout with the specified rows, columns,
	  * horizontal gap, and vertical gap.
	  * @param rows the rows; zero means 'any number.'
	  * @param cols the columns; zero means 'any number.'  Only one of 'rows'
	  * and 'cols' can be zero, not both.
	  * @param hgap the horizontal gap variable
	  * @param vgap the vertical gap variable
	  * @exception IllegalArgumentException If the rows and columns are invalid.
	  */
	public ProportionLayout(int[] rows, int[] cols, int hgap, int vgap) {
		super(hgap, vgap);
		setRows(rows);
		setCols(cols);
	}

	/**
	  * Returns the number of rows in this layout.
	  */
	public int[] getRows() {
		return iRows;
	}

	/**
	  * Sets the number of rows in this layout.
	  * @param rows number of rows in this layout
	  */
	public void setRows(int rows) {
		if (rows == 0) {
			throw new IllegalArgumentException("rows cannot set to zero");
		}
		iRows = new int[rows];
		for (int i = 0; i < rows; i++) {
			iRows[i] = 1;
		}
		setRows(iRows);
	}

	/**
	  * Sets the number of rows in this layout.
	  * @param rows number of rows in this layout
	  */
	public void setRows(int[] rows) {
		if ((rows == null) || (rows.length == 0)) {
			throw new IllegalArgumentException("rows cannot be null or zero length");
		}
		float total = 0;
		for (int i = 0; i < rows.length; i++) {
			total += rows[i];
		}
		iRows = rows;
		fRows = new float[rows.length];
		for (int i = 0; i < rows.length; i++) {
			fRows[i] = (float) iRows[i] / total;
		}
	}

	/**
	  * Returns the number of columns in this layout.
	  */
	public int[] getCols() {
		return iCols;
	}

	/**
	  * Sets the number of cols in this layout.
	  * @param cols number of cols in this layout
	  */
	public void setCols(int cols) {
		if (cols == 0) {
			throw new IllegalArgumentException("cols cannot set to zero");
		}
		iCols = new int[cols];
		for (int i = 0; i < cols; i++) {
			iCols[i] = 1;
		}
		setCols(iCols);
	}

	/**
	  * Sets the number of columns in this layout.
	  * @param cols number of columns in this layout
	  */
	public void setCols(int[] cols) {
		if ((cols == null) || (cols.length == 0)) {
			throw new IllegalArgumentException("cols cannot be null or zero length");
		}
		float total = 0;
		for (int i = 0; i < cols.length; i++) {
			total += cols[i];
		}
		iCols = cols;
		fCols = new float[cols.length];
		for (int i = 0; i < cols.length; i++) {
			fCols[i] = (float) cols[i] / total;
		}
	}

	/**
	  * Returns the preferred dimensions for this layout given the
	  * components in the specified panel.
	  * @param parent The component which needs to be laid out
	  * @see #minimumLayoutSize
	  */
	public Dimension preferredLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();
		int nrows = iRows.length;
		int ncols = iCols.length;

		Dimension dim;
		Component comp;
		int count = 0;
		float xUnit = 0;
		float yUnit = 0;
		float unit;

		for (int row = 0; row < nrows; row++) {
			for (int col = 0; col < ncols; col++) {
				if (count > ncomponents)
					break;
				else {
					comp = parent.getComponent(count);
					dim = comp.getPreferredSize();
					unit = (float) dim.width / (float) iCols[col];
					if (unit > xUnit)
						xUnit = unit;
					unit = (float) dim.height / (float) iRows[row];
					if (unit > yUnit)
						yUnit = unit;
					count++;
				}
			}
		}

		int height = 0;
		for (int row = 0; row < nrows; row++) {
			height += yUnit * iRows[row];
		}
		int width = 0;
		for (int col = 0; col < ncols; col++) {
			width += xUnit * iCols[col];
		}

		return new Dimension(insets.left + insets.right + width,
                     		insets.top + insets.bottom + height);
	}

	/**
	  * Returns the minimum dimensions needed to layout the
	  * components contained in the specified panel.
	  * @param parent The component which needs to be laid out
	  * @see #preferredLayoutSize
	  */
	public Dimension minimumLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();
		int nrows = iRows.length;
		int ncols = iCols.length;

		Dimension dim;
		Component comp;
		int count = 0;
		float xUnit = 0;
		float yUnit = 0;
		float unit;

		for (int row = 0; row < nrows; row++) {
			for (int col = 0; col < ncols; col++) {
				if (count > ncomponents)
					break;
				else {
					comp = parent.getComponent(count);
					dim = comp.getMinimumSize();
					unit = (float) dim.width / (float) iCols[col];
					if (unit > xUnit)
						xUnit = unit;
					unit = (float) dim.height / (float) iRows[row];
					if (unit > yUnit)
						yUnit = unit;
					count++;
				}
			}
		}

		int height = 0;
		for (int row = 0; row < nrows; row++) {
			height += yUnit * iRows[row];
		}
		int width = 0;
		for (int col = 0; col < ncols; col++) {
			width += xUnit * iCols[col];
		}

		return new Dimension(insets.left + insets.right + width,
                     		insets.top + insets.bottom + height);
	}

	/**
	  * Lays out the container in the specified panel.
	  * @param parent the specified component being laid out
	  * @see Container
	  */
	public void layoutContainer(Container parent) {
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0)
			return;

		Insets insets = parent.getInsets();
		int nrows = iRows.length;
		int ncols = iCols.length;
		int w = parent.getSize().width - (insets.left + insets.right);
		int h = parent.getSize().height - (insets.top + insets.bottom);

		int x = insets.left;
		for (int c = 0; c < ncols; c++) {
			int ww = (int)((float) w * fCols[c]) - hgap;
			int y = insets.top;
			for (int r = 0; r < nrows; r++) {
				int i = r * ncols + c;
				int hh = (int)((float) h * fRows[r]) - vgap;
				if (i < ncomponents) {
					parent.getComponent(i).setBounds(x, y, ww, hh);
				}
				y += hh + vgap;
			}
			x += ww + hgap;
		}
	}

}