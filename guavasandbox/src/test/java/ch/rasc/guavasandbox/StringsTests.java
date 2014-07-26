package ch.rasc.guavasandbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.base.Strings;

public class StringsTests {

	@Test
	public void isNullOrEmptyTests() {
		assertFalse(Strings.isNullOrEmpty("Dustin"));
		assertTrue(Strings.isNullOrEmpty(null));
		assertTrue(Strings.isNullOrEmpty(""));
		assertFalse(Strings.isNullOrEmpty(" "));
	}

	@Test
	public void nullToEmptyTests() {
		assertEquals("", Strings.nullToEmpty(""));
		assertEquals(" ", Strings.nullToEmpty(" "));
		assertEquals("", Strings.nullToEmpty(null));
		assertEquals("test", Strings.nullToEmpty("test"));
	}

	@Test
	public void emptyToNullTests() {
		assertNull(Strings.emptyToNull(""));
		assertEquals(" ", Strings.emptyToNull(" "));
		assertNull(Strings.emptyToNull(null));
		assertEquals("test", Strings.emptyToNull("test"));
	}

	@Test
	public void repeatTests() {
		assertEquals("_____", Strings.repeat("_", 5));
		assertEquals("_ _ _ _ _ ", Strings.repeat("_ ", 5));
	}

	@Test
	public void padStartTests() {
		assertEquals("TEST", Strings.padStart("TEST", 3, '-'));
		assertEquals("TEST", Strings.padStart("TEST", 4, '-'));
		assertEquals("-TEST", Strings.padStart("TEST", 5, '-'));
		assertEquals("--TEST", Strings.padStart("TEST", 6, '-'));
	}

	@Test
	public void padEndTests() {
		assertEquals("TEST", Strings.padEnd("TEST", 3, '-'));
		assertEquals("TEST", Strings.padEnd("TEST", 4, '-'));
		assertEquals("TEST-", Strings.padEnd("TEST", 5, '-'));
		assertEquals("TEST--", Strings.padEnd("TEST", 6, '-'));
	}

}
