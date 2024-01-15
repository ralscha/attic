package ch.ess.mp3search.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ch.ess.mp3search.model.Info;
import ch.ess.mp3search.model.Songs;

@Service
public class SearchService {

  private static final Log logger = LogFactory.getLog(SearchService.class);

  @Autowired
  @Qualifier("indexDir")
  private String indexDir;

  public Info getInfo() {

    Info info = new Info();
    try {
      File infoFile = new File(indexDir, "info.properties");
      FileReader fr = new FileReader(infoFile);
      Properties properties = new Properties();
      properties.load(fr);
      fr.close();

      String totalDurationString = (String)properties.get("totalDuration");
      String noOfSongsString = (String)properties.get("noOfSongs");

      if (StringUtils.isNotBlank(totalDurationString)) {
        info.setTotalDuration(new Integer(totalDurationString));
      }
      if (StringUtils.isNotBlank(noOfSongsString)) {
        info.setNoOfSongs(new Integer(noOfSongsString));
      }

    } catch (FileNotFoundException e) {
      logger.error("getInfo", e);
    } catch (IOException e) {
      logger.error("getInfo", e);
    }

    return info;
  }

  public List<Songs> search(String queryString) throws CorruptIndexException, IOException, ParseException {

    logger.info("SEARCH FOR: " + queryString);

    List<Songs> resultList = new ArrayList<Songs>();
    Searcher searcher = new IndexSearcher(indexDir);
    try {
      String[] fields = {"title", "author", "album", "date", "fileName", "directory"};
      MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());

      parser.setDefaultOperator(QueryParser.AND_OPERATOR);
      parser.setAllowLeadingWildcard(true);

      Query query;
      try {
        query = parser.parse(queryString);
      } catch (ParseException e) {
        query = parser.parse(QueryParser.escape(queryString));
      }

      Hits hits = searcher.search(query);
      logger.info("FOUND:      " + hits.length());

      for (int i = 0; i < hits.length(); i++) {
        Songs song = new Songs();
        song.setFileName(hits.doc(i).get("fileName"));

        File dir = new File(hits.doc(i).get("directory"));
        File f = new File(dir, song.getFileName());
        song.setDirectory(f.getPath());

        song.setArtist(hits.doc(i).get("author"));
        song.setYear(hits.doc(i).get("date"));
        song.setAlbum(hits.doc(i).get("album"));
        song.setTitle(hits.doc(i).get("title"));

        if (StringUtils.isBlank(song.getArtist()) && StringUtils.isBlank(song.getAlbum())
            && StringUtils.isBlank(song.getTitle())) {
          song.setTitle(song.getFileName());
        }

        String durationString = hits.doc(i).get("duration");
        if (StringUtils.isNotBlank(durationString)) {
          song.setDuration(new Integer(durationString));
        } else {
          song.setDuration(null);
        }

        resultList.add(song);
      }
    } finally {
      if (searcher != null) {
        searcher.close();
      }
    }

    return resultList;

  }
}
