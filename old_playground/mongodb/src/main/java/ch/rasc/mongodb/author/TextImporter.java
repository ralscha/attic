package ch.rasc.mongodb.author;

import java.nio.file.Path;

public interface TextImporter {

	void doImport(Path file);

}