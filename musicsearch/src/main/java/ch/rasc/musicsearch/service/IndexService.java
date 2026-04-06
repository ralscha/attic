package ch.rasc.musicsearch.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.annotation.PreDestroy;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.rasc.musicsearch.AppConfig;

@Service
public class IndexService {

	private final Directory indexDirectory;

	private final IndexReader indexReader;

	private final IndexSearcher indexSearcher;

	private final AppConfig appConfig;

	@Autowired
	public IndexService(AppConfig appConfig) throws IOException {
		this.appConfig = appConfig;

		Path ixDir = Paths.get(appConfig.getIndexDir());

		if (!Files.exists(ixDir) || !Files.list(ixDir).findFirst().isPresent()) {
			indexMusic(ixDir);
		}

		this.indexDirectory = FSDirectory.open(ixDir);
		this.indexReader = DirectoryReader.open(this.indexDirectory);
		this.indexSearcher = new IndexSearcher(this.indexReader);
	}

	@PreDestroy
	public void destroy() {
		if (this.indexReader != null) {
			try {
				this.indexReader.close();
			}
			catch (IOException e) {
				// ignore exception
			}
		}
		if (this.indexDirectory != null) {
			try {
				this.indexDirectory.close();
			}
			catch (IOException e) {
				// ignore exception
			}
		}
	}

	public IndexSearcher getIndexSearcher() {
		return this.indexSearcher;
	}

	private void indexMusic(Path ixDir) throws IOException {

		Path musicDir = Paths.get(this.appConfig.getMusicDir());

		try (Directory dir = FSDirectory.open(ixDir);
				Analyzer analyzer = new StandardAnalyzer()) {
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

			IndexFileWalker walker = null;

			try (IndexWriter writer = new IndexWriter(dir, iwc)) {
				walker = new IndexFileWalker(writer, musicDir);
				Files.walkFileTree(musicDir, walker);

				writer.forceMerge(1);
			}

			Path infoFile = ixDir.resolve("info.properties");
			try (BufferedWriter bw = Files.newBufferedWriter(infoFile,
					StandardCharsets.UTF_8)) {
				Properties properties = new Properties();
				properties.put(SearchService.TOTAL_DURATION,
						String.valueOf(walker.getTotalDuration()));
				properties.put(SearchService.NO_OF_SONGS,
						String.valueOf(walker.getNoOfSongs()));
				properties.store(bw, "dbinfo");
			}

			Path artistsFile = ixDir.resolve("artists.txt");
			try (BufferedWriter bw = Files.newBufferedWriter(artistsFile,
					StandardCharsets.UTF_8)) {
				for (String artist : walker.getArtists()) {
					bw.write(artist);
					bw.write("\n");
				}
			}
		}
	}

}
