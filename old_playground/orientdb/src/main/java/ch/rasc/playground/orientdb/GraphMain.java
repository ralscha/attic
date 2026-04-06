package ch.rasc.playground.orientdb;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

public class GraphMain {

	public static void main(String[] args) throws Exception {
		OrientGraphFactory factory = new OrientGraphFactory("plocal:./test/graphdb");
		// OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/testdb",
		// "root", "root");

		// OrientGraph graph = factory.getTx();
		OrientGraphNoTx graph = factory.getNoTx();
		try {

			// Create a vertex
			// Vertex v = graph.addVertex(null);
			// System.out.println("Created vertex: " + v.getId());

			// Create an edge
			// Vertex luca = graph.addVertex(null);
			// luca.setProperty("name", "Luca");
			//
			// Vertex marko = graph.addVertex(null);
			// marko.setProperty("name", "Marko");
			//
			// Edge lucaKnowsMarko = graph.addEdge(null, luca, marko, "knows");
			// System.out.println("Created edge: " + lucaKnowsMarko.getId());

			// retrieve all vertices
			// for (Vertex v : graph.getVertices()) {
			// System.out.println(v);
			// }
			//
			// // retrieve all edges
			// for (Edge e : graph.getEdges()) {
			// System.out.println(e);
			// }

			graph.begin();

			Vertex v = graph.addVertex("class:Customer", "name", "Jill", "age", 33,
					"city", "Rome", "born", "Victoria, TX");
			// graph.createKeyIndex("name", Vertex.class, new Parameter("class",
			// "Customer"));

			graph.commit();

			graph.begin();
			for (Vertex x : graph.getVertices("Customer.name", "Jill")) {
				System.out.println(x);
			}

			for (Vertex c : graph.getVerticesOfClass("Customer")) {
				System.out.println((String) c.getProperty("name"));
			}

			graph.commit();
		}
		finally {
			graph.shutdown();
		}
	}

}
