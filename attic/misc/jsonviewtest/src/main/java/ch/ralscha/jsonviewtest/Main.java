package ch.ralscha.jsonviewtest;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class Main {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {

		Address address = new Address();
		address.setName("addressname");
		
		Bean bean = new Bean();
		bean.setAddress(address);
		bean.setName("name");
		bean.setSsn("abc");

		
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.viewWriter(Views.Public.class);
		System.out.println(writer.writeValueAsString(bean));
		//{"name":"name"}
		
		writer = mapper.viewWriter(Views.ExtendedPublic.class);
		System.out.println(writer.writeValueAsString(bean));
		//{"address":{"name":"addressname"},"name":"name"}
		
		writer = mapper.viewWriter(Views.Internal.class);
		System.out.println(writer.writeValueAsString(bean));
		//{"address":{"name":"addressname"},"name":"name","ssn":"abc"}
		
		
		

	}

}
