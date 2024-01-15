import java.io.File;
import java.io.FileFilter;

public class PdfFilter implements FileFilter {

  @Override
  public boolean accept(File pathname) {

    return pathname.getName().toLowerCase().endsWith(".pdf") || pathname.isDirectory();

  }

}
