package ch.rasc.dependency;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Node {

	private final String name;

	private final Set<Node> edges = new HashSet<>();

	public Node(String name) {
		this.name = name;
	}

	public void addEdge(Node edge) {
		edges.add(edge);
	}

	public void removeEdge(Node edge) {
		edges.remove(edge);
	}

	public String getName() {
		return name;
	}

	public Set<Node> getEdges() {
		return Collections.unmodifiableSet(edges);
	}

	@Override
	public String toString() {
		return "Node [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Node other = (Node) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
