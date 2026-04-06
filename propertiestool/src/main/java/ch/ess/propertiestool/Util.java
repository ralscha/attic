package ch.ess.propertiestool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.bushe.swing.event.EventBus;

public class Util {
	public static Set<String> loadKeys(InputStream is) throws IOException {

		LineReader lr = new LineReader(is);
		Set<String> keyList = new LinkedHashSet<>();

		int limit;
		int keyLen;
		int valueStart;
		char c;
		boolean hasSep;
		boolean precedingBackslash;

		while ((limit = lr.readLine()) >= 0) {
			c = 0;
			keyLen = 0;
			valueStart = limit;
			hasSep = false;

			// System.out.println("line=<" + new String(lineBuf, 0, limit) +
			// ">");
			precedingBackslash = false;
			while (keyLen < limit) {
				c = lr.lineBuf[keyLen];
				// need check if escaped.
				if ((c == '=' || c == ':') && !precedingBackslash) {
					valueStart = keyLen + 1;
					hasSep = true;
					break;
				}
				else if ((c == ' ' || c == '\t' || c == '\f') && !precedingBackslash) {
					valueStart = keyLen + 1;
					break;
				}
				if (c == '\\') {
					precedingBackslash = !precedingBackslash;
				}
				else {
					precedingBackslash = false;
				}
				keyLen++;
			}
			while (valueStart < limit) {
				c = lr.lineBuf[valueStart];
				if (c != ' ' && c != '\t' && c != '\f') {
					if (!hasSep && (c == '=' || c == ':')) {
						hasSep = true;
					}
					else {
						break;
					}
				}
				valueStart++;
			}
			String key = loadConvert(lr.lineBuf, 0, keyLen);
			keyList.add(key);
		}

		return keyList;
	}

	/*
	 * Converts encoded &#92;uxxxx to unicode chars and changes special saved chars to
	 * their original forms
	 */
	private static String loadConvert(char[] in, int offset, int len) {
		char[] convtBuf = new char[1024];
		int off = offset;

		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0) {
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				}
				else {
					if (aChar == 't') {
						aChar = '\t';
					}
					else if (aChar == 'r') {
						aChar = '\r';
					}
					else if (aChar == 'n') {
						aChar = '\n';
					}
					else if (aChar == 'f') {
						aChar = '\f';
					}
					out[outLen++] = aChar;
				}
			}
			else {
				out[outLen++] = aChar;
			}
		}
		return new String(out, 0, outLen);
	}

	/*
	 * Converts unicodes to encoded &#92;uxxxx and escapes special characters with a
	 * preceding slash
	 */
	public static String saveConvert(String theString, boolean escapeSpace,
			boolean escapeUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if (aChar > 61 && aChar < 127) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace) {
					outBuffer.append('\\');
				}
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if ((aChar < 0x0020 || aChar > 0x007e) & escapeUnicode) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex(aChar >> 12 & 0xF));
					outBuffer.append(toHex(aChar >> 8 & 0xF));
					outBuffer.append(toHex(aChar >> 4 & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				}
				else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	private static char toHex(int nibble) {
		return hexDigit[nibble & 0xF];
	}

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static List<String> mergeOrdered(final List<String> list0,
			final List<String> list1) {
		List<String> result = new ArrayList<>();

		while (list0.size() > 0 && list1.size() > 0) {
			if (list0.get(0).compareTo(list1.get(0)) < 0) {
				result.add(list0.get(0));
				list0.remove(0);
			}
			else {
				result.add(list1.get(0));
				list1.remove(0);
			}
		}

		if (list0.size() > 0) {
			result.addAll(list0);
		}
		else if (list1.size() > 0) {
			result.addAll(list1);
		}

		return result;
	}

	public static Map<String, Map<String, String>> keyValuesPerLanguageResource(
			String resourceFile) throws IOException {

		EventBus.publish("log", "Resource file: " + resourceFile);

		File masterFile = new File(resourceFile);
		String resourceName = masterFile.getName();
		int pos = resourceName.indexOf("_");
		if (pos != -1) {
			resourceName = resourceName.substring(0, pos);
		}
		else {
			resourceName = resourceName.substring(0, resourceName.indexOf("."));
		}

		List<File> languageFiles = new LinkedList<>();
		File dir = new File(masterFile.getParent());
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.getName().startsWith(resourceName)
					&& !resourceName.equals(file.getName())
					&& file.getName().endsWith(".properties")) {
				languageFiles.add(file);
				EventBus.publish("log", "Language files: " + file.getName());
			}
		}

		Set<String> languages = new LinkedHashSet<>();
		List<String> keyList = new ArrayList<>();
		for (File file : languageFiles) {
			try (InputStream fis = Files.newInputStream(file.toPath())) {
				List<String> keys = new ArrayList<>(Util.loadKeys(fis));
				keyList = mergeOrdered(keyList, keys);
			}
		}

		Map<String, Map<String, String>> keyValuesPerLanguage = new LinkedHashMap<>();

		for (File file : languageFiles) {
			String language = "";
			String name = file.getName();
			pos = name.indexOf("_");
			if (pos != -1) {
				language = name.substring(pos + 1, name.indexOf("."));
			}
			else {
				language = "default";
			}
			languages.add(language);

			Properties langProps = new Properties();
			try (InputStream fis = Files.newInputStream(file.toPath())) {
				langProps.load(fis);
			}

			for (String key : keyList) {
				String value = langProps.getProperty(key);
				if (!keyValuesPerLanguage.containsKey(key)) {
					Map<String, String> langValues = new LinkedHashMap<>();
					langValues.put(language, value);
					keyValuesPerLanguage.put(key, langValues);
				}
				else {
					Map<String, String> langValues = keyValuesPerLanguage.get(key);
					langValues.put(language, value);
				}
			}
		}
		return keyValuesPerLanguage;
	}

	public static Map<String, Map<String, String>> keyValuesPerLanguageXls(
			String excelFile) throws IOException {

		Map<String, Map<String, String>> keyValuesPerLanguage = new LinkedHashMap<>();

		try (InputStream excel = Files.newInputStream(Paths.get(excelFile));
				Workbook workbook = WorkbookFactory.create(excel)) {
			Sheet sheet = workbook.getSheetAt(0);

			Set<String> languages = new LinkedHashSet<>();
			int cols = 1;
			Row firstRow = sheet.getRow(0);
			while (cols < 16000) {
				Cell cell = firstRow.getCell(cols);
				if (cell != null && cell.getStringCellValue() != null
						&& !cell.getStringCellValue().trim().equals("")) {
					languages.add(cell.getStringCellValue());
					cols++;
				}
				else {
					break;
				}
			}

			Iterator<Row> rowIt = sheet.rowIterator();
			rowIt.next();

			while (rowIt.hasNext()) {
				Row row = rowIt.next();
				int col = 0;
				String key = row.getCell(col++).getStringCellValue();

				Map<String, String> langValues;
				if (!keyValuesPerLanguage.containsKey(key)) {
					langValues = new LinkedHashMap<>();
					keyValuesPerLanguage.put(key, langValues);
				}
				else {
					langValues = keyValuesPerLanguage.get(key);
				}

				for (String language : languages) {
					langValues.put(language, row.getCell(col++).getStringCellValue());
				}
			}
		}
		return keyValuesPerLanguage;
	}

	public static boolean containsNonWordChars(String text) {
		Pattern htmlPattern = Pattern.compile("[^\\w]", Pattern.DOTALL);
		return htmlPattern.matcher(text).matches();
	}

}
