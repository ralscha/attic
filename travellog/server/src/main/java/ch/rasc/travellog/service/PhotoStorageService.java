package ch.rasc.travellog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Service;

import ch.rasc.travellog.Application;
import ch.rasc.travellog.config.AppProperties;

@Service
public class PhotoStorageService {

	private final Path photoStorageLocation;

	private MessageDigest messageDigest;

	public PhotoStorageService(AppProperties appProperties) {
		this.photoStorageLocation = Paths.get(appProperties.getPhotoStorageLocation());

		try {
			Files.createDirectories(this.photoStorageLocation);
		}
		catch (IOException e) {
			Application.log.error("init photo storage service", e);
		}

		try {
			this.messageDigest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			Application.log.error("get MD5 instance");
		}

	}

	public String store(byte[] data) throws IOException {
		byte[] thedigest = this.messageDigest.digest(data);
		String storageId = Base64.getEncoder().withoutPadding().encodeToString(thedigest);

		String dirName = storageId.substring(0, 2);
		String fileName = storageId.substring(2);

		Path dir = this.photoStorageLocation.resolve(dirName);
		Files.createDirectories(this.photoStorageLocation);

		Path file = dir.resolve(fileName);
		Files.write(file, data);

		return storageId;
	}

	public byte[] fetch(String storageId) throws IOException {
		String dirName = storageId.substring(0, 2);
		String fileName = storageId.substring(2);

		Path file = this.photoStorageLocation.resolve(dirName).resolve(fileName);
		return Files.readAllBytes(file);
	}

	public void delete(String storageId) throws IOException {
		String dirName = storageId.substring(0, 2);
		String fileName = storageId.substring(2);

		Path file = this.photoStorageLocation.resolve(dirName).resolve(fileName);
		Files.deleteIfExists(file);
	}

}
