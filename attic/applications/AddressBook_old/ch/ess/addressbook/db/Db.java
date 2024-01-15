package ch.ess.addressbook.db;

public class Db {

	private Db() { }

	private static MaxidsTable maxidsTable;
	private static ContactTable contactTable;

	public static MaxidsTable getMaxidsTable() {
		if (maxidsTable == null)
			maxidsTable = new MaxidsTable();

		return maxidsTable;
	}

	public static ContactTable getContactTable() {
		if (contactTable == null)
			contactTable = new ContactTable();

		return contactTable;
	}

}
