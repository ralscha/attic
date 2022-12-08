package ch.rasc.sqrl.tree;

import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import ch.rasc.sqrl.tree.RandomTree;

class RandomTreeTest {

	@Test
	void testNut() {
		RandomTree tree = new RandomTree(16);
		String nut = tree.nut();
		Assertions.assertEquals(16, Base64.getUrlDecoder().decode(nut).length);
	}
	
	@Test
	void testNutUnique() {
		RandomTree tree = new RandomTree(16);
		Set<String> nuts = new LinkedHashSet<>();
		for (int i = 0; i < 10; i++) {
			String nut = tree.nut();
			if (nuts.contains(nut)) {
				Assertions.fail("Found duplicate");
			}
			nuts.add(nut);
		}
	}
}
