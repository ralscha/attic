

import java.awt.*;

/**
 * A layout manager designed to make it easy to build user interfaces based on a
 * grid metaphor.  Like the GridLayout, it is created by specifying the number of
 * rows and columns desired and each component covers only one cell.  Like the
 * GridBagLayout, however, it dynamically determines the height of each row and
 * width of each column dynamically.  The heights are determined by iterating all
 * of the elements in a given row and setting the row height to the largest
 * preferred height.  The row widths are determined in a similar fashion.
 * In effect, the layout manager packs the  widgets to their preferred size.
 * <p>
 * NOTE: This layout manager does not change the sizes of the rows and columns
 * propotionally (like the GridBagLayout and GridLayout) with the exception of the
 * last row and column, which can be optionally configured to fill to the end.
 * Instead, the rows and columns remain sized to the preferred size.
 * <p>
 * Another interesting capability of this layout manager is its ability to size
 * columns across panels.  This proves exceptionally handy when BorderPanels are
 * used to group Checkbox components.  By using a DynaGridManager, the column sizes
 * can be computed for selected columns based on the preferred sizes of the
 * components on multiple panels.  This makes the user interface exceptionally
 * clean.
 * <p>
 * All row and column numbers are 1 relative.
 */
public class DynaGridLayout implements LayoutManager {
   int hgap;
   int vgap;
   int rows;
   int cols;

   int fillStyle[][];
   boolean fillLast = false;

   DynaGridManager     mngr;
   Panel               panel_;

   /**
    * Component should fill all available space of a cell
    */
   public static final int BOTH = 0;
   /**
    * Component should fill the height of the cell but should remain at its
    * preferred width.
    */
   public static final int HEIGHT = 1;
   /**
    * Component should fill the width of the cell but should reamain at its
    * preferred height.
    */
   public static final int WIDTH = 2;
   /**
    * Component should be sized to its preferred size.
    */
   public static final int NONE = 3;

   /**
    * Creates a DynaGridLayout with the specified rows and columns.  If more
    * components are added than cells created, extra rows are added.
    * @param rows the rows (must be greater than zero)
    * @param cols the columns (must be greater than zero)
    */
   public DynaGridLayout(int rows, int cols) {
      this(rows, cols, 0, 0);
   }

   /**
    * Creates a DynaGridLayout with the specified rows, columns, horizontal gap,
    * and vertical gap. If more components are added than cells created, extra
    * rows are added.
    * @param rows the rows (must be greater than zero)
    * @param cols the columns (must be greater than zero)
    * @param hgap the horizontal gap variable
    * @param vgap the vertical gap variable
    * @exception IllegalArgumentException If the rows and columns are invalid.
    */
   public DynaGridLayout(int rows, int cols, int hgap, int vgap) {
      if ((rows <= 0) || (cols <= 0)) {
         throw new IllegalArgumentException("invalid rows,cols");
      }

      this.rows = rows;
      this.cols = cols;
      this.hgap = hgap;
      this.vgap = vgap;
      fillStyle = new int[rows][cols];
   }

   /**
    * Sets the DynaGridManager that will be used to coordinate column sizes
    * across multiple panels.  A DynaGridLayout may only have one manager at
    * a time.  Method sets the layout of the panel to this layout instance
    * and adds it to the list of panels being managed by the manager. By
    * default, no columns are managed.
    * @param p The panel for which this layout is to arrange the components
    * @param m The manager responsible for coordinating the grid column widhts.
    */
   public void setManager(Panel p, DynaGridManager m) {
      if (mngr != null) {
         unmanage();
      }

      p.setLayout(this);
      m.add(p);
      panel_ = p;
      mngr = m;
   }

   /**
    * If the layout is being managed by a DynaGridManager, it is removed from the
    * list of panels being managed.
    */
   public void unmanage() {
      if (mngr != null) {
         mngr.remove(panel_);
         mngr = null;
      }
   }

   /**
    * Gets the DynaGridManager that is coordinating the column sizes.
    */
   public DynaGridManager getManager() {
      return mngr;
   }

