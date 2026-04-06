import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

public class ReadCsv1b {

	public ReadCsv1b() throws IOException {
		try (InputStream is = getClass().getResourceAsStream("/test1.csv");
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			String line;
			while ((line = br.readLine()) != null) {
				Iterable<String> splittedLine = Splitter.on(",").split(line);
				List<String> fields = ImmutableList.copyOf(splittedLine);
				System.out.printf("%3d %-15s %s\n", Integer.valueOf(fields.get(2)),
						fields.get(0), fields.get(1));
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		new ReadCsv1b();
	}

}
