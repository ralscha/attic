package common.io;

import java.io.*;
import java.util.zip.*;
import javax.crypto.*;

public class ZipOutputFile {
	
	private static final int BUF_LEN = 32 * 1024;
	private byte[] buffer = new byte[BUF_LEN];
	private ZipOutputStream zip = null;
	private String zipFileName = null;
	private FileFilter filter = null;
		
	
	public ZipOutputFile(String zipFileName) throws IOException {
		this(new File(zipFileName), null, null);
	}
	
	public ZipOutputFile(String zipFileName, Cipher cipher) throws IOException {
		this(new File(zipFileName), null, cipher);
	}
	
	public ZipOutputFile(String zipFileName, FileFilter filter) throws IOException {
		this(new File(zipFileName), filter, null);
	}

	public ZipOutputFile(File zipFile) throws IOException {
		this(zipFile, null, null);
	}

	public ZipOutputFile(File zipFile, FileFilter filter) throws IOException {
		this(zipFile, filter, null);
	}
	
	public ZipOutputFile(File zipFile, FileFilter filter, Cipher cipher) throws IOException {
		this.filter = filter;
		zipFileName = zipFile.getPath();
		if (cipher != null)
			zip = new ZipOutputStream(new CipherOutputStream(
					new BufferedOutputStream(new FileOutputStream(zipFile)), cipher));
		else
			zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));

		zip.setMethod(ZipOutputStream.DEFLATED);
		zip.setLevel(Deflater.BEST_COMPRESSION);				
	}
	
	public void close() throws IOException {
		if (zip != null) 
			zip.close();
	}

	public void add(String fileName) throws IOException {
		File files[] = new File[1];
		files[0] = new File(fileName);
		add(files);
	}
	
	public void add(File file) throws IOException {
		File files[] = new File[1];
		files[0] = file;
		add(files);
	}
	
	public void add(File[] files) throws IOException {
				
		int len;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				File[] dirFiles = files[i].listFiles(filter);
				add(dirFiles);
			} else {
				if (!zipFileName.equals(files[i].getPath())) {
					ZipEntry entry = new ZipEntry(files[i].getPath());
					entry.setTime(files[i].lastModified());
		
					FileInputStream in = new FileInputStream(files[i]);
					zip.putNextEntry(entry);
					
					while((len = in.read(buffer)) != -1) {			
						zip.write(buffer, 0, len);
					}
					zip.closeEntry();	
					in.close();				
				}
			}				
		}
	}
	
	//Ein allfälliger FileFilter wird nicht berücksichtigt	
	public void addFile(File file) throws IOException {
				
		int len;
		if (!zipFileName.equals(file.getPath())) {
			ZipEntry entry = new ZipEntry(file.getPath());
			entry.setTime(file.lastModified());
			
			FileInputStream in = new FileInputStream(file);
			zip.putNextEntry(entry);
			
			while((len = in.read(buffer)) != -1) {			
				zip.write(buffer, 0, len);
			}
			zip.closeEntry();	
			in.close();				
		}
	}

}

			