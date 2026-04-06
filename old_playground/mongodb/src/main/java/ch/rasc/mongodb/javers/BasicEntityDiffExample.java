package ch.rasc.mongodb.javers;

import java.util.List;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.changelog.SimpleTextChangeLog;
import org.javers.core.diff.Change;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.repository.mongo.MongoRepository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class BasicEntityDiffExample {
	public static void main(String[] args) {
		try (MongoClient mongoClient = new MongoClient("localhost")) {
			MongoDatabase mongoDb = mongoClient.getDatabase("testjavers");

			MongoRepository mongoRepo = new MongoRepository(mongoDb);
			Javers javers = JaversBuilder.javers().registerJaversRepository(mongoRepo)
					.build();

			// init your data
			Person robert = new Person("bob", "Robert Martin");
			// and persist initial commit
			javers.commit("sr", robert);

			// do some changes
			robert.setName("Robert C.");
			// and persist another commit
			javers.commit("sr", robert);

			// when:
			List<CdoSnapshot> snapshots = javers.findSnapshots(
					QueryBuilder.byInstanceId("bob", Person.class).build());

			for (CdoSnapshot e : snapshots) {
				System.out.println(e);
			}

			List<Change> changes = javers
					.findChanges(QueryBuilder.byInstanceId("bob", Person.class).build());
			String changeLog = javers.processChangeList(changes,
					new SimpleTextChangeLog());
			System.out.println(changeLog);
		}
	}
}