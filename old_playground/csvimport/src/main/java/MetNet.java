import java.io.IOException;
import java.util.List;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MetNet {

	public static void main(String[] args) throws IOException {

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("http://data.geo.admin.ch/ch.meteoschweiz.swissmetnet/VQHA69.csv")
				.build();

		try (Response response = client.newCall(request).execute()) {

			CsvParserSettings parserSettings = new CsvParserSettings();
			parserSettings.getFormat().setLineSeparator("\n");
			parserSettings.getFormat().setDelimiter('|');

			parserSettings.setNumberOfRowsToSkip(2);
			parserSettings.setHeaderExtractionEnabled(true);
			BeanListProcessor<Station> rowProcessor = new BeanListProcessor<>(
					Station.class);
			parserSettings.setRowProcessor(rowProcessor);

			CsvParser parser = new CsvParser(parserSettings);
			try (ResponseBody body = response.body()) {
				if (body != null) {
					parser.parse(body.byteStream());
				}
			}
			List<Station> beans = rowProcessor.getBeans();
			beans.forEach(System.out::println);
		}

	}

}
