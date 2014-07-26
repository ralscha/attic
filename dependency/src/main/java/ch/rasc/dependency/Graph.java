package ch.rasc.dependency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {

	private final Set<Node> nodes = new HashSet<>();

	public Node createNode(String name) {
		Node newNode = new Node(name);
		nodes.add(newNode);
		return newNode;
	}

	public List<Node> resolveDependencies() throws CircularReferenceException {
		List<Node> resolved = new ArrayList<>();
		Set<Node> unresolved = new HashSet<>();
		for (Node node : nodes) {
			if (!resolved.contains(node)) {
				depResolve(node, resolved, unresolved);
			}
		}
		return resolved;
	}

	private void depResolve(Node node, List<Node> resolved, Set<Node> unresolved)
			throws CircularReferenceException {
		unresolved.add(node);
		for (Node edge : node.getEdges()) {
			if (!resolved.contains(edge)) {
				if (unresolved.contains(edge)) {
					throw new CircularReferenceException(node, edge);
				}
				depResolve(edge, resolved, unresolved);
			}
		}
		resolved.add(node);
		unresolved.remove(node);
	}
}
