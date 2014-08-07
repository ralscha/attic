import java.io.IOException;

import wamp.out.WampCallResultMessage;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class M {

	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		U u = new U();
		u.name = "name";
		u.firstName = "firstName";
		u.email = "email";

		WampCallResultMessage msg = new WampCallResultMessage("callid", u);

		System.out.println(mapper.writeValueAsString(msg.serialize()));

	}

}
