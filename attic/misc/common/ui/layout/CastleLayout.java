package common.ui.layout;

import java.util.Hashtable;
import java.io.Serializable;
import java.awt.LayoutManager2;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Insets;

/**
 * CastleLayout allows a component to occupy a central area
 * with surrounding members named "North", "South", "East",
 * "West", "NorthEast", "NorthWest", "SouthEast" & "SouthWest".
 *
 * The "North", "South", "East", "West", "NorthEast", NorthWest",
 * "SouthEast" and "SouthWest" components get laid out according
 * to the larger of their preferred sizes and the constraints of
 * the container's size. The "Center" component will get any
 * space remaining.
 *
 * @version 1.0
 * @author Claude Duguay
 */

public class CastleLayout extends AbstractLayout implements LayoutManager2, Serializable {
	public static final String EAST = "East";
	public static final String WEST = "West";
	public static final String NORTH = "North";
	public static final String SOUTH = "South";
	public static final String CENTER = "Center";
	public static final String NORTHEAST = "NorthEast";
	public static final String NORTHWEST = "NorthWest";
	public static final String SOUTHEAST = "SouthEast";
	public static final String SOUTHWEST = "SouthWest";

	protected Hashtable table = new Hashtable();

	private static final int PREFERRED = 1;
	private static final int MINIMUM = 0;

	/**
	 * Constructs a CastleLayout with no gaps between components.
	**/
	public CastleLayout() {
		super();
	}

	/**
	  * Constructs a CastleLayout with the specified gaps.
	  * @param hgap The horizontal gap
	  * @param vgap The vertical gap
	  */
	public CastleLayout(int hgap, int vgap) {
		super(hgap, vgap);
	}

	// ----------------------
	// Private helper methods
	// ----------------------

	private Component getComponent(String name) {
		if (!table.containsKey(name))
			name = CENTER;
		return (Component) table.get(name);
	}

	private boolean isVisible(String name) {
		if (!table.containsKey(name))
			return false;
		return getComponent(name).isVisible();
	}

	private void setBounds(String name, int x, int y, int w, int h) {
		if (!isVisible(name))
			return;
		getComponent(name).setBounds(x, y, w, h);
	}

	private Dimension getSize(int type, String name) {
		if (!isVisible(name))
			return new Dimension(0, 0);
		if (type == PREFERRED)
			return getComponent(name).getPreferredSize();
		if (type == MINIMUM)
			return getComponent(name).getMinimumSize();
		return new Dimension(0, 0);
	}

	/**
	  * Adds the specified component to the layout, using the
	  * specified constraint object.
	  * @param comp The component to be added
	  * @param name The name of the component position
	 **/
	public void addLayoutComponent(Component comp, Object name) {
		if (!(name instanceof String)) {
			throw new IllegalArgumentException("constraint object must be a valid name");
		}
		// Assume a null name is the same as "Center"
		if (name == null)
			name = CENTER;
		table.put((String) name, comp);
	}

	/**
	  * Removes the specified component from the layout.
	  * @param comp The component to be removed
	  */
	public void removeLayoutComponent(Component comp) {
		table.remove(comp);
	}

	/**
	  * Returns an Insets object indicating sized edge for
	  * the minimum and preferred layouts.
	  * @param type The type, either PREFERRED or MINIMUM
	 **/
	private Insets calculateLayoutSize(int type) {
		Dimension size = new Dimension(0, 0);

		int northHeight = 0;
		int southHeight = 0;
		int eastWidth = 0;
		int westWidth = 0;

		size = getSize(type, NORTH);
		northHeight = Math.max(northHeight, size.height);

		size = getSize(type, SOUTH);
		southHeight = Math.max(southHeight, size.height);

		size = getSize(type, EAST);
		eastWidth = Math.max(eastWidth, size.width);

		size = getSize(type, WEST);
		westWidth = Math.max(westWidth, size.width);

		size = getSize(type, NORTHWEST);
		northHeight = Math.max(northHeight, size.height);
		westWidth = Math.max(westWidth, size.width);

		size = getSize(type, SOUTHWEST);
		southHeight = Math.max(southHeight, size.height);
		westWidth = Math.max(westWidth, size.width);

		size = getSize(type, NORTHEAST);
		northHeight = Math.max(northHeight, size.height);
		eastWidth = Math.max(eastWidth, size.width);

		size = getSize(type, SOUTHEAST);
		southHeight = Math.max(southHeight, size.height);
		eastWidth = Math.max(eastWidth, size.width);

		return new Insets(northHeight, westWidth, southHeight, eastWidth);
	}

