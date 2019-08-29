package json.benchmark;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import json.application.TestDataSupplier;
import json.application.pojo.PojoSupplier;
import json.application.pojo.Response;
import json.benchmark.BenchmarkBase.ParserBenchmarkBase;
import json.benchmark.BenchmarkBase.SerializerBenchmarkBase;

public class YassonBenchmark {

	public static class ParserBenchmark extends ParserBenchmarkBase {
		Jsonb  jsonb;

		@Setup
		public void prepare() {
			this.jsonb = JsonbBuilder.create();
		}

		@Benchmark
		public Response stream() throws Exception {
			try (InputStream in = this.json.openStream()) {
				return this.jsonb.fromJson(in, Response.class);
			}
		}

		@Benchmark
		@SuppressWarnings("rawtypes")
		public Map parse2Map() throws Exception {
			try (InputStream in = this.json.openStream()) {
				return this.jsonb.fromJson(in, Map.class);
			}
		}

		@Benchmark
		public Response bufferdStream() throws Exception {
			try (InputStream in = this.json.openStream()) {
				return this.jsonb.fromJson(new BufferedInputStream(in), Response.class);
			}
		}

		@Benchmark
		public Response reader() throws Exception {
			try (InputStream in = this.json.openStream()) {
				return this.jsonb.fromJson(
						new InputStreamReader(in, StandardCharsets.UTF_8),
						Response.class);
			}
		}

		@Benchmark
		public Response bufferedReader() throws Exception {
			try (InputStream in = this.json.openStream()) {
				return this.jsonb.fromJson(
						new BufferedReader(
								new InputStreamReader(in, StandardCharsets.UTF_8)),
						Response.class);
			}
		}
	}

	public static class SerializerBenchmark extends SerializerBenchmarkBase<Response> {

		Jsonb  jsonb;

		@Setup
		public void prepare() {
			this.jsonb = JsonbBuilder.create();
		}

		@Override
		protected TestDataSupplier<Response> newSupplier() {
			return new PojoSupplier();
		}

		@Benchmark
		public String string() throws Exception {
			return this.jsonb.toJson(this.testdata);
		}
	}

	public static void main(String[] args) throws Exception {
		BenchmarkBase.runForDebug(YassonBenchmark.class);
	}
}
