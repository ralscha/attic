import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ReadCommonCsv {

	public ReadCommonCsv() throws IOException {

		try (InputStream is = getClass().getResourceAsStream("/test1.csv");
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				CSVParser parser = CSVFormat.newFormat(',').parse(br)) {
			for (CSVRecord record : parser.getRecords()) {
				String name = record.get(0);
				String email = record.get(1);
				Integer id = Integer.valueOf(record.get(2));
				System.out.printf("%3d %-15s %s\n", id, name, email);
			}
		}

		System.out.println("===========================");

		try (InputStream is = getClass().getResourceAsStream("/test2.csv");
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				CSVParser parser = CSVFormat.DEFAULT.parse(br)) {
			for (CSVRecord record : parser.getRecords()) {
				String name = record.get(0);
				String email = record.get(1);
				Integer id = Integer.valueOf(record.get(2));
				System.out.printf("%3d %-15s %s\n", id, name, email);
			}
		}

		System.out.println("===========================");

		try (InputStream is = getClass().getResourceAsStream("/testHeader.csv");
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(br)) {

			Map<String, Integer> headerMap = parser.getHeaderMap();
			headerMap.forEach((k, v) -> System.out.println(k + "->" + v));

			for (CSVRecord record : parser.getRecords()) {
				String name = record.get("Name");
				String email = record.get("E-Mail");
				Integer id = Integer.valueOf(record.get("ID"));
				System.out.printf("%3d %-15s %s\n", id, name, email);
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		new ReadCommonCsv();
	}

}
