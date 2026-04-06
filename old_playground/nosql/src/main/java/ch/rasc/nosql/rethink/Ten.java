package ch.rasc.nosql.rethink;

import java.util.concurrent.TimeoutException;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class Ten {

	public static final RethinkDB r = RethinkDB.r;

	public static void main(String[] args) throws TimeoutException {
		try (Connection conn = r.connection().hostname("localhost").port(28015)
				.connect()) {

			// createTable(conn);
			// insertData(conn);
			readData(conn);
			readWithFilter(conn);
			readWithPrimaryKey(conn);

			Cursor cursor = r.table("authors").changes().run(conn);
			while (cursor.hasNext()) {
				System.out.println(cursor.next(1000));
			}
			// for (Object doc : cursor) {
			// System.out.println(doc);
			// }
			System.out.println("the end");
		}
	}

	private static void readWithPrimaryKey(Connection conn) {
		Object result = r.db("test").table("authors")
				.get("7644aaf2-9928-4231-aa68-4e65e31bf219").run(conn);
		System.out.println(result);
	}

	private static void readWithFilter(Connection conn) {
		Cursor cursor = r.table("authors")
				.filter(row -> row.g("name").eq("William Adama")).run(conn);
		for (Object doc : cursor) {
			System.out.println(doc);
		}

		System.out.println();

		cursor = r.table("authors").filter(row -> row.g("posts").count().gt(2)).run(conn);
		for (Object doc : cursor) {
			System.out.println(doc);
		}
	}

	private static void readData(Connection conn) {
		Cursor cursor = r.table("authors").run(conn);
		for (Object doc : cursor) {
			System.out.println(doc);
		}
	}

	private static void insertData(Connection conn) {
		r.table("authors")
				.insert(r.array(
						r.hashMap("name", "William Adama")
								.with("tv_show", "Battlestar Galactica")
								.with("posts", r.array(r.hashMap(
										"title", "Decommissioning speech")
										.with("content", "The Cylon War is long over..."),
										r.hashMap("title", "We are at war").with(
												"content",
												"Moments ago, this ship received..."),
										r.hashMap("title", "The new Earth").with(
												"content",
												"The discoveries of the past few days..."))),
						r.hashMap("name", "Laura Roslin")
								.with("tv_show", "Battlestar Galactica")
								.with("posts", r.array(
										r.hashMap("title", "The oath of office").with(
												"content", "I, Laura Roslin, ..."),
										r.hashMap("title",
												"They look like us").with("content",
														"The Cylons have the ability..."))),
						r.hashMap("name", "Jean-Luc Picard")
								.with("tv_show", "Star Trek TNG").with("posts",
										r.array(r.hashMap("title", "Civil rights").with(
												"content",
												"There are some words I've known since...")))))
				.run(conn);
	}

	private static void createTable(Connection conn) {
		r.db("test").tableCreate("authors").run(conn);
	}

}
