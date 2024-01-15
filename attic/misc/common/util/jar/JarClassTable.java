package common.util.jar;

import java.util.*;
import java.util.zip.*;
import java.io.*;

/**
 * This class provides an abstraction for classes within
 * a jar/zip file.  It simply returns the bytes for named
 * classes that live within the supplied jar/zip file.
 *
 * Once retrieved from the jar/zip file, the bytes for
 * a class are cached within the JarClassTable.  This
 * class makes use of JarInfo.
 *
 * @author cleve@acknowledge.co.uk
 * @see com.ack.jar.JarInfo
 */
public class JarClassTable {
	private static final String CLASS_SUFFIX = ".class";

	private JarInfo fInfo;
	private Hashtable fClassTable;
	private Hashtable fZipTable;
	private String fZipFileName;

	/**
	 * Create a JarClassTable that is a cache for bytes
	 * for classes within the supplied zip file
	 *
	 * @exception reports problems reading the zip file
	 * @param the jar/zip file to search for classes
	 */
	public JarClassTable(String zipFile) throws JarInfoException {
		// creates a JarInfo object that contains zip entries for Java Classes only
		fInfo = new JarInfo(zipFile, new SuffixZipEntryFilter(CLASS_SUFFIX));

		fClassTable = new Hashtable(); // maps class names to class bytes
		fZipTable = new Hashtable(); // maps class names to ZipEntry
		fZipFileName = zipFile; // holds copy of zip file name
		init();
	}

	/**
	  * get the class bytes for the named class.  this will first
	  * look in its internal cache for the bytes for the named class
	  * and if it is not found in there load it from the underlying
	  * jar/zip file.
	  *
	  * @param the class name
	  * @return bytes for the named class.  if it can't be found
	  * this method returns null
	  */
	public byte[] getClassBytes(String className) throws JarInfoException {
		if (className == null)
			throw new JarInfoException("supplied className to getClassBytes() was null");

		// first check to see if bytes for this class are cached
		byte[] b = (byte[]) fClassTable.get(className);

		// if not
		if (b == null) {
			// get classes bytes from archive and cache them
			if ((b = loadBytes(className)) != null)
				fClassTable.put(className, b);
			else
				throw new JarInfoException("Unable to load class bytes for -> " +
                           				className);
		}
		return b;
	}

	/**
	  * Called from the constructor to create a cache of
	  * named classes against the actual ZipEntry in the
	  * jar/zip file.
	  */
	private void init() {
		// cycle through all zip entries within fInfo
		// which are all zip entries for java classes
		for (Enumeration e = fInfo.zipEntries(); e.hasMoreElements();) {
			ZipEntry ze = (ZipEntry) e.nextElement();
			String className = ze.getName();
			int index = className.lastIndexOf('.');
			className = className.substring(0, index).replace('/','.');
			fZipTable.put(className, ze);
		}
	}

	/**
	  * Extract the bytes for a class from the jar/zip file
	  * based on the supplied class name
	  *
	  * @exception reports any problem trying to get the
	  * the class bytes from the ZipEntry
	  * @param the full pathname of the class
	  * @return the bytes for the named class
	  */
	private byte[] loadBytes(String className) throws JarInfoException {
		// get the zip entry for the named class from JarInfo
		ZipEntry zentry = (ZipEntry) fZipTable.get(className);
		if (zentry == null)
			return null;
		byte[] b = null;

		ZipInputStream zis = null;
		try {
			// if there is such a ZipEntry, open up the jar/zip file
			FileInputStream fis = new FileInputStream(fZipFileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			zis = new ZipInputStream(bis);

			ZipEntry ze;
			int size;
			String zen = zentry.getName();

			// cycle through the entries in the jar/zip file
			while ((ze = zis.getNextEntry()) != null) {
				if (!zen.equals(ze.getName()))
					continue;

				// until we get the named ZipEntry
				size = (int) zentry.getSize();
				b = new byte[size];
				int index = 0;
				int count;

				// read in the bytes from that ZipEntry
				while ((count = zis.read(b, index, size)) > 0) {
					size -= count;
					index += count;
				}
				break;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new JarInfoException(ioe.getMessage());
		} catch (ClassFormatError cfe) {
			cfe.printStackTrace();
			throw new JarInfoException(cfe.getMessage());
		} finally { 
			if (zis != null)
      		try {
          		zis.close();
      		} catch (IOException ioe) {}
		} 
		return b;
	}
}