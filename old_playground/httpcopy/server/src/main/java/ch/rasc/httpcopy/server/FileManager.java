package ch.rasc.httpcopy.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import ch.rasc.httpcopy.ChunkInfoOuterClass.ChunkInfo;
import ch.rasc.httpcopy.ChunkOuterClass.Chunk;
import ch.rasc.httpcopy.FilesInfoOuterClass.FileInfo;

@Service
public class FileManager {

	private final Path uploadDirectory;

	public FileManager(AppProperties appProperties) {
		this.uploadDirectory = Paths.get(appProperties.getUploadDirectory());
	}

	private boolean chunkExists(String clientId, String filename, long chunkSize,
			int chunkNo) throws IOException {
		Path chunkPath = getChunkPath(clientId, filename, chunkNo);
		if (Files.exists(chunkPath)) {
			long size = Files.size(chunkPath);
			return size == chunkSize;
		}
		return false;
	}

	public boolean isSupported(Path path) {
		return true;
	}

	public void storeChunk(Chunk chunk) throws IOException {
		Path chunkPath = getChunkPath(chunk.getClientId(), chunk.getFilename(),
				chunk.getNo());
		Files.createDirectories(chunkPath.getParent());

		try (SeekableByteChannel channel = Files.newByteChannel(chunkPath,
				StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE,
				StandardOpenOption.WRITE)) {
			ByteBuffer byteBuffer = chunk.getPayload().asReadOnlyByteBuffer();
			channel.write(byteBuffer);
		}
	}

	public boolean allChunksUploaded(Chunk chunk) throws IOException {
		long noOfChunks = noOfChunks(chunk.getSize(), chunk.getTotalSize());

		for (int chunkNo = 1; chunkNo <= noOfChunks; chunkNo++) {

			long chunkSize;
			if (chunkNo < noOfChunks) {
				chunkSize = chunk.getSize();
			}
			else {
				chunkSize = getSizeOfLastChunk(chunk.getSize(), chunk.getTotalSize());
			}

			if (!chunkExists(chunk.getClientId(), chunk.getFilename(), chunkSize,
					chunkNo)) {
				return false;
			}
		}
		return true;
	}

	public void mergeAndDeleteChunks(Chunk chunk) throws IOException {
		long noOfChunks = noOfChunks(chunk.getSize(), chunk.getTotalSize());

		Path mergeFilePath = this.uploadDirectory.resolve(chunk.getFilename() + "_merge");
		Files.createDirectories(mergeFilePath.getParent());

		try (OutputStream out = Files.newOutputStream(mergeFilePath)) {
			for (int chunkNo = 1; chunkNo <= noOfChunks; chunkNo++) {
				Files.copy(
						getChunkPath(chunk.getClientId(), chunk.getFilename(), chunkNo),
						out);
			}
		}

		Path newFilePath = this.uploadDirectory.resolve(chunk.getFilename());
		Files.move(mergeFilePath, newFilePath, StandardCopyOption.ATOMIC_MOVE,
				StandardCopyOption.REPLACE_EXISTING);

		// cleanup
		for (int chunkNo = 1; chunkNo <= noOfChunks; chunkNo++) {
			Path chunkPath = getChunkPath(chunk.getClientId(), chunk.getFilename(),
					chunkNo);
			Files.delete(chunkPath);
		}

		Files.delete(getChunkDir(chunk.getClientId(), chunk.getFilename()));
	}

	private Path getChunkDir(String clientId, String filename) {
		byte[] md5 = DigestUtils.md5Digest(filename.getBytes(StandardCharsets.UTF_8));
		return this.uploadDirectory
				.resolve(clientId + "_" + Base64.getUrlEncoder().encodeToString(md5));
	}

	private Path getChunkPath(String clientId, String filename, int chunkNo) {
		return getChunkDir(clientId, filename).resolve(String.valueOf(chunkNo));
	}

	public FileInfo checkFile(FileInfo fileInfo) throws IOException {
		FileInfo.Builder builder = FileInfo.newBuilder().setName(fileInfo.getName());

		Path filePath = this.uploadDirectory.resolve(fileInfo.getName());
		if (Files.exists(filePath)) {
			long size = Files.size(filePath);
			if (size == fileInfo.getSize()) {
				if (calcMd5(filePath).equals(fileInfo.getHash())) {
					builder.setStatus(FileInfo.Status.FILE_EXISTS);
				}
				else {
					builder.setStatus(FileInfo.Status.FILENAME_EXISTS);
				}
			}
			else {
				builder.setStatus(FileInfo.Status.FILENAME_EXISTS);
			}
		}
		else {
			if (isSupported(filePath)) {
				builder.setStatus(FileInfo.Status.FILE_DOES_NOT_EXISTS);
			}
			else {
				builder.setStatus(FileInfo.Status.FILETYPE_NOT_SUPPORTED);
			}
		}

		return builder.build();
	}

	public ChunkInfo checkChunks(ChunkInfo chunkInfo) throws IOException {
		long noOfChunks = noOfChunks(chunkInfo.getSize(), chunkInfo.getTotalSize());

		ChunkInfo.Builder builder = ChunkInfo.newBuilder();
		for (int chunkNo = 1; chunkNo <= noOfChunks; chunkNo++) {

			long chunkSize;
			if (chunkNo < noOfChunks) {
				chunkSize = chunkInfo.getSize();
			}
			else {
				chunkSize = getSizeOfLastChunk(chunkInfo.getSize(),
						chunkInfo.getTotalSize());
			}

			if (chunkExists(chunkInfo.getClientId(), chunkInfo.getFilename(), chunkSize,
					chunkNo)) {
				builder.addExistingChunks(chunkNo);
			}
		}

		return builder.build();
	}

	private static long getSizeOfLastChunk(long chunkSize, long totalSize) {
		long sizeOfLastChunk = totalSize % chunkSize;
		if (sizeOfLastChunk == 0) {
			return chunkSize;
		}
		return sizeOfLastChunk;
	}

	private static long noOfChunks(long chunkSize, long totalSize) {
		long noOfChunks = totalSize / chunkSize;
		if (totalSize % chunkSize != 0) {
			noOfChunks++;
		}
		return noOfChunks;
	}

	private static String calcMd5(Path path) throws IOException {
		try (InputStream is = Files.newInputStream(path)) {
			return Base64.getEncoder().encodeToString(DigestUtils.md5Digest(is));
		}
	}

}
