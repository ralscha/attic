package ch.ralscha.mongodbbench;

import uk.co.flamingpenguin.jewel.cli.CommandLineInterface;
import uk.co.flamingpenguin.jewel.cli.Option;

@CommandLineInterface(application = "mongodbbench")
public interface Options {
	//./bin/run.sh org.eatbacon.mongodb.benchmark.TestSuite -h localhost -c test1 -d test -l 100000 -S 1000000

	@Option(shortName = "m", longName = "mongo", description = "IP Address to MongoDB Server")
	String getIp();

	@Option(shortName = "c", longName = "collection", description = "Name of the Collection")
	String getCollection();

	@Option(shortName = "d", longName = "database", description = "Name of the Database")
	String getDatabase();

	@Option(shortName = "n", longName = "documents", description = "Number of Documents")
	int getNoOfDocuments();

	@Option(shortName = "q", longName = "queries", description = "Number of Queries")
	int getNoOfQueries();

	@Option(shortName = "t", longName = "threads", description = "Number of Threads")
	int getNoOfThreads();
	
	@Option(shortName = "i", longName = "queryinstances", description = "Number of Query Instances")
	int getNoOfQueryInstances();
	
}