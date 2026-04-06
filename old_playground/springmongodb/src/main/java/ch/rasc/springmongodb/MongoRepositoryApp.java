package ch.rasc.springmongodb;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MongoRepositoryApp {
	private static final Logger log = LoggerFactory.getLogger(MongoRepositoryApp.class);

	public static void main(String[] args) throws Exception {

		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		PersonRepository personRepository = ctx.getBean(PersonRepository.class);

		// Insert
		Person p = new Person("Joe", 34);
		personRepository.save(p);
		log.info("Insert: " + p);

		// Find
		p = personRepository.findOne(p.getId());
		log.info("Found: " + p);

		// Find with Querydsl
		for (Person person : personRepository
				.findAll(QPerson.person.name.startsWithIgnoreCase("J"))) {
			System.out.println("Found with QueryDSL: " + person);
		}

		// Update
		p.setAge(36);
		personRepository.save(p);
		p = personRepository.findOne(p.getId());
		log.info("Updated: " + p);

		// Delete
		personRepository.delete(p);
		// Check that deletion worked
		List<Person> people = personRepository.findAll();
		log.info("Number of people = : " + people.size());

	}
}