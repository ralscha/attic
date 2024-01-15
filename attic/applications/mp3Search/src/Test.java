import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.commons.lang.mutable.MutableLong;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.tritonus.share.sampled.file.TAudioFileFormat;


public class Test {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    try {

//      File file = new File("C:/movies/mp3/2Raumwohnung - 36 Grad/01 - Besser gehts nicht.mp3");
//      AudioFileFormat af = AudioSystem.getAudioFileFormat(file);
//      Map properties = ((TAudioFileFormat)af).properties();
//      Long duration = (Long)properties.get("duration")/1000/1000;
//      System.out.println(duration);
//      
//      int min = (int)(duration / 60.0);
//      int sec = (int)(duration % 60.0);
//      System.out.println(min);
//      System.out.println(sec);
//      
////      System.out.println(af);
////      
////      System.out.println(af.getFormat().getFrameRate());
////      System.out.println(af.getFrameLength());
////
////      System.out.println(af.getFormat().getFrameSize());
////      System.out.println(af.getFormat().getSampleRate());
////      System.out.println(af.getFormat().getSampleSizeInBits());
//      
//      
//      System.out.println(af.getFrameLength() / af.getFormat().getFrameRate());
//      
//      double duration = af.getFrameLength() / af.getFormat().getFrameRate();
//      int min = (int)(duration / 60.0);
//      int sec = (int)(duration % 60.0);
//      
//      System.out.println(min + ":" + sec);
//      
//      
////      System.out.println((af.getByteLength()/af.getFrameLength())/af.getFormat().getFrameRate());
//      
////      # mp3.bitrate.nominal.bps [Integer], nominal bitrate in bps.
////      # mp3.length.bytes [Integer], length in bytes.
////      # mp3.length.frames [Integer], length in frames.
////      # mp3.framesize.bytes [Integer], framesize of the first frame. framesize is not constant for VBR streams.
////      # mp3.framerate.fps [Float], framerate in frames per seconds.
////      # mp3.header.pos [Integer], position of first audio header (or ID3v2 size). 
//      
//      if (af instanceof TAudioFileFormat)
//      {
//          Map properties = ((TAudioFileFormat)af).properties();
//          
//          System.out.println("Bitrate       " + properties.get("mp3.bitrate.nominal.bps"));
//          System.out.println("Length Bytes  " + properties.get("mp3.length.bytes"));
//          System.out.println("Length Frames " + properties.get("mp3.length.frames"));
//          System.out.println("Framesize     " + properties.get("mp3.framesize.bytes"));
//          System.out.println("Framerate     " + properties.get("mp3.framerate.fps"));
//          System.out.println("Header Pos    " + properties.get("mp3.header.pos"));
//          
//          
//          System.out.println((Long)properties.get("duration")/1000);
//
////          key = "mp3.id3tag.v2";
////          InputStream tag= (InputStream) properties.get(key);
//      }
//      
////      af.getFormat().get
////      TAudioFileFormat taf = (TAudioFileFormat)af;
////      taf.
////      
////      Map properties = ((TAudioFileFormat)baseFileFormat).properties();
//      
//      
//          
//      MP3File mp3file = new MP3File("C:/movies/mp3/2Raumwohnung - 36 Grad/01 - Besser gehts nicht.mp3");
//      System.out.println("Variable BitRate : " + mp3file.isVariableBitRate());
//      System.out.println("Bitrate          : " + mp3file.getBitRate());
//      if (mp3file.seekMP3Frame()) {
//        
//        System.out.println("Bitrate          : " + mp3file.getBitRate());
//      }
      
      MutableLong totalDuration = new MutableLong(0);
      MutableInt noOfSongs = new MutableInt(0);
      
      File indexDir = new File("D:\\workspace\\MP3Search\\ix");
      IndexWriter writer = new IndexWriter(indexDir, new StandardAnalyzer(), true);
      writer.setMergeFactor(20);

      long start = System.currentTimeMillis();
      File baseDir = new File("C:\\movies\\mp3");

      indexDir2(writer, baseDir, totalDuration, noOfSongs);

      writer.optimize();
      writer.close();

      long stop = System.currentTimeMillis();
      System.out.println(((stop - start) / 1000) + " s");

