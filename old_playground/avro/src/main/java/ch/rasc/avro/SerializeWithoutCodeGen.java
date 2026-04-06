package ch.rasc.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;

public class SerializeWithoutCodeGen {

	public static void main(String[] args) throws IOException {
		Schema schema = new Schema.Parser().parse(new File("./src/main/avro/user.avsc"));

		// Using this schema, let's create some users.

		GenericRecord user1 = new GenericData.Record(schema);
		user1.put("name", "Alyssa");
		user1.put("favorite_number", 256);
		// Leave favorite color null

		GenericRecord user2 = new GenericData.Record(schema);
		user2.put("name", "Ben");
		user2.put("favorite_number", 7);
		user2.put("favorite_color", "red");

		// Serialize user1 and user2 to disk
		File file = new File("users.avro");
		DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
		try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(
				datumWriter)) {
			dataFileWriter.create(schema, file);
			dataFileWriter.append(user1);
			dataFileWriter.append(user2);
		}
	}

}
