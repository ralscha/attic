package ch.rasc.smninfo.importdata;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import ch.rasc.smninfo.domain.DataPoint;
import ch.rasc.smninfo.domain.Station;
import ch.rasc.smninfo.domain.TimeDouble;
import ch.rasc.smninfo.xodus.ExodusManager;
import jetbrains.exodus.entitystore.Entity;

@Component
public class ImportData implements ApplicationRunner {

	private final ApplicationContext appContext;

	private final ExodusManager exodusManager;

	public ImportData(ExodusManager exodusManager, ApplicationContext appContext) {
		this.exodusManager = exodusManager;
		this.appContext = appContext;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (args.getNonOptionArgs().contains("import")) {
			doStationImport(args.getOptionValues("file"));
			SpringApplication.exit(this.appContext, () -> 0);
		}
		else if (args.getNonOptionArgs().contains("export")) {
			doStationExport(args.getOptionValues("file"));
			SpringApplication.exit(this.appContext, () -> 0);
		}
		else if (args.getNonOptionArgs().contains("importdp")) {
			doDataPointImport(args.getOptionValues("file"));
			SpringApplication.exit(this.appContext, () -> 0);
		}
		else if (args.getNonOptionArgs().contains("exportdp")) {
			doDataPointExport(args.getOptionValues("file"));
			SpringApplication.exit(this.appContext, () -> 0);
		}
		else if (args.getNonOptionArgs().contains("exportdpstation")) {
			doDataPointStationExport(args.getOptionValues("station"),
					args.getOptionValues("file"));
			SpringApplication.exit(this.appContext, () -> 0);
		}
		else if (args.getNonOptionArgs().contains("test")) {
			doTest();
			SpringApplication.exit(this.appContext, () -> 0);
		}

	}

