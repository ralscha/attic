import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class ReadWithUnivocity {

	public static void main(String[] args) throws UnsupportedEncodingException {
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.setLineSeparatorDetectionEnabled(true);
		BeanListProcessor<User> rowProcessor = new BeanListProcessor<>(User.class);
		parserSettings.setRowProcessor(rowProcessor);
		parserSettings.setHeaderExtractionEnabled(true);

		CsvParser parser = new CsvParser(parserSettings);
		parser.parse(new InputStreamReader(
				ReadWithUnivocity.class.getResourceAsStream("/testHeader.csv"), "UTF-8"));

		List<User> beans = rowProcessor.getBeans();
		beans.forEach(System.out::println);

	}

}
