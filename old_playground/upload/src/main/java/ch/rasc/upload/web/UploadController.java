package ch.rasc.upload.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

	private final FileManager fileManager;

	UploadController(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public void chunkExists(HttpServletResponse response,
			@RequestParam("resumableChunkNumber") Integer resumableChunkNumber,
			@RequestParam("resumableChunkSize") Long resumableChunkSize,
			@RequestParam("resumableIdentifier") String resumableIdentifier,
			@RequestParam("resumableFilename") String resumableFilename,
			@RequestParam("resumableTotalSize") Long resumableTotalSize)
			throws IOException {

		if (this.fileManager.chunkExists(resumableIdentifier, resumableChunkNumber,
				resumableChunkSize)) {
			// do not upload chunk again
			response.setStatus(200);
		}
		else {
			// chunk not on the server, upload it
			response.setStatus(404);
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void processUpload(HttpServletResponse response,
			@RequestParam("resumableChunkNumber") Integer resumableChunkNumber,
			@RequestParam("resumableChunkSize") Long resumableChunkSize,
			@RequestParam("resumableTotalSize") Long resumableTotalSize,
			@RequestParam("resumableIdentifier") String[] resumableIdentifier,
			@RequestParam("resumableFilename") String[] resumableFilename,
			@RequestParam("file") MultipartFile file) throws IOException {

		if (!this.fileManager.isSupported(resumableFilename[0])) {
			// cancel the whole upload
			response.setStatus(501);
			return;
		}

		try (InputStream is = file.getInputStream()) {
			this.fileManager.storeChunk(resumableIdentifier[0], resumableChunkNumber, is);
		}

		if (this.fileManager.allChunksUploaded(resumableIdentifier[0], resumableChunkSize,
				resumableTotalSize)) {
			this.fileManager.mergeAndDeleteChunks(resumableFilename[0],
					resumableIdentifier[0], resumableChunkSize, resumableTotalSize);
		}

		response.setStatus(200);

	}

	@RequestMapping(value = "/simpleUpload", method = RequestMethod.POST)
	public String processUpload(HttpServletRequest request)
			throws IOException, ServletException {

		for (Part part : request.getParts()) {

			for (String header : part.getHeaderNames()) {
				System.out.println(header);
			}

			System.out.println("FILENAME   : " + getFileName(part));
			System.out.println("CONTENTTYPE: " + part.getContentType());
			System.out.println("SIZE       : " + part.getSize());
			System.out.println();
			part.delete();
		}
		return "redirect:index2.html";

	}

	private static String getFileName(Part part) {
		String partHeader = part.getHeader("content-disposition");

		for (String cd : partHeader.split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}
