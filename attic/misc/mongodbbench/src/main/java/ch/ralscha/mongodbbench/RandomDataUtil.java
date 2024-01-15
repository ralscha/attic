package ch.ralscha.mongodbbench;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomDataUtil {
	public static long getRandomLong() {
		return getRandomLong(1, Long.MAX_VALUE);
	}

	public static int getRandomInt() {
		return getRandomInt(1, Integer.MAX_VALUE);
	}

	public static String getRandomString() {
		int stringLength = (int) (Math.random() * 128);
		return getRandomString(stringLength);
	}

	public static String getRandomAlphaString(int length) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < length; i++) {
			//	ascii 60 to 90
			char str = (char) (65 + (int) (Math.random() * (90 - 65)));
			b.append(str);
		}

		return b.toString();
	}

	public static String getRandomString(int length) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < length; i++) {
			//	ascii 33 (!) to 126 (~)
			char str = (char) (33 + (int) (Math.random() * 93));
			b.append(str);
		}

		return b.toString();
	}

	public static Date getRandomDate() {
		//	month
		int month = getRandomInt(1, 12);

		//	day
		int day = getRandomInt(1, 28);

		//	year
		int year = getRandomInt(1970, 2009);

		String dateString = year + "-" + month + "-" + day;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(dateString);
		} catch (ParseException ex) {
			return new Date();
		}
	}

	public static int getRandomInt(int min, int max) {
		return min + (int) ((Math.random() * (max - min)));
	}

	public static long getRandomLong(long min, long max) {
		return min + (long) ((Math.random() * (max - min)));
	}

//	public static Set<Integer> getUniqueRandomNumbers(int min, int max, int totalCount) {
//		int spread = max - min;
//		if ((max - min) < totalCount) {
//			totalCount = spread;
//		}
//		Set<Integer> output = new HashSet<Integer>();
//		while (true) {
//			int id = min + (int) Math.floor(((double) spread) * Math.random());
//			if (!output.contains(id)) {
//				output.add(new Integer(id));
//			}
//			if (output.size() >= totalCount) {
//				break;
//			}
//		}
//		return output;
//	}

	public static String getRandomEmail() {
		return RandomDataUtil.getRandomAlphaString(10) + "@" + RandomDataUtil.getRandomAlphaString(15) + ".com";
	}

	public static String getRandomURL() {
		return "http://" + getRandomAlphaString(3) + "." + getRandomAlphaString(10) + ".com/"
				+ getRandomAlphaString(getRandomInt(3, 10));
	}

	public static String getRandomTestEmail() {
		return RandomDataUtil.getRandomAlphaString(10) + "@" + RandomDataUtil.getRandomAlphaString(3)
				+ ".wordniktesting.com";
	}

//	public static String getRandomWords(int wordCount) {
//		StringBuilder buf = new StringBuilder();
//		for (int i = 0; i < wordCount; i++) {
//			if (i > 0) {
//				buf.append(" ");
//			}
//			String word = RandomDataUtil.getRandomAlphaString(RandomDataUtil.getRandomInt(5, 15));
//			if (i == 0) {
//				word = Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
//			} else {
//				word = word.toLowerCase();
//			}
//			buf.append(word);
//		}
//		return buf.toString();
//	}

//	public static String getRandomWords(int min, int max) {
//		int wordsToCreate = getRandomInt(min, max);
//		return getRandomWords(wordsToCreate);
//	}

	public static String getRandomIpAddress() {
		return getRandomInt(0, 255) + "." + getRandomInt(0, 255) + "." + getRandomInt(0, 255) + "."
				+ getRandomInt(0, 255);
	}

//	public static File getRandomFile() throws IOException {
//		File file = File.createTempFile("randomDataUtil", "tmp");
//		file.deleteOnExit();
//
//		OutputStream out = new FileOutputStream(file);
//		Writer writer = null;
//		try {
//			writer = new OutputStreamWriter(out, "UTF-8");
//			writer.write(getRandomWords(RandomDataUtil.getRandomInt(10, 500)));
//			return file;
//		} finally {
//			if (writer != null) {
//				try {
//					writer.close();
//				} catch (Exception e) {
//				}
//			}
//		}
//	}
}