	private void doStationExport(List<String> args) {
		Path p = Paths.get(args.iterator().next());
		try (OutputStream out = Files.newOutputStream(p)) {
			CsvWriterSettings settings = new CsvWriterSettings();
			CsvWriter writer = new CsvWriter(out, settings);
			writer.writeHeaders("_id", "altitude", "kmCoordinates", "lngLat", "name");
			List<Station> stations = this.exodusManager.readAllStations();
			Collection<Object[]> rows = new ArrayList<>();
			for (Station station : stations) {
				Object[] row = new Object[5];
				row[0] = station.getCode();
				row[1] = station.getAltitude();
				row[2] = "[" + station.getKmCoordinates()[0] + ", "
						+ station.getKmCoordinates()[1] + "]";
				row[3] = "[" + station.getLngLat()[0] + ", " + station.getLngLat()[1]
						+ "]";
				row[4] = station.getName();
				rows.add(row);
			}

			writer.writeRowsAndClose(rows);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doStationImport(List<String> args) {
		CsvParserSettings settings = new CsvParserSettings();

		settings.getFormat().setDelimiter(',');
		settings.getFormat().setQuote('"');
		settings.setLineSeparatorDetectionEnabled(true);
		settings.setHeaderExtractionEnabled(true);
		CsvParser parser = new CsvParser(settings);

		Path p = Paths.get(args.iterator().next());
		List<String[]> allRows = parser.parseAll(p.toFile());
		List<Station> stations = new ArrayList<>();

		for (String[] row : allRows) {
			Station station = new Station();
			station.setCode(row[0]);
			station.setAltitude(Integer.valueOf(row[1]));

			String km = row[2].substring(1, row[2].length() - 1);
			String[] kmsplitted = km.split(",");
			station.setKmCoordinates(new int[] { Integer.parseInt(kmsplitted[0].trim()),
					Integer.parseInt(kmsplitted[1].trim()) });

			String ll = row[3].substring(1, row[3].length() - 1);
			String[] llsplitted = ll.split(",");
			station.setLngLat(new double[] { Double.valueOf(llsplitted[0].trim()),
					Double.valueOf(llsplitted[1].trim()) });

			station.setName(row[4]);
			stations.add(station);
		}
		this.exodusManager.deleteAllStations();
		this.exodusManager.insertStations(stations);
	}

	private void doDataPointImport(List<String> args) {
		CsvParserSettings settings = new CsvParserSettings();
		settings.setHeaderExtractionEnabled(false);
		settings.getFormat().setDelimiter(',');
		settings.getFormat().setQuote('"');
		settings.setLineSeparatorDetectionEnabled(true);
		settings.setHeaderExtractionEnabled(true);
		CsvParser parser = new CsvParser(settings);

		Path p = Paths.get(args.iterator().next());
		List<DataPoint> dps = new ArrayList<>();

		parser.beginParsing(p.toFile());
		String[] row;
		int i = 0;
		while ((row = parser.parseNext()) != null) {

			DataPoint dp = new DataPoint();
			dp.setCode(row[0]);
			dp.setEpochSeconds(OffsetDateTime.parse(row[1]).toEpochSecond());
			dp.setGustPeak(row[2] != null ? Double.valueOf(row[2]) : null);
			dp.setHumidity(row[3] != null ? Double.valueOf(row[3]) : null);
			dp.setPrecipitation(row[4] != null ? Double.valueOf(row[4]) : null);
			dp.setQfePressure(row[5] != null ? Double.valueOf(row[5]) : null);
			dp.setQffPressure(row[6] != null ? Double.valueOf(row[6]) : null);
			dp.setQnhPressure(row[7] != null ? Double.valueOf(row[7]) : null);
			dp.setSunshine(row[8] != null ? Double.valueOf(row[8]) : null);
			dp.setTemperature(row[9] != null ? Double.valueOf(row[9]) : null);
			dp.setWindDirection(row[10] != null ? Double.valueOf(row[10]) : null);
			dp.setWindSpeed(row[11] != null ? Double.valueOf(row[11]) : null);
			dp.setGlobalRadiation(row[12] != null ? Double.valueOf(row[12]) : null);
			dp.setDewPoint(row[13] != null ? Double.valueOf(row[13]) : null);
			dp.setGeoPotentialHeight850(row[14] != null ? Double.valueOf(row[14]) : null);
			dp.setGeoPotentialHeight700(row[15] != null ? Double.valueOf(row[15]) : null);
			dp.setWindDirectionTool(row[16] != null ? Double.valueOf(row[16]) : null);
			dp.setWindSpeedTower(row[17] != null ? Double.valueOf(row[17]) : null);
			dp.setGustPeakTower(row[18] != null ? Double.valueOf(row[18]) : null);
			dp.setAirTemperatureTool(row[19] != null ? Double.valueOf(row[19]) : null);
			dp.setRelAirHumidityTower(row[20] != null ? Double.valueOf(row[20]) : null);
			dp.setDewPointTower(row[21] != null ? Double.valueOf(row[21]) : null);

			dps.add(dp);
			i++;

			if (i % 100_000 == 0) {
				System.out.println(i);
				this.exodusManager.importDataPoints(dps);
				dps = new ArrayList<>();
			}
		}

		if (!dps.isEmpty()) {
			this.exodusManager.importDataPoints(dps);
		}

		this.exodusManager.getPersistentEntityStore().getEnvironment().gc();
	}

	private void doTest() {
		List<TimeDouble> result = this.exodusManager.readDataPointProperty("TAE",
				"temperature", OffsetDateTime.now().minusDays(7).toEpochSecond(),
				OffsetDateTime.now().toEpochSecond());

		result.stream().forEach(r -> {
			System.out.print(OffsetDateTime
					.ofInstant(Instant.ofEpochSecond(r.epochSeconds()), ZoneOffset.UTC));
			System.out.print("-->");
			System.out.println(r.value());
		});
		System.exit(0);
	}

	private void doDataPointExport(List<String> args) {
		DateTimeFormatter DF_PATTERN = DateTimeFormatter
				.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		this.exodusManager.getPersistentEntityStore()
				.executeInReadonlyTransaction(txn -> {
					Path p = Paths.get(args.iterator().next());
					try (OutputStream out = Files.newOutputStream(p)) {
						CsvWriterSettings settings = new CsvWriterSettings();
						// settings.setQuotationTriggers(
						// "ABCDEFGHIJKLMNOPQRSUVWXY".toCharArray());
						CsvWriter writer = new CsvWriter(out, settings);

						writer.writeHeaders("code", "dateTime", "gustPeak", "humidity",
								"precipitation", "qfePressure", "qffPressure",
								"qnhPressure", "sunshine", "temperature", "windDirection",
								"windSpeed", "globalRadiation", "dewPoint",
								"geoPotentialHeight850", "geoPotentialHeight700",
								"windDirectionTool", "windSpeedTower", "gustPeakTower",
								"airTemperatureTool", "relAirHumidityTower",
								"dewPointTower");

						for (Entity e : txn.sort("datapoint", ExodusManager.EPOCH_SECONDS,
								txn.sort("datapoint", ExodusManager.CODE, true), true)) {

							String code = getProperty(e, ExodusManager.CODE);
							long epochSeconds = getProperty(e,
									ExodusManager.EPOCH_SECONDS);
							Double temperature = getProperty(e, "temperature");
							Double sunshine = getProperty(e, "sunshine");
							Double precipitation = getProperty(e, "precipitation");
							Double windDirection = getProperty(e, "windDirection");
							Double windSpeed = getProperty(e, "windSpeed");
							Double qnhPressure = getProperty(e, "qnhPressure");
							Double gustPeak = getProperty(e, "gustPeak");
							Double humidity = getProperty(e, "humidity");
							Double qfePressure = getProperty(e, "qfePressure");
							Double qffPressure = getProperty(e, "qffPressure");

							Double globalRadiation = getProperty(e, "globalRadiation");
							Double dewPoint = getProperty(e, "dewPoint");
							Double geoPotentialHeight850 = getProperty(e,
									"geoPotentialHeight850");
							Double geoPotentialHeight700 = getProperty(e,
									"geoPotentialHeight700");
							Double windDirectionTool = getProperty(e,
									"windDirectionTool");
							Double windSpeedTower = getProperty(e, "windSpeedTower");
							Double gustPeakTower = getProperty(e, "gustPeakTower");
							Double airTemperatureTool = getProperty(e,
									"airTemperatureTool");
							Double relAirHumidityTower = getProperty(e,
									"relAirHumidityTower");
							Double dewPointTower = getProperty(e, "dewPointTower");

							String dateTime = OffsetDateTime
									.ofInstant(Instant.ofEpochSecond(epochSeconds),
											ZoneOffset.UTC)
									.format(DF_PATTERN);

							writer.writeRow(code, dateTime,
									gustPeak != null ? decimalFormat.format(gustPeak)
											: null,
									humidity,
									precipitation != null
											? decimalFormat.format(precipitation)
											: null,
									qfePressure != null
											? decimalFormat.format(qfePressure)
											: null,
									qffPressure != null
											? decimalFormat.format(qffPressure)
											: null,
									qnhPressure != null
											? decimalFormat.format(qnhPressure)
											: null,
									sunshine,
									temperature != null
											? decimalFormat.format(temperature)
											: null,
									windDirection,
									windSpeed != null ? decimalFormat.format(windSpeed)
											: null,

									globalRadiation != null
											? decimalFormat.format(globalRadiation)
											: null,
									dewPoint != null ? decimalFormat.format(dewPoint)
											: null,
									geoPotentialHeight850 != null
											? decimalFormat.format(geoPotentialHeight850)
											: null,
									geoPotentialHeight700 != null
											? decimalFormat.format(geoPotentialHeight700)
											: null,
									windDirectionTool != null
											? decimalFormat.format(windDirectionTool)
											: null,
									windSpeedTower != null
											? decimalFormat.format(windSpeedTower)
											: null,
									gustPeakTower != null
											? decimalFormat.format(gustPeakTower)
											: null,
									airTemperatureTool != null
											? decimalFormat.format(airTemperatureTool)
											: null,
									relAirHumidityTower != null
											? decimalFormat.format(relAirHumidityTower)
											: null,
									dewPointTower != null
											? decimalFormat.format(dewPointTower)
											: null

				);
						}

						writer.flush();
						writer.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

				});
	}

	private void doDataPointStationExport(List<String> stations, List<String> files) {
		DateTimeFormatter DF_PATTERN = DateTimeFormatter
				.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		this.exodusManager.getPersistentEntityStore()
				.executeInReadonlyTransaction(txn -> {
					Path p = Paths.get(files.iterator().next());
					try (OutputStream out = Files.newOutputStream(p)) {
						CsvWriterSettings settings = new CsvWriterSettings();
						// settings.setQuotationTriggers(
						// "ABCDEFGHIJKLMNOPQRSUVWXY".toCharArray());
						CsvWriter writer = new CsvWriter(out, settings);

						writer.writeHeaders("dateTime", "gustPeak", "humidity",
								"precipitation", "qfePressure", "qffPressure",
								"qnhPressure", "sunshine", "temperature", "windDirection",
								"windSpeed");

						for (Entity e : txn.sort("datapoint", ExodusManager.EPOCH_SECONDS,
								txn.find(ExodusManager.DATAPOINT, ExodusManager.CODE,
										stations.iterator().next()),
								true)) {

							long epochSeconds = getProperty(e,
									ExodusManager.EPOCH_SECONDS);
							Double temperature = getProperty(e, "temperature");
							Integer sunshine = getProperty(e, "sunshine");
							Double precipitation = getProperty(e, "precipitation");
							Integer windDirection = getProperty(e, "windDirection");
							Double windSpeed = getProperty(e, "windSpeed");
							Double qnhPressure = getProperty(e, "qnhPressure");
							Double gustPeak = getProperty(e, "gustPeak");
							Integer humidity = getProperty(e, "humidity");
							Double qfePressure = getProperty(e, "qfePressure");
							Double qffPressure = getProperty(e, "qffPressure");

							Double globalRadiation = getProperty(e, "globalRadiation");
							Double dewPoint = getProperty(e, "dewPoint");
							Double geoPotentialHeight850 = getProperty(e,
									"geoPotentialHeight850");
							Double geoPotentialHeight700 = getProperty(e,
									"geoPotentialHeight700");
							Double windDirectionTool = getProperty(e,
									"windDirectionTool");
							Double windSpeedTower = getProperty(e, "windSpeedTower");
							Double gustPeakTower = getProperty(e, "gustPeakTower");
							Double airTemperatureTool = getProperty(e,
									"airTemperatureTool");
							Double relAirHumidityTower = getProperty(e,
									"relAirHumidityTower");
							Double dewPointTower = getProperty(e, "dewPointTower");

							String dateTime = OffsetDateTime
									.ofInstant(Instant.ofEpochSecond(epochSeconds),
											ZoneOffset.UTC)
									.format(DF_PATTERN);

							writer.writeRow(dateTime,
									gustPeak != null ? decimalFormat.format(gustPeak)
											: null,
									humidity,
									precipitation != null
											? decimalFormat.format(precipitation)
											: null,
									qfePressure != null
											? decimalFormat.format(qfePressure)
											: null,
									qffPressure != null
											? decimalFormat.format(qffPressure)
											: null,
									qnhPressure != null
											? decimalFormat.format(qnhPressure)
											: null,
									sunshine,
									temperature != null
											? decimalFormat.format(temperature)
											: null,
									windDirection,
									windSpeed != null ? decimalFormat.format(windSpeed)
											: null,
									globalRadiation != null
											? decimalFormat.format(globalRadiation)
											: null,
									dewPoint != null ? decimalFormat.format(dewPoint)
											: null,
									geoPotentialHeight850 != null
											? decimalFormat.format(geoPotentialHeight850)
											: null,
									geoPotentialHeight700 != null
											? decimalFormat.format(geoPotentialHeight700)
											: null,
									windDirectionTool != null
											? decimalFormat.format(windDirectionTool)
											: null,
									windSpeedTower != null
											? decimalFormat.format(windSpeedTower)
											: null,
									gustPeakTower != null
											? decimalFormat.format(gustPeakTower)
											: null,
									airTemperatureTool != null
											? decimalFormat.format(airTemperatureTool)
											: null,
									relAirHumidityTower != null
											? decimalFormat.format(relAirHumidityTower)
											: null,
									dewPointTower != null
											? decimalFormat.format(dewPointTower)
											: null);
						}

						writer.flush();
						writer.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

				});

	}

	private static <T> T getProperty(Entity e, String propertyName) {
		return (T) e.getProperty(propertyName);
	}
}
