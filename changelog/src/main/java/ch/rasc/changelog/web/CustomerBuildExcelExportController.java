package ch.rasc.changelog.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.rasc.changelog.entity.Change;
import ch.rasc.changelog.entity.ChangeType;
import ch.rasc.changelog.entity.Customer;
import ch.rasc.changelog.entity.CustomerBuild;
import ch.rasc.changelog.service.LogService;

@Controller
public class CustomerBuildExcelExportController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LogService logService;

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping(value = "/customerBuildExcelExport.xls", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public void export(
			@RequestParam(value = "customerBuildId",
					required = false) final Long customerBuildId,
			final Locale locale,
			@RequestParam(value = "customerId", required = false) final Long customerId,
			final HttpServletResponse response) throws IOException {

		List<Change> changes;
		Customer customer;
		CustomerBuild build = null;

		if (customerBuildId != null) {
			build = this.entityManager.find(CustomerBuild.class, customerBuildId);
			changes = this.logService.loadChanges(customerBuildId, null);
			customer = build.getCustomer();
		}
		else {
			changes = this.logService.loadChanges(null, customerId);
			customer = this.entityManager.find(Customer.class, customerId);
		}

		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-disposition", "attachment;filename=changes.xls");

		Workbook workbook = new HSSFWorkbook();

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
		normalStyle.setWrapText(true);
		normalStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(titleFont);

		Sheet sheet = workbook.createSheet(
				this.messageSource.getMessage("change_changes", null, locale));

		int rowNo = 0;
		Row row = sheet.createRow(rowNo);
		createCell(row, 0, this.messageSource.getMessage("customer", null, locale),
				titleStyle);
		createCell(row, 1, customer.getLongName(), normalStyle);

		rowNo++;
		row = sheet.createRow(rowNo);
		createCell(row, 0, this.messageSource.getMessage("customer_build", null, locale),
				titleStyle);
		if (build != null) {
			createCell(row, 1, build.getVersionNumber(), normalStyle);
		}
		else {
			createCell(row, 1, "*", normalStyle);
		}

		rowNo++;
		row = sheet.createRow(rowNo);
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		createCell(row, 0,
				this.messageSource.getMessage("customer_build_date", null, locale),
				titleStyle);
		if (build != null) {
			createCell(row, 1, df.format(build.getVersionDate()), normalStyle);
		}
		else {
			createCell(row, 1, df.format(new Date()), normalStyle);
		}

		rowNo += 2;
		row = sheet.createRow(rowNo);
		createCell(row, 0, this.messageSource.getMessage("change_typ", null, locale),
				titleStyle);
		createCell(row, 1, this.messageSource.getMessage("change_module", null, locale),
				titleStyle);
		createCell(row, 2, this.messageSource.getMessage("change_bugno", null, locale),
				titleStyle);
		createCell(row, 3, this.messageSource.getMessage("change_subject", null, locale),
				titleStyle);
		createCell(row, 4,
				this.messageSource.getMessage("change_description", null, locale),
				titleStyle);

		for (Change change : changes) {
			rowNo++;
			row = sheet.createRow(rowNo);

			Cell cell = row.createCell(0);
			cell.setCellType(Cell.CELL_TYPE_STRING);

			cell.setCellStyle(normalStyle);

			if (change.getTyp() == ChangeType.FIX) {
				cell.setCellValue(
						this.messageSource.getMessage("change_typ_fix", null, locale));
			}
			else if (change.getTyp() == ChangeType.ENHANCEMENT) {
				cell.setCellValue(this.messageSource.getMessage("change_typ_enhancement",
						null, locale));
			}
			else {
				cell.setCellValue(
						this.messageSource.getMessage("change_typ_new", null, locale));
			}

			cell = row.createCell(1);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(change.getModule());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(change.getBugNumber());
			cell.setCellStyle(normalStyle);

			cell = row.createCell(3);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String subject = StringUtils.replace(change.getSubject(), "\r", "\n");
			cell.setCellValue(subject);
			cell.setCellStyle(normalStyle);

			cell = row.createCell(4);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String description = StringUtils.replace(change.getDescription(), "\r", "\n");
			cell.setCellValue(description);
			cell.setCellStyle(normalStyle);
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.setColumnWidth(4, 256 * 140);

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