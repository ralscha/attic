package ch.ralscha.mongodbbench;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Main {
	public static void main(String[] args) {
		
		Mongo mongo = null;
		
		try {
			Options options = CliFactory.parseArguments(Options.class, args);
								
			mongo = new Mongo(options.getIp());

			
			DataInserter inserter = new DataInserter(mongo, options);

			Stopwatch stopWatch = new Stopwatch();
			stopWatch.start();
			inserter.doIt();
			stopWatch.stop();
			System.out.println("DATA INSERT: " + stopWatch.elapsedMillis() + " ms");
			
			mongo.close();
			mongo = new Mongo(options.getIp());

			//Warm Up
			new DataQuery(mongo, options).call();
			new DataQuery(mongo, options).call();
			
			ExecutorService es = Executors.newFixedThreadPool(options.getNoOfThreads());
			
			List<DataQuery> queries = Lists.newArrayList();
			for(int i = 0; i < options.getNoOfQueryInstances(); i++) {
				queries.add(new DataQuery(mongo, options));
			}
			
			
			
			
			stopWatch.reset();
			stopWatch.start();

			List<Future<Long>> results = es.invokeAll(queries);
			
			long result = 0;
			for (Future<Long> future : results) {
				result += future.get();
			}

			stopWatch.stop();
			System.out.println(result);
			System.out.println("DATA QUERY: " + stopWatch.elapsedMillis() + " ms");
			
			es.shutdown();
			
		} catch (ArgumentValidationException e) {
			System.out.println(e.getMessage());
		} catch (UnknownHostException e) {
			System.out.println(e);
		} catch (MongoException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}			
		}
		
	}

}
