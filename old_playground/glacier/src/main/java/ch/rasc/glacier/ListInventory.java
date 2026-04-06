package ch.rasc.glacier;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class ListInventory {

	public static void main(String[] args) {
		DB db = DBMaker.newFileDB(new File("glacierdb")).closeOnJvmShutdown().make();
		ConcurrentNavigableMap<Long, String> files = db.getTreeMap("glacier");

		for (Long ts : files.keySet()) {
			Date d = new Date(ts);
			System.out.printf("%1$tY:%1$tm:%1$te %1$tH:%1$tM:%1$tS --> %2$s\n", d,
					files.get(ts));
		}

		db.close();
	}
}
