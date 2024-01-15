package common.util.jar;

import java.util.zip.ZipEntry;

/**
 * A filter for ZipEntries that is based on whether
 * the entry has the specified suffix.
 *
 * @author cleve@acknowledge.co.uk
 * @see com.ack.jar.JarInfoEntryFilter
 */
public class SuffixZipEntryFilter implements ZipEntryFilter {
	private String fSuffix;

	/**
	 * Use this constructor to specify the suffix for
	 * ZipEntries that will be accepted
	 * @param the suffix in question
	 */
	public SuffixZipEntryFilter(String suffix) {
		fSuffix = suffix;
	}

	public boolean accept(ZipEntry ze) {
		if (ze == null || fSuffix == null)
			return false;

		return ze.getName().endsWith(fSuffix);
	}
}