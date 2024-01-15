package ch.ess.langtool;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PropertiesFileFilter extends FileFilter {

  @Override
  public String getDescription() {
    return ".properties (Property File)";
  }
  
  @Override
  public boolean accept(File f) {
    if (f.isDirectory()) {
      return true;
    }
    return f.getName().endsWith(".properties");
  }

}
