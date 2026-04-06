package ch.rasc.upload.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.rasc.upload.Application;

@Service
public class FileManager {

	private final String uploadDirectory;

	public FileManager(@Value("#{environment.uploadDirectory}") String uploadDirectory) {
		this.uploadDirectory = uploadDirectory;
		Path dataDir = Paths.get(uploadDirectory);

		try {
			Files.createDirectories(dataDir);
		}
		catch (IOException e) {
			Application.logger.error("constructor", e);
		}
	}

	public boolean chunkExists(String identifier, Integer chunkNumber, Long chunkSize)
			throws IOException {
		Path chunkFile = Paths.get(this.uploadDirectory, identifier,
				chunkNumber.toString());
		if (Files.exists(chunkFile)) {
			long size = (Long) Files.getAttribute(chunkFile, "basic:size");
			return size == chunkSize;
		}
		return false;
	}

	public boolean isSupported(@SuppressWarnings("unused") String resumableFilename) {
		return true;
	}

	public void storeChunk(String identifier, Integer chunkNumber,
			InputStream inputStream) throws IOException {
		Path chunkFile = Paths.get(this.uploadDirectory, identifier,
				chunkNumber.toString());
		try {
			Files.createDirectories(chunkFile);
		}
		catch (IOException e) {
			Application.logger.error("store chunk", e);
		}
		Files.copy(inputStream, chunkFile, StandardCopyOption.REPLACE_EXISTING);
	}

	public boolean allChunksUploaded(String identifier, Long chunkSize, Long totalSize) {

		long noOfChunks = totalSize / chunkSize;

		for (int chunkNo = 1; chunkNo <= noOfChunks; chunkNo++) {
			if (!Files.exists(Paths.get(this.uploadDirectory, identifier,
					String.valueOf(chunkNo)))) {
				return false;
			}
		}
		return true;

	}

	public void mergeAndDeleteChunks(String fileName, String identifier, Long chunkSize,
			final Long totalSize) throws IOException {
		long noOfChunks = totalSize / chunkSize;

		Path newFilePath = Paths.get(this.uploadDirectory, fileName);
		if (Files.exists(newFilePath)) {
			Files.delete(newFilePath);
		}

		try (OutputStream out = Files.newOutputStream(newFilePath);
				BufferedOutputStream bos = new BufferedOutputStream(out)) {
			for (int chunkNo = 1; chunkNo <= noOfChunks; chunkNo++) {
				Path chunkPath = Paths.get(this.uploadDirectory, identifier,
						String.valueOf(chunkNo));
				Files.copy(chunkPath, bos);
				Files.delete(chunkPath);
			}
		}

		Files.delete(Paths.get(this.uploadDirectory, identifier));
	}

}
