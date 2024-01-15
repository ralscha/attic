
import java.awt.*;
import java.util.*;

/**
 * Class used to coordinate the sizes of columns across panels when the panels
 * components are arranged by a DynaGridLayout manager.  Each DynaGridLayout
 * may have at most one manager.  When a container requests to have
 * its components arranged and a DynaGridLayout is being managed, it will
 * request the column sizes from the manager one at a time.  If a column
 * number is one being managed, all DynaGridLayout managers will be asked
 * their preferred column size.  The largest size returned will be used.
 * <p>
 * All column numbers are 1 relative.
 */
public class DynaGridManager implements java.io.Serializable {
    Vector          panels = new Vector(5);
    Vector          cols = new Vector(5);
    Hashtable       exclude = new Hashtable();

    void add(Panel p) {
        if (!(p.getLayout() instanceof DynaGridLayout)) {
            System.out.println("Layout mngr: " + p.getLayout());
            throw new IllegalArgumentException();
        }

        panels.addElement(p);
    }

    void remove(Panel p) {
        panels.removeElement(p);
    }

    /**
     * Places a column number under management.
     */
    public void manageCol(int col) {
        Integer c = new Integer(col-1);
        if (!cols.contains(c)) {
            cols.addElement(c);
        }
    }

    /**
     * Removes a column number from management for a specific panel.  All other
     * containers will continue to be managed.
     */
    public void excludeFromManagement(Panel p, int col) {
        Vector cols;

        if (exclude.contains(p)) {
            cols = (Vector)exclude.get(p);
        } else {
            cols = new Vector();
            exclude.put(p, cols);
        }

        cols.addElement(new Integer(col-1));
    }

    /**
     * Stops the manager from coordinating the sizes of a column for all
     * containers.
     */
    public void stopManaging(int col) {
        cols.removeElement(new Integer(col-1));
    }

    /**
     * Gets whether a column is being managed.
     */
    public boolean underManagement(int col) {
        return cols.contains(new Integer(col-1));
    }

    /**
     * Gets whether a column is being managed.
     */
    public boolean underManagement(Integer col) {
        return cols.contains(col);
    }

    int colSize(int col) {
        int     size = 0;
        Integer c = new Integer(col);

        if (!underManagement(c)) {
            throw new IllegalArgumentException();
        }

        Enumeration e = panels.elements();
        while(e.hasMoreElements()) {
            Panel p = (Panel)e.nextElement();
            if (exclude.containsKey(p)) {
                Vector cols = (Vector)exclude.get(p);
                if (cols.contains(new Integer(col))) {
                    continue;
                }
            }

            DynaGridLayout dgl = (DynaGridLayout)p.getLayout();
            size = Math.max(size, dgl.getColSize(p, col));
        }

        return size;
    }
}