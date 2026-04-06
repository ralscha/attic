package ch.rasc.retrofit;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize(as = ImmutableIp.class)
public interface Ip {
	String origin();
}