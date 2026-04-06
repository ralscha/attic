package ch.rasc.resourcesgen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

public class Util {

	public static void copy(Path from, Path to, Predicate<Path> fileFilter)
			throws IOException {
		Files.walkFileTree(from, new CopyTree(from, to, fileFilter));
	}

	public static void copy(Path from, Path to) throws IOException {
		Files.walkFileTree(from, new CopyTree(from, to, null));
	}

	public static void delete(Path path) throws IOException {
		if (Files.exists(path)) {
			Files.walkFileTree(path, new DeleteDirectory());
		}
	}
}
