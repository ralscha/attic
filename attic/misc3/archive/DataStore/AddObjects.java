import com.borland.datastore.*;

public class AddObjects {
	
	public static void main(String[] args) {
		DataStoreConnection store = new DataStoreConnection();
		int[] intArray = { 5, 7, 9 };
		java.util.Date date = new java.util.Date();
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("a property", "a value");
		try {
			store.setFileName("Basic.jds");
			store.open();
			store.writeObject("add/create-time", date);
			store.writeObject("add/values", properties);
			store.writeObject("add/array of ints", intArray);
		} catch (com.borland.dx.dataset.DataSetException dse) {
			dse.printStackTrace();
		}
		catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}
		finally { 
			try {
          		store.close();
      		} catch (com.borland.dx.dataset.DataSetException dse) {
          		dse.printStackTrace();
          	}
        } 
	}
}