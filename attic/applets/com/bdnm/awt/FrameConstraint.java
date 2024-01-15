/*
 * @(#)FrameConstraint.java	1.01 96/04/18 Eric Lunt
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
 * A FrameConstraint is used with an instance of
 * FractionalLayout to compute a Rectangle relative to a
 * containing Rectangle by computing the relative edges and then
 * adding offsets to them.  Any preferredSize of the component
 * is ignored.
 *
 * A couple of examples:
 * <ul>
 * <li><code>new FrameConstraint(0.0, 0, 0.0, 10, 1.0, 0, 0.0, 40)</code>
 * will cause the component to be 30 pixels high (40 - 10), with the top
 * of the component 10 pixels down from the top of the container.  The
 * container's width will be the entire width of the container.
 * <li><code>new FrameConstraint(0.0, 0, 0.5, 0, 1.0, 0, 1.0, 0)</code>
 * will cause the component to take up the entire bottom half of the container.
 * </ul>
 *
 * @see FractionalLayout
 * @version 1.01, 04/18/96
 * @author Eric Lunt (elunt@mcs.net)
 */

public class FrameConstraint extends OriginConstraint {

	/**
	 * A number between 0.0 and 1.0 which represents the
	 * relative fraction (of the container) for the right
	 * edge of a component.
	 */
	public double rightFraction;

	/**
	 * Represents the number of pixels to be added as an
	 * offset to the right edge of a component.
	 */
	public int right;

	/**
	 * A number between 0.0 and 1.0 which represents the
	 * relative fraction (of the container) for the bottom
	 * edge of a component.
	 */
	public double bottomFraction;

	/**
	 * Represents the number of pixels to be added as an
	 * offset to the bottom edge of a component.
	 */
	public int bottom;

	/**
	 * Create an instanace of an FrameConstraint.  This
	 * will place all four sides of the component at the
	 * upper-left corner of the container, which isn't
	 * very useful by itself.
	 */
	public FrameConstraint() {
		super();
	}

	/**
	 * Create an instance of an FrameConstraint given the
	 * left fraction, the left offset, the top fraction, the top offset,
	 * the right fraction, the right offset, the bottom fraction, and
	 * the bottom offset.
	 * @param lf	the left fraction
	 * @param l		the left offset
	 * @param tf	the top fraction
	 * @param t		the top offset
	 * @param rf	the right fraction
	 * @param r		the right offset
	 * @param bf	the bottom fraction
	 * @param b		the bottom offset
	 */
	public FrameConstraint(double lf, int l, double tf, int t,
		double rf, int r, double bf, int b) {
		super(lf, l, tf, t);
		rightFraction = rf;
		right = r;
		bottomFraction = bf;
		bottom = b;
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
		int leftPos = adjustedLeft(containerSize);
		int topPos = adjustedTop(containerSize);
		int rightPos = adjustedRight(containerSize);
		int bottomPos = adjustedBottom(containerSize);

		return new Rectangle(leftPos, topPos, rightPos - leftPos, bottomPos - topPos);
	}

	/**
	 * Given a container size, resolve the right edge of a component
	 * @param   containerSize   the size of the container
	 * @return  the pixel position of the right edge of the component
	 */
	protected int adjustedRight(Dimension containerSize) {
		return (int) Math.round(rightFraction*containerSize.width) + right;
	}

	/**
	 * Given a container size, resolve the bottom edge of a component
	 * @param   containerSize   the size of the container
	 * @return  the pixel position of the bottom edge of the component
	 */
	protected int adjustedBottom(Dimension containerSize) {
		return (int) Math.round(bottomFraction*containerSize.height) + bottom;
	}

	/**
	 * Given the size of a component, return the smallest size
	 * container which could contain it given this constraint.
	 * @param componentSize the size of a component
	 * @return the minimum size of a container to satisfy this contraint
	 */
	public Dimension containSize(Dimension componentSize) {

		Dimension dim = new Dimension();

		if (rightFraction != leftFraction) {
			dim.width = (int) Math.ceil((componentSize.width - right + left) / (rightFraction - leftFraction));
		} else {
			dim.width = (int) Math.ceil(Math.max(-left,-right) / leftFraction);
		}

		if (topFraction != bottomFraction) {
			dim.height = (int) Math.ceil((componentSize.height - bottom + top) / (bottomFraction - topFraction));
		} else {
			dim.height = (int) Math.ceil(Math.max(-top,-bottom) / topFraction);
		}

		return dim;
	}
}