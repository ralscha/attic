package ch.rasc.packt.web;

import java.io.OutputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.rasc.packt.entity.Film;
import ch.rasc.packt.entity.QFilm;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

@Controller
@Lazy
public class ExportFilmsPdf {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	@RequestMapping(value = "/exportFilms.pdf", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public void userExport(HttpServletResponse response) throws Exception {

		response.setContentType("application/pdf");
		// response.addHeader("Content-disposition",
		// "attachment;filename=films.pdf");

		JPQLQuery query = new JPAQuery(entityManager).from(QFilm.film);
		List<Film> films = query.list(QFilm.film);

		try (OutputStream out = response.getOutputStream()) {

			Document document = new Document(PageSize.A4.rotate());

			PdfWriter.getInstance(document, out);

			document.open();

			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 1, 3, 1, 1, 1, 1 });

			table.addCell("Film Id");
			table.addCell("Title");
			table.addCell("Language");
			table.addCell("Release Year");
			table.addCell("Length");
			table.addCell("Rating");

			for (Film film : films) {
				table.addCell(String.valueOf(film.getId()));
				table.addCell(film.getTitle());
				table.addCell(film.getLanguage().getName());
				table.addCell(String.valueOf(film.getReleaseYear()));

				PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(film.getLength())));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				table.addCell(film.getRating());
			}

			document.add(table);

			document.close();

		}
	}

}