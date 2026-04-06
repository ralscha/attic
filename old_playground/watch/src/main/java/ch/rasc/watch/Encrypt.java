package ch.rasc.watch;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.jooq.lambda.Unchecked;

public class Encrypt {

	public static void main(String[] args)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException {

		if (args.length == 3) {

			Path tmpFile = Files.createTempFile("encrypt", "zip");
			tmpFile.toFile().deleteOnExit();

			Path inputDirectory = Paths.get(args[0]);

			try (ZipOutputStream zos = new ZipOutputStream(
					Files.newOutputStream(tmpFile));
					WritableByteChannel out = Channels.newChannel(zos)) {
				zos.setLevel(9);
				Files.walk(inputDirectory).filter(file -> !Files.isDirectory(file))
						.peek(System.out::println).forEach(Unchecked.consumer(file -> {
							String name = inputDirectory.getParent().relativize(file)
									.toString();
							name = name.replace('\\', '/');
							zos.putNextEntry(new ZipEntry(name));
							try (FileChannel in = FileChannel.open(file,
									StandardOpenOption.READ)) {
								in.transferTo(0, in.size(), out);
							}
						}));
			}

			// encrypt from tmpFile to encFile
			String password = args[2];
			Path outputFile = Paths.get(args[1]);

			Path encTmpFile = Files.createTempFile("encrypt", "zip.enc");
			encTmpFile.toFile().deleteOnExit();

			SecretKey key = createSecretKey(password,
					outputFile.getFileName().toString());

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, key);

			AlgorithmParameters params = cipher.getParameters();
			byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();

			try (FileChannel in = FileChannel.open(tmpFile, StandardOpenOption.READ);
					OutputStream fos = Files.newOutputStream(encTmpFile);
					CipherOutputStream cos = new CipherOutputStream(fos, cipher);
					WritableByteChannel wbc = Channels.newChannel(cos)) {
				in.transferTo(0, in.size(), wbc);
			}

			Path encFile = Paths.get(args[1]);

			if (encFile.getParent() != null
					&& !encFile.getParent().equals(encFile.getRoot())) {
				Files.createDirectories(encFile.getParent());
			}

			List<CopyOption> copyOptions = new ArrayList<>();
			if (encTmpFile.getRoot().equals(encFile.getRoot())) {
				copyOptions.add(StandardCopyOption.ATOMIC_MOVE);
			}
			copyOptions.add(StandardCopyOption.REPLACE_EXISTING);
			Files.move(encTmpFile, encFile,
					copyOptions.toArray(new CopyOption[copyOptions.size()]));

			Files.write(Paths.get(args[1] + ".iv"), iv, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);

			Files.deleteIfExists(tmpFile);
			Files.deleteIfExists(encTmpFile);

		}
		else {
			System.out.println(
					"java ch.rasc.watch.Encrypt <directory> <encryptedZipOutputFile> <password>");
		}

	}

	public static SecretKey createSecretKey(String password, String fileName)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = fileName.getBytes(StandardCharsets.UTF_8);
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey sk = factory.generateSecret(keySpec);
		SecretKey mySymmetricKey = new SecretKeySpec(sk.getEncoded(), "AES");
		return mySymmetricKey;
	}
}
