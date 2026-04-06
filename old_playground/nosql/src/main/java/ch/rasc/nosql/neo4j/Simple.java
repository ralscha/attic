package ch.rasc.nosql.neo4j;

import java.nio.file.Paths;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Simple {

	public static void main(String[] args) {

		GraphDatabaseService graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase(Paths.get("simple").toFile());
		try (Transaction tx = graphDb.beginTx()) {

			Node john = graphDb.createNode();
			john.setProperty("name", "John");

			Node sara = graphDb.createNode();
			sara.setProperty("name", "Sara");

			Node joe = graphDb.createNode();
			joe.setProperty("name", "Joe");

			Node maria = graphDb.createNode();
			maria.setProperty("name", "Maria");

			Node steve = graphDb.createNode();
			steve.setProperty("name", "Steve");

			john.createRelationshipTo(sara, SimpleRelTypes.FRIEND);
			john.createRelationshipTo(joe, SimpleRelTypes.FRIEND);
			sara.createRelationshipTo(maria, SimpleRelTypes.FRIEND);
			joe.createRelationshipTo(steve, SimpleRelTypes.FRIEND);

			tx.success();
		}

		graphDb.shutdown();

	}

}
