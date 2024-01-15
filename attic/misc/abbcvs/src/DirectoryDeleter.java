

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author sr
 */
public class DirectoryDeleter {

  private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMM");

  public static void main(String[] args) {

    if (args.length == 2) {

      try {
        String dir = args[0];
        int months = Integer.parseInt(args[1]);

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, -months);

        String directoryString = DATE_FORMAT.format(cal.getTime());

        File dirF = new File(dir);
        File[] directories = dirF.listFiles();
        if (directories != null) {
          for (int i = 0; i < directories.length; i++) {
            String name = directories[i].getName();

            if (directoryString.compareTo(name) > 0) {

              deleteDirectory(directories[i]);
            }

          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    } else {
      System.out.println("java DirectoryDeleter <dir> <months>");
    }

  }

  private static void deleteDirectory(File directory) throws IOException {
    if (!directory.exists()) {
      return;
    }

    cleanDirectory(directory);
    if (!directory.delete()) {
      String message = "Unable to delete directory " + directory + ".";
      throw new IOException(message);
    }
  }

  private static void cleanDirectory(File directory) throws IOException {
    if (!directory.exists()) {
      String message = directory + " does not exist";
      throw new IllegalArgumentException(message);
    }

    if (!directory.isDirectory()) {
      String message = directory + " is not a directory";
      throw new IllegalArgumentException(message);
    }

    IOException exception = null;

    File[] files = directory.listFiles();
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      try {
        forceDelete(file);
      } catch (IOException ioe) {
        exception = ioe;
      }
    }

    if (null != exception) {
      throw exception;
    }
  }

  private static void forceDelete(File file) throws IOException {
    if (file.isDirectory()) {
      deleteDirectory(file);
    } else {
      if (!file.exists()) {
        throw new FileNotFoundException("File does not exist: " + file);
      }
      if (!file.delete()) {
        String message = "Unable to delete file: " + file;
        throw new IOException(message);
      }
    }
  }
}