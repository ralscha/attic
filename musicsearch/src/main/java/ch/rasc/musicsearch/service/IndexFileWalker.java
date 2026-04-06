package ch.rasc.musicsearch.service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class IndexFileWalker extends SimpleFileVisitor<Path> {

	private static final Logger logger = LoggerFactory.getLogger(IndexFileWalker.class);

	private long totalDuration = 0;

	private int noOfSongs = 0;

	private final IndexWriter writer;

	private final Path baseDir;

	private final Set<String> artists;

	public IndexFileWalker(IndexWriter writer, Path baseDir) {
		this.writer = writer;
		this.baseDir = baseDir;
		this.artists = new TreeSet<>();
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {

		try {
			AudioFile audioFile = AudioFileIO.read(file.toFile());
			Tag tag = audioFile.getTag();
			if (tag == null) {
				return FileVisitResult.CONTINUE;
			}
			AudioHeader ah = audioFile.getAudioHeader();

			int trackLength = ah.getTrackLength();
			this.totalDuration = this.totalDuration + trackLength;
			this.noOfSongs++;

			Document doc = new Document();

			doc.add(new TextField("fileName", file.getFileName().toString(),
					Field.Store.YES));
			doc.add(new TextField("directory",
					this.baseDir.relativize(file.getParent()).toString(),
					Field.Store.YES));
			
			doc.add(new StoredField("size", Files.size(file)));
			doc.add(new StoredField("bitrate", ah.getBitRateAsNumber()));
			
			String encoding = null;
			String encodingType = ah.getEncodingType().toLowerCase();

			if ("mp3".equals(encodingType)) {
				encoding = "audio/mpeg";
			}
			else if ("aac".equals(encodingType)) {
				encoding = "audio/aac";
			}
			else {
				logger.info("Encoding type {} not found", encodingType);
				return FileVisitResult.CONTINUE;
			}

			doc.add(new StoredField("encoding", encoding));

			String value = tag.getFirst(FieldKey.TITLE);
			if (StringUtils.hasText(value)) {
				doc.add(new TextField("title", value, Field.Store.YES));
			}

			value = tag.getFirst(FieldKey.ARTIST);
			if (StringUtils.hasText(value)) {
				doc.add(new TextField("artist", value, Field.Store.YES));
				this.artists.add(value);
			}

			value = tag.getFirst(FieldKey.ALBUM);
			if (StringUtils.hasText(value)) {
				doc.add(new TextField("album", value, Field.Store.YES));
			}

			value = tag.getFirst(FieldKey.COMMENT);
			if (StringUtils.hasText(value)) {
				doc.add(new TextField("comment", value, Field.Store.NO));
			}

			value = tag.getFirst(FieldKey.YEAR);
			if (StringUtils.hasText(value)) {
				doc.add(new TextField("year", value, Field.Store.YES));
			}

			value = tag.getFirst(FieldKey.COMPOSER);
			if (StringUtils.hasText(value)) {
				doc.add(new TextField("composer", value, Field.Store.NO));
			}

			if (trackLength > 0) {
				doc.add(new StoredField("duration", trackLength));
			}

			this.writer.addDocument(doc);

		}
		catch (IOException | CannotReadException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			LogFactory.getLog(IndexFileWalker.class).info(e.getMessage());
		}

		return FileVisitResult.CONTINUE;

	}

	public long getTotalDuration() {
		return this.totalDuration;
	}

	public int getNoOfSongs() {
		return this.noOfSongs;
	}

	public Set<String> getArtists() {
		return this.artists;
	}

}
