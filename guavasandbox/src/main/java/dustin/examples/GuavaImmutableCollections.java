package dustin.examples;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

/**
 * Class that demonstrates Guava's support of immutable collections.
 *
 * @author Dustin
 */
public class GuavaImmutableCollections {
	/**
	 * Build a sample set to be used in demonstrations.
	 *
	 * @return Sample set of Strings.
	 */
	public Set<String> buildUnderlyingSampleSet() {
		final Set<String> strings = new HashSet<>();
		strings.add("Dustin");
		strings.add("Java");
		strings.add("College Football");
		return strings;
	}

	/**
	 * Build a sample list to be used in demonstrations.
	 *
	 * @return Sample list of Strings.
	 */
	public List<String> buildUnderlyingSampleList() {
		final List<String> gStrings = new ArrayList<>();
		gStrings.add("Guava");
		gStrings.add("Groovy");
		gStrings.add("Grails");
		gStrings.add("Gradle");
		gStrings.add("Grape");
		return gStrings;
	}

	/**
	 * Demonstrate Guava's ImmutableSet.
	 */
	public void demoGuavaImmutableSet() {
		printHeader("Guava's ImmutableSet");
		final Set<String> originalStrings = buildUnderlyingSampleSet();
		final ImmutableSet<String> strings = ImmutableSet.copyOf(originalStrings);
		out.println("Immutable Set of Strings: " + strings);
		originalStrings.remove("Java");
		out.println("Original Set of Strings: " + originalStrings);
		out.println("Immutable Set of Strings: " + strings);
	}

	/**
	 * Demonstrate JDK's UnmodifiableSet.
	 */
	public void demoJdkUnmodifiableSet() {
		printHeader("JDK unmodifiableSet");
		final Set<String> originalStrings = buildUnderlyingSampleSet();
		final Set<String> strings = Collections.unmodifiableSet(originalStrings);
		out.println("Unmodifiable Set of Strings: " + strings);
		originalStrings.remove("Java");
		out.println("Original Set of Strings: " + originalStrings);
		out.println("Unmodifiable Set of Strings: " + strings);
	}

	/**
	 * Demonstrate Guava's ImmutableList.
	 */
	public void demoGuavaImmutableList() {
		printHeader("Guava's ImmutableList");
		final List<String> originalStrings = buildUnderlyingSampleList();
		final ImmutableList<String> strings = ImmutableList.copyOf(originalStrings);
		out.println("Immutable List of Strings: " + strings);
		originalStrings.remove("Groovy");
		out.println("Original List of Strings: " + originalStrings);
		out.println("Immutable List of Strings: " + strings);
	}

	/**
	 * Demonstrate JDK's UnmodifiableList.
	 */
	public void demoJdkUnmodifiableList() {
		printHeader("JDK unmodifiableList");
		final List<String> originalStrings = buildUnderlyingSampleList();
		final List<String> strings = Collections.unmodifiableList(originalStrings);
		out.println("Unmodifiable List of Strings: " + strings);
		originalStrings.remove("Groovy");
		out.println("Original List of Strings: " + originalStrings);
		out.println("Unmodifiable List of Strings: " + strings);
	}

	/**
	 * Demonstrate Guava's ImmutableMap. Uses ImmutableMap.builder().
	 */
	public void demoGuavaImmutableMap() {
		printHeader("Guava's ImmutableMap");
		final Map<String, String> originalStringsMapping = new HashMap<>();
		originalStringsMapping.put("D", "Dustin");
		originalStringsMapping.put("G", "Guava");
		originalStringsMapping.put("J", "Java");
		final ImmutableMap<String, String> strings = ImmutableMap
				.<String, String> builder().putAll(originalStringsMapping).build();
		out.println("Immutable Map of Strings: " + strings);
		originalStringsMapping.remove("D");
		out.println("Original Map of Strings: " + originalStringsMapping);
		out.println("Immutable Map of Strings: " + strings);
	}

	/**
	 * Demonstrate JDK's UnmodifiableMap.
	 */
	public void demoJdkUnmodifiableMap() {
		printHeader("JDK unmodifiableMap");
		final Map<String, String> originalStringsMapping = new HashMap<>();
		originalStringsMapping.put("D", "Dustin");
		originalStringsMapping.put("G", "Guava");
		originalStringsMapping.put("J", "Java");
		final Map<String, String> strings = Collections
				.unmodifiableMap(originalStringsMapping);
		out.println("Unmodifiable Map of Strings: " + strings);
		originalStringsMapping.remove("D");
		out.println("Original Map of Strings: " + originalStringsMapping);
		out.println("Unmodifiable Map of Strings: " + strings);
	}

	/**
	 * Demonstrate using Builders to build up Guava immutable collections.
	 */
	public void demoGuavaBuilders() {
		printHeader("Guava's Builders");

		final ImmutableMap<String, String> languageStrings = ImmutableMap
				.<String, String> builder().put("C", "C++").put("F", "Fortran")
				.put("G", "Groovy").put("J", "Java").put("P", "Pascal").put("R", "Ruby")
				.put("S", "Scala").build();
		out.println("Languages Map: " + languageStrings);

		final ImmutableSet<String> states = ImmutableSet.<String> builder()
				.add("Arizona").add("Colorado").add("Wyoming").build();
		out.println("States: " + states);

		final ImmutableList<String> cities = ImmutableList.<String> builder()
				.add("Boston").add("Colorado Springs").add("Denver").add("Fort Collins")
				.add("Salt Lake City").add("San Francisco").add("Toledo").build();
		out.println("Cities: " + cities);

		final ImmutableMultimap<String, String> multimapLanguages = ImmutableMultimap
				.<String, String> builder().put("C", "C").put("C", "C++").put("C", "C#")
				.put("F", "Fortran").put("G", "Groovy").put("J", "Java")
				.put("P", "Pascal").put("P", "Perl").put("P", "PHP").put("P", "Python")
				.put("R", "Ruby").put("S", "Scala").build();
		out.println("Languages: " + multimapLanguages);
	}

	/**
	 * Write a separation header to standard output that includes provided header text.
	 *
	 * @param headerText Text to be used in separation header.
	 */
	public static void printHeader(String headerText) {
		out.println("\n========================================================");
		out.println("== " + headerText);
		out.println("========================================================");
	}

	/**
	 * Main function to demonstrate Guava's immutable collections support.
	 *
	 * @param arguments Command-line arguments; none expected.
	 */
	public static void main(String[] arguments) {
		final GuavaImmutableCollections me = new GuavaImmutableCollections();

		// Compare JDK UnmodifiableSet to Guava's ImmutableSet
		me.demoJdkUnmodifiableSet();
		me.demoGuavaImmutableSet();

		// Compare JDK UnmodifiableList to Guava's ImmutableList
		me.demoJdkUnmodifiableList();
		me.demoGuavaImmutableList();

		// Compare JDK unmodifiableMap to Guava's ImmutableMap
		me.demoJdkUnmodifiableMap();
		me.demoGuavaImmutableMap();

		// Demonstrate using builders to build up Guava Immutable Collections
		me.demoGuavaBuilders();
	}
}
