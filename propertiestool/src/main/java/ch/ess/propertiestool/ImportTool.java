package ch.ess.propertiestool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.bushe.swing.event.EventBus;

public class ImportTool {

	public static void importResourceFile(String masterFileName, String excelFileName) {

		try {
			File masterFile = Paths.get(masterFileName).toFile();

			String resourceName = masterFile.getName();
			int pos = resourceName.indexOf("_");
			if (pos != -1) {
				resourceName = resourceName.substring(0, pos);
			}
			else {
				resourceName = resourceName.substring(0, resourceName.indexOf("."));
			}

			EventBus.publish("log", "Resourcename: " + resourceName);

			try (InputStream excel = Files.newInputStream(Paths.get(excelFileName));
					Workbook workbook = WorkbookFactory.create(excel);) {

				Sheet sheet = workbook.getSheetAt(0);

				int cols = 1;
				Row firstRow = sheet.getRow(0);
				while (cols < 16000) {
					Cell cell = firstRow.getCell(cols);
					if (cell != null && cell.getStringCellValue() != null
							&& !cell.getStringCellValue().trim().equals("")) {
						cols++;
					}
					else {
						break;
					}
				}

				List<String> masterFileContent = new ArrayList<>();
				try (InputStream fis = Files.newInputStream(masterFile.toPath());
						BufferedReader br = new BufferedReader(new InputStreamReader(fis,
								StandardCharsets.ISO_8859_1))) {
					String l;

					while ((l = br.readLine()) != null) {
						masterFileContent.add(l);
					}
				}
				Map<String, String> langMap = new HashMap<>();

				for (int i = 1; i < cols; i++) {

					Iterator<Row> rowIt = sheet.rowIterator();
					rowIt.next();

					while (rowIt.hasNext()) {

						Row row = rowIt.next();
						Cell cell = row.getCell(0);
						if (cell != null) {
							String key = cell.getStringCellValue();
							String value = row.getCell(i).getStringCellValue();
							langMap.put(key, value);
						}
					}

					Cell headerCell = sheet.getRow(0).getCell(i);
					String lang = headerCell.getStringCellValue();

					String fileName;
					if ("default".equals(lang)) {
						fileName = resourceName + ".properties";
					}
					else {
						fileName = resourceName + "_" + lang + ".properties";
					}

					EventBus.publish("log", "Import: " + fileName);

					File outputFile = new File(masterFile.getParentFile(), fileName);

					Properties oldLangProps = new Properties();
					if (outputFile.exists()) {
						try (InputStream fis = Files
								.newInputStream(outputFile.toPath())) {
							oldLangProps.load(fis);
						}
					}

					try (OutputStream fos = Files.newOutputStream(outputFile.toPath());
							PrintWriter pw = new PrintWriter(
									new BufferedWriter(new OutputStreamWriter(fos,
											StandardCharsets.ISO_8859_1)))) {

						for (String line : masterFileContent) {

							int eqs = line.indexOf('=');
							if (eqs > 0) {
								String key = line.substring(0, eqs).trim();
								String val = langMap.get(key);
								if (val != null && !val.trim().equals("")) {
									val = Util.saveConvert(val, false, true);
								}
								else {
									if (i == 1) {
										val = line.substring(eqs + 1);
									}
									else {
										String origVal = (String) oldLangProps.get(key);
										if (origVal != null) {
											val = origVal;
										}
										else {
											val = "";
										}
									}
								}

								if (val != null && !val.trim().equals("")) {
									String outLine = key + "=" + val;
									pw.print(outLine);
								}
								pw.print("\n");

								oldLangProps.remove(key);

							}
							else {
								pw.print(line);
								pw.print("\n");
							}

						}

						Set<Map.Entry<Object, Object>> oldEntries = oldLangProps
								.entrySet();
						for (Map.Entry<Object, Object> entry : oldEntries) {
							pw.print(entry.getKey());
							pw.print("=");
							pw.print(Util.saveConvert((String) entry.getValue(), false,
									true));
							pw.print("\n");
						}
					}

				}

			}

			EventBus.publish("log", "The End");

		}
		catch (IOException e) {
			EventBus.publish("log", "\n" + e.toString());
			e.printStackTrace();
		}

	}
}
