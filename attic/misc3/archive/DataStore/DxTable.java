import com.borland.datastore.*;
import com.borland.dx.dataset.*;

public class DxTable {
	DataStoreConnection store = new DataStoreConnection();
	TableDataSet table = new TableDataSet();
	public void demo() {
		try {
			store.setFileName("Basic.jds");
			table.setStoreName("Accounts");
			table.setStore(store);
			table.open();
			if ( table.getColumns().length == 0 ) {
				createTable();
			}
			
		} catch (DataSetException dse) {
			dse.printStackTrace();
		}
		finally { try {
          			store.close();
          			table.close();
      		} catch (DataSetException dse) {
          			dse.printStackTrace();
          		}
        		} }
        		
	public void createTable() throws DataSetException {
		table.addColumn( "ID" , Variant.INT );
		table.addColumn( "Name" , Variant.STRING );
		table.addColumn( "Update", Variant.TIMESTAMP );
		table.addColumn( "Text" , Variant.INPUTSTREAM );
		table.restructure();
	}
        		
	public static void main(String[] args) {
		new DxTable().demo();
	}
}