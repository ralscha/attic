package ch.rasc.springmongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoApp {
	private static final Logger log = LoggerFactory.getLogger(MongoApp.class);

	public static void main(String[] args) throws Exception {

		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);

		Person p = new Person("Joe", 34);
		// Insert is used to initially store the object into the database.
		mongoOps.insert(p);
		log.info("Insert: " + p);

		// Find
		p = mongoOps.findById(p.getId(), Person.class);
		log.info("Found: " + p);

		// Update
		mongoOps.updateFirst(query(where("name").is("Joe")), update("age", 35),
				Person.class);
		p = mongoOps.findOne(query(where("name").is("Joe")), Person.class);
		log.info("Updated: " + p);

		// Delete
		mongoOps.remove(p);
		// Check that deletion worked
		List<Person> people = mongoOps.findAll(Person.class);
		log.info("Number of people = : " + people.size());

		mongoOps.dropCollection(Person.class);

	}
}