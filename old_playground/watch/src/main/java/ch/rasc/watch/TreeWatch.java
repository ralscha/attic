package ch.rasc.watch;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class TreeWatch {

	static final Map<WatchKey, Path> watchKeyToPathMap = new HashMap<>();

	private static void registerTree(final WatchService watchService, Path start)
			throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
					throws IOException {
				System.out.printf("Registering %s\n", dir);
				WatchKey key = dir.register(watchService,
						StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_MODIFY,
						StandardWatchEventKinds.ENTRY_DELETE);
				watchKeyToPathMap.put(key, dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	private static void watch(WatchService watchService, Path start)
			throws IOException, InterruptedException {
		registerTree(watchService, start);

		while (true) {
			WatchKey key = watchService.take();
			for (WatchEvent<?> watchEvent : key.pollEvents()) {
				Kind<?> kind = watchEvent.kind();
				Path eventPath = (Path) watchEvent.context();

				Path directory = watchKeyToPathMap.get(key);
				// Path directory = (Path) key.watchable(); //looks like this
				// has problems with renames
				Path child = directory.resolve(eventPath);

				if (kind == StandardWatchEventKinds.ENTRY_CREATE
						&& Files.isDirectory(child)) {
					registerTree(watchService, child);
				}

				System.out.printf("%s:%s\n", child, kind);
			}

			System.out.println();

			boolean valid = key.reset();
			if (!valid) {
				watchKeyToPathMap.remove(key);
				if (watchKeyToPathMap.isEmpty()) {
					break;
				}
			}
		}

	}

	public static void main(String[] args) {
		Path path = Paths.get("C:/watchme");
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			watch(watchService, path);
		}
		catch (IOException | InterruptedException ex) {
			System.err.println(ex);
		}
	}

}
