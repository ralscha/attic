package org.immutables.samples.json;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.immutables.gson.stream.JsonGeneratorWriter;
import org.immutables.gson.stream.JsonParserReader;
import org.immutables.samples.json.autojackson.AutoDocument;
import org.immutables.samples.json.immutables.Gocument;
import org.immutables.samples.json.immutables.GsonAdaptersGocument;
import org.immutables.samples.json.pojo.OptionalJsonAdapterFactory;
import org.immutables.samples.json.pojo.OptionalTypeAdapterFactory;
import org.immutables.samples.json.pojo.PojoDocument;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@BenchmarkMode({ Mode.AverageTime })
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class JsonBenchmarks {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private String json;
	private Gson gson;
	private TypeAdapter<Gocument> gocumentAdapter;
	private Moshi moshi;

	@Setup
	public void setup() throws IOException {
		this.moshi = new Moshi.Builder().add(new OptionalJsonAdapterFactory()).build();
		this.json = Resources.toString(JsonBenchmarks.class.getResource("sample.json"),
				StandardCharsets.UTF_8);
		this.objectMapper.registerModule(new GuavaModule());
		this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		this.gson = new GsonBuilder()
				.registerTypeAdapterFactory(new OptionalTypeAdapterFactory())
				.registerTypeAdapterFactory(new GsonAdaptersGocument()).create();

		this.gocumentAdapter = this.gson.getAdapter(Gocument.class);
	}

	@Benchmark
	public String autoJackson() throws IOException {
		AutoDocument document = this.objectMapper.readValue(this.json,
				AutoDocument.class);
		return this.objectMapper.writeValueAsString(document);
	}

	@Benchmark
	public String pojoJackson() throws IOException {
		PojoDocument document = this.objectMapper.readValue(this.json,
				PojoDocument.class);
		return this.objectMapper.writeValueAsString(document);
	}

	@Benchmark
	public String pojoGson() {
		PojoDocument document = this.gson.fromJson(this.json, PojoDocument.class);
		return this.gson.toJson(document);
	}

	@Benchmark
	public String pojoMoshi() throws IOException {
		JsonAdapter<PojoDocument> adapter = this.moshi.adapter(PojoDocument.class);
		PojoDocument document = adapter.fromJson(this.json);
		return adapter.toJson(document);
	}

	@Benchmark
	public String immutablesMoshi() throws IOException {
		JsonAdapter<Gocument> adapter = this.moshi.adapter(Gocument.class);
		Gocument document = adapter.fromJson(this.json);
		return adapter.toJson(document);
	}

	@SuppressWarnings("resource")
	@Benchmark
	public String pojoGsonJackson() throws IOException {
		JsonParser parser = this.objectMapper.getFactory().createParser(this.json);
		JsonReader reader = new JsonParserReader(parser);

		Object pojo = this.gson.fromJson(reader, PojoDocument.class);

		SegmentedStringWriter sw = new SegmentedStringWriter(
				this.objectMapper.getFactory()._getBufferRecycler());
		JsonGenerator generator = this.objectMapper.getFactory().createGenerator(sw);
		JsonWriter writer = new JsonGeneratorWriter(generator);
		this.gson.toJson(pojo, PojoDocument.class, writer);
		writer.close();
		return sw.toString();
	}

	@SuppressWarnings("resource")
	@Benchmark
	public String immutablesGsonJackson() throws IOException {
		JsonParser parser = this.objectMapper.getFactory().createParser(this.json);
		JsonReader reader = new JsonParserReader(parser);

		Gocument gocument = this.gocumentAdapter.read(reader);

		SegmentedStringWriter sw = new SegmentedStringWriter(
				this.objectMapper.getFactory()._getBufferRecycler());
		JsonGenerator generator = this.objectMapper.getFactory().createGenerator(sw);
		JsonWriter writer = new JsonGeneratorWriter(generator);

		this.gocumentAdapter.write(writer, gocument);

		writer.close();
		return sw.toString();
	}

	@SuppressWarnings("resource")
	@Benchmark
	public String immutablesGson() throws IOException {
		Gocument gocument = this.gocumentAdapter.fromJson(this.json);
		return this.gocumentAdapter.toJson(gocument);
	}
}
