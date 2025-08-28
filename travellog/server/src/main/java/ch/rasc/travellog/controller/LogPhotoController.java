package ch.rasc.travellog.controller;

import static ch.rasc.travellog.db.tables.Log.LOG;
import static ch.rasc.travellog.db.tables.LogPhoto.LOG_PHOTO;
import static ch.rasc.travellog.db.tables.Travel.TRAVEL;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.jooq.DSLContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import ch.rasc.travellog.Application;
import ch.rasc.travellog.config.security.AppUserDetail;
import ch.rasc.travellog.dto.PhotoDto;
import ch.rasc.travellog.service.PhotoStorageService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

@RestController
@RequestMapping("/be")
public class LogPhotoController {

	private final DSLContext dsl;

	private MessageDigest messageDigest;

	private final PhotoStorageService photoStorageService;

	public LogPhotoController(DSLContext dsl, PhotoStorageService photoStorageService) {
		this.dsl = dsl;
		this.photoStorageService = photoStorageService;

		try {
			this.messageDigest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			Application.log.error("get MD5 instance");
		}
	}

	@GetMapping("fetch_photo/{id}")
	public ResponseEntity<byte[]> fetchPhoto(@PathVariable("id") long id)
			throws IOException {
		var record = this.dsl.select(LOG_PHOTO.STORAGE, LOG_PHOTO.MIME_TYPE)
				.from(LOG_PHOTO).where(LOG_PHOTO.ID.eq(id)).fetchOne();
		byte[] data = this.photoStorageService.fetch(record.get(LOG_PHOTO.STORAGE));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, record.get(LOG_PHOTO.MIME_TYPE))
				.body(data);
	}

	@GetMapping("fetch_thumbnail/{id}")
	public ResponseEntity<byte[]> fetchThumbnail(@PathVariable("id") long id) {
		var record = this.dsl.select(LOG_PHOTO.THUMBNAIL, LOG_PHOTO.MIME_TYPE)
				.from(LOG_PHOTO).where(LOG_PHOTO.ID.eq(id)).fetchOne();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, record.get(LOG_PHOTO.MIME_TYPE))
				.body(record.get(LOG_PHOTO.THUMBNAIL));
	}

	@PostMapping("list_photo")
	public List<PhotoDto> listPhoto(@AuthenticationPrincipal AppUserDetail userDetails,
			@RequestParam("id") long id) {
		return this.dsl
				.select(LOG_PHOTO.ID, LOG_PHOTO.STORAGE, LOG_PHOTO.NAME,
						LOG_PHOTO.MIME_TYPE, LOG_PHOTO.SIZE, LOG_PHOTO.UPDATED)
				.from(LOG_PHOTO).join(LOG).onKey().join(TRAVEL).onKey()
				.where(LOG_PHOTO.ID.eq(id)
						.and(TRAVEL.APP_USER_ID.eq(userDetails.getAppUserId())))
				.fetch().stream()
				.map(record -> new PhotoDto(record.get(LOG_PHOTO.ID),
						record.get(LOG_PHOTO.STORAGE), record.get(LOG_PHOTO.NAME),
						record.get(LOG_PHOTO.SIZE), record.get(LOG_PHOTO.MIME_TYPE),
						createDataUrl(record.get(LOG_PHOTO.MIME_TYPE),
								record.get(LOG_PHOTO.THUMBNAIL)),
						record.get(LOG_PHOTO.UPDATED).toEpochSecond(ZoneOffset.UTC)))
				.collect(Collectors.toList());
	}

	@PostMapping("delete_photo")
	public void deletePhoto(@AuthenticationPrincipal AppUserDetail userDetails,
			@RequestParam("id") long id) {

		Set<Long> travelIds = this.dsl.select(TRAVEL.ID).from(TRAVEL)
				.where(TRAVEL.APP_USER_ID.eq(userDetails.getAppUserId()))
				.fetchSet(TRAVEL.ID);

		Long photoId = this.dsl.select(LOG_PHOTO.ID).from(LOG_PHOTO).join(LOG).onKey()
				.where(LOG_PHOTO.ID.eq(id).and(LOG.TRAVEL_ID.in(travelIds)))
				.fetchOne(LOG_PHOTO.ID);

		if (photoId != null) {
			this.dsl.delete(LOG_PHOTO).where(LOG_PHOTO.ID.eq(photoId)).execute();
		}
	}

	@PostMapping("upload_photo")
	public void uploadPhoto(@AuthenticationPrincipal AppUserDetail userDetails,
			@RequestParam("logId") long logId,
			@RequestParam("file") List<MultipartFile> files)
			throws IOException, ImageProcessingException {

		Set<Long> travelIds = this.dsl.select(TRAVEL.ID).from(TRAVEL)
				.where(TRAVEL.APP_USER_ID.eq(userDetails.getAppUserId()))
				.fetchSet(TRAVEL.ID);

		Long checkLogId = this.dsl.select(LOG.ID).from(LOG)
				.where(LOG.ID.eq(logId).and(LOG.TRAVEL_ID.in(travelIds)))
				.fetchOne(LOG.ID);

		if (checkLogId != null) {
			for (MultipartFile file : files) {
				Metadata metadata = ImageMetadataReader
						.readMetadata(file.getInputStream());

				GpsDirectory gpsDirectory = metadata
						.getFirstDirectoryOfType(GpsDirectory.class);

				if (gpsDirectory.getGeoLocation() != null) {
					GeoLocation geoLocation = gpsDirectory.getGeoLocation();
					if (geoLocation != null && !geoLocation.isZero()) {
						// TODO: geoLocation.
					}
				}

				BufferedImage bi = ImageIO.read(file.getInputStream());
				Builder<BufferedImage> thumbnailBuilder = Thumbnails.of(bi);

				try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
					thumbnailBuilder.height(100).outputFormat("jpg").keepAspectRatio(true)
							.toOutputStream(baos);

					baos.toByteArray();

					// long id = this.dsl .insertInto(LOG,
					// LOG.CREATED, LOG.LAT, LOG.LNG,
					// LOG.LOCATION, LOG.REPORT, LOG.UPDATED, LOG.TRAVEL_ID)
					// .values(LocalDateTime.ofInstant(Instant.ofEpochSecond(
					// clientLog.getCreated()), ZoneOffset.UTC),
					// clientLog.getLat(),
					// clientLog.getLng(),
					// clientLog.getLocation(),
					// clientLog.getReport(), now,
					// clientLog.getTravelId()).returning(LOG.ID).fetchOne().getId();

				}

			}

			/*
			 * 
			 * 
			 * 
			 * // thumbnail on server
			 * 
			 * InputStream in = new ByteArrayInputStream(ba); BufferedImage bi =
			 * ImageIO.read(in); BufferedImage resizedBi;
			 * 
			 * Builder<BufferedImage> thumbnailBuilder = Thumbnails.of(bi); if (hoehe !=
			 * null) { thumbnailBuilder.height(hoehe); } else if (breite != null) {
			 * thumbnailBuilder.width(breite); }
			 * 
			 * resizedBi = thumbnailBuilder.keepAspectRatio(true).asBufferedImage();
			 * 
			 * try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			 * ImageIO.write(resizedBi, "jpg", baos); baos.flush(); ba =
			 * baos.toByteArray(); return ResponseEntity.ok().contentLength(ba.length)
			 * .contentType(MediaType.parseMediaType(mediaType))
			 * .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS)).body(ba); }
			 * 
			 */
		}
	}

	private String createDataUrl(String contentType, byte[] data) {
		String b64 = Base64.getEncoder().encodeToString(data);
		return "data:" + contentType + ";base64," + b64;
	}
}
