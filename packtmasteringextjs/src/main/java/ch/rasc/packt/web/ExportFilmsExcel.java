package ch.rasc.packt.web;

import java.io.OutputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.rasc.packt.entity.Film;
import ch.rasc.packt.entity.QFilm;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

@Controller
@Lazy
public class ExportFilmsExcel {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	@RequestMapping(value = "/exportFilms.xlsx", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public void userExport(HttpServletResponse response) throws Exception {

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		// response.addHeader("Content-disposition",
		// "attachment;filename=films.xlsx");

		Workbook workbook = new XSSFWorkbook();

		Font font = workbook.createFont();
		Font titleFont = workbook.createFont();

		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);

		titleFont.setColor(IndexedColors.BLACK.getIndex());
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 10);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle normalStyle = workbook.createCellStyle();
		normalStyle.setFont(font);

		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(titleFont);

		Sheet sheet = workbook.createSheet("Films");

		Row row = sheet.createRow(0);
		createCell(row, 0, "Film Id", titleStyle);
		createCell(row, 1, "Title", titleStyle);
		createCell(row, 2, "Language", titleStyle);
		createCell(row, 3, "Release Year", titleStyle);
		createCell(row, 4, "Length", titleStyle);
		createCell(row, 5, "Rating", titleStyle);

		JPQLQuery query = new JPAQuery(entityManager).from(QFilm.film);
		List<Film> films = query.list(QFilm.film);

		int rowNo = 1;
		for (Film film : films) {
			row = sheet.createRow(rowNo);

			Cell cell = row.createCell(0);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(film.getId());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(1);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(film.getTitle());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(film.getLanguage().getName());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(3);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(film.getReleaseYear());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(4);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(film.getLength());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(5);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(film.getRating());
			cell.setCellStyle(normalStyle);

			rowNo++;
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);

		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
		}
	}

	private static void createCell(Row row, int column, String value, CellStyle style) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}

}