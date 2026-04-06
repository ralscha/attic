package ch.ess.propertiestool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bushe.swing.event.EventBus;

public class ExportTool {

	public static void exportExcel(String masterFileName, String exportFileName)
			throws IOException {

		Map<String, Map<String, String>> keyValuesPerLanguage = Util
				.keyValuesPerLanguageResource(masterFileName);
		writeExcel(exportFileName, keyValuesPerLanguage);

		EventBus.publish("log", "EXPORTED: " + exportFileName);
	}

	@SuppressWarnings("resource")
	private static void writeExcel(String exportFileName,
			Map<String, Map<String, String>> keyValuesPerLanguage)
			throws IOException, FileNotFoundException {

		Set<String> languages = new LinkedHashSet<>();

		Map<String, String> first = keyValuesPerLanguage.entrySet().iterator().next()
				.getValue();
		for (Map.Entry<String, String> entry : first.entrySet()) {
			languages.add(entry.getKey());
		}

		Workbook workbook;
		if (exportFileName.toLowerCase().endsWith(".xlsx")) {
			workbook = new XSSFWorkbook();
		}
		else {
			workbook = new HSSFWorkbook();
		}
		try {

			Sheet sheet = workbook.createSheet("Sprachen");

			Font arial10font = workbook.createFont();
			arial10font.setFontName("Arial");
			arial10font.setFontHeightInPoints((short) 10);
			arial10font.setColor(IndexedColors.BLACK.getIndex());

			Font arial10redfont = workbook.createFont();
			arial10redfont.setFontName("Arial");
			arial10redfont.setFontHeightInPoints((short) 10);
			arial10redfont.setColor(IndexedColors.RED.getIndex());

			Font arial10fontbold = workbook.createFont();
			arial10fontbold.setFontName("Arial");
			arial10fontbold.setFontHeightInPoints((short) 10);
			arial10fontbold.setBold(true);
			arial10fontbold.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setFont(arial10fontbold);
			headerStyle.setLocked(true);
			headerStyle.setVerticalAlignment(VerticalAlignment.TOP);
			headerStyle.setBorderBottom(BorderStyle.THIN);
			headerStyle.setBorderRight(BorderStyle.THIN);
			headerStyle.setBorderLeft(BorderStyle.THIN);
			headerStyle.setBorderTop(BorderStyle.THIN);
			headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			CellStyle keyFormat = workbook.createCellStyle();
			keyFormat.setFont(arial10font);
			keyFormat.setLocked(true);
			keyFormat.setWrapText(false);
			keyFormat.setVerticalAlignment(VerticalAlignment.TOP);
			keyFormat.setBorderBottom(BorderStyle.THIN);
			keyFormat.setBorderRight(BorderStyle.THIN);
			keyFormat.setBorderLeft(BorderStyle.THIN);

			CellStyle valueFormat = workbook.createCellStyle();
			valueFormat.setFont(arial10font);
			valueFormat.setWrapText(true);
			valueFormat.setLocked(false);
			valueFormat.setVerticalAlignment(VerticalAlignment.TOP);
			valueFormat.setBorderBottom(BorderStyle.THIN);
			valueFormat.setBorderRight(BorderStyle.THIN);

			CellStyle missingValueFormat = workbook.createCellStyle();
			missingValueFormat.setFont(arial10font);
			missingValueFormat.setWrapText(true);
			missingValueFormat.setVerticalAlignment(VerticalAlignment.TOP);
			missingValueFormat.setBorderBottom(BorderStyle.THIN);
			missingValueFormat.setBorderRight(BorderStyle.THIN);
			missingValueFormat.setLocked(false);
			missingValueFormat.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			missingValueFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			CellStyle translatedValueFormat = workbook.createCellStyle();
			translatedValueFormat.setFont(arial10font);
			translatedValueFormat.setWrapText(true);
			translatedValueFormat.setVerticalAlignment(VerticalAlignment.TOP);
			translatedValueFormat.setBorderBottom(BorderStyle.THIN);
			translatedValueFormat.setBorderRight(BorderStyle.THIN);
			translatedValueFormat.setLocked(false);
			translatedValueFormat
					.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
			translatedValueFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			CellStyle translatedValueCheckFormat = workbook.createCellStyle();
			translatedValueCheckFormat.setFont(arial10redfont);
			translatedValueCheckFormat.setWrapText(true);
			translatedValueCheckFormat.setVerticalAlignment(VerticalAlignment.TOP);
			translatedValueCheckFormat.setBorderBottom(BorderStyle.THIN);
			translatedValueCheckFormat.setBorderRight(BorderStyle.THIN);
			translatedValueCheckFormat.setLocked(false);
			translatedValueCheckFormat
					.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
			translatedValueCheckFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			int rowNo = 0;
			Row row = sheet.createRow(rowNo++);

			// create header row by first map entry
			int col = 0;
			Cell cell = row.createCell(col++);
			cell.setCellStyle(headerStyle);
			cell.setCellValue("Key");
			sheet.setColumnWidth(0, 0);

			for (String language : languages) {
				sheet.setColumnWidth(col, 60 * 256);
				cell = row.createCell(col++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(language);
			}

			for (Map.Entry<String, Map<String, String>> entry : keyValuesPerLanguage
					.entrySet()) {
				Map<String, String> languageValues = entry.getValue();

				row = sheet.createRow(rowNo++);
				col = 0;
				cell = row.createCell(col++);
				cell.setCellStyle(keyFormat);
				cell.setCellValue(entry.getKey());

				for (String language : languages) {
					cell = row.createCell(col++);
					String value = languageValues.get(language);
					if (null != value && value.length() > 0) {
						cell.setCellStyle(valueFormat);
						cell.setCellValue(value);
					}
					else {
						cell.setCellStyle(missingValueFormat);
						cell.setCellValue("");
					}
				}

			}

			try (OutputStream fos = Files.newOutputStream(Paths.get(exportFileName))) {
				workbook.write(fos);
			}

		}
		finally {
			workbook.close();
		}

	}

}
