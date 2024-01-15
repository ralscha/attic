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

import java.io.*;
import java.util.*;
import java.awt.*;

/**
 * <p><i>Null Cells and Phantom Rows</i> 
 *
 * <p>elementAt() can return null if
 * there is no cell at the requested coordinate due to spans
 * across areas where there are no cells, for example,
 * 
 * <p><blockquote>
 * &lt;table&gt;<br>
 * &lt;tr&gt;&lt;td rowspan=2&gt;abc&lt;td&gt;def<br>
 * &lt;/table&gt;
 * </blockquote>
 *
 * <p>In this case, calling elementAt(1, 1) would return
 * null. Similarly, getRowTag() could return null if
 * the requested index is a phantom row (a row that doesn't
 * have any real cells). In the example above, the table
 * has two rows due to the rowspan but row 1 is a phantom
 * row and does not have a &lt;tr&gt; tag.
 *
 * <p><i>Implementation Note:</i> the table parser is very 
 * strict, that is, &lt;TABLE&gt;
 * can only contain &lt;TR&gt;. &lt;TR&gt; can only contain
 * &lt;TD&gt;. Any deviation from this nesting is considered
 * bad data and will be thrown away by the parser. In a future
 * release, we hope to be more forgiving.
 *
 * <p><ul>
 * <li> 02/09/98 Dr. Jaron Collis, added support for <TH> and
 * introduced a parseTable(Reader) convenience function.
 * </ul>
 *
 * @version 0.9 01/32/98
 * @author Arthur Do <arthur@cs.stanford.edu>
 * @see     adc.parser.TableCell
 */
public class Table
{
	public Table()
	{
	}

	public void parseTable(Reader data)
		throws HtmlException, IOException
	{
		HtmlStreamTokenizer tok = new HtmlStreamTokenizer(data);
		HtmlTag tag = new HtmlTag();

		while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
		{
			int ttype = tok.getTokenType();
			if (ttype == HtmlStreamTokenizer.TT_TAG)
			{
				HtmlStreamTokenizer.parseTag(tok.getStringValue(), tag);
				if (tag.getTagType() == HtmlTag.T_TABLE && !tag.isEndTag())
					parseTable(tok, new HtmlTag(tag));
			}
		}
	}

	public void parseTable(HtmlStreamTokenizer tokenizer, HtmlTag tableTag)
		throws HtmlException, IOException
	{
		m_tok = tokenizer;
		m_tableTag = tableTag;
		HtmlTag tag = new HtmlTag();

		while (nextToken() != HtmlStreamTokenizer.TT_EOF)
		{
			int ttype = getTokenType();
			if (ttype == HtmlStreamTokenizer.TT_TAG)
			{
				try
				{
					HtmlStreamTokenizer.parseTag(getStringValue(), tag);
					int tagtype = tag.getTagType();
					boolean isEndTag = tag.isEndTag();

					if (tagtype == HtmlTag.T_TR && !isEndTag)
					{
						m_rowTags.addElement(new HtmlTag(tag));
						newRow();
						if (!parseRow())
							break;
					}
					else if (tagtype == HtmlTag.T_TABLE && isEndTag)
					{
						break;
					}
					else
					{
						// otherwise, data is considered bad and thrown away
						//System.err.println("bad data " + m_tok.getLineNumber());
					}
				}
				catch (HtmlException e)
				{
				}
			}
		}

		m_tok = null;
		m_pushback = false;
		m_tokenType = 0;
		m_stringValue = null;
		m_whiteSpace = null;
		m_cell = null;
		m_row = null;

		organizeRowCol();
	}

	/**
	 * @return	the original &lt;TABLE&gt; tag for this table.
	 */
	public HtmlTag getTableTag()
	{
		return m_tableTag;
	}

	/**
	 * @param	row  the row to get
	 * @return	the original &lt;TR&gt; tag for this row or null
	 *			if this is a phantom row, i.e. a row that doesn't
	 *			have any real cells.
	 */
	public HtmlTag getRowTag(int row)
	{
		if (row < m_rowTags.size())
			return (HtmlTag)m_rowTags.elementAt(row);
		return null;
	}

