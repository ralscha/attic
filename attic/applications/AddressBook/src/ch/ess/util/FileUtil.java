/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/FileUtil.java,v 1.2 2002/04/02 18:27:21 sr Exp $
 * $Revision: 1.2 $
 * $Date: 2002/04/02 18:27:21 $
 */

package ch.ess.util;

import java.io.*;


/**
 * Class FileUtil
 *
 * @version $Revision: 1.2 $ $Date: 2002/04/02 18:27:21 $
 */
public final class FileUtil {

  public static final int EQUAL = 0;
  public static final int NOT_EQUAL = 1;

  /**
   * This class shouldn't be instantiated.
   */
  private FileUtil() {
  }

  /**
   * Copy files and/or directories.
   *
   * @param src source file or directory
   * @param dest destination file or directory
   *        @exception IOException if operation fails
   */
  public static void copy(File src, File dest) throws IOException {

    FileInputStream source = null;
    FileOutputStream destination = null;
    byte[] buffer;
    int bytes_read;

    // Make sure the specified source exists and is readable.
    if (!src.exists()) {
      throw new IOException("source not found: " + src);
    }

    if (!src.canRead()) {
      throw new IOException("source is unreadable: " + src);
    }

    if (src.isFile()) {
      if (!dest.exists()) {
        File parentdir = parent(dest);

        if (!parentdir.exists()) {
          parentdir.mkdir();
        }
      } else if (dest.isDirectory()) {
        dest = new File(dest + File.separator + src);
      }
    } else if (src.isDirectory()) {
      if (dest.isFile()) {
        throw new IOException("cannot copy directory " + src + " to file " + dest);
      }

      if (!dest.exists()) {
        dest.mkdir();
      }
    }

    // The following line requires that the file already
    // exists!!  Thanks to Scott Downey (downey@telestream.com)
    // for pointing this out.  Someday, maybe I'll find out
    // why java.io.File.canWrite() behaves like this.  Is it
    // intentional for some odd reason?
    //if (!dest.canWrite())
    //      throw new IOException("destination is unwriteable: " + dest);
    // If we've gotten this far everything is OK and we can copy.
    if (src.isFile()) {
      try {
        source = new FileInputStream(src);
        destination = new FileOutputStream(dest);
        buffer = new byte[1024];

        while (true) {
          bytes_read = source.read(buffer);

          if (bytes_read == -1) {
            break;
          }

          destination.write(buffer, 0, bytes_read);
        }
      } finally {
        if (source != null) {
          try {
            source.close();
          } catch (IOException e) {}
        }

        if (destination != null) {
          try {
            destination.close();
          } catch (IOException e) {}
        }
      }
    } else if (src.isDirectory()) {
      String targetfile, target, targetdest;
      String[] files = src.list();

      for (int i = 0; i < files.length; i++) {
        targetfile = files[i];
        target = src + File.separator + targetfile;
        targetdest = dest + File.separator + targetfile;

        if ((new File(target)).isDirectory()) {
          copy(new File(target), new File(targetdest));
        } else {
          try {
            source = new FileInputStream(target);
            destination = new FileOutputStream(targetdest);
            buffer = new byte[1024];

            while (true) {
              bytes_read = source.read(buffer);

              if (bytes_read == -1) {
                break;
              }

              destination.write(buffer, 0, bytes_read);
            }
          } finally {
            if (source != null) {
              try {
                source.close();
              } catch (IOException e) {}
            }

            if (destination != null) {
              try {
                destination.close();
              } catch (IOException e) {}
            }
          }
        }
      }
    }
  }

  public static void copy(String src, String dest) throws IOException {

    File srcFile = new File(src);
    File destFile = new File(dest);

    copy(srcFile, destFile);
  }

  public static void rename(File oldFile, File newFile) {
    oldFile.renameTo(newFile);
  }

  public static void rename(String oldFileName, String newFileName) {

    File oldFile = new File(oldFileName);
    File newFile = new File(newFileName);

    oldFile.renameTo(newFile);
  }

  public static int compare(String file1, String file2) throws IOException, FileNotFoundException {

    File f1 = new File(file1);
    File f2 = new File(file2);

    return compare(f1, f2);
  }

  public static int compare(File f1, File f2) throws IOException, FileNotFoundException {

    byte[] buffer = new byte[32 * 1024];
    byte[] buffer2 = new byte[32 * 1024];
    int len;

    if (!f1.exists()) {
      throw new FileNotFoundException(f1.getPath());
    }

    if (!f2.exists()) {
      throw new FileNotFoundException(f2.getPath());
    }

    if (f1.length() == f2.length()) {
      FileInputStream fis1 = null, fis2 = null;

      try {
        fis1 = new FileInputStream(f1);
        fis2 = new FileInputStream(f2);

        while ((len = fis1.read(buffer)) != -1) {
          fis2.read(buffer2);

          if (compareBuffers(buffer, buffer2, len) == NOT_EQUAL) {
            return NOT_EQUAL;
          }
        }

        return EQUAL;
      } finally {
        fis1.close();
        fis2.close();
      }
    } else {
      return NOT_EQUAL;
    }
  }

  private static int compareBuffers(byte[] buffer1, byte[] buffer2, int len) {

    for (int i = 0; i < len; i++) {
      if (buffer1[i] != buffer2[i]) {
        return NOT_EQUAL;
      }
    }

    return EQUAL;
  }

  /**
   * File.getParent() can return null when the file is specified without
   * a directory or is in the root directory. This method handles those cases.
   *
   * @param f the target File to analyze
   * @return the parent directory as a File
   */
  private static File parent(File f) {

    String dirname = f.getParent();

    if (dirname == null) {
      if (f.isAbsolute()) {
        return new File(File.separator);
      } else {
        return new File(System.getProperty("user.dir"));
      }
    }

    return new File(dirname);
  }
}
