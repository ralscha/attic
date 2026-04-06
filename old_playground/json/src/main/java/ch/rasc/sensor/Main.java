package ch.rasc.sensor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.util.StreamUtils;
import org.xerial.snappy.Snappy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

public class Main {

	public static void main(String[] args) throws IOException {

		long start = ZonedDateTime.now().toEpochSecond();
		// System.out.println(start);
		// System.out.println(Integer.MAX_VALUE);
		// System.out.println(ZonedDateTime
		// .ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneOffset.UTC));

		Random rand = new Random();

		List<SensorData> result = new ArrayList<>();

		// 1 sensor 1 day = 748,717

		int noOfSensors = 8;
		for (int i = 0; i < noOfSensors; i++) {
			long ix = start;
			int dataPoints = 5_000 * (64 / noOfSensors);
			SensorData sd = new SensorData(UUID.randomUUID().toString());
			for (int j = 0; j < dataPoints; j++) {
				sd.addMeasurement(ix, new int[] { rand.nextInt(9999), rand.nextInt(9999),
						rand.nextInt(9999), rand.nextInt(9999) });
				ix += 2;
			}
			result.add(sd);
		}

		LZ4Factory factory = LZ4Factory.fastestInstance();
		LZ4Compressor compressor = factory.fastCompressor();

		ObjectMapper om = new ObjectMapper();
		byte[] json = om.writeValueAsBytes(result);
		// System.out.println(new String(json, StandardCharsets.UTF_8));
		System.out.println(json.length + " bytes JSON");

		int maxCompressedLength = compressor.maxCompressedLength(json.length);
		byte[] compressed = new byte[maxCompressedLength];
		int compressedLength = compressor.compress(json, 0, json.length, compressed, 0,
				maxCompressedLength);
		System.out.println("LZ4 compressed: " + compressedLength);

		byte[] compressedSnappy = Snappy.compress(json);
		System.out.println("Snappy compressed: " + compressedSnappy.length);

		om = new ObjectMapper(new CBORFactory());
		json = om.writeValueAsBytes(result);
		System.out.println(json.length + " bytes CBOR");

		long startms = System.currentTimeMillis();
		byte[] compressedlz4 = compressor.compress(json);
		System.out.println(System.currentTimeMillis() - startms + " ms");
		System.out.println("LZ 4 compressed: " + compressedlz4.length);
		startms = System.currentTimeMillis();
		compressedSnappy = Snappy.compress(json);
		System.out.println(System.currentTimeMillis() - startms + " ms");
		System.out.println("Snappy compressed: " + compressedSnappy.length);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		LZ4BlockOutputStream los = new LZ4BlockOutputStream(bos);
		los.write(json);
		los.close();
		byte[] b = bos.toByteArray();

		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		LZ4BlockInputStream lis = new LZ4BlockInputStream(bis);
		byte[] db = StreamUtils.copyToByteArray(lis);

		System.out.println();
		System.out.println(json.length);
		System.out.println(db.length);

	}

}
