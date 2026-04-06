package ch.rasc.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

public class Deserialize {

	public static void main(String[] args) throws IOException {
		// Deserialize Users from disk
		File file = new File("users.avro");
		DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
		try (DataFileReader<User> dataFileReader = new DataFileReader<>(file,
				userDatumReader)) {
			User user = null;
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
