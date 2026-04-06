package org.immutables.samples.free;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface FreeDocument {
	int getId();

	String getName();

	class Builder extends FreeDocument_Builder {
	}
}
