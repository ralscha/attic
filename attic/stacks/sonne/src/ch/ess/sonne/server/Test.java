package ch.ess.sonne.server;

import java.io.File;
import java.util.Iterator;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

public class Test {

  public static void main(String[] args) {
    try {
      File f = new File("D:\\eclipse\\workspace\\Sonne\\www\\ch.ess.sonne.Sonne\\images\\20060626b.jpg");
      Metadata metadata = JpegMetadataReader.readMetadata(f);

      Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
      String cameraMake = exifDirectory.getString(ExifDirectory.TAG_DATETIME);
      System.out.println(cameraMake);

      Iterator directories = metadata.getDirectoryIterator();
      while (directories.hasNext()) {
        Directory directory = (Directory)directories.next();
        // iterate through tags and print to System.out
        Iterator tags = directory.getTagIterator();
        while (tags.hasNext()) {
          Tag tag = (Tag)tags.next();
          // use Tag.toString()
          System.out.println(tag);
        }
      }
    
    } catch (JpegProcessingException e) {
      e.printStackTrace();
    }

  }
}
