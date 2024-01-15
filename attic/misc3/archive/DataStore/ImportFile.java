import com.borland.datastore.*;
public class ImportFile {
	private static final String DATA = "/data";
	private static final String LAST_MOD = "/modified";
	
	public static void read(String storeFileName, String fileToImport) {
		read(storeFileName, fileToImport, fileToImport);
	}
	
	public static void read(String storeFileName, String fileToImport, String streamName) {
		DataStoreConnection store = new DataStoreConnection();
		try {
			store.setFileName(storeFileName);
			store.open();
			FileStream fs = store.createFileStream(streamName + DATA);
			byte[] buffer = new byte[4 * store.getDataStore().getBlockSize() * 1024];
			java.io.File file = new java.io.File(fileToImport);
			java.io.FileInputStream fis = new java.io.FileInputStream(file);
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				fs.write(buffer, 0, bytesRead);
			}
			fs.close();
			fis.close();
			store.writeObject(streamName + LAST_MOD, new Long(file.lastModified()));
		} catch (com.borland.dx.dataset.DataSetException dse) {
			dse.printStackTrace();
		}
		catch (java.io.FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}
		finally { try {
          			store.close();
      		} catch (com.borland.dx.dataset.DataSetException dse) {
          			dse.printStackTrace();
          		}
        		} 
	}
	
	public static void main(String[] args) {
		if (args.length == 2) {
			read(args[0], args[1]);
		} else if (args.length >= 3) {
			read(args[0], args[1], args[2]);
		}
	}
}