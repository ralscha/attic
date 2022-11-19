package ch.rasc.smninfo.job;

import java.io.IOException;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import ch.rasc.smninfo.Application;
import ch.rasc.smninfo.domain.DataPoint;
import ch.rasc.smninfo.xodus.ExodusManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Component
public class DataPointFetcher {

	private static final String SWISS_MET_NET_URL = "https://data.geo.admin.ch/ch.meteoschweiz.messwerte-aktuell/VQHA80.csv";

	private final CsvParserSettings parserSettings;

	private final BeanListProcessor<DataPoint> rowProcessor;

	private final OkHttpClient httpClient;

	private final ExodusManager exodusManager;

	public DataPointFetcher(OkHttpClient httpClient, ExodusManager exodusManager) {
		this.httpClient = httpClient;
		this.exodusManager = exodusManager;

		this.parserSettings = new CsvParserSettings();
		this.parserSettings.getFormat().setLineSeparator("\n");
		this.parserSettings.getFormat().setDelimiter(';');

		// this.parserSettings.setNumberOfRowsToSkip(2);
		this.parserSettings.setHeaderExtractionEnabled(true);
		this.rowProcessor = new BeanListProcessor<>(DataPoint.class);
		this.parserSettings.setProcessor(this.rowProcessor);
	}

	@Scheduled(fixedRate = 10 * 60 * 1_000)
	public void fetchData() {
		Request request = new Request.Builder().url(SWISS_MET_NET_URL).build();

		try (Response response = this.httpClient.newCall(request).execute()) {

			CsvParser parser = new CsvParser(this.parserSettings);
			try (ResponseBody body = response.body()) {
				if (body != null) {
					parser.parse(body.byteStream());
				}
			}

			List<DataPoint> dataPoints = this.rowProcessor.getBeans();
			this.exodusManager.insertDataPoints(dataPoints);

		}
		catch (IOException e) {
			Application.logger.error("fetching smn data", e);
		}

	}

}
