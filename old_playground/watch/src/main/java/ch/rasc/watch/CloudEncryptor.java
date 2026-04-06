package ch.rasc.watch;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ByteChannel;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.DeflaterOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CloudEncryptor {

	static final Map<WatchKey, Path> watchKeyToPathMap = new HashMap<>();

	private static void registerTree(final WatchService watchService, Path start)
			throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
					throws IOException {
				WatchKey key = dir.register(watchService,
						StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_MODIFY,
						StandardWatchEventKinds.ENTRY_DELETE);
				watchKeyToPathMap.put(key, dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	private static void watch(WatchService watchService, Cipher cipher,
			Path localDirectory, Path cloudDirectory)
			throws IOException, InterruptedException {
		registerTree(watchService, localDirectory);
		registerTree(watchService, cloudDirectory);

		while (true) {
			WatchKey key = watchService.take();
			for (WatchEvent<?> watchEvent : key.pollEvents()) {
				Kind<?> kind = watchEvent.kind();
				Path eventPath = (Path) watchEvent.context();

				Path directory = watchKeyToPathMap.get(key);
				Path child = directory.resolve(eventPath);

				if (kind == StandardWatchEventKinds.ENTRY_CREATE
						&& Files.isDirectory(child)) {
					registerTree(watchService, child);
				}
				else if ((kind == StandardWatchEventKinds.ENTRY_MODIFY
						|| kind == StandardWatchEventKinds.ENTRY_CREATE)
						&& !Files.isDirectory(child)) {
					if (child.toString().endsWith("_enc")
							&& child.startsWith(cloudDirectory)) {
						// nothing here right now
					}
					else if (!child.toString().endsWith("_enc")
							&& child.startsWith(localDirectory)) {

						System.out.println("encrypt: " + child);
						Path encryptedFile = child.resolveSibling(
								Paths.get(child.getFileName().toString() + "_enc"));

						while (true) {
							try (ByteChannel channel = Files.newByteChannel(child)) {
								break;
							}
							catch (FileSystemException fse) {
								TimeUnit.SECONDS.sleep(1);
							}
						}

						try (InputStream is = Files.newInputStream(child);
								BufferedInputStream bufferedIn = new BufferedInputStream(
										is);
								OutputStream out = Files.newOutputStream(encryptedFile);
								CipherOutputStream cos = new CipherOutputStream(out,
										cipher);
								DeflaterOutputStream dos = new DeflaterOutputStream(
										cos)) {

							final byte[] buffer = new byte[1024];
							int n = 0;
							while ((n = bufferedIn.read(buffer)) != -1) {
								dos.write(buffer, 0, n);
							}
						}

						Path cloudPath = cloudDirectory.resolve(
								encryptedFile.subpath(localDirectory.getNameCount(),
										encryptedFile.getNameCount()));

						Files.move(encryptedFile, cloudPath,
								StandardCopyOption.REPLACE_EXISTING);

					}
				}
				else if (kind == StandardWatchEventKinds.ENTRY_DELETE
						&& !Files.isDirectory(child)) {
					System.out.println("DELETE: " + child);
					if (child.toString().endsWith("_enc")
							&& child.startsWith(cloudDirectory)) {
						String fileName = child.getFileName().toString();
						Path plainFile = child.resolveSibling(
								Paths.get(fileName.substring(0, fileName.length() - 4)));
						Path localFile = localDirectory.resolve(plainFile.subpath(
								cloudDirectory.getNameCount(), plainFile.getNameCount()));
						Files.deleteIfExists(localFile);
					}
					else if (!child.toString().endsWith("_enc")
							&& child.startsWith(localDirectory)) {
						Path encryptedFile = child.resolveSibling(
								Paths.get(child.getFileName().toString() + "_enc"));
						Path cloudFile = cloudDirectory.resolve(
								encryptedFile.subpath(localDirectory.getNameCount(),
										encryptedFile.getNameCount()));
						Files.deleteIfExists(cloudFile);
					}
				}

			}

			boolean valid = key.reset();
			if (!valid) {
				watchKeyToPathMap.remove(key);
				if (watchKeyToPathMap.isEmpty()) {
					break;
				}
			}
		}

	}

	public static void main(String[] args)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

		String key = "97SrFHfWqOlp3vfIOqw9xA==";

		byte[] keyByteArray = Base64.getDecoder().decode(key);
		SecretKeySpec skeySpec = new SecretKeySpec(keyByteArray, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		Path localDir = Paths.get("C:/localDir");
		Path cloudDir = Paths.get("C:/cloudDir");
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			watch(watchService, cipher, localDir, cloudDir);
		}
		catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
