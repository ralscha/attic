package ch.rasc.avro;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

public class SerializeWithoutSchema {
	public static void main(String... args) throws IOException {
		User user1 = new User();
		user1.setName("Alyssa");
		user1.setFavoriteNumber(256);
		// Leave favorite color null

		// Alternate constructor
		User user2 = new User("Ben", 7, "red");

		// Construct via builder
		User user3 = User.newBuilder().setName("Charlie").setFavoriteColor("blue")
				.setFavoriteNumber(null).build();

		// write only date, no schema

		File f = new File("users_without_schema.avro");
		try (OutputStream fos = Files.newOutputStream(f.toPath())) {
			BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(fos, null);
			DatumWriter<User> writer = new SpecificDatumWriter<>(User.getClassSchema());

			writer.write(user1, encoder);
			writer.write(user2, encoder);
			writer.write(user3, encoder);
			encoder.flush();
		}

	}
}
