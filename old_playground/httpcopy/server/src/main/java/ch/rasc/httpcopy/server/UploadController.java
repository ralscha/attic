package ch.rasc.httpcopy.server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.rasc.httpcopy.ChunkInfoOuterClass.ChunkInfo;
import ch.rasc.httpcopy.ChunkOuterClass.Chunk;
import ch.rasc.httpcopy.FilesInfoOuterClass.FileInfo;
import ch.rasc.httpcopy.FilesInfoOuterClass.FilesInfo;

@RestController
@RequestMapping("/upload")
public class UploadController {

	private final FileManager fileManager;

	public UploadController(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@PostMapping("/filesinfo")
	public FilesInfo handleFilesInfo(@RequestBody FilesInfo filesInfo)
			throws IOException {
		FilesInfo.Builder builder = FilesInfo.newBuilder();

		for (FileInfo fileInfo : filesInfo.getFileList()) {
			FileInfo response = this.fileManager.checkFile(fileInfo);
			builder.addFile(response);
		}

		return builder.build();
	}

	@PostMapping("/chunkinfo")
	public ChunkInfo handleChunkInfo(@RequestBody ChunkInfo chunkInfo)
			throws IOException {
		return this.fileManager.checkChunks(chunkInfo);
	}

	@PostMapping("/chunk")
	public void processUpload(@RequestBody Chunk chunk, HttpServletResponse response)
			throws IOException {
		this.fileManager.storeChunk(chunk);

		if (this.fileManager.allChunksUploaded(chunk)) {
			this.fileManager.mergeAndDeleteChunks(chunk);
		}

		response.setStatus(HttpServletResponse.SC_OK);
	}

}
