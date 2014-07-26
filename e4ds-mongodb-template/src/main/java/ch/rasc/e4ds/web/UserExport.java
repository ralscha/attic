package ch.rasc.e4ds.web;

import java.io.OutputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.rasc.e4ds.domain.User;
import ch.rasc.e4ds.repository.UserCustomRepository;

@Controller
@Lazy
public class UserExport {

	@Autowired
	private UserCustomRepository userCustomRepository;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/usersExport.xls", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void userExport(HttpServletResponse response, Locale locale,
			@RequestParam(required = false) final String filter) throws Exception {

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addHeader("Content-disposition", "attachment;filename=users.xlsx");

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

		Sheet sheet = workbook.createSheet(messageSource.getMessage("user_users", null, locale));

		Row row = sheet.createRow(0);
		createCell(row, 0, "ID", titleStyle);
		createCell(row, 1, messageSource.getMessage("user_username", null, locale), titleStyle);
		createCell(row, 2, messageSource.getMessage("user_firstname", null, locale), titleStyle);
		createCell(row, 3, messageSource.getMessage("user_lastname", null, locale), titleStyle);
		createCell(row, 4, messageSource.getMessage("user_email", null, locale), titleStyle);
		createCell(row, 5, messageSource.getMessage("user_enabled", null, locale), titleStyle);

		Page<User> page = userCustomRepository.findWithFilter(filter, null);

		int rowNo = 1;
		for (User user : page) {
			row = sheet.createRow(rowNo);
			rowNo++;

			Cell cell = row.createCell(0);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(user.getId());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(1);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(user.getUserName());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(user.getFirstName());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(3);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(user.getName());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(4);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(user.getEmail());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(5);
			cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
			cell.setCellValue(user.isEnabled());
			cell.setCellStyle(normalStyle);
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
