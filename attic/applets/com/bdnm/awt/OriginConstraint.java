/*
 * @(#)OriginConstraint.java	1.01 96/04/18 Eric Lunt
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

/**
 * An OriginConstraint is used with an instance of
 * FractionalLayout to compute a Rectangle relative
 * to a containing Rectangle, given a preferred Rectangle, by
 * computing a relative origin and then adding an offset to it.
 * The extent of the computed rectangle is the preferredRectangle's
 * extent.
 *
 * After that explanation, I think a couple of examples are
 * in order.  Please see the documentation of the instance
 * variables for more information.
 * <ul>
 * <li><code>new OriginConstraint(0.0, 0, 0.0, 0)</code>
 * will place the upper-left corner of the component at the
 * upper-left corner (0,0) of the container.  The component
 * will be its preferred size.
 * <li><code>new OriginConstraint(0.0, 10, 0.5, 0)</code>
 * will place the upper-left corner of the component 10 pixels
 * down from the top of the container, and exactly halfway
 * across the panel horizontally.  The component will be its
 * preferred size.
 * </ul>
 *
 * @see FractionalLayout
 * @see AlignmentOriginConstraint
 * @see FrameConstraint
 * @version 1.01, 04/18/96
 * @author Eric Lunt (elunt@mcs.net)
 */
public class OriginConstraint {

	/**
	 * A number between 0.0 and 1.0 which represents the
	 * relative fraction (of the container) for the left
	 * edge of a component.
	 */
	public double leftFraction;

	/**
	 * Represents the number of pixels to be added as an
	 * offset to the left edge of a component.
	 */
	public int left;

	/**
	 * A number between 0.0 and 1.0 which represents the
	 * relative fraction (of the container) for the top
	 * edge of a component.
	 */
	public double topFraction;

	/**
	 * Represents the number of pixels to be added as an
	 * offset to the top edge of a component.
	 */
	public int top;

	/**
	 * Create an instanace of an OriginConstraint.  This
	 * will place the component at the upper-left of the
	 * container.
	 */
	public OriginConstraint() {
		super();
	}

	/**
	 * Create an instance of an OriginConstraint given the
	 * left fraction, the left offset, the top fraction, and
	 * the top offset.
	 * @param lf	the left fraction
	 * @param l		the left offset
	 * @param tf	the top fraction
	 * @param t		the top offset
	 */
	public OriginConstraint(double lf, int l, double tf, int t) {
		this();
		leftFraction = lf;
		left = l;
		topFraction = tf;
		top = t;
	}

	/**
	 * Given the size of a container and the component size,
	 * return a Rectangle which appropriately bounds the component
	 * given this constraint.
	 * @param containerSize	the size of the container
	 * @param componentSize	the size of the component
	 * @return	the containing rectangle
	 */
	public Rectangle adjustedRectangle(Dimension containerSize,
		Dimension componentSize) {

		Rectangle newRect = new Rectangle(componentSize);
		newRect.setLocation(adjustedLeft(containerSize),adjustedTop(containerSize));
		return newRect;
	}

	/**
	 * Given a container size, resolve the left edge of a component
	 * @param   containerSize   the size of the container
	 * @return  the pixel position of the left edge of the component
	 */
	protected int adjustedLeft(Dimension containerSize) {
		return (int) Math.round(leftFraction*containerSize.width) + left;
	}

	/**
	 * Given a container size, resolve the top edge of a component
	 * @param   containerSize   the size of the container
	 * @return  the pixel position of the top edge of the component
	 */
	protected int adjustedTop(Dimension containerSize) {
		return (int) Math.round(topFraction*containerSize.height) + top;
	}

	/**
	 * Given the size of a component, return the smallest size
	 * container which could contain it given this constraint.
	 * @param componentSize the size of a component
	 * @return the minimum size of a container to satisfy this contraint
	 */
	public Dimension containSize(Dimension componentSize) {

		Dimension dim = new Dimension();

		if (leftFraction > 0.0) {
			if (leftFraction < 1.0) {
				dim.width = (int) Math.ceil((left + componentSize.width) / (1 - leftFraction));
				int bump = (int) (leftFraction*dim.width) + left;
				if (bump < 0) {
					dim.width -= bump;
				}
			} else {
				dim.width = -left;
			}
		} else {
			dim.width = left + componentSize.width;
		}

		if (topFraction > 0.0) {
			if (topFraction < 1.0) {
				dim.height = (int) Math.ceil((top + componentSize.height) / (1 - topFraction));
				int bump = (int) (topFraction*dim.height) + top;
				if (bump < 0) {
					dim.height -= bump;
				}
			} else {
				dim.height = -top;
			}
		} else {
			dim.height = top + componentSize.height;
		}

		return dim;
	}
}