	/**
	  * Returns the minimum dimensions needed to layout the
	  * components contained in the specified target container.
	  * @param target The Container on which to do the layout
	  */
	public Dimension minimumLayoutSize(Container target) {
		Dimension size, dim = new Dimension(0, 0);
		size = getSize(MINIMUM, CENTER);
		dim.width += size.width;
		dim.height += size.height;

		Insets edge = calculateLayoutSize(MINIMUM);
		dim.width += edge.right + hgap;
		dim.width += edge.left + hgap;
		dim.height += edge.top + hgap;
		dim.height += edge.bottom + hgap;

		Insets insets = target.getInsets();
		dim.width += insets.left + insets.right;
		dim.height += insets.top + insets.bottom;
		return dim;
	}

	/**
	  * Returns the preferred dimensions for this layout given the
	  * components in the specified target container.
	  * @param target The component which needs to be laid out
	  */
	public Dimension preferredLayoutSize(Container target) {

		Dimension size, dim = new Dimension(0, 0);
		size = getSize(PREFERRED, CENTER);
		dim.width += size.width;
		dim.height += size.height;

		Insets edge = calculateLayoutSize(PREFERRED);
		dim.width += edge.right + hgap;
		dim.width += edge.left + hgap;
		dim.height += edge.top + hgap;
		dim.height += edge.bottom + hgap;

		Insets insets = target.getInsets();
		dim.width += insets.left + insets.right;
		dim.height += insets.top + insets.bottom;
		return dim;
	}

	/**
	  * Lays out the specified container. This method will actually
	  * reshape the components in the specified target container in
	  * order to satisfy the constraints of the layout object.
	  * @param target The component being laid out
	  */
	public void layoutContainer(Container target) {
		Insets insets = target.getInsets();
		Insets edge = calculateLayoutSize(PREFERRED);

		int top = insets.top;
		int bottom = target.getSize().height - insets.bottom;
		int left = insets.left;
		int right = target.getSize().width - insets.right;

		setBounds(NORTH, left + edge.left + hgap, top,
          		right - edge.left - hgap - edge.right - hgap, edge.top);
		setBounds(SOUTH, left + edge.left + hgap, bottom - edge.bottom,
          		right - edge.left - hgap - edge.right - hgap, edge.bottom);
		setBounds(EAST, right - edge.right, top + edge.top + vgap, edge.right,
          		bottom - edge.top - vgap - edge.bottom - vgap);
		setBounds(WEST, left, top + edge.top + vgap, edge.left,
          		bottom - edge.top - vgap - edge.bottom - vgap);
		setBounds(NORTHWEST, left, top, edge.left, edge.top);
		setBounds(SOUTHWEST, left, bottom - edge.bottom, edge.left, edge.bottom);
		setBounds(NORTHEAST, right - edge.right, top, edge.right, edge.top);
		setBounds(SOUTHEAST, right - edge.right, bottom - edge.bottom, edge.right, edge.bottom);

		// Center is what remains
		top += edge.top + vgap;
		bottom -= edge.bottom + vgap;
		left += edge.left + hgap;
		right -= edge.right + hgap;
		setBounds(CENTER, left, top, right - left, bottom - top);
	}

}