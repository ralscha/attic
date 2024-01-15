/*
 * Copyright (c) 1997 by Arthur Do <arthur@cs.stanford.edu>. All Rights Reserved.
 *
 * Please refer to the file "license.txt" important copyright and licensing 
 * information.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package adc.parser;

import java.util.*;

/**
 * <p>TableCell repesents a table cell. Normally its elements are
 * a collection of markup and text data. However, if <i>isSpanned()</i>
 * is true, then it is a pseudo cell, a place holder cell that
 * contains the location of the actual cell. Pseudo cells
 * have no elements, i.e. size() returns zero. An example
 * of a pseudo cell would be:
 *
 * <p><blockquote>
 * &lt;table&gt;<br>
 * &lt;tr&gt;&lt;td colspan=2&gt;abc<br>
 * &lt;/table&gt;
 * </blockquote>
 *
 * <p>In this case, the cell at coordinate [0,0] is a real cell
 * while [0,1] is a pseudo cell.
 *
 * <p><ul>
 * <li> 02/09/98 Dr. Jaron Collis, added getCharacterData() which
 * returns only the cell's character data, without any markup.
 * </ul>
 *
 * @version 0.9 01/32/98
 * @author Arthur Do <arthur@cs.stanford.edu>
 * @see     adc.parser.Table
 */
public class TableCell
{
	public TableCell(int rowspan, int colspan, HtmlTag cellTag)
	{
		m_rowspan = rowspan;
		m_colspan = colspan;
		m_cellTag = cellTag;
	}

	/**
	 * Use this constructor to create a pseudo cell. This is a 
	 * placeholder for a cell that has been spanned. 
	 * Call <i>getRowSpan()</i> and <i>getColumnSpan()</i> to locate
	 * the coordinate of the actual cell.
	 */
	public TableCell(int row, int col)
	{
		m_spanned = true;
		m_rowspan = row;
		m_colspan = col;
	}

	/**
	 * @return	whether this is a spanned (pseudo) cell.
	 */
	public boolean isSpanned()
	{
		return m_spanned;
	}

	/**
	 * @return	if <i>isSpanned()</i> is true then function
	 *			returns the row of the actual cell that
	 *			spans this pseudo cell otherwise it returns
	 *			the number of rows this cell spans.
	 */
	public int getRowSpan()
	{
		return m_rowspan;
	}

	/**
	 * @return	if <i>isSpanned()</i> is true then function
	 *			returns the column of the actual cell that
	 *			spans this pseudo cell otherwise it returns
	 *			the number of columns this cell spans.
	 */
	public int getColSpan()
	{
		return m_colspan;
	}

	/**
	 * @return	the original &lt;TD&gt; tag for this cell.
	 */
	public HtmlTag getCellTag()
	{
		return m_cellTag;
	}

	/**
	 * @return	the number of elements.
	 */
	public int size()
	{
		return m_content.size();
	}

	/**
	 * @param	obj  element to add
	 */
	public void addElement(Object obj)
	{
		m_content.addElement(obj);
	}

	/**
	 * @param	index  index of element
	 * @return	a cell element, normally this will be either
	 *			a String, HtmlTag, or HtmlTable object (comments are thrown
	 *			away) but new object types could be added in the 
	 *			future. Use <i>instanceof</i>
	 *			to determine the particular object type.
	 */
	public Object elementAt(int index)
	{
		return m_content.elementAt(index);
	}

	/**
	 * @return	only the character data, i.e. no markup
	 */
	public String getCharacterData()
	{
		String content = "";
		for (int i=0; i < m_content.size(); i++)
		{
			Object obj = m_content.elementAt(i);
			if (obj instanceof String)
				content += obj;
		}
		return content;
	}

	private int m_rowspan = 0;
	private int m_colspan = 0;
	private Vector m_content = new Vector();
	private HtmlTag m_cellTag = null;
	private boolean m_spanned = false;
}
