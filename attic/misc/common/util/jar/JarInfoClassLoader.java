package common.util.jar;

import java.security.SecureClassLoader;

/**
 * This extension to a the SecureClassLoader is responsible for
 * loading classes from the supplied zip/jar file.  These classes
 * will be loaded if they cannot be found using the parent
 * class loader.
 *
 * @author cleve@acknowledge.co.uk
 * @see java.security.SecureClassLoader
 */
public class JarInfoClassLoader extends SecureClassLoader {
	private JarClassTable fClassTable;

	/**
	 * Create a class loader that will read the files
	 * from the supplied zip/jar file
	 * @param the zip/jar file to check for loaded classes
	 * @exception is thrown if any errors occur during the
	 *  reading of the jar/zip file
	 */
	public JarInfoClassLoader(String zipFile) throws JarInfoException {
		this(zipFile, ClassLoader.getSystemClassLoader());
	}

	/**
	  * Create a class loader that will read the files
	  * from the supplied zip/jar file
	  * @param the zip/jar file to check for loaded classes
	  * @param the parent class loader for this class
	  * @exception is thrown if any errors occur during the
	  *  reading of the jar/zip file
	  */
	public JarInfoClassLoader(String zipFile, ClassLoader cl) throws JarInfoException {
		super(cl);
		fClassTable = new JarClassTable(zipFile);
	}

	/**
	  * Under Java 2 this method is called to find a class.
	  * It is based on new delegation model for loading classes,
	  * and will be called by the loadClass method after checking
	  * the parent class loader for the requested class.  The parent
	  * class loader is supplied by the constructors of this class.
	  */
	public Class findClass(String className) throws ClassNotFoundException {
		System.out.println("locating class -> " + className);

		// get the bytes for a classes from the class table
		byte[] b = null;
		try {
			fClassTable.getClassBytes(className);
		} catch (JarInfoException jie) {}

		// and if there are some, use the inherited method defineClass
		// to convert a these bytes into an instance of a Class
		if (b != null)
			return defineClass(className, b, 0, b.length);

		// otherwise if there is not such class
		return null;
	}

}