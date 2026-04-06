package ch.rasc.svg2img;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;

public class Svg2PngServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String type = request.getParameter("type");
		String svg = request.getParameter("svg");
		String filename = request.getParameter("filename");

		if (request.getParameter("pdf") != null) {
			type = "application/pdf";
		}

		String postfix;
		if ("image/jpeg".equals(type)) {
			response.setContentType("image/jpeg");
			postfix = "jpg";
		}
		else if ("application/pdf".equals(type)) {
			response.setContentType("application/pdf");
			postfix = "pdf";
		}
		else {
			response.setContentType("image/png");
			postfix = "png";
		}

		if (filename == null) {
			filename = "chart";
		}

		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + filename + "." + postfix + "\";");

		try (StringReader stringReader = new StringReader(svg);
				OutputStream out = response.getOutputStream()) {
			try {
				TranscoderInput input = new TranscoderInput(stringReader);
				TranscoderOutput output = new TranscoderOutput(out);

				if ("image/jpeg".equals(type)) {
					JPEGTranscoder jpegTranscoder = new JPEGTranscoder();
					jpegTranscoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 1F);
					jpegTranscoder.transcode(input, output);
				}
				else if ("application/pdf".equals(type)) {
					new PDFTranscoder().transcode(input, output);
				}
				else {
					new PNGTranscoder().transcode(input, output);
				}

			}
			catch (TranscoderException e) {
				throw new ServletException(e);
			}
		}
	}
}
