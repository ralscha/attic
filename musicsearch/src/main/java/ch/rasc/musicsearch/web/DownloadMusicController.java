package ch.rasc.musicsearch.web;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.rasc.musicsearch.AppConfig;
import ch.rasc.musicsearch.service.IndexService;

@Controller
public class DownloadMusicController {

	private final AppConfig appConfig;

	private final IndexService indexService;

	@Autowired
	public DownloadMusicController(AppConfig appConfig, IndexService indexService) {
		this.appConfig = appConfig;
		this.indexService = indexService;
	}

	@RequestMapping(value = "/downloadMusic", method = RequestMethod.GET)
	public void download(@RequestParam(value = "docId", required = true) int docId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		Document doc = this.indexService.getIndexSearcher().doc(docId);

		if (doc != null) {

			Path musicFile = Paths.get(this.appConfig.getMusicDir(), doc.get("directory"),
					doc.get("fileName"));

			String contentType = Files.probeContentType(musicFile);
			response.setContentType(contentType);
			long fileSize = Files.size(musicFile);

			if (StringUtils.hasText(this.appConfig.getNginxSendFileContext())) {
				String redirectUrl = this.appConfig.getNginxSendFileContext() + "/"
						+ doc.get("directory") + "/" + doc.get("fileName");
				response.setHeader("X-Accel-Redirect", redirectUrl);
			}
			else if (this.appConfig.isApacheSendFile()) {
				response.setHeader("X-SendFile", musicFile.toAbsolutePath().toString());
			}
			else if (Boolean.TRUE
					.equals(request.getAttribute("org.apache.tomcat.sendfile.support"))) {
				long startAt = 0;
				long end = fileSize - 1;
				String rangeHeader = request.getHeader("Range");
				if (rangeHeader != null) {
					response.setStatus(206);

					String[] startEnd = rangeHeader.replace("bytes=", "").split("-");
					if (startEnd.length >= 1) {
						startAt = Long.parseLong(startEnd[0]);
					}
					if (startEnd.length == 2) {
						end = Long.parseLong(startEnd[1]);
					}

					response.setHeader("Content-Range",
							String.format("bytes %d-%d/%d", startAt, end, fileSize));
					long dataToWrite = end + 1 - startAt;
					response.setContentLength((int) dataToWrite);
				}
				else {
					response.setContentLength((int) fileSize);
				}

				request.setAttribute("org.apache.tomcat.sendfile.filename",
						musicFile.toString());
				request.setAttribute("org.apache.tomcat.sendfile.start", startAt);
				request.setAttribute("org.apache.tomcat.sendfile.end", end + 1);

			}
			else {
				response.setContentLength((int) fileSize);
				try (OutputStream out = response.getOutputStream()) {
					Files.copy(musicFile, out);
				}
			}
		}

	}
}