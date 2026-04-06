package ch.rasc.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.jooq.gen.Tables;
import ch.rasc.jooq.gen.tables.records.AddressRecord;

@Service
public class Playground implements CommandLineRunner {

	private final DSLContext dsl;

	@Autowired
	public Playground(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Result<Record> result = this.dsl.select().from(Tables.ADDRESS).fetch();

		for (Record r : result) {
			Integer id = r.getValue(Tables.ADDRESS.ID);
			String city = r.getValue(Tables.ADDRESS.CITY);

			System.out.println("ID: " + id + " city: " + city);
		}

		Result<AddressRecord> ar = this.dsl.fetch(Tables.ADDRESS);
		// System.out.println(ar);
		for (AddressRecord record : ar) {
			System.out.println(record.getCity());
		}
		// System.out.println(ar.formatJSON());

		AddressRecord newAddress = new AddressRecord();
		newAddress.attach(this.dsl.configuration());
		newAddress.setCity("a new city");
		newAddress.setFirstname("first");
		newAddress.setLastname("last");
		newAddress.insert();

		System.out.println(newAddress);
	}

}
