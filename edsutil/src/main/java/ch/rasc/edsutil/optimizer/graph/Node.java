/**
 * Copyright 2013-2016 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.edsutil.optimizer.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.io.Resource;

public class Node {

	private final Resource resource;

	private final Set<Node> edges = new HashSet<>();

	public Node(Resource resource) {
		this.resource = resource;
	}

	public void addEdge(Node edge) {
		this.edges.add(edge);
	}

	public void removeEdge(Node edge) {
		this.edges.remove(edge);
	}

	public Resource getResource() {
		return this.resource;
	}

	public Set<Node> getEdges() {
		return Collections.unmodifiableSet(this.edges);
	}

	@Override
	public String toString() {
		return "Node [" + this.resource.getDescription() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result
				+ (this.resource == null ? 0 : this.resource.getDescription().hashCode());

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
		if (this.resource == null) {
			if (other.resource != null) {
				return false;
			}
		}
		else if (!this.resource.getDescription()
				.equals(other.resource.getDescription())) {
			return false;
		}

		return true;
	}

}
