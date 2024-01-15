import com.borland.datastore.*;
public class Hello {
	public static void main(String[] args) {
		DataStore store = new DataStore();
		try {
			store.setFileName("Basic.jds");
			if (!new java.io.File(store.getFileName()).exists()) {
				store.create();
				try {
					store.writeObject("hello",
                  					"Hello, DataStore! It's " + new java.util.Date());
				} catch (java.io.IOException ioe) {
					ioe.printStackTrace();
				}

			} else {
				store.open();
				try {
					String s = (String) store.readObject("hello");
					System.out.println(s);
				} catch (com.borland.dx.dataset.DataSetException dse) {
					dse.printStackTrace();
				}
				catch (java.lang.ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}
				catch (java.io.IOException ioe) {
					ioe.printStackTrace();
				}

			}
			store.close();
		} catch (com.borland.dx.dataset.DataSetException dse) {
			dse.printStackTrace();
		}
	}
}