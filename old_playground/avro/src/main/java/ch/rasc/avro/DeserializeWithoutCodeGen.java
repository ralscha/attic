package ch.rasc.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;

public class DeserializeWithoutCodeGen {

	public static void main(String[] args) throws IOException {
		Schema schema = new Schema.Parser().parse(new File("./src/main/avro/user.avsc"));
		// Deserialize Users from disk
		File file = new File("users.avro");
		// Deserialize users from disk
		DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
		try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file,
				datumReader)) {
			GenericRecord user = null;
			while (dataFileReader.hasNext()) {
				// Reuse user object by passing it to next(). This saves us from
				// allocating and garbage collecting many objects for files with
				// many items.
				user = dataFileReader.next(user);
				System.out.println(user);
			}
		}
	}

}