   /**
    * Sets the fill style for the cell. Rows and column numbers are 1 relative.
    * @param row The row number of the cell
    * @param col The column number of the cell
    * @param style The sizing style (one of BOTH, HEIGHT, WIDTH, NONE)
    */
   public void setFillStyle(int row, int col, int style) {
      fillStyle[row-1][col-1] = style;
   }

   /**
    * Gets the fill style for the cell. Rows and column numbers are 1 relative.
    * @return The sizing style (one of BOTH, HEIGHT, WIDTH, NONE)
    */
   public int getFillStyle(int row, int col) {
      return fillStyle[row-1][col-1];
   }

   /**
    * Sets the fill style for all the cells in a row.
    * @param row The row to set the style
    * @param style The sizing style (one of BOTH, HEIGHT, WIDTH, NONE)
    */
   public void setRowFillStyle(int row, int style) {
      for (int i=0; i<cols;i++) {
         fillStyle[row-1][i] = style;
      }
   }

   /**
    * Sets the fill style for all the cells in a column.
    * @param col The column to set the style
    * @param style The sizing style (one of BOTH, HEIGHT, WIDTH, NONE)
    */
   public void setColFillStyle(int col, int style) {
      for (int i=0; i<rows;i++) {
         fillStyle[i][col-1] = style;
      }
   }

   /**
    * Sets whether the cells in the last row should fill to the bottom of the
    * panel.  The default is false.
    */
   public void setFillLast(boolean f) {
      fillLast = f;
   }

   /**
    * Gets whether the cells in the last row should fill to the bottom of the
    * panel.  The default is false.
    */
   public boolean getFillLast() {
      return fillLast;
   }

   /**
    * Adds the specified component with the specified name to the layout.
    * @param name the name of the component
    * @param comp the component to be added
    */
   public void addLayoutComponent(String name, Component comp) {
   }

   /**
    * Removes the specified component from the layout. Does not apply.
    * @param comp the component to be removed
    */
   public void removeLayoutComponent(Component comp) {
   }

   /**
    * Returns the preferred dimensions for this layout given the components
    * int the specified panel.
    * @param parent the component which needs to be laid out
    * @see #minimumLayoutSize
    */
   public Dimension preferredLayoutSize(Container parent) {
      int         colsize[] = colSizes(parent, true);
      int         rowsize[] = rowSizes(parent);
      Dimension d = new Dimension();
      Insets insets = parent.getInsets();

      d.width = insets.left + insets.right + 2;
      d.height = insets.top + insets.bottom + 2;

      for (int i=0;i<colsize.length;i++) {
         d.width += colsize[i];
      }
      for (int i=0;i<rowsize.length;i++) {
         d.height += rowsize[i];
      }
      return d;
   }

   /**
    * Returns the minimum dimensions needed to layout the components
    * contained in the specified panel.
    * @param parent the component which needs to be laid out
    * @see #preferredLayoutSize
    */
   public Dimension minimumLayoutSize(Container parent) {
      return preferredLayoutSize(parent);
   }

   /**
    * Lays out the container in the specified panel.
    * @param parent the specified component being laid out
    * @see Container
    */
   public void layoutContainer(Container parent) {
      Insets insets = parent.getInsets();
      int ncomponents = parent.getComponentCount();

      if (ncomponents == 0) {
         return;
      }

      int         colsize[] = colSizes(parent, false);
      int         rowsize[] = rowSizes(parent);
      Dimension   d = numRowsAndCols(parent);
      Dimension   psize = parent.getSize();

      psize.width = psize.width - (insets.left + insets.right + 2);
      psize.height = psize.height - (insets.top + insets.bottom + 2);

      for (int c = 0, x=insets.left; c < d.width; x+=colsize[c], c++ ) {
         for (int r = 0, y=insets.top; r < d.height;y+=(rowsize[r]), r++) {
            int i = r * d.width + c;
            if (i < ncomponents) {
               Dimension csize = getComponentSize(parent, r, c, d,
                                                  colsize, rowsize);
               Component comp = parent.getComponent(i);
               comp.setBounds(x, y, csize.width, csize.height);
            }
         }
      }
   }

