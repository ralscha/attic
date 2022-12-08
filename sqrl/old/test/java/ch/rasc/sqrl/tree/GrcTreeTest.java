package ch.rasc.sqrl.tree;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.rasc.sqrl.tree.GrcTree;

class GrcTreeTest {

	@Test
	void testNut() {
		GrcTree tree = new GrcTree(10, new byte[] { 1, 2, 3, 4 });
		String nut = tree.nut();
		Assertions.assertEquals("xi6Qzk1Kmrg", nut);
	}

	@Test
	void testUniqueNut() {
		GrcTree tree = new GrcTree(9, new byte[] { 1, 2, 3, 4 });
		Set<String> nuts = new LinkedHashSet<>();
		for (int i = 0; i < 10; i++) {
			String nut = tree.nut();
			if (nuts.contains(nut)) {
				Assertions.fail("Found duplicate");
			}
			nuts.add(nut);
		}
		Iterator<String> iterator = nuts.iterator();
		iterator.next();
		String nut = iterator.next();
		Assertions.assertEquals("xi6Qzk1Kmrg", nut);

	}
}
