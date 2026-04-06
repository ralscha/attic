package ch.rasc.mongodb.sandbox;

import java.util.Date;

import org.bson.types.ObjectId;

public class Main2 {

	public static void main(String[] args) {
		// List<String> records = new ArrayList<>();
		// records.add("{\"name\": \"johnd\"}");
		// records.add("{\"name\": \"bill\"}");
		// JsonRecord jr = new JsonRecord();
		// jr.setResult(records);
		//
		// ObjectMapper mapper = new ObjectMapper();
		// String result = mapper.writeValueAsString(jr);
		// System.out.println(result);

		Date d = new Date();
		ObjectId oid = ObjectId.createFromLegacyFormat((int) (d.getTime() / 1000), 0, 0);
		System.out.println(oid);
		System.out.println(oid.getDate());

		ObjectId max = new ObjectId("4d46bf1fffffffffffffffff");
		System.out.println(max.getDate());
		// new line
		// a second line
	}

}
