package ch.rasc.pdf;

import java.util.List;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class TableBuilder {

	private final Table table = new Table();

	public TableBuilder setHeight(float height) {
		this.table.setHeight(height);
		return this;
	}

	public TableBuilder setNumberOfRows(Integer numberOfRows) {
		this.table.setNumberOfRows(numberOfRows);
		return this;
	}

	public TableBuilder setRowHeight(float rowHeight) {
		this.table.setRowHeight(rowHeight);
		return this;
	}

	public TableBuilder setContent(String[][] content) {
		this.table.setContent(content);
		return this;
	}

	public TableBuilder setColumns(List<Column> columns) {
		this.table.setColumns(columns);
		return this;
	}

	public TableBuilder setCellMargin(float cellMargin) {
		this.table.setCellMargin(cellMargin);
		return this;
	}

	public TableBuilder setMargin(float margin) {
		this.table.setMargin(margin);
		return this;
	}

	public TableBuilder setPageSize(PDRectangle pageSize) {
		this.table.setPageSize(pageSize);
		return this;
	}

	public TableBuilder setLandscape(boolean landscape) {
		this.table.setLandscape(landscape);
		return this;
	}

	public TableBuilder setTextFont(PDFont textFont) {
		this.table.setTextFont(textFont);
		return this;
	}

	public TableBuilder setFontSize(float fontSize) {
		this.table.setFontSize(fontSize);
		return this;
	}

	public Table build() {
		return this.table;
	}
}