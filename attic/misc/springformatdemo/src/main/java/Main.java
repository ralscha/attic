import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {

	public static void main(String[] args) {
		String baseURL = "http://localhost:8080/action/user";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> entity = restTemplate.getForEntity(baseURL + "/findUsersWithString?birthDay={birthDay}",
				String.class, "1965-11-03");
		System.out.println("WITH String");
		System.out.println(entity.getBody());
		System.out.println();

		entity = restTemplate.getForEntity(baseURL + "/findUsersWithLocalDate?birthDay={birthDay}", String.class,
				"1965-11-03");
		System.out.println("WITH LocalDate");
		System.out.println(entity.getBody());
		System.out.println();

		entity = restTemplate.getForEntity(baseURL + "/findUsersWithDate?birthDay={birthDay}", String.class,
				"1965-11-03");
		System.out.println("WITH Date");
		System.out.println(entity.getBody());
		System.out.println();

		entity = restTemplate.getForEntity(baseURL + "/findUsersWithCalendar?birthDay={birthDay}", String.class,
				"1965-11-03");
		System.out.println("WITH Calendar");
		System.out.println(entity.getBody());

	}

}
