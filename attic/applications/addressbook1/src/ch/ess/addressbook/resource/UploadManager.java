package ch.ess.addressbook.resource;

import java.awt.*;
import java.io.*;

import ch.ess.util.*;

public class UploadManager {
  
  private final static UploadManager instance = new UploadManager();
  private final static int MAX_WIDTH = 400;
  private final static int MAX_HEIGHT = 290;
  
  private String uploadDir;
  
  private UploadManager() {
  }
  
  public static UploadManager getInstance() {
    return instance;
  }

  public static void setUploadDir(String uploadDir) {        
    instance.uploadDir = uploadDir;
  }
  
  public static void save(long id, InputStream is) throws IOException {    
    File outputFile = new File(instance.uploadDir, String.valueOf(id));
  
    if (outputFile.exists()) {
      outputFile.delete();
    }
  
    OutputStream bos = new FileOutputStream(outputFile);    
    IOUtil.copy(is, bos);    
    bos.close();  
  }

  public static void delete(long id) throws IOException {    
    File outputFile = new File(instance.uploadDir, String.valueOf(id));
  
    if (outputFile.exists()) {
      outputFile.delete();
    }
  
  }

  
  public static void read(Long id, OutputStream os) throws IOException {
    File file = new File(instance.uploadDir, id.toString());
  
    if (file.exists()) {  
      InputStream is = new FileInputStream(file);    
      IOUtil.copy(is, os);            
    }  
  }
  
  public static ImageInfo getImageInfo(long id) throws IOException {
    
    ImageInfo ii = new ImageInfo();
    File inputFile = new File(instance.uploadDir, String.valueOf(id));
    
    InputStream is = new FileInputStream(inputFile);
    try {    
      ii.setInput(is);    
  
      if (!ii.check()) {
        return null;
      }
      
      return ii;
    } finally {
      is.close();
    }    
  } 
  
    
  public static Dimension getImageDimension(ImageInfo ii) {

    int height = ii.getHeight();
    int width = ii.getWidth();
        
    if (width > MAX_WIDTH) {
      float d = (float)MAX_WIDTH / (float)width;
      width = MAX_WIDTH;
      height = (int)(height * d);
    }
    
    if (height > MAX_HEIGHT) {
      float d = (float)MAX_HEIGHT / (float)height;
      height = MAX_HEIGHT;
      width = (int)(width * d);      
    }
        
    return new Dimension(width, height);
    
  }
  

}
