package ch.rasc.tailerhttplog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApacheLogParser {

	boolean DEBUG = false;

	/* Log file feilds */
	String clientHost = null;

	String requestTime = null;

	String clientRequest = null;

	String httpStatusCode = null;

	String numOfBytes = null;

	String referer = null;

	String agent = null;

	/**
	 * Structures Apache combined access log
	 *
	 * @return regex
	 */
	static String getAccessLogRegex() {
		String regex1 = "^([\\d.]+)"; // Client IP
		String regex2 = " (\\S+)"; // -
		String regex3 = " (\\S+)"; // -
		String regex4 = " \\[([\\w:/]+\\s[+\\-]\\d{4})\\]"; // Date
		String regex5 = " \"(.+?)\""; // request method and url
		String regex6 = " (\\d{3})"; // HTTP code
		String regex7 = " (\\d+|(.+?))"; // Number of bytes
		String regex8 = " \"([^\"]+|(.+?))\""; // Referer
		String regex9 = " \"([^\"]+|(.+?))\""; // Agent

		return regex1 + regex2 + regex3 + regex4 + regex5 + regex6 + regex7 + regex8
				+ regex9;
	}

	/**
	 * Writes the URLs file to HASHMAP
	 *
	 * @param filePath : the file which holds the URLS
	 * @return hashMap<int,String>
	 * @throws IOException
	 */
	public HashMap<Integer, String> readUrlsList(String filePath) throws IOException {
		HashMap<Integer, String> urls = new HashMap<>();

		try (BufferedReader bufferReader = new BufferedReader(new FileReader(filePath))) {

			String line = "";
			int index = 0;

			/* put the URLS to be filtered in HASH MAP */
			while ((line = bufferReader.readLine()) != null) {
				urls.put(index++, line);
			}

		}

		return urls;
	}

	/**
	 * Converts any formated date to epoch
	 *
	 * @param dateFormmat : eg. yyy-MM-dd
	 * @param date : it should which fits dateFormmat criteria
	 * @return long : epoch representation
	 * @throws ParseException
	 */
	public long convertTimetoEpoch(String dateFormat, String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		return sdf.parse(date).getTime();
	}

	/**
	 * Parses Apache combined access log, and prints out the following <br>
	 * 1. Requester IP <br>
	 * 2. Date of Request <br>
	 * 3. Requested Page Path
	 *
	 * @param String accessFilePath
	 * @param String urlsFilePath
	 * @param String startingDate
	 * @param String endingDate
	 * @param String dateFormat
	 * @throws ParseException
	 * @throws IOException
	 */
	public void ApacheAccessLogParser(String accessFilePath, String urlsFilePath,
			String startingDate, String endingDate, String dateFormat)
			throws ParseException, IOException {

		long startingEpoch = convertTimetoEpoch(dateFormat, startingDate);
		long endingEpoch = convertTimetoEpoch(dateFormat, endingDate);
		long accessLogEntryEpoch;

		HashMap<Integer, String> urlsMap = readUrlsList(urlsFilePath);

		try (BufferedReader bufferReader = new BufferedReader(
				new FileReader(accessFilePath))) {

			String line = "";
			long index = 0;

			SimpleDateFormat accesslogDateFormat = new SimpleDateFormat(
					"dd/MMM/yyyy:HH:mm:ss Z");

			Pattern accessLogPattern = Pattern.compile(getAccessLogRegex(),
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher accessLogEntryMatcher;

			while ((line = bufferReader.readLine()) != null) {
				index++;

				accessLogEntryMatcher = accessLogPattern.matcher(line);

				if (!accessLogEntryMatcher.matches()) {
					if (this.DEBUG) {
						System.out.println("" + index + " : couldn't be parsed");
					}
					continue;
				}
				this.requestTime = accessLogEntryMatcher.group(4);
				accessLogEntryEpoch = accesslogDateFormat.parse(this.requestTime)
						.getTime();

				if (accessLogEntryEpoch >= startingEpoch
						&& accessLogEntryEpoch <= endingEpoch) {
					this.clientRequest = accessLogEntryMatcher.group(5);

					if (this.DEBUG) {
						System.out.println(
								"" + index + " : " + this.clientRequest.split(" ")[1]);
					}

					if (urlsMap.containsValue(this.clientRequest.split(" ")[1])) {
						System.out.println("Line num : " + index + " "
								+ accessLogEntryMatcher.group(1) + " "
								+ accessLogEntryMatcher.group(4) + " "
								+ accessLogEntryMatcher.group(5));
					}
					else {
						continue;
					}
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			ApacheLogParser logParser = new ApacheLogParser();
			logParser.ApacheAccessLogParser("Z:\\Scripts\\access_log",
					"Z:\\Scripts\\urls.txt", "01/Oct/2011", "08/Oct/2011", "dd/MMM/yyyy");

		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}