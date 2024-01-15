package common.util.jar;

// zip imports
import java.util.zip.ZipException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

// util imports
import java.util.Enumeration;
import java.util.Hashtable;

// I/O imports
import java.io.IOException;

/**
 * Reads the header part of a zip/jar file and creates
 * an array of ZipEntry objects.  JarInfo also allows to
 * apply a filter to get the zip entries of their choice
 *
 * @author  Cleveland Gibbon, Acknowledge Technologies
 * @version 1.0
 * @see com.ack.jar.JarInfoEntryFilter
 */

public class JarInfo {
	private Hashtable fEntries;

	/**
	 * Use the JarInfoEntryFilter to selectively determine which
	 * ZipEntries a JarInfo object should be comprised of.
	 * @param the jar/zip file to interrogate
	 * @exception is thrown if any errors occur trying read the
	 *            the jar/zip file
	 * @see com.ack.jar.JarInfoEntryFilter#accept
	 */
	public JarInfo(String fileName, ZipEntryFilter filter) throws JarInfoException {
		if (fileName == null)
			throw new JarInfoException("supplied filename to JarInfo was null");

		if (filter == null)
			throw new JarInfoException(
  			"supplied filter to JarInfo was null for archive ->" + fileName);

		fEntries = new Hashtable();
		ZipFile zipFile = null;

		try {
			// create a ZipFile for the supplied 'fileName'
			zipFile = new ZipFile(fileName);

			// cycle through the header information with the zipFile
			Enumeration e = zipFile.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();

				// if we have a filter for JarInfo, apply it
				if (filter.accept(ze) == true)
					fEntries.put(ze.getName(), ze);
			}
		} catch (ZipException zipe) {
			throw new JarInfoException("Zip format problems reading " + fileName + "\n" +
                           			zipe.getMessage());
		}
		catch (IOException ioe) {
			throw new JarInfoException(ioe.getMessage());
		}
		catch (SecurityException se) {
			throw new JarInfoException("Security problems reading " + fileName + "\n" +
                           			se.getMessage());
		}
		finally { if (zipFile != null)
      			try {
          				zipFile.close();
      			} catch (IOException ioe) {}
        		} }

	/**
	  * accessor method to get a handle on the ZipEntries for JarInfo
	  * @return an Enumeration of the ZipEntries
	  */
	public Enumeration zipEntries() {
		return fEntries.elements();
	}

	/**
	  * accessor method to get a handle on the zip entries name for JarInfo
	  * @return an Enumeration of the zip entries names
	  */
	public Enumeration zipName() {
		return fEntries.keys();
	}

	/**
	  * accessor method to get a ZipEntry by name
	  * @param the name for the entry
	  * @return the ZipEntry if found, otherwise return null
	  */
	public ZipEntry getZipEntry(String zipEntryName) {
		ZipEntry entry = null;
		if (zipEntryName != null)
			entry = (ZipEntry) fEntries.get(zipEntryName);
		return entry;
	}

	/**
	  * display the entries within JarInfo
	  * @return entries collapsed into a single string
	  */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		Enumeration e = zipEntries();
		while (e.hasMoreElements()) {
			ZipEntry ze = (ZipEntry) e.nextElement();
			buf.append("\n**************************\n");
			buf.append("Zip Entry: " + ze.getName() + "\n");
			buf.append("Zip Date:  " + new java.util.Date(ze.getTime()) + "\n");
			buf.append("Zip Size:  " + ze.getSize() + "\n");
		}
		return buf.toString();
	}
}