package ch.rasc.todo.dto;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

@Value.Style(visibility = ImplementationVisibility.PACKAGE)
@Value.Immutable(copy = false, builder = false)
public interface EntityChangeEvent {

	@Value.Parameter
	EntityChangeType type();

	@Value.Parameter
	String author();

	@Value.Parameter
	Object entity();

	public static EntityChangeEvent of(EntityChangeType type, String author,
			Object entity) {
		return ImmutableEntityChangeEvent.of(type, author, entity);
	}
}
