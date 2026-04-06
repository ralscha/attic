package ch.rasc.pdf;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Text {
	public static void main(String[] args) {

		try {

			System.out.println("Create Simple PDF file with Text");
			String fileName = "PdfWithtext.pdf"; // name of our file

			PDDocument doc = new PDDocument();
			PDPage page = new PDPage();

			doc.addPage(page);

			PDPageContentStream content = new PDPageContentStream(doc, page);

			content.beginText();
			content.setFont(PDType1Font.HELVETICA, 26);
			content.newLineAtOffset(220, 750);
			content.showText("Registration Form");
			content.endText();

			content.beginText();
			content.setFont(PDType1Font.HELVETICA, 16);
			content.newLineAtOffset(80, 700);
			content.showText("Name : ");
			content.endText();

			content.beginText();
			content.setFont(PDType1Font.HELVETICA, 16);
			content.newLineAtOffset(80, 650);
			content.showText("Father Name : ");
			content.endText();

			content.beginText();
			content.newLineAtOffset(80, 600);
			content.showText("DOB : ");
			content.endText();

			content.close();
			doc.save(fileName);
			doc.close();

			System.out
					.println("your file created in : " + System.getProperty("user.dir"));

		}
		catch (IOException e) {

			System.out.println(e.getMessage());

		}

	}
}
