package ch.rasc.smninfo.domain;

import org.immutables.value.Value;

@Value.Immutable
public interface TimeDouble {

	@Value.Parameter
	long epochSeconds();

	@Value.Parameter
	double value();

}
