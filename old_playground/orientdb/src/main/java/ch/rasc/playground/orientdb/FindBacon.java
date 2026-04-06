package ch.rasc.playground.orientdb;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.index.OIndex;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OResultSet;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.branch.LoopPipe.LoopBundle;

public class FindBacon {
	public static void main(String[] args) {
		OrientGraphFactory factory = new OrientGraphFactory("plocal:/E:/temp/db/moviedb");
		// OrientGraphFactory factory = new
		// OrientGraphFactory("remote:localhost/moviedb","admin", "admin");

		OrientGraphNoTx graphDb = factory.getNoTx();

		final OIndex<?> idx = graphDb.getRawGraph().getMetadata().getIndexManager()
				.getIndex("Actor.actor");
		String target = "Craig, Daniel (I)";
		ORecordId bond = (ORecordId) idx.get(target);

		ORecordId mrBacon = (ORecordId) idx.get("Bacon, Kevin (I)");

		System.out.println(bond);
		System.out.println(mrBacon);

		String s = "select shortestPath(" + mrBacon + "," + bond + ",'BOTH')";
		System.out.println(s);

		OResultSet<ODocument> spath = graphDb.getRawGraph()
				.query(new OSQLSynchQuery<>(s));

		for (ODocument oDocument : spath) {
			System.out.println(oDocument);
			List<ORecordId> ids = oDocument.field("shortestPath");
			for (ORecordId id : ids) {
				System.out.println(id);
			}
		}

		int no = 0;

		String firstActor = null;
		String movie = null;

		long start = System.currentTimeMillis();
		for (Vertex v : (Iterable<Vertex>) graphDb.command(new OCommandSQL(s))
				.execute()) {
			String actor = v.getProperty("actor");
			String title = v.getProperty("title");
			if (actor != null) {
				if (firstActor == null) {
					firstActor = actor;
				}
				else {
					System.out.printf("%s and %s appeared in %s.\n", firstActor, actor,
							movie);
					firstActor = actor;
				}
			}
			else {
				no++;
				movie = title;
			}
		}
		System.out.println(System.currentTimeMillis() - start + " ms");

		System.out.printf("\n%s's Bacon number is %d\n", target, no);

		final OrientVertex sourceVertex = graphDb.getVertex(bond);
		final OrientVertex destinationVertex = graphDb.getVertex(mrBacon);
		start = System.currentTimeMillis();
		List<ORID> orids = findShortestPath(sourceVertex, destinationVertex);
		System.out.println(System.currentTimeMillis() - start + " ms");
		for (ORID orid : orids) {
			System.out.println(orid);
		}

		// final Graph g = TinkerGraphFactory.createTinkerGraph();
		// g.getVertex(1).addEdge("knows", g.getVertex(6));

		OrientVertex v1 = graphDb.getVertex(bond);
		OrientVertex v2 = graphDb.getVertex(mrBacon);
		System.out.println(v1);
		System.out.println(v2);
		Set<Vertex> x = new HashSet<>(Collections.singleton(v1));

		GremlinPipeline<Object, List> pipe = new GremlinPipeline<>(v1).as("x").both()
				.except(x).store(x)
				.loop("x",
						(PipeFunction<LoopBundle<Vertex>, Boolean>) bundle -> !x
								.contains(v2),
						(PipeFunction<LoopBundle<Vertex>, Boolean>) bundle -> bundle
								.getObject().equals(v2))
				.path();

		for (final List path : pipe) {
			System.out.println(path);
		}

		System.out.println("====");

		pipe = new GremlinPipeline<>(v1).as("x").both()
				.loop("x",
						(PipeFunction<LoopBundle<Vertex>, Boolean>) bundle -> !bundle
								.getObject().equals(v2),
						(PipeFunction<LoopBundle<Vertex>, Boolean>) bundle -> bundle
								.getObject().equals(v2))
				.path();

		int pathLength = Integer.MAX_VALUE;
		for (final List path : pipe) {
			if (path.size() <= pathLength) {
				pathLength = path.size();
				System.out.println(path);
			}
			else {
				break;
			}

		}
	}

	private static List<ORID> findShortestPath(final OrientVertex sourceVertex,
			final OrientVertex destinationVertex) {
		final ArrayDeque<OrientVertex> queue = new ArrayDeque<>();
		final Set<ORID> visited = new HashSet<>();
		final Map<ORID, ORID> previouses = new HashMap<>();

		queue.add(sourceVertex);
		visited.add(sourceVertex.getIdentity());

		OrientVertex current;
		while (!queue.isEmpty()) {
			current = queue.poll();

			final Iterable<Vertex> neighbors = current.getVertices(Direction.BOTH);
			for (Vertex neighbor : neighbors) {
				final OrientVertex v = (OrientVertex) neighbor;
				final ORID neighborIdentity = v.getIdentity();

				if (!visited.contains(neighborIdentity)) {

					previouses.put(neighborIdentity, current.getIdentity());

					if (destinationVertex.equals(neighbor)) {
						return computePath(previouses, neighborIdentity);
					}

					queue.offer(v);
					visited.add(neighborIdentity);
				}

			}
		}

		return Collections.emptyList();
	}

	private static List<ORID> computePath(final Map<ORID, ORID> distances,
			final ORID neighbor) {
		final List<ORID> result = new ArrayList<>();

		ORID current = neighbor;
		while (current != null) {
			result.add(0, current);

			current = distances.get(current);
		}

		return result;
	}
}
