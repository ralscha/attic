package ch.rasc.musicsearch.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParserBase;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.musicsearch.AppConfig;
import ch.rasc.musicsearch.model.Artist;
import ch.rasc.musicsearch.model.Info;
import ch.rasc.musicsearch.model.Song;

@Service
public class SearchService {

	public static final String NO_OF_SONGS = "noOfSongs";

	public static final String TOTAL_DURATION = "totalDuration";

	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

	private final static String[] QUERY_FIELDS = { "fileName", "directory", "title",
			"artist", "album", "comment", "year", "composer" };

	private final AppConfig appConfig;

	private final IndexService indexService;

	@Autowired
	public SearchService(AppConfig appConfig, IndexService indexService) {
		this.appConfig = appConfig;
		this.indexService = indexService;
	}

	@ExtDirectMethod
	public Info getInfo() {

		Integer noOfSongs = null;
		Integer totalDuration = null;

		try {
			Path infoFile = Paths.get(this.appConfig.getIndexDir(), "info.properties");
			Properties properties;
			try (BufferedReader br = Files.newBufferedReader(infoFile,
					StandardCharsets.UTF_8)) {
				properties = new Properties();
				properties.load(br);
			}

			String totalDurationString = (String) properties
					.get(SearchService.TOTAL_DURATION);
			String noOfSongsString = (String) properties.get(SearchService.NO_OF_SONGS);

			if (StringUtils.hasText(totalDurationString)) {
				totalDuration = Integer.valueOf(totalDurationString);
			}
			if (StringUtils.hasText(noOfSongsString)) {
				noOfSongs = Integer.valueOf(noOfSongsString);
			}

		}
		catch (IOException e) {
			logger.info("getInfo", e);
		}

		return new Info(noOfSongs, totalDuration);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	public List<Artist> readArtists() throws IOException {
		Path artistsFile = Paths.get(this.appConfig.getIndexDir(), "artists.txt");
		return Files.lines(artistsFile, StandardCharsets.UTF_8).map(Artist::new)
				.collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	public List<Song> search(ExtDirectStoreReadRequest storeRequest) {

		String filterValue = null;
		if (!storeRequest.getFilters().isEmpty()) {
			StringFilter filter = (StringFilter) storeRequest.getFilters().iterator()
					.next();
			filterValue = filter.getValue();
		}

		List<Song> resultList = new ArrayList<>();

		try (Analyzer analyzer = new StandardAnalyzer()) {

			MultiFieldQueryParser parser = new MultiFieldQueryParser(QUERY_FIELDS,
					analyzer);
			parser.setDefaultOperator(QueryParserBase.AND_OPERATOR);
			parser.setAllowLeadingWildcard(true);

			Query query;
			try {
				query = parser.parse(filterValue);
			}
			catch (ParseException e) {
				try {
					query = parser.parse(QueryParserBase.escape(filterValue));
				}
				catch (ParseException e1) {
					logger.error("lucene query parse", e1);
					return Collections.emptyList();
				}
			}

			TopDocs results = this.indexService.getIndexSearcher().search(query, 10000);
			for (ScoreDoc scoreDoc : results.scoreDocs) {
				Document doc = this.indexService.getIndexSearcher().doc(scoreDoc.doc);

				Song song = new Song();
				song.setId(scoreDoc.doc);

				song.setTitle(doc.get("title"));
				song.setArtist(doc.get("artist"));
				song.setAlbum(doc.get("album"));
				song.setYear(doc.get("year"));

				song.setEncoding(doc.get("encoding"));

				if (!StringUtils.hasText(song.getArtist())
						&& !StringUtils.hasText(song.getAlbum())
						&& !StringUtils.hasText(song.getTitle())) {
					song.setTitle(doc.get("fileName"));
				}

				IndexableField field = doc.getField("duration");
				if (field != null) {
					song.setDurationInSeconds(field.numericValue().intValue());
				}
				else {
					song.setDurationInSeconds(null);
				}

				field = doc.getField("bitrate");
				if (field != null) {
					song.setBitrate(field.numericValue().longValue());
				}
				else {
					song.setBitrate(null);
				}

				resultList.add(song);

			}

		}
		catch (IOException e) {
			logger.error("search service", e);
		}

		return resultList;

	}
}
