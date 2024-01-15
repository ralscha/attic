import com.borland.datastore.*;

public class PrintFile {
	private static final String DATA = "/data";
	private static final String LAST_MOD = "/modified";

	public static void printBackwards(String storeFileName, String streamName) {
		DataStoreConnection store = new DataStoreConnection();
		try {
			store.setFileName(storeFileName);
			store.setUserName("bob");
			store.open();
			FileStream fs = store.openFileStream(streamName + DATA);
			int streamPos = fs.available();
			while (--streamPos >= 0) {
				fs.seek(streamPos);
				System.out.print((char) fs.read());
			}
			fs.close();
			System.out.println("Last modified: " + new java.util.Date(
                     			((Long) store.readObject(streamName + LAST_MOD)).longValue()));
		} catch (com.borland.dx.dataset.DataSetException dse) {
			dse.printStackTrace();
		}
		catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}
		catch (java.lang.ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		finally { try {
          			store.close();
      		} catch (com.borland.dx.dataset.DataSetException dse) {
          			dse.printStackTrace();
          		}
        		} }
	public static void main(String[] args) {
		if (args.length == 2) {
			printBackwards(args[0], args[1]);
		}
	}
}