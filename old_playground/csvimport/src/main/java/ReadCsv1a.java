import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ReadCsv1a {

	public ReadCsv1a() throws IOException {
		try (InputStream is = getClass().getResourceAsStream("/test1.csv");
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				String name = st.nextToken();
				String email = st.nextToken();
				Integer id = Integer.valueOf(st.nextToken());

				System.out.printf("%3d %-15s %s\n", id, name, email);
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		new ReadCsv1a();
	}

}
