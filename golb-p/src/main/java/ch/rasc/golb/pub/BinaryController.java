package ch.rasc.golb.pub;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import ch.rasc.golb.Application;
import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.entity.Binary;
import ch.rasc.golb.entity.CBinary;

@Controller
public class BinaryController {

	private final MongoDb mongoDb;

	public BinaryController(MongoDb mongoDb) {
		this.mongoDb = mongoDb;
	}

	@RequestMapping(path = "/binary/{id}/{fileName}", method = RequestMethod.GET)
	public void fetch(@PathVariable("id") String id,
			@PathVariable("fileName") String fileName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Binary doc = this.mongoDb.getCollection(Binary.class)
				.find(Filters.eq(CBinary.id, id)).first();

		if (doc != null && doc.getFileId() != null) {

			Document filesDoc = this.mongoDb.getCollection("binary.files")
					.find(Filters.eq("_id", doc.getFileId()))
					.projection(Projections.fields(Projections.exclude("_id"),
							Projections.include("md5")))
					.first();
			String md5 = "\"" + filesDoc.getString("md5") + "\"";

			response.setDateHeader(HttpHeaders.EXPIRES,
					System.currentTimeMillis() + 31_536_000_000L);
			response.setHeader(HttpHeaders.ETAG, md5);
			response.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000");

			String requestETag = request.getHeader(HttpHeaders.IF_NONE_MATCH);
			if (md5.equals(requestETag)) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}

			response.setContentType(doc.getType());
			response.setContentLengthLong(doc.getSize());

			GridFSBucket bucket = this.mongoDb.createBucket("binary");
			@SuppressWarnings("resource")
			ServletOutputStream out = response.getOutputStream();
			bucket.downloadToStream(doc.getFileId(), out);
			out.flush();
		}
		else {
			Application.logger.info("fetch unknown binary: {}/{}", id, fileName);
		}
	}

}
