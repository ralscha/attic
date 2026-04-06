package ch.rasc.nosql.rethink;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class Pojo {
	public static final RethinkDB r = RethinkDB.r;

	public static void main(String[] args) {
		User u = new User();
		u.setName("John Doe");
		u.setEmail("doe@mail.com");

		try (Connection conn = r.connection().hostname("localhost").port(28015)
				.connect()) {

			r.db("test").tableCreate("users").run(conn);
			InsertResponse o = r.table("users").insert(u).run(conn, InsertResponse.class);
			System.out.println(o);

			Cursor<User> cursor = r.table("users").run(conn, User.class);
			for (User user : cursor) {
				System.out.println(user);
			}

			// r.table("users").delete().run(conn);
		}
	}

}
