/*
 * @(#)FractionalLayout.java	1.01 96/04/18 Eric Lunt
 * Copyright (c) 1996 All Rights Reserved.
 *
 * Version history:
 * 1.01 04/18/96
 *      Formalized packaging into com.bdnm.awt.
 *      Clean-up code & comments
 * 1.0  02/28/96
 *      Initial creation of FractionalLayout, OriginConstraint,
 *          AlignmentOriginConstraint, and FrameConstraint.
 */
package com.bdnm.awt;

import java.awt.*;
import java.util.*;

/**
 * A layout manager for a container that lays out components
 * in a VisualWorks kinda way.  For each component, you can
 * specify a constraint, which must be a kind of OriginConstraint.
 * Please see the constraint classes for more information.
 *
 * @see OriginConstraint
 * @see AlignmentOriginConstraint
 * @see FrameConstraint
 * @version 1.01, 04/18/96
 * @author Eric Lunt (elunt@mcs.net)
 */
public class FractionalLayout implements LayoutManager {

	/**
	 * The keys are components, which are pointing to contraints.
	 */
	protected Hashtable components = new Hashtable();

	/**
	 * Create an instanace of a FractionalLayout
	 */
	public FractionalLayout () {
	  super();
	}

	/**
	 * Add a component and a corresponding constraint to
	 * the layout.
	 * @param comp			the component to add
	 * @param constraint	an instance of a constraint.  Note that I
	 *						do not clone the instance, so be careful.
	 */
	public void setConstraint(Component comp, OriginConstraint constraint) {
		components.put(comp, constraint);
	}

	/**
	 * Return the constraint that is associated with the given
	 * component.  If a constraint hasn't been set for the component,
	 * then give it a default OriginContraint (which places the
	 * component at the upper-right of the container).
	 * @param comp	the component to return the constraint for
	 * @return		the appropriate constraint
	 */
	public OriginConstraint getConstraint(Component comp) {
		OriginConstraint constraint = (OriginConstraint) components.get(comp);
		if (constraint == null) {
			constraint = new OriginConstraint();
			setConstraint(comp,constraint);
		}

		return constraint;
	}

	/**
	 * Adds the specified component with the specified name to the layout.
	 * @param name the name of the component (ignored)
	 * @param comp the component to be added
	 */
	public void addLayoutComponent(String name, Component comp) {
		setConstraint(comp, new OriginConstraint());
	}

	/**
	 * Removes the specified component from the layout.
	 * @param comp the component to be removed
	 */
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	/**
	 * Returns the preferred dimensions for this layout given the components
	 * in the specified panel.
	 * @param parent the component which needs to be laid out
	 * @return		 the preferred dimension for this layout
	 * @see #minimumLayoutSize
	 */
	public Dimension preferredLayoutSize(Container parent) {
		// Basically, I just need to loop through the constraints
		// and choose the biggest one
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();
		Dimension maxDimension = new Dimension();

		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			OriginConstraint constraint = getConstraint(comp);
			Dimension containSize = constraint.containSize(comp.getPreferredSize());
			maxDimension.width = Math.max(maxDimension.width, containSize.width);
			maxDimension.height = Math.max(maxDimension.height, containSize.height);
		}

		maxDimension.width += insets.left + insets.right;
		maxDimension.height += insets.top + insets.bottom;
		return maxDimension;
	}

	/**
	 * Returns the minimum dimensions for this layout given the components
	 * in the specified panel.
	 * @param parent the component which needs to be laid out
	 * @return		 the minimum dimension for this layout
	 * @see #preferredLayoutSize
	 */
	public Dimension minimumLayoutSize(Container parent) {
		// Basically, I just need to loop through the constraints
		// and choose the biggest one
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();
		Dimension maxDimension = new Dimension();

		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			OriginConstraint constraint = getConstraint(comp);
			Dimension containSize = constraint.containSize(comp.getMinimumSize());
			maxDimension.width = Math.max(maxDimension.width, containSize.width);
			maxDimension.height = Math.max(maxDimension.height, containSize.height);
		}

		maxDimension.width += insets.left + insets.right;
		maxDimension.height += insets.top + insets.bottom;
		return maxDimension;
	}

	/**
	 * Lays out the container in the specified panel.
	 * @param parent the specified component being laid out
	 * @see Container
	 */
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();
		Dimension workingSize = parent.getSize();

		workingSize.width -= insets.left + insets.right;
		workingSize.height -= insets.top + insets.bottom;

		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			OriginConstraint constraint = getConstraint(comp);
			Rectangle newBounds = constraint.adjustedRectangle(workingSize,
				comp.getPreferredSize());
			comp.setBounds(newBounds.x, newBounds.y, newBounds.width, newBounds.height);
		}
	}

}