   Dimension getComponentSize(Container p, int r, int c, Dimension d,
                              int csize[], int rsize[])
   {
      int         i = r * d.width + c;
      Dimension   pcs = p.getComponent(i).getPreferredSize();

      switch (fillStyle[r][c]) {
         case WIDTH:
            return new Dimension(csize[c]-hgap, p.getComponent(i).getPreferredSize().height);
         case HEIGHT:
            return new Dimension(p.getComponent(i).getPreferredSize().width, rsize[r]-vgap);
         case NONE:
            return p.getComponent(i).getPreferredSize();
         case BOTH:
         default:
            return new Dimension(csize[c]-hgap, rsize[r]-vgap);
      }
   }

   int[] colSizes(Container parent, boolean preferred) {
      Dimension d = numRowsAndCols(parent);
      int  colsize[] = new int[d.width];
      Insets insets = parent.getInsets();
      int  totalw = parent.getSize().width - insets.left - insets.right - 2;
      int ncomponents = parent.getComponentCount();

      for (int c = 0; c < d.width ; c++) {
         if (mngr != null && mngr.underManagement(c+1)) {
            colsize[c] = mngr.colSize(c);
            continue;
         }

         for (int r = 0; r < d.height ; r++) {
            int i = r * d.width + c;
            if (i < ncomponents) {
               Component comp = parent.getComponent(i);
               if (comp.isVisible()) {
                  int w = comp.getPreferredSize().width+hgap+5;
                  if (w > colsize[c]) {
                     colsize[c] = w;
                  }
               }
            }
         }
      }

      if (!preferred) {
         colsize[d.width-1] = totalw;
         for (int i=0;i<colsize.length-1;i++) {
            colsize[d.width-1] -= (colsize[i] + hgap);
         }
      }

      return colsize;
   }

   int getColSize(Container p, int c) {
      Dimension d = numRowsAndCols(p);
      int ncomponents = p.getComponentCount();
      int colSize = 0;

      if (c >= d.width) {
         return colSize;
      }

      for (int r = 0; r < d.height ; r++) {
         int i = r * d.width + c;
         if (i < ncomponents) {
            Component comp = p.getComponent(i);
            if (comp.isVisible()) {
               int w =comp.getPreferredSize().width + hgap;
               if (w > colSize) {
                  colSize = w;
               }
            }
         }
      }

      return colSize;
   }

   int[] rowSizes(Container parent) {
      Dimension d = numRowsAndCols(parent);
      int rowsize[] = new int[d.height];
      Insets insets = parent.getInsets();
      int  totalh = parent.getSize().height - insets.top - insets.bottom - 4;
      int ncomponents = parent.getComponentCount();

      for (int c = 0; c < d.width ; c++) {
         for (int r = 0; r < d.height ; r++) {
            int i = r * d.width + c;
            if (i < ncomponents) {
               Component comp = parent.getComponent(i);
               if (comp.isVisible()) {
                  int h = comp.getPreferredSize().height+vgap;
                  if (h > rowsize[r]) {
                     rowsize[r] = h + vgap;
                  }
               }
            }
         }
      }

      if (fillLast && totalh > 0) {
         for (int i=0;i<rowsize.length-1;i++) {
            totalh -= (rowsize[i] + vgap);
         }

         if (totalh > 0) {
            rowsize[d.height-1] = totalh;
         }
      }

      return rowsize;
   }

   Dimension numRowsAndCols(Container parent) {
      //determine the size of each column
      int nrows = rows;
      int ncomponents = parent.getComponentCount();

      if (ncomponents == 0) {
         return new Dimension();
      }

      nrows = (ncomponents + cols - 1) / cols;

      if (nrows > rows) {
         int fs[][] = new int[nrows][cols];
         System.arraycopy(fillStyle, 0, fs, 0, fillStyle.length);
         fillStyle = fs;
         rows = nrows;
      }

      return new Dimension(cols, nrows);
   }

   /**
    * Returns the String representation of this GridLayout's values.
    */
   public String toString() {
      return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap +
      ",rows=" + rows + ",cols=" + cols + "]";
   }
}
