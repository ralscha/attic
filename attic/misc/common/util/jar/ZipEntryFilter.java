package common.util.jar;

import java.util.zip.ZipEntry;

/**
 * Filter interface that is used by JavaInfo constructor to determine
 * which ZipEntries it will extract from the  zip/jar file
 *
 * @author cleve@acknowledge.co.uk
 * @version 1.0
 * @see com.ack.jar.JarInfo
 */
public interface ZipEntryFilter {
	/**
	  * Implement accept method to determine whether the ZipEntry
	  * satisfies you criteria
	  * @return value indicating whether ZipEntry is accepted
	  * @param the ZipEntry to interrogate
	  */
	public boolean accept(ZipEntry ze);
}