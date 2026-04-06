package ch.rasc.autovalue;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Animal {
	public static Animal create(String name, int numberOfLegs) {
		return new AutoValue_Animal(name, numberOfLegs);
	}

	abstract String name();

	abstract int numberOfLegs();
}