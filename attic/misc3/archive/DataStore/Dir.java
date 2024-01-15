import com.borland.datastore.*;

public class Dir {
	public static void print(String storeFileName) {
		DataStoreConnection store = new DataStoreConnection();
		com.borland.dx.dataset.TableDataSet storeDir;
		try {
			store.setFileName(storeFileName);
			store.open();
			storeDir = (com.borland.dx.dataset.TableDataSet) store.openDirectory();
			while (storeDir.inBounds()) {

				short dirVal = storeDir.getShort(DataStore.DIR_TYPE);
				if ((dirVal & DataStore.TABLE_STREAM) != 0) {
					System.out.print("T");
				} else if ((dirVal & DataStore.FILE_STREAM) != 0) {
					System.out.print("F");
				} else {
					System.out.print("?");
				}
				System.out.print(" ");

				System.out.println(storeDir.getString(DataStore.DIR_STORE_NAME));
				storeDir.next();
			}
			store.closeDirectory();
		} catch (com.borland.dx.dataset.DataSetException dse) {
			dse.printStackTrace();
		}
		finally { try {
          			store.close();
      		} catch (com.borland.dx.dataset.DataSetException dse) {
          			dse.printStackTrace();
          		}
        		} }

	public static void main(String[] args) {
		if (args.length > 0) {
			print(args[0]);
		}
	}
}