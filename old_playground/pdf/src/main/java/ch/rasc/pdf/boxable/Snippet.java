package ch.rasc.pdf.boxable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;

public class Snippet {
	public static void main(String[] args) throws IOException {
		// Initialize Document
		PDDocument doc = new PDDocument();
		PDPage page = new PDPage();
		// Create a landscape page
		page.setMediaBox(
				new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
		doc.addPage(page);
		// Initialize table
		float margin = 10;
		float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
		float yStartNewPage = page.getMediaBox().getHeight() - 2 * margin;
		float yStart = yStartNewPage;
		float bottomMargin = 0;

		// Create the data
		List<List> data = new ArrayList<>();
		data.add(new ArrayList<>(Arrays.asList("Column One", "Column Two", "Column Three",
				"Column Four", "Column Five")));
		for (int i = 1; i <= 100; i++) {
			data.add(new ArrayList<>(Arrays.asList("Row " + i + " Col One",
					"Row " + i + " Col Two", "Row " + i + " Col Three",
					"Row " + i + " Col Four", "Row " + i + " Col Five")));
		}

		BaseTable dataTable = new BaseTable(yStart, yStartNewPage, bottomMargin,
				tableWidth, margin, doc, page, true, true);
		DataTable t = new DataTable(dataTable, page);
		t.addListToTable(data, DataTable.HASHEADER);
		dataTable.draw();
		File file = new File("box.pdf");
		System.out.println("Sample file saved at : " + file.getAbsolutePath());
		doc.save(file);
		doc.close();

	}

}
