package ch.rasc.nosql.neo4j;

import java.nio.file.Paths;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.impl.StandardExpander;
import org.neo4j.graphdb.index.Index;

public class Find {

	public static void main(String[] args) {
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase(Paths.get("e:\\temp\\neo4j\\moviedb").toFile());
		try (Transaction tx = graphDb.beginTx()) {
			Index<Node> index = graphDb.index().forNodes("myIndex");

			Node kevinBaconNode = index.get("actor", "Bacon, Kevin (I)").getSingle();

			String actorName = "Craig, Daniel (I)";

			// String actorName = "McAvoy, James";
			Node actorNode = index.get("actor", actorName).getSingle();

			PathExpander<RelationshipType> expander = StandardExpander
					.create(RelTypes.ACTS_IN, Direction.BOTH);
			PathFinder<Path> finder = GraphAlgoFactory.shortestPath(expander, 10);

			for (Path path : finder.findAllPaths(actorNode, kevinBaconNode)) {

				System.out.printf("%s's Bacon number is %d\n", actorName,
						path.length() / 2);

				String movieTitle = null;
				String prevActor = null;
				for (Relationship rel : path.relationships()) {
					Node start = rel.getStartNode();
					Node end = rel.getEndNode();

					if (movieTitle == null) {
						movieTitle = (String) end.getProperty("title");
						prevActor = (String) start.getProperty("actor");
					}
					else {
						System.out.printf("%s and %s appeared in %s.\n", prevActor,
								start.getProperty("actor"), movieTitle);
						movieTitle = null;
						prevActor = null;
					}

				}
				System.out.println("============================");
			}
			tx.success();
		}
		graphDb.shutdown();

	}

}
