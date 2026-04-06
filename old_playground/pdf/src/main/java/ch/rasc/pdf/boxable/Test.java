package ch.rasc.pdf.boxable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;

public class Test {

	public static void main(String[] args) throws IOException {

		try (PDDocument doc = new PDDocument()) {
			PDPage page = new PDPage();

			// Create a landscape page
			// page.setMediaBox(new PDRectangle(PDRectangle.A4.getHeight(),
			// PDRectangle.A4.getWidth()));
			doc.addPage(page);

			// Initialize table
			float margin = 10;
			float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
			float yStartNewPage = page.getMediaBox().getHeight() - 2 * margin;
			float yStart = yStartNewPage;
			float bottomMargin = 0;

			// Create the data
			List<List> data = new ArrayList<>();
			data.add(new ArrayList<>(Arrays.asList("Key", "Value")));
			for (int i = 1; i <= 5; i++) {
				data.add(new ArrayList<>(Arrays.asList(String.valueOf(i), "value:" + i)));
			}

			BaseTable dataTable = new BaseTable(yStart, yStartNewPage, bottomMargin,
					tableWidth, margin, doc, page, true, true);
			DataTable t = new DataTable(dataTable, page);
			t.addListToTable(data, DataTable.HASHEADER);
			dataTable.draw();
			File file = new File("box.pdf");
			System.out.println("Sample file saved at : " + file.getAbsolutePath());
			doc.save(file);
		}

	}

}
