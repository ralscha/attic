package ch.ess.calendar.tools;
import java.io.*;
import java.util.*;
import java.util.jar.*;

public class UpdateJar {

  private static final String CONF_FILENAME_JAR = "WEB-INF/web.xml";

  public void update(String jarName, String confNameLocal) throws IOException {  
    

    // Create file descriptors for the jar and a temp jar.

    File jarFile = new File(jarName);
    File tempJarFile = new File(jarName + ".tmp");

    // Open the jar file.

    JarFile jar = new JarFile(jarFile);
    System.out.println(jarName + " opened.");

    // Initialize a flag that will indicate that the jar was updated.

    boolean jarUpdated = false;

    try {
      // Create a temp jar file with no manifest. (The manifest will
      // be copied when the entries are copied.)

      //Manifest jarManifest = jar.getManifest();
      JarOutputStream tempJar = new JarOutputStream(new FileOutputStream(tempJarFile));
      tempJar.setLevel(0);
      // Allocate a buffer for reading entry data.

      byte[] buffer = new byte[1024];
      int bytesRead;

      try {
        // Open the given file.

        FileInputStream file = new FileInputStream(confNameLocal);

        try {
          // Create a jar entry and add it to the temp jar.

          JarEntry entry = new JarEntry(CONF_FILENAME_JAR);
          tempJar.putNextEntry(entry);

          // Read the file and write it to the jar.

          while ((bytesRead = file.read(buffer)) != -1) {
            tempJar.write(buffer, 0, bytesRead);
          }

          System.out.println(entry.getName() + " added.");
        } finally {
          file.close();
        }

        // Loop through the jar entries and add them to the temp jar,
        // skipping the entry that was added to the temp jar already.

        for (Enumeration entries = jar.entries(); entries.hasMoreElements();) {
          // Get the next entry.

          JarEntry entry = (JarEntry) entries.nextElement();

          // If the entry has not been added already, add it.

          if (!entry.getName().equals(CONF_FILENAME_JAR)) {
            // Get an input stream for the entry.

            InputStream entryStream = jar.getInputStream(entry);

            // Read the entry and write it to the temp jar.

            tempJar.putNextEntry(entry);

            while ((bytesRead = entryStream.read(buffer)) != -1) {
              tempJar.write(buffer, 0, bytesRead);
            }
          }
        }

        jarUpdated = true;
      } catch (Exception ex) {
        System.out.println(ex);

        // Add a stub entry here, so that the jar will close without an
        // exception.

        tempJar.putNextEntry(new JarEntry("stub"));
      } finally {
        tempJar.close();
      }
    } finally {
      jar.close();
      System.out.println(jarName + " closed.");

      // If the jar was not updated, delete the temp jar file.

      if (!jarUpdated) {
        tempJarFile.delete();
      }
    }

    // If the jar was updated, delete the original jar file and rename the
    // temp jar file to the original name.

    if (jarUpdated) {
      jarFile.delete();
      tempJarFile.renameTo(jarFile);
      System.out.println(jarName + " updated.");
    }
  }

}
