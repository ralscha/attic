package ch.rasc.dependency;

import java.util.List;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class SimpleTest {

	@Test
	public void simpleTest() throws CircularReferenceException {
		Graph g = new Graph();
		Node a = g.createNode("a");
		Node b = g.createNode("b");
		Node c = g.createNode("c");
		Node d = g.createNode("d");
		Node e = g.createNode("e");

		a.addEdge(b);
		a.addEdge(d);
		b.addEdge(c);
		b.addEdge(e);
		c.addEdge(d);
		c.addEdge(e);

		List<Node> resolved = g.resolveDependencies();

		Assertions.assertThat(resolved).containsExactly(d, e, c, b, a);

	}

	@Test(expected = CircularReferenceException.class)
	public void testThrowCircularReferenceException() throws CircularReferenceException {
		Graph g = new Graph();
		Node a = g.createNode("a");
		Node b = g.createNode("b");
		Node c = g.createNode("c");
		Node d = g.createNode("d");
		Node e = g.createNode("e");

		a.addEdge(b);
		a.addEdge(d);
		b.addEdge(c);
		b.addEdge(e);
		c.addEdge(d);
		c.addEdge(e);
		d.addEdge(b);

		@SuppressWarnings("unused")
		List<Node> resolved = g.resolveDependencies();
	}

}
