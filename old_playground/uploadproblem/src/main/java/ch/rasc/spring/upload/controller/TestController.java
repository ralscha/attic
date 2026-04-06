package ch.rasc.spring.upload.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TestController {

	@RequestMapping(value = "/getSomething", method = RequestMethod.GET)
	@ResponseBody
	public String getSomething() {
		return "A TEST";
	}

	@RequestMapping(value = "/uploadTest", method = RequestMethod.POST)
	public String uploadTest(HttpServletRequest request,
			@RequestParam(value = "oneFile", required = false) final Part oneFile,
			@RequestParam(value = "oneFile",
					required = false) final MultipartFile oneMultipartFile,
			@RequestParam(value = "multipleFiles",
					required = false) final List<MultipartFile> multipleMultipartFiles)
			throws IllegalStateException, IOException, ServletException {

		Enumeration<String> e = request.getParameterNames();
		System.out.println("-- START PARAMETERS --");
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			System.out.printf("  %s->%s\n", key, request.getParameter(key));
		}
		System.out.println("-- END PARAMETERS --\n");

		for (Part part : request.getParts()) {
			System.out.println("-- START PART --");
			System.out.println("  ContentType: " + part.getContentType());
			System.out.println("  Name       : " + part.getName());
			System.out.println("  Size       : " + part.getSize());

			System.out.println("  Headers    :");
			for (String header : part.getHeaderNames()) {
				System.out.printf("     %s->%s\n", header, part.getHeader(header));
			}

			// Test if getHeader is case insensitive according to servlet 3.0
			// spec
			boolean isGetHeaderCaseInsensitive = true;
			for (String header : part.getHeaderNames()) {
				String h1 = part.getHeader(header);
				String h2 = part.getHeader(header.toUpperCase());
				if (!h1.equals(h2)) {
					isGetHeaderCaseInsensitive = false;
					break;
				}
			}

			System.out.println();
			if (!isGetHeaderCaseInsensitive) {
				System.out.println("   part.getHeader is NOT case insensitive");
			}
			else {
				System.out.println("   part.getHeader IS case insensitive");
			}

			System.out.println("-- END PART --\n");
		}

		System.out.println("-- START ONE FILE --");
		if (oneFile != null) {
			System.out.println("FileName: " + getFileName(oneFile));
			System.out.println("Size    : " + oneFile.getSize());
		}
		System.out.println("-- END ONE FILE --\n");

		System.out.println("-- START ONE MULTIPART FILE --");
		if (oneMultipartFile != null) {
			System.out.println("FileName: " + oneMultipartFile.getOriginalFilename());
			System.out.println("Size    : " + oneMultipartFile.getSize());
		}
		System.out.println("-- END ONE MULTIPART FILE --\n");

		System.out.println("-- START MULTIPLE MULTIPART FILES --");
		if (multipleMultipartFiles != null && !multipleMultipartFiles.isEmpty()) {
			for (MultipartFile multipartFile : multipleMultipartFiles) {
				System.out.println("FileName: " + multipartFile.getOriginalFilename());
				System.out.println("Size    : " + multipartFile.getSize());
				System.out.println();
			}
		}
		System.out.println("-- END MULTIPLE MULTIPART FILES --\n");

		return "redirect:index.html";
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
