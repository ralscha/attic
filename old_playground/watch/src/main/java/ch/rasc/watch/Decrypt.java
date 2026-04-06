package ch.rasc.watch;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Decrypt {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {

		if (args.length == 3) {
			Path inputFile = Paths.get(args[0]);
			byte[] iv = Files.readAllBytes(Paths.get(args[0] + ".iv"));

			String password = args[2];

			SecretKey key = Encrypt.createSecretKey(password,
					inputFile.getFileName().toString());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

			Path outputDir = Paths.get(args[1]);
			ZipEntry entry;

			try (InputStream is = Files.newInputStream(inputFile);
					CipherInputStream cis = new CipherInputStream(is, cipher);
					ZipInputStream zis = new ZipInputStream(cis);
					ReadableByteChannel inChannel = Channels.newChannel(zis)) {
				while ((entry = zis.getNextEntry()) != null) {

					Path entryFile = outputDir.resolve(Paths.get(entry.getName()));
					System.out.println(entryFile);

					Files.createDirectories(entryFile.getParent());
					try (FileChannel out = FileChannel.open(entryFile,
							StandardOpenOption.WRITE, StandardOpenOption.CREATE,
							StandardOpenOption.TRUNCATE_EXISTING)) {
						copy(inChannel, out);
					}
				}
			}
		}
		else {
			System.out.println(
					"java ch.rasc.watch.Decrypt <encryptedZipInputFile> <outputFile> <password>");
		}

	}

	public static void copy(final ReadableByteChannel src, final WritableByteChannel dest)
			throws IOException {
		final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);

		while (src.read(buffer) != -1) {
			buffer.flip();
			dest.write(buffer);
			buffer.compact();
		}

		buffer.flip();

		while (buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}
}
