package ch.rasc.springplayground.tx;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

//@Service
public class InitService {

	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	@Transactional
	public void init() {
		System.out.println("INSIDE init()");
		Customer c = new Customer();
		c.setEmail("test@test.com");
		c.setFirstName("John");
		c.setName("Doe");

		Address a = new Address();
		a.setCity("city");
		a.setCountry("CH");
		a.setPostalCode("po");
		a.setStreet("aStreet");
		a.setType(Address.Type.HOME);

		a.setCustomer(c);
		c.getAddresses().add(a);

		this.entityManager.persist(c);
	}
}