      File infoFile = new File(indexDir, "info.properties");
      FileWriter fw = new FileWriter(infoFile);
      Properties properties = new Properties();
      properties.put("totalDuration", totalDuration.toString());
      properties.put("noOfSongs", noOfSongs.toString());
      properties.store(fw, "dbinfo");
      fw.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    } 

  }
  
  @SuppressWarnings("unchecked")
  public static void indexDir2(IndexWriter writer, File dir, MutableLong totalDuration, MutableInt noOfSongs) throws IOException {
    System.out.println("INDEXING: " + dir.getPath());
    
    File[] files = dir.listFiles(new FileFilter() {

      @Override
      public boolean accept(File file) {
        return file.isDirectory() || file.getName().endsWith(".mp3");
      }
    });
    
    for (File file : files) {      
      if (file.isDirectory()) {
        indexDir2(writer,file, totalDuration, noOfSongs);
      } else {        
        Document doc = new Document();
        
        try {
          AudioFileFormat audioFileFormat = AudioSystem.getAudioFileFormat(file);
          if (audioFileFormat instanceof TAudioFileFormat) {
            Map properties = ((TAudioFileFormat)audioFileFormat).properties();
            
            addField(doc, "album", (String)properties.get("album"));          
            addField(doc, "artist", (String)properties.get("author"));        
            addField(doc, "title", (String)properties.get("title"));
            addField(doc, "year", (String)properties.get("date"));
            
            Long duration = (Long)properties.get("duration");
            if (duration != null) {
              long dur = (duration/1000)/1000;
              totalDuration.add(dur);
              doc.add(new Field("duration", String.valueOf(dur), Field.Store.YES, Field.Index.NO));
            }
//            *  duration [Long], duration in microseconds.
//            * title [String], Title of the stream.
//            * author [String], Name of the artist of the stream.
//            * album [String], Name of the album of the stream.
//            * date [String], The date (year) of the recording or release of the stream.
//            * copyright [String], Copyright message of the stream.
//            * comment [String], Comment of the stream. 
            
          }
            
        
          
          
        } catch (UnsupportedAudioFileException uoe) {
          System.out.println(uoe.toString());
        } 

        
        doc.add(new Field("fileName", file.getName(), Field.Store.YES, Field.Index.TOKENIZED));
        doc.add(new Field("directory", file.getParent(), Field.Store.YES, Field.Index.TOKENIZED));
        doc.add(new Field("size", String.valueOf(file.length()), Field.Store.YES, Field.Index.NO));
        writer.addDocument(doc);
        noOfSongs.increment();

      }
    }
    
  }

  
  
//  public static void indexDir(IndexWriter writer, File dir) throws IOException, TagException {
//    System.out.println("INDEXING: " + dir.getPath());
//    
//    File[] files = dir.listFiles(new FileFilter() {
//
//      @Override
//      public boolean accept(File file) {
//        return file.isDirectory() || file.getName().endsWith(".mp3");
//      }
//    });
//    
//    for (File file : files) {      
//      if (file.isDirectory()) {
//         indexDir(writer,file);
//      } else {        
//        Document doc = new Document();
//        
//        try {
//          MP3File mp3file = new MP3File(file);
//          
//          if (mp3file.getID3v1Tag() != null) {
//            
//            
//            ID3v1 tags = mp3file.getID3v1Tag();
//            addField(doc, "album", tags.getAlbum());          
//            addField(doc, "albumTitle", tags.getAlbumTitle());
//            addField(doc, "artist", tags.getArtist());        
//            addField(doc, "songTitle", tags.getSongTitle());
//            addField(doc, "title", tags.getTitle());
//            addField(doc, "year", tags.getYear());
//            addField(doc, "genre", String.valueOf(tags.getGenre()));
//          } else if (mp3file.getID3v2Tag() != null) {
//            
//            AbstractID3v2 tags = mp3file.getID3v2Tag();
//            addField(doc, "album", tags.getAlbumTitle());          
//            addField(doc, "albumTitle", tags.getAlbumTitle());
//            addField(doc, "artist", tags.getLeadArtist());        
//            addField(doc, "songTitle", tags.getSongTitle());
//            addField(doc, "title", tags.getSongTitle());
//            addField(doc, "year", tags.getYearReleased());
//            addField(doc, "genreV2", tags.getSongGenre());
//          }
//          
//          
//        } catch (UnsupportedOperationException uoe) {
//          System.out.println(uoe.toString());
//        } catch (TagException te) {
//          System.out.println(te.toString());
//        } catch (IOException ioe) {
//          System.out.println(ioe.toString());
//        } catch (NumberFormatException nfe) {
//          System.out.print(nfe.toString());          
//        }
//
//        
//        doc.add(new Field("fileName", file.getName(), Field.Store.YES, Field.Index.TOKENIZED));
//        doc.add(new Field("directory", file.getParent(), Field.Store.YES, Field.Index.TOKENIZED));
//        doc.add(new Field("size", String.valueOf(file.length()), Field.Store.YES, Field.Index.NO));
//        writer.addDocument(doc);
//
//      }
//    }
//    
//  }

  private static void addField(Document doc, String field, String value) {
    if (value != null && value.trim().length() > 0) {
      doc.add(new Field(field, value, Field.Store.YES, Field.Index.TOKENIZED));
    }
  }
  
 

}
