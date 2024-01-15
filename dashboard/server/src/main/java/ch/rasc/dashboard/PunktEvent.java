package ch.rasc.dashboard;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@JsonInclude(Include.NON_NULL)
@JsonSerialize(as = ImmutablePunktEvent.class)
@JsonDeserialize(as = ImmutablePunktEvent.class)
public interface PunktEvent {
	long spieler();
	int ries();
	int punkt();
	boolean nr();
}
