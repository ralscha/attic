import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import au.com.bytecode.opencsv.CSVReader;

public class ReadCsv3 {

	public ReadCsv3() throws IOException {

		try (InputStream is = getClass().getResourceAsStream("/test3.csv");
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				CSVReader reader = new CSVReader(br)) {

			String[] line;
			while ((line = reader.readNext()) != null) {
				System.out.printf("%3d %-15s %s\n", Integer.valueOf(line[2]), line[0],
						line[1]);
			}
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		new ReadCsv3();
	}

}