	/**
	 * @return	the number of rows in this table.
	 */
	public int getRows()
	{
		return m_elements.length;
	}

	/**
	 * @return	the number of columns in this table.
	 */
	public int getColumns()
	{
		return m_elements[0].length;
	}

	/**
	 * @param	row  row to get
	 * @param	col  column to get
	 * @return	the cell located at the specified location in the table
	 *			or null if there is no cell due to uneven spans.
	 */
	public TableCell elementAt(int row, int col)
	{
		return m_elements[row][col];
	}

	private void organizeRowCol()
		throws HtmlException
	{
		// calculate max number of columns
		int maxcol = 0;
		Vector rows = m_rows;
		int sizey = rows.size();
		for (int y=0; y<sizey; y++)
		{
			int col = 0;
			Vector row = (Vector)rows.elementAt(y);
			int sizex = row.size();
			for (int x=0; x<sizex; x++)
			{
				TableCell cell = (TableCell)row.elementAt(x);
				col += cell.getColSpan();
				if (col > maxcol)
					maxcol = col;
			}
		}
		if (maxcol == 0)
			throw new HtmlException("zero columns");
		// rownum[] tracks the current row number for a particular column
		int rownum[] = new int[maxcol];
		// calculate max number of rows
		int maxrow = 0;
		for (int y=0; y<sizey; y++)
		{
			int col = 0;
			Vector row = (Vector)rows.elementAt(y);
			int sizex = row.size();
			for (int x=0; x<sizex; x++)
			{
				while (y < rownum[col])
					col++;	// skip to a column that is not spanned in the current row
				TableCell cell = (TableCell)row.elementAt(x);
				int colspan = cell.getColSpan();
				for (int i=0; i<colspan; i++)
				{
					int colnum = col+i;
					rownum[colnum] += cell.getRowSpan();
					if (rownum[colnum] > maxrow)
						maxrow = rownum[colnum];
				}
				col += colspan;
			}
		}
		if (maxrow == 0)
			throw new HtmlException("zero rows");
		for (int i=0; i<maxcol; i++)
			rownum[i] = 0;

		TableCell elements[][] = new TableCell[maxrow][maxcol];
		// for each row
		for (int y=0; y<sizey; y++)
		{
			int col = 0;
			Vector row = (Vector)rows.elementAt(y);
			int sizex = row.size();
			// for each cell
			for (int x=0; x<sizex; x++)
			{
				while (y < rownum[col])
					col++;	// skip to a column that is not spanned in the current row
				TableCell cell = (TableCell)row.elementAt(x);
				int r = rownum[col];
				int c = col;
				elements[r][c] = cell;
				int colspan = cell.getColSpan();
				// for each column this cell occupies
				for (int i=0; i<colspan; i++)
				{
					int colnum = col+i;
					int rowspan = cell.getRowSpan();
					// for each row this cell occupies
					for (int j=0; j<rowspan; j++)
					{
						if (i > 0 || j > 0)
							// create a pseudo cell
							elements[rownum[colnum] + j][colnum] = new TableCell(r, c);
					}
					// update current row number for this column
					rownum[colnum] += rowspan;
				}
				col += colspan;
			}
			row.removeAllElements();
		}
		rows.removeAllElements();

		m_elements = elements;
	}

	private boolean parseRow()
		throws IOException
	{
		boolean continueParsing = false;

		HtmlTag tag = new HtmlTag();
		while (nextToken() != HtmlStreamTokenizer.TT_EOF)
		{
			int ttype = getTokenType();

			if (ttype == HtmlStreamTokenizer.TT_TAG)
			{
				try
				{
					HtmlStreamTokenizer.parseTag(getStringValue(), tag);
					int tagtype = tag.getTagType();
					boolean isEndTag = tag.isEndTag();

					if (tagtype == HtmlTag.T_TR)
					{
						if (!isEndTag)
							pushBackToken();
						// row ended, continue with next row
						continueParsing = true;
						break;
					}
					else if (tagtype == HtmlTag.T_TD || tagtype == HtmlTag.T_TH)
					{
						if (!isEndTag)
						{
							beginCell(tag);
							if (!parseCol())
							{
								endCell();
								continueParsing = false;
								break;
							}
							endCell();
						}
					}
				}
				catch (HtmlException e)
				{
				}
			}
		}

		return continueParsing;
	}

