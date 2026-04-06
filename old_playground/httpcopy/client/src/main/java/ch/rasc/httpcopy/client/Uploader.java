package ch.rasc.httpcopy.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;

import ch.rasc.httpcopy.ChunkInfoOuterClass.ChunkInfo;
import ch.rasc.httpcopy.ChunkOuterClass.Chunk;
import ch.rasc.httpcopy.FilesInfoOuterClass.FileInfo;
import ch.rasc.httpcopy.FilesInfoOuterClass.FilesInfo;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Uploader {

	private static final MediaType X_PROTOBUF_MEDIATYPE = MediaType
			.parse("application/x-protobuf");

	private final static int CHUNK_SIZE = 1 * 1024 * 1024;

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	private ScheduledFuture<?> watchTask;

	private final OkHttpClient httpClient;

	private Config config;

	private WorkQueue workQueue;

	private DirectoryWatchService watchService;

	public Uploader() {
		this.httpClient = new OkHttpClient();
	}

	public void start(Config cfg) {
		try {
			this.config = cfg;

			stop();

			this.workQueue = new WorkQueue();
			this.watchService = new DirectoryWatchService(this.workQueue);

			// list all files from the watched directory and add them to the working queue
			addFilesToQueue(this.config.getWatchDir());

			this.watchService.watch(this.config.getWatchDir());

			this.watchTask = this.scheduler.scheduleWithFixedDelay(this::watch, 5, 5,
					TimeUnit.SECONDS);
		}
		catch (IOException e) {
			LoggerFactory.getLogger(Uploader.class).error("start", e);
		}
	}

	private void addFilesToQueue(Path path) throws IOException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path entry : stream) {
				if (Files.isDirectory(entry)) {
					addFilesToQueue(entry);
				}
				else {
					Path watchDir = this.config.getWatchDir();
					Path subpath = entry.subpath(watchDir.getNameCount(),
							entry.getNameCount());
					this.workQueue.add(watchDir, subpath);
				}
			}
		}
	}

	private void watch() {
		try {
			Set<WorkJob> paths = this.workQueue.getPaths();
			if (!paths.isEmpty()) {
				handlePaths(paths);
			}
		}
		catch (IOException e) {
			LoggerFactory.getLogger(Uploader.class).error("watch loop", e);
		}
	}

	public void stop() {
		if (this.watchTask != null) {
			this.watchTask.cancel(false);
			this.watchTask = null;
		}

		if (this.watchService != null) {
			this.watchService.shutdown();
			this.watchService = null;
		}
	}

	public void shutdown() {
		this.scheduler.shutdown();
	}

	public void handlePaths(Set<WorkJob> paths) {
		try {
			FilesInfo filesInfoRequest = buildFilesInfo(paths);
			FilesInfo filesInfoResponse = sendFilesInfo(filesInfoRequest);
			if (filesInfoResponse != null) {
				Map<String, WorkJob> jobs = paths.stream().collect(
						Collectors.toMap(WorkJob::getFilename, Function.identity()));

				for (FileInfo fileInfo : filesInfoResponse.getFileList()) {
					WorkJob job = jobs.get(fileInfo.getName());
					if (fileInfo.getStatus() == FileInfo.Status.FILE_DOES_NOT_EXISTS) {
						uploadFile(job);
					}
					else if (fileInfo.getStatus() == FileInfo.Status.FILENAME_EXISTS) {
						if (this.config.isOverwrite()) {
							uploadFile(job);
						}
						else {
							job.setFilename(generateUniqueFilename(job.getFilename()));
							// todo check if this filename is unique in this set of paths
							this.workQueue.add(job);
						}
					}
				}
			}
			else {
				// request unsuccessful, re-add all paths to the queue
				this.workQueue.add(paths);
			}
		}
		catch (IOException e) {
			LoggerFactory.getLogger(Uploader.class).error("handleMessage", e);
			this.workQueue.add(paths);
		}
	}

	private void uploadFile(WorkJob job) throws IOException {
		ChunkInfo chunkInfo = ChunkInfo.newBuilder()
				.setClientId(this.config.getClientId()).setFilename(job.getFilename())
				.setSize(CHUNK_SIZE).setTotalSize(Files.size(job.getFile())).build();

		ChunkInfo chunkInfoResponse = sendChunkInfo(chunkInfo);
		if (chunkInfoResponse != null) {
			uploadChunks(job, chunkInfoResponse.getExistingChunksList());
		}
		else {
			// request unsuccessful, re-add path to the queue
			this.workQueue.add(job);
		}
	}

	private void uploadChunks(WorkJob job, List<Integer> existingChunks)
			throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(CHUNK_SIZE);

		try (SeekableByteChannel channel = Files.newByteChannel(job.getFile(),
				StandardOpenOption.READ)) {

			int chunkNo = 1;
			long totalSize = Files.size(job.getFile());
			while (channel.read(buffer) > 0) {
				if (!existingChunks.contains(chunkNo)) {
					buffer.flip();
					byte[] payload = new byte[buffer.remaining()];
					buffer.get(payload);

					Chunk chunk = Chunk.newBuilder()
							.setClientId(this.config.getClientId())
							.setFilename(job.getFilename()).setNo(chunkNo)
							.setSize(CHUNK_SIZE).setTotalSize(totalSize)
							.setPayload(ByteString.copyFrom(payload)).build();

					if (!uploadChunk(chunk)) {
						// unsuccesful. let's stop here and re-add the path to the queue
						this.workQueue.add(job);
						break;
					}
				}

				buffer.clear();
				chunkNo++;
			}
		}
	}

	private boolean uploadChunk(Chunk chunk) throws IOException {
		HttpUrl filesinfoUrl = HttpUrl.parse(this.config.getServerEndpoint() + "/chunk");

		RequestBody body = RequestBody.create(X_PROTOBUF_MEDIATYPE, chunk.toByteArray());
		Request request = new Request.Builder().url(filesinfoUrl).post(body).build();
		try (Response response = this.httpClient.newCall(request).execute()) {
			return response.isSuccessful();
		}
	}

	private ChunkInfo sendChunkInfo(ChunkInfo chunkInfo) throws IOException {
		HttpUrl filesinfoUrl = HttpUrl
				.parse(this.config.getServerEndpoint() + "/chunkinfo");

		RequestBody body = RequestBody.create(X_PROTOBUF_MEDIATYPE,
				chunkInfo.toByteArray());
		Request request = new Request.Builder().url(filesinfoUrl).post(body).build();
		try (Response response = this.httpClient.newCall(request).execute();
				ResponseBody responseBody = response.body()) {
			if (response.isSuccessful()) {
				if (responseBody != null) {
					return ChunkInfo.parseFrom(responseBody.bytes());
				}
			}

			LoggerFactory.getLogger(Uploader.class).info("chunkinfo unsuccessful: {}",
					responseBody);
		}
		return null;
	}

	private FilesInfo sendFilesInfo(FilesInfo filesInfo) throws IOException {
		HttpUrl filesinfoUrl = HttpUrl
				.parse(this.config.getServerEndpoint() + "/filesinfo");

		RequestBody body = RequestBody.create(X_PROTOBUF_MEDIATYPE,
				filesInfo.toByteArray());
		Request request = new Request.Builder().url(filesinfoUrl).post(body).build();
		try (Response response = this.httpClient.newCall(request).execute();
				ResponseBody responseBody = response.body()) {

			if (response.isSuccessful()) {
				if (responseBody != null) {
					return FilesInfo.parseFrom(responseBody.bytes());
				}
			}

			LoggerFactory.getLogger(Uploader.class).info("filesinfo unsuccessful: {}",
					responseBody);
		}
		return null;
	}

	private static FilesInfo buildFilesInfo(Set<WorkJob> paths) throws IOException {
		FilesInfo.Builder builder = FilesInfo.newBuilder();

		for (WorkJob job : paths) {
			Path path = job.getFile();
			FileInfo fileInfo = FileInfo.newBuilder().setHash(calcMd5(path))
					.setName(job.getFilename()).setSize(Files.size(path)).build();

			builder.addFile(fileInfo);
		}
		return builder.build();
	}

	private static String calcMd5(Path path) throws IOException {
		try (InputStream is = Files.newInputStream(path)) {
			byte[] digest = digest("MD5", is);
			return Base64.getEncoder().encodeToString(digest);
		}
	}

	private static byte[] digest(String algorithm, InputStream inputStream)
			throws IOException {
		MessageDigest messageDigest = getDigest(algorithm);

		final byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			messageDigest.update(buffer, 0, bytesRead);
		}
		return messageDigest.digest();
	}

	private static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException(
					"Could not find MessageDigest with algorithm \"" + algorithm + "\"",
					ex);
		}
	}

	private static String generateUniqueFilename(String filename) {
		int lastdot = filename.lastIndexOf(".");
		int lastus = filename.lastIndexOf("_");

		String base;
		String suffix;
		if (lastdot != -1) {
			base = filename.substring(0, lastdot);
			suffix = filename.substring(lastdot);
		}
		else {
			base = filename;
			suffix = "";
		}

		if (lastus != -1) {
			String noPart = base.substring(lastus + 1);
			if (isNumeric(noPart)) {
				int next = Integer.parseInt(noPart) + 1;
				base = base.substring(0, lastus + 1) + next;
			}
			else {
				base = base + "_1";
			}
		}
		else {
			base = base + "_1";
		}

		return base + suffix;
	}

	private static boolean isNumeric(String str) {
		if (str.isEmpty()) {
			return false;
		}

		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

}
