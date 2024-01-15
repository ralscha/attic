package ch.ess.mp3search.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.commons.lang.mutable.MutableLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.tritonus.share.sampled.file.TAudioFileFormat;

@Component
public class Indexer {

  private static final Log logger = LogFactory.getLog(Indexer.class);

  @Autowired
  @Qualifier("indexDir")
  private String indexDir;

  @Autowired
  @Qualifier("mp3Dir")
  private String mp3Dir;

  @PostConstruct
  public void checkIndex() {
    File ixDir = new File(indexDir);
    if (!ixDir.exists()) {
      indexMp3();
    }
  }

  public void indexMp3() {
    try {

      File ixDir = new File(indexDir);
      IndexWriter writer = new IndexWriter(ixDir, new StandardAnalyzer(), true);
      writer.setMergeFactor(20);

      long start = System.currentTimeMillis();
      File baseDir = new File(mp3Dir);

      
      MutableLong totalDuration = new MutableLong(0);
      MutableInt noOfSongs = new MutableInt(0);
      indexDirectory(writer, baseDir, totalDuration, noOfSongs);

      writer.optimize();
      writer.close();

      long stop = System.currentTimeMillis();
      logger.info("INDEXING TIME: " + ((stop - start) / 1000) + " s");

      File infoFile = new File(indexDir, "info.properties");
      FileWriter fw = new FileWriter(infoFile);
      Properties properties = new Properties();
      properties.put("totalDuration", totalDuration.toString());
      properties.put("noOfSongs", noOfSongs.toString());
      properties.store(fw, "dbinfo");
      fw.close();
      
    } catch (Exception e) {
      logger.error("indexMp3", e);
    }
  }

  @SuppressWarnings("unchecked")
  private void indexDirectory(IndexWriter writer, File dir, MutableLong totalDuration, MutableInt noOfSongs) throws IOException {
    logger.debug("INDEXING: " + dir.getPath());

    File[] files = dir.listFiles(new FileFilter() {

      @Override
      public boolean accept(File file) {
        return file.isDirectory() || file.getName().endsWith(".mp3");
      }
    });

    for (File file : files) {
      if (file.isDirectory()) {
        indexDirectory(writer, file, totalDuration, noOfSongs);
      } else {
        Document doc = new Document();

        try {
          AudioFileFormat audioFileFormat = AudioSystem.getAudioFileFormat(file);
          if (audioFileFormat instanceof TAudioFileFormat) {
            Map properties = ((TAudioFileFormat)audioFileFormat).properties();

            addField(doc, "title", (String)properties.get("title"));
            addField(doc, "author", (String)properties.get("author"));
            addField(doc, "album", (String)properties.get("album"));
            
            Long duration = (Long)properties.get("duration");
            if (duration != null) {
              long dur = (duration/1000)/1000;
              totalDuration.add(dur);
              doc.add(new Field("duration", String.valueOf(dur), Field.Store.YES, Field.Index.NO));
            }
            
            String date = (String)properties.get("date");
            if (StringUtils.isNotBlank(date) && !date.trim().equals("0")) {
              addField(doc, "date", date);
            }

//          * duration [Long], duration in microseconds.
//          * title [String], Title of the stream.
//          * author [String], Name of the artist of the stream.
//          * album [String], Name of the album of the stream.
//          * date [String], The date (year) of the recording or release of the stream.
//          * copyright [String], Copyright message of the stream.
//          * comment [String], Comment of the stream. 

          }

        } catch (UnsupportedAudioFileException uoe) {
          logger.info("indexDirectory: " + uoe.toString() + "-->" + file.getName());
        } catch (IOException io) {
          logger.info("indexDirectory: " + io.toString() + "-->" + file.getName());
        }

        doc.add(new Field("fileName", file.getName(), Field.Store.YES, Field.Index.TOKENIZED));
        doc.add(new Field("directory", file.getParent(), Field.Store.YES, Field.Index.TOKENIZED));
        doc.add(new Field("size", String.valueOf(file.length()), Field.Store.YES, Field.Index.NO));
        writer.addDocument(doc);
        noOfSongs.increment();

      }
    }

  }

  private void addField(Document doc, String field, String value) {
    if (StringUtils.isNotBlank(value)) {
      doc.add(new Field(field, value, Field.Store.YES, Field.Index.TOKENIZED));
    }
  }
}