	private boolean parseCol()
		throws IOException
	{
		boolean continueParsing = false;

		HtmlTag tag = new HtmlTag();
		while (nextToken() != HtmlStreamTokenizer.TT_EOF)
		{
			int ttype = getTokenType();

			if (ttype == HtmlStreamTokenizer.TT_TAG)
			{
				try
				{
					HtmlStreamTokenizer.parseTag(getStringValue(), tag);
					int tagtype = tag.getTagType();
					boolean isEndTag = tag.isEndTag();

					if (tagtype == HtmlTag.T_TR)
					{
						if (!isEndTag)
							pushBackToken();
						// column ended
						continueParsing = true;
						break;
					}
					else if (tagtype == HtmlTag.T_TD || tagtype == HtmlTag.T_TH)
					{
						if (!isEndTag)
							pushBackToken();
						// column ended
						continueParsing = true;
						break;
					}
					else if (tagtype == HtmlTag.T_TABLE)
					{
						if (isEndTag)
						{
							continueParsing = false;
							break;
						}
						Table table = new Table();
						table.parseTable(m_tok, new HtmlTag(tag));
						addToCell(table);
					}
					else
					{
						addToCell(new HtmlTag(tag));
					}
				}
				catch (HtmlException e)
				{
					addToCell("<" + getStringValue().toString() + ">");
				}
			}
			else if (ttype == HtmlStreamTokenizer.TT_TEXT)
			{
				String obj = getWhiteSpace().toString();
				obj += getStringValue().toString();
				addToCell(obj);
			}
			else if (ttype == HtmlStreamTokenizer.TT_COMMENT)
			{
				// throw away
			}
		}

		return continueParsing;
	}

	private void newRow()
	{
		Vector row = new Vector();
		m_row = row;
		m_rows.addElement(row);
	}

	private void beginCell(HtmlTag tag)
	{
		int rowspan = 1;
		try
		{
			rowspan = tag.getIntParam(HtmlTag.P_ROWSPAN);
			if (rowspan <= 0)
				rowspan = 1;
		}
		catch (NumberFormatException e)
		{
		}
		int colspan = 1;
		try
		{
			colspan = tag.getIntParam(HtmlTag.P_COLSPAN);
			if (colspan <= 0)
				colspan = 1;
		}
		catch (NumberFormatException e)
		{
		}
		TableCell cell = new TableCell(rowspan, colspan, new HtmlTag(tag));
		m_cell = cell;
		m_row.addElement(cell);
	}

	private void endCell()
	{
		m_cell = null;
	}

	private void addToCell(Object o)
	{
		m_cell.addElement(o);
	}

	private int nextToken()
		throws IOException
	{
		if (m_pushback)
			m_pushback = false;
		else
		{
			m_tokenType = m_tok.nextToken();
			m_stringValue = m_tok.getStringValue();
			m_whiteSpace = m_tok.getWhiteSpace();
		}

		return m_tokenType;
	}

	private void pushBackToken()
		throws IOException
	{
		if (m_pushback)
			throw new IOException("only one token pushback supported");

		m_tokenType = m_tok.getTokenType();
		m_stringValue = m_tok.getStringValue();
		m_whiteSpace = m_tok.getWhiteSpace();
		m_pushback = true;
	}

	private int getTokenType()
	{
		return m_tokenType;
	}

	private StringBuffer getStringValue()
	{
		return m_stringValue;
	}

	private StringBuffer getWhiteSpace()
	{
		return m_whiteSpace;
	}

	private HtmlStreamTokenizer m_tok = null;
	private boolean m_pushback = false;
	private int m_tokenType = 0;
	private StringBuffer m_stringValue = null;
	private StringBuffer m_whiteSpace = null;
	private TableCell m_cell = null;
	private Vector m_row = null;
	private Vector m_rows = new Vector();
	private TableCell m_elements[][] = null;
	private HtmlTag m_tableTag = null;
	private Vector m_rowTags = new Vector();
}
