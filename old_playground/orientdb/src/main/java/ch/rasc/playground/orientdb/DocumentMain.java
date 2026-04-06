package ch.rasc.playground.orientdb;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DocumentMain {

	public static void main(String[] args) {

		try (ODatabaseDocumentTx db = new ODatabaseDocumentTx(
				"plocal:./test/db"/* "remote:localhost/testdb" */);) {
			if (db.exists()) {
				db.open("admin", "admin");
			}
			else {
				db.create();
			}

			// CREATE A NEW DOCUMENT AND FILL IT
			ODocument doc = new ODocument("Person");
			doc.field("name", "Luke");
			doc.field("surname", "Skywalker");
			doc.field("city", new ODocument("City").field("name", "Rome").field("country",
					"Italy"));

			// SAVE THE DOCUMENT
			doc.save();

			for (ODocument person : db.browseClass("Person")) {
				String name = person.field("name");
				System.out.println(person);
				System.out.println(name);
				System.out.println(person.getIdentity());
			}
		}

	}
}
