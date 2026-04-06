package ch.rasc.playground.orientdb;

import java.util.List;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

import ch.rasc.playground.orientdb.domain.Account;
import ch.rasc.playground.orientdb.domain.Address;
import ch.rasc.playground.orientdb.domain.City;
import ch.rasc.playground.orientdb.domain.Country;

public class ObjectMain {

	public static void main(String[] args) {
		try (OObjectDatabaseTx db = new OObjectDatabaseTx("remote:localhost/testdb")
				.open("root", "root")) {

			// REGISTER THE CLASS ONLY ONCE AFTER THE DB IS OPEN/CREATED
			db.setAutomaticSchemaGeneration(true);
			db.getEntityManager()
					.registerEntityClasses("ch.rasc.playground.orientdb.domain");

			// CREATE A NEW PROXIED OBJECT AND FILL IT
			Account account = db.newInstance(Account.class);
			account.setName("Luke");
			account.setSurname("Skywalker");

			City rome = db.newInstance(City.class, "Rome",
					db.newInstance(Country.class, "Italy"));
			account.getAddresses()
					.add(new Address("Residence", rome, "Piazza Navona, 1"));

			db.save(account);

			// CREATE A NEW OBJECT AND FILL IT
			account = new Account();
			account.setName("Luke");
			account.setSurname("Skywalker");

			rome = new City("Rome", new Country("Italy"));
			account.getAddresses()
					.add(new Address("Residence", rome, "Piazza Navona, 1"));

			// SAVE THE ACCOUNT: THE DATABASE WILL SERIALIZE THE OBJECT AND GIVE THE
			// PROXIED
			// INSTANCE
			account = db.save(account);

			List<Account> accounts = db.query(new OSQLSynchQuery<Account>(
					"select from Account where name like 'L%'"));
			for (Account acc : accounts) {
				Account detached = db.detach(acc, true);
				System.out.println(detached);
				System.out.println(detached.getRid());
			}
		}
	}

}
