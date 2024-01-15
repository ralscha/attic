package ch.ess.sonne.server;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.ess.sonne.client.common.DateItem;
import ch.ess.sonne.client.common.ImageInfo;
import ch.ess.sonne.client.common.PictureService;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PictureServiceImpl extends RemoteServiceServlet implements PictureService {

  private final static DateFormat PARSE_DATE_FORMAT = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
  private final static DateFormat KEY_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
  private final static DateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  public List getDateItemList() {
    
    Map picMap = new TreeMap(Collections.reverseOrder());
    
    try {
      File imageDirectory = new File(ApplicationConextListener.IMAGE_DIRECTORY);
      File[] files = imageDirectory.listFiles();
      if (files != null) {
        for (int i = 0; i < files.length; i++) {
          
          Metadata metadata = JpegMetadataReader.readMetadata(files[i]);
          Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
          String cameraMake = exifDirectory.getString(ExifDirectory.TAG_DATETIME);
          Date makeDate;
          try {
            makeDate = PARSE_DATE_FORMAT.parse(cameraMake);
          } catch (ParseException e) {
            makeDate = null;
          }
          
          
          if (makeDate != null) {
            String key = KEY_DATE_FORMAT.format(makeDate);
            List l = (List)picMap.get(key);
            if (l == null) {
              l = new ArrayList();
              picMap.put(key, l);
            }
            ImageInfo ii = new ImageInfo();
            ii.setFileName(files[i].getName());
            ii.setDateTime(OUTPUT_DATE_FORMAT.format(makeDate));
            
            l.add(ii);            
          }
          
        }
      }
    } catch (JpegProcessingException e) {
      e.printStackTrace();
    }
    
    
    List dateItemList = new ArrayList();
    for (Iterator it = picMap.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry)it.next();
      
      List l = (List)entry.getValue();
      String date = (String)entry.getKey();
      date = date.substring(6) + "." + date.substring(4,6) + "."+date.substring(0,4);
      DateItem di = new DateItem((ImageInfo[])l.toArray(new ImageInfo[l.size()]), date);
      dateItemList.add(di);      
    }
    
    return dateItemList;
  }